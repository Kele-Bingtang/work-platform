package cn.youngkbt.ag.system.service.impl;

import cn.youngkbt.ag.core.enums.BelongType;
import cn.youngkbt.ag.core.enums.ProjectMemberRole;
import cn.youngkbt.ag.core.enums.TeamMemberRole;
import cn.youngkbt.ag.core.helper.AgHelper;
import cn.youngkbt.ag.system.mapper.ProjectMapper;
import cn.youngkbt.ag.system.model.dto.*;
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
        LambdaQueryWrapper<Project> wrapper = buildQueryWrapper(projectDTO);
        String userId = AgHelper.getUserId();

        List<Project> projectList = baseMapper.listProject(projectDTO.getBelongType(), userId, wrapper);

        return MapstructUtil.convert(projectList, ProjectVO.class);
    }

    private LambdaQueryWrapper<Project> buildQueryWrapper(ProjectDTO projectDTO) {
        return Wrappers.<Project>lambdaQuery()
                .eq(StringUtil.hasText(projectDTO.getTeamId()), Project::getTeamId, projectDTO.getTeamId())
                .eq(StringUtil.hasText(projectDTO.getProjectName()), Project::getProjectName, projectDTO.getProjectName())
                .eq(StringUtil.hasText(projectDTO.getDatabaseName()), Project::getDatabaseName, projectDTO.getDatabaseName())
                .eq(Objects.nonNull(projectDTO.getStatus()), Project::getStatus, projectDTO.getStatus())
                .eq(Project::getIsDeleted, 0)
                .orderByAsc(Project::getCreateTime);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addProject(ProjectDTO projectDTO) {
        checkProjectAllowed(projectDTO.getTeamId(), projectDTO.getProjectId(), AgHelper.getUserId());

        Project project = MapstructUtil.convert(projectDTO, Project.class);
        // 不带 - 的 UUID 作为项目密钥
        String simpleUUID = IdsUtil.simpleUUID();
        project.setSecretKey(simpleUUID);
        int result = baseMapper.insert(project);

        // 生成默认目录
        CategoryDTO categoryDTO = new CategoryDTO()
                .setCategoryCode("default")
                .setCategoryName("默认目录")
                .setProjectId(project.getProjectId())
                .setIsMain(1)
                .setTeamId(project.getTeamId());

        categoryService.addCategory(categoryDTO);

        // 添加成员角色 - 创建人为管理员
        ProjectMemberDTO projectMemberDTO = new ProjectMemberDTO()
                .setProjectId(project.getProjectId())
                .setUserId(AgHelper.getUserId())
                .setProjectRole(ProjectMemberRole.ADMIN.ordinal())
                .setBelongType(BelongType.CREATE.ordinal())
                .setTeamId(project.getTeamId());

        // 团队的所有成员都可以看到项目
        List<TeamMemberVO> teamMemberVOList = teamMemberService.listAll(new TeamMemberDTO().setTeamId(project.getTeamId()));

        List<ProjectMemberDTO> projectMemberDTOList = ListUtil.newArrayList(teamMemberVOList, teamMember ->
                        new ProjectMemberDTO()
                                .setProjectId(project.getProjectId())
                                .setUserId(teamMember.getUserId())
                                // 团队所有者 & 管理者默认是项目的管理者，团队普通成员默认是团队的普通成员
                                .setProjectRole(teamMember.getTeamRole() != 3 ? ProjectMemberRole.ADMIN.ordinal() : ProjectMemberRole.MEMBER.ordinal())
                                .setBelongType(BelongType.JOINER.ordinal())
                                .setTeamId(teamMember.getTeamId())
                , ProjectMemberDTO.class);

        projectMemberDTOList.add(projectMemberDTO);
        projectMemberService.addBatchProjectMember(projectMemberDTOList);
        
        return result > 0;
    }

    @Override
    public boolean editProject(ProjectDTO projectDTO) {
        checkProjectAllowed(projectDTO.getTeamId(), projectDTO.getProjectId(), AgHelper.getUserId());

        Project project = MapstructUtil.convert(projectDTO, Project.class);
        return baseMapper.updateById(project) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeProject(String projectId) {
        String teamId = projectMemberService.listTeamId(projectId);
        checkProjectAllowed(teamId, projectId, AgHelper.getUserId());

        // 删除项目的目录
        categoryService.removeAllCategory(projectId);
        // 删除项目成员
        projectMemberService.removeAllMember(projectId);
        // 删除项目
        return baseMapper.delete(Wrappers.<Project>lambdaQuery()
                .eq(Project::getProjectId, projectId)) > 0;
    }

    @Override
    public void checkProjectAllowed(String teamId, String project, String userId) {
        // 检查是否为团队操作人（所有者 | 管理员）
        if (teamMemberService.checkMemberRole(teamId, userId, List.of(TeamMemberRole.OWNER.ordinal(), TeamMemberRole.ADMIN.ordinal()))) {
            return;
        }

        // 检查是否为项目管理员
        if (projectMemberService.checkMemberRole(project, userId, Collections.singletonList(ProjectMemberRole.ADMIN.ordinal()))) {
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




