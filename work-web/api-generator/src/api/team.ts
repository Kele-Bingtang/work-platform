import http from "@/config/request";

export declare namespace Team {
  export interface TeamMenu {
    teamId: string; // 团队 ID
    teamName: string; // 团队名字
    belongType: number; // 1 团队创建者 2 团队加入者
  }
  export interface TeamInfo {
    id: number; // 主键
    teamId: string; // 团队 ID
    teamName: string; // 团队名字
    description: string; // 团队介绍
    orderNum: string; // 显示顺序
  }

  type TeamSearch = Partial<TeamInfo>;
  type TeamInsert = Omit<TeamInfo, "id" | "teamId">;
  type TeamUpdate = RequiredKeyPartialOther<TeamInfo, "id" | "teamName">;
  type TeamDelete = RequiredKeyPartialOther<TeamInfo, "teamId">;
}

const baseUri = "/team";

export const listMyAllTeamRoute = () => {
  return http.get<http.Response<RouterConfigRaw[]>>(`${baseUri}/listMyAllTeamRoute`);
};

export const addTeam = (data: Team.TeamInsert) => {
  return http.post<http.Response<Boolean>>(baseUri, data);
};

export const editTeam = (data: Team.TeamUpdate) => {
  return http.put<http.Response<Boolean>>(baseUri, data);
};

export const removeTeam = (teamId: Team.TeamDelete) => {
  return http.delete<http.Response<Boolean>>(`${baseUri}/${teamId}`);
};

/**
 * 移交团队负责人
 */
export const transferOwner = (data: { teamId: string; userId: string; username: string }) => {
  return http.post<http.Response<Boolean>>(`${baseUri}/transferOwner/${data.teamId}/${data.userId}/${data.username}`);
};
