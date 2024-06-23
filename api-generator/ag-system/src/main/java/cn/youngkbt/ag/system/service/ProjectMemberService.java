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
     * 编辑项目成员
     *
     * @param projectMemberDTO 项目成员信息
     * @return 是否成功
     */
    boolean editProjectMember(ProjectMemberDTO projectMemberDTO);
}
