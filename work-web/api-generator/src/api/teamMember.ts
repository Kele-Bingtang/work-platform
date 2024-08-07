import http from "@/config/request";

export namespace TeamMember {
  export interface TeamMemberInfo {
    id: number; // 主键
    userId: string; // 用户 ID
    username: string; // 用户名
    nickname: string; // 团队内昵称
    teamRole: number; // 团队角色（1 所有者 2 管理员 3 普通成员）
    belongType: number; // 1 团队创建者 2 团队加入者
    teamId: string; // 团队 ID
  }

  export interface TeamMemberInfoWithProjectRole {
    teamMember: { teamRole: string; nickname: string; teamId: string };
    projectMemberRole: { id: number; projectRole: string };
  }
}

const baseUri = "/teamMember";

export const listMembers = (params: Partial<TeamMember.TeamMemberInfo>) => {
  return http.get<http.Response<TeamMember.TeamMemberInfo[]>>(`${baseUri}/listAll`, params);
};

export const listMembersPage = (params: Partial<TeamMember.TeamMemberInfo>) => {
  return http.get<http.Response<TeamMember.TeamMemberInfo[]>>(`${baseUri}/listPage`, params);
};

export const leaveTeam = (teamId: string) => {
  return http.delete<http.Response<Boolean>>(`${baseUri}/${teamId}`);
};

export const editTeamMember = (data: TeamMember.TeamMemberInfoWithProjectRole) => {
  return http.put<http.Response<Boolean>>(baseUri, data);
};
export const inviteMembers = (data: TeamMember.TeamMemberInfo[], inviteUserId: string) => {
  return http.post<http.Response<Boolean>>(`${baseUri}/inviteMembers`, data, { params: { inviteUserId } });
};
