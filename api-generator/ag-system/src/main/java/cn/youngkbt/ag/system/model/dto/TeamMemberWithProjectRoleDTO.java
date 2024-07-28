package cn.youngkbt.ag.system.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2024/7/28 17:55:14
 * @note
 */
@Data
public class TeamMemberWithProjectRoleDTO {
    private TeamMemberDTO teamMember;
    private List<ProjectMemberDTO> projectMemberList;
}
