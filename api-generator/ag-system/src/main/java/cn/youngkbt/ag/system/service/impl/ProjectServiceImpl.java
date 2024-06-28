package cn.youngkbt.ag.system.service.impl;

import cn.youngkbt.ag.core.constant.ColumnConstant;
import cn.youngkbt.ag.core.enums.BelongType;
import cn.youngkbt.ag.core.enums.ProjectMemberRole;
import cn.youngkbt.ag.core.enums.TeamMemberRole;
import cn.youngkbt.ag.core.helper.AgHelper;
import cn.youngkbt.ag.system.mapper.ProjectMapper;
import cn.youngkbt.ag.system.model.dto.CategoryDTO;
import cn.youngkbt.ag.system.model.dto.ProjectDTO;
import cn.youngkbt.ag.system.model.dto.ProjectMemberDTO;
import cn.youngkbt.ag.system.model.dto.TeamMemberDTO;
import cn.youngkbt.ag.system.model.po.Project;
import cn.youngkbt.ag.system.model.vo.ProjectVO;
import cn.youngkbt.ag.system.model.vo.TeamMemberVO;
import cn.youngkbt.ag.system.service.CategoryService;
import cn.youngkbt.ag.system.service.ProjectMemberService;
import cn.youngkbt.ag.system.service.ProjectService;
import cn.youngkbt.ag.system.service.TeamMemberService;
import cn.youngkbt.core.error.Assert;
import cn.youngkbt.core.exception.ServiceException;
import cn.youngkbt.utils.IdsUtil;
import cn.youngkbt.utils.ListUtil;
import cn.youngkbt.utils.MapstructUtil;
import cn.youngkbt.utils.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2024-06-22 00:15:48
 * @note 针对表「t_project（项目表）」的数据库操作 Service 实现
 */
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    private final ProjectMemberService projectMemberService;
    private final CategoryService categoryService;
    private final TeamMemberService teamMemberService;


    @Override
    public ProjectVO listProjectBySecretKey(String secretKey) {
        Project project = baseMapper.selectOne(Wrappers.<Project>lambdaQuery()
                .eq(Project::getSecretKey, secretKey));
        Assert.isTrue(Objects.nonNull(project), "密钥或者项目不存在");
        return MapstructUtil.convert(project, ProjectVO.class);
    }

    @Override
    public List<ProjectVO> listProject(ProjectDTO projectDTO) {
        QueryWrapper<Project> wrapper = buildQueryWrapper(projectDTO);
        List<Project> projectList = baseMapper.listProject(wrapper);

        return MapstructUtil.convert(projectList, ProjectVO.class);
    }

    private LambdaQueryWrapper<Project> buildLambdaQueryWrapper(ProjectDTO projectDTO) {
        return Wrappers.<Project>lambdaQuery()
                .eq(StringUtil.hasText(projectDTO.getTeamId()), Project::getTeamId, projectDTO.getTeamId())
                .eq(StringUtil.hasText(projectDTO.getProjectName()), Project::getProjectName, projectDTO.getProjectName())
                .eq(StringUtil.hasText(projectDTO.getDatabaseName()), Project::getDatabaseName, projectDTO.getDatabaseName())
                .eq(Objects.nonNull(projectDTO.getStatus()), Project::getStatus, projectDTO.getStatus())
                .eq(Project::getIsDeleted, 0)
                .orderByDesc(Project::getCreateTime);
    }

    private QueryWrapper<Project> buildQueryWrapper(ProjectDTO projectDTO) {
        final String projectTablePrefix = "tp.";
        final String projectMemberTablePrefix = "tpm.";
        return Wrappers.<Project>query()
                // project 表条件
                .eq(projectTablePrefix + "team_id", projectDTO.getTeamId())
                .eq(StringUtil.hasText(projectDTO.getProjectName()), projectTablePrefix + "project_name", projectDTO.getProjectName())
                .eq(StringUtil.hasText(projectDTO.getDatabaseName()), projectTablePrefix + "database_name", projectDTO.getDatabaseName())
                .eq(Objects.nonNull(projectDTO.getStatus()), projectTablePrefix + "status", projectDTO.getStatus())
                .eq(projectTablePrefix + "is_deleted", 0)
                .orderByDesc(projectTablePrefix + "create_time")
                // project_member 表条件
                .eq(projectMemberTablePrefix + "user_id", AgHelper.getUserId())
                .eq(Objects.nonNull(projectDTO.getBelongType()) && projectDTO.getBelongType() != 0, projectMemberTablePrefix + "belong_type", projectDTO.getBelongType());

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addProject(ProjectDTO projectDTO) {
        checkProjectAllowed(projectDTO.getTeamId(), projectDTO.getProjectId(), AgHelper.getUserId(), true, false);

        Project project = MapstructUtil.convert(projectDTO, Project.class);
        // 不带 - 的 UUID 作为项目密钥
        project.setSecretKey(IdsUtil.simpleUUID());
        int result = baseMapper.insert(project);

        // 生成默认目录
        CategoryDTO categoryDTO = new CategoryDTO()
                .setCategoryCode(ColumnConstant.DEFAULT_CATEGORY_CODE)
                .setCategoryName(ColumnConstant.DEFAULT_CATEGORY_NAME)
                .setProjectId(project.getProjectId())
                .setIsMain(1)
                .setTeamId(project.getTeamId());

        categoryService.addCategory(categoryDTO);

        // 团队的所有成员都可以看到项目
        List<TeamMemberVO> teamMemberVOList = teamMemberService.listAll(new TeamMemberDTO().setTeamId(project.getTeamId()));

        List<ProjectMemberDTO> projectMemberDTOList = ListUtil.newArrayList(teamMemberVOList, teamMember ->
                        new ProjectMemberDTO()
                                .setProjectId(project.getProjectId())
                                .setUserId(teamMember.getUserId())
                                // 团队所有者 & 管理者默认是项目的管理者，团队普通成员默认是项目的普通成员
                                .setProjectRole(teamMember.getTeamRole() != TeamMemberRole.MEMBER.ordinal() ? ProjectMemberRole.ADMIN.ordinal() : ProjectMemberRole.MEMBER.ordinal())
                                .setBelongType(BelongType.CREATE.ordinal())
                                .setTeamId(teamMember.getTeamId())
                , ProjectMemberDTO.class);

        projectMemberService.addBatchProjectMember(projectMemberDTOList);
        return result > 0;
    }

    @Override
    public boolean editProject(ProjectDTO projectDTO) {
        checkProjectAllowed(projectDTO.getTeamId(), projectDTO.getProjectId(), AgHelper.getUserId(), false, true);

        Project project = MapstructUtil.convert(projectDTO, Project.class);
        return baseMapper.updateById(project) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeProject(String projectId) {
        Project project = baseMapper.selectOne(Wrappers.<Project>lambdaQuery()
                .select(Project::getTeamId)
                .eq(Project::getProjectId, projectId));

        Assert.nonNull(project, "删除的项目不存在");

        checkProjectAllowed(project.getTeamId(), projectId, AgHelper.getUserId(), false, true);

        // 删除项目目录
        categoryService.removeAllCategory(projectId);
        // 删除项目成员
        projectMemberService.removeAllMember(projectId);
        // 删除项目
        return baseMapper.delete(Wrappers.<Project>lambdaQuery()
                .eq(Project::getProjectId, projectId)) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeAllProject(String teamId) {
        // 删除团队下所有项目目录
        categoryService.removeAllCategoryByTeamId(teamId);
        // 删除团队下所有项目成员
        projectMemberService.removeAllMemberByTeamId(teamId);
        
        return baseMapper.delete(Wrappers.<Project>lambdaQuery()
                .eq(Project::getTeamId, teamId)) > 0;
    }

    @Override
    public void checkProjectAllowed(String teamId, String projectId, String userId, boolean checkTeamRole, boolean checkProjectRole) {
        // 检查是否为团队操作人（所有者 | 管理员）
        if (checkTeamRole && teamMemberService.checkMemberRole(teamId, userId, List.of(TeamMemberRole.OWNER.ordinal(), TeamMemberRole.ADMIN.ordinal()))) {
            return;
        }

        // 检查是否为项目管理员
        if (checkProjectRole && projectMemberService.checkMemberRole(projectId, userId, Collections.singletonList(ProjectMemberRole.ADMIN.ordinal()))) {
            return;
        }

        throw new ServiceException("用户没有项目操作权限");
    }

    @Override
    public boolean checkProjectNameUnique(ProjectDTO projectDTO) {
        return baseMapper.exists(Wrappers.<Project>lambdaQuery()
                .eq(Project::getProjectName, projectDTO.getProjectName())
                .eq(Project::getTeamId, projectDTO.getTeamId())
                .ne(Objects.nonNull(projectDTO.getId()), Project::getId, projectDTO.getId()));
    }
}




