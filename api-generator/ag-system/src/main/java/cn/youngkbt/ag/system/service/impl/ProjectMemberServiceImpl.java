package cn.youngkbt.ag.system.service.impl;

import cn.youngkbt.ag.system.mapper.ProjectMemberMapper;
import cn.youngkbt.ag.system.model.dto.ProjectMemberDTO;
import cn.youngkbt.ag.system.model.po.ProjectMember;
import cn.youngkbt.ag.system.model.vo.ProjectMemberVO;
import cn.youngkbt.ag.system.service.ProjectMemberService;
import cn.youngkbt.utils.MapstructUtil;
import cn.youngkbt.utils.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2024-06-22 00:15:48
 * @note 针对表「t_project_member（项目成员表）」的数据库操作 Service 实现
 */
@Service
public class ProjectMemberServiceImpl extends ServiceImpl<ProjectMemberMapper, ProjectMember> implements ProjectMemberService {

    @Override
    public List<ProjectMemberVO> listProjectRole(String teamId, String userId) {
        QueryWrapper<ProjectMember> wrapper = Wrappers.<ProjectMember>query()
                .eq("tpm.team_id", teamId)
                .eq("tpm.user_id", userId);

        return baseMapper.listProjectRole(wrapper);
    }

    @Override
    public String listTeamId(String projectId) {
        ProjectMember projectMember = baseMapper.selectOne(Wrappers.<ProjectMember>lambdaQuery()
                .select(ProjectMember::getTeamId)
                .eq(ProjectMember::getProjectId, projectId));
        return projectMember.getTeamId();
    }

    private LambdaQueryWrapper<ProjectMember> buildQueryWrapper(ProjectMemberDTO projectMemberDTO) {
        return Wrappers.<ProjectMember>lambdaQuery()
                .eq(StringUtil.hasText(projectMemberDTO.getTeamId()), ProjectMember::getTeamId, projectMemberDTO.getTeamId())
                .eq(StringUtil.hasText(projectMemberDTO.getUserId()), ProjectMember::getUserId, projectMemberDTO.getUserId())
                .eq(Objects.nonNull(projectMemberDTO.getProjectId()), ProjectMember::getProjectId, projectMemberDTO.getProjectId())
                .eq(Objects.nonNull(projectMemberDTO.getProjectRole()), ProjectMember::getProjectRole, projectMemberDTO.getProjectRole())
                .eq(Objects.nonNull(projectMemberDTO.getBelongType()), ProjectMember::getBelongType, projectMemberDTO.getBelongType())
                .orderByAsc(ProjectMember::getId);
    }

    @Override
    public boolean addProjectMember(ProjectMemberDTO projectMemberDTO) {
        ProjectMember projectMember = MapstructUtil.convert(projectMemberDTO, ProjectMember.class);
        return baseMapper.insert(projectMember) > 0;
    }

    @Override
    public boolean addBatchProjectMember(List<ProjectMemberDTO> projectMemberDTOList) {
        List<ProjectMember> projectMemberList = MapstructUtil.convert(projectMemberDTOList, ProjectMember.class);
        return saveBatch(projectMemberList);
    }

    @Override
    public boolean editProjectMember(ProjectMemberDTO projectMemberDTO) {
        ProjectMember projectMember = MapstructUtil.convert(projectMemberDTO, ProjectMember.class);
        return baseMapper.updateById(projectMember) > 0;
    }

    @Override
    public boolean checkMemberRole(String project, String userId, List<Integer> ordinal) {
        return baseMapper.exists(Wrappers.<ProjectMember>lambdaQuery()
                .eq(ProjectMember::getProjectId, project)
                .eq(ProjectMember::getUserId, userId)
                .in(ProjectMember::getProjectRole, ordinal));
    }

    @Override
    public boolean removeAllMember(String projectId) {
        return baseMapper.delete(Wrappers.<ProjectMember>lambdaQuery()
                .eq(ProjectMember::getProjectId, projectId)) > 0;
    }

    @Override
    public boolean checkMemberUnique(ProjectMemberDTO projectMemberDTO) {
        return baseMapper.exists(Wrappers.<ProjectMember>lambdaQuery()
                .eq(ProjectMember::getTeamId, projectMemberDTO.getTeamId())
                .eq(ProjectMember::getUserId, projectMemberDTO.getUserId())
                .eq(ProjectMember::getProjectId, projectMemberDTO.getProjectId()));
    }
}




