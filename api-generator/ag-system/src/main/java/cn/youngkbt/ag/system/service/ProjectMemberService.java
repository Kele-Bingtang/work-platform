package cn.youngkbt.ag.system.service;

import cn.youngkbt.ag.system.model.dto.ProjectMemberDTO;
import cn.youngkbt.ag.system.model.po.ProjectMember;
import cn.youngkbt.ag.system.model.vo.ProjectMemberVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024-06-22 00:15:48
 * @note 针对表「t_project_member（项目成员表）」的数据库操作 Service
 */
public interface ProjectMemberService extends IService<ProjectMember> {

    /**
     * 查询项目成员角色
     *
     * @param userId 用户 ID
     * @return 项目成员角色列表
     */
    List<ProjectMemberVO> listProjectRole(String teamId, String userId);

    /**
     * 查询项目所在的 TeamId
     *
     * @param projectId 项目 ID
     * @return 项目所在的 TeamId
     */
    String listTeamId(String projectId);

    /**
     * 添加项目成员
     *
     * @param projectMemberDTO 项目成员信息
     * @return 是否成功
     */
    boolean addProjectMember(ProjectMemberDTO projectMemberDTO);

    /**
     * 批量添加项目成员
     *
     * @param projectMemberDTOList 项目成员信息列表
     * @return 是否成功
     */
    boolean addBatchProjectMember(List<ProjectMemberDTO> projectMemberDTOList);

    /**
     * 编辑项目成员
     *
     * @param projectMemberDTO 项目成员信息
     * @return 是否成功
     */
    boolean editProjectMember(ProjectMemberDTO projectMemberDTO);

    /**
     * 检查成员角色
     *
     * @param project 项目 ID
     * @param userId  用户 ID
     * @param ordinal 角色序号
     * @return 是否成功
     */
    boolean checkMemberRole(String project, String userId, List<Integer> ordinal);

    /**
     * 移除所有成员
     *
     * @param projectId 项目 ID
     * @return 是否成功
     */
    boolean removeAllMember(String projectId);

    /**
     * 检查成员唯一性
     *
     * @param projectMemberDTO 项目成员信息
     * @return 是否成功
     */
    boolean checkMemberUnique(ProjectMemberDTO projectMemberDTO);

}
