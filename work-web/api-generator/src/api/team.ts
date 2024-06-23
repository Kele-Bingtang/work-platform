import http from "@/config/request";

export namespace Team {
  export interface TeamMenu {
    teamId: string; // 团队 ID
    teamName: string; // 团队名字
    belongType: number; // 0 团队创建者 1 团队加入者
  }
}

const baseUri = "/team";

export const listMyAllTeamRoute = () => {
  return http.get<http.Response<RouterConfigRaw[]>>(`${baseUri}/listMyAllTeamRoute`);
};

export const addTeam = (data: Record<string, any>) => {
  return http.post<http.Response<Boolean>>(baseUri, data);
};

export const editTeam = (data: Record<string, any>) => {
  return http.put<http.Response<Boolean>>(baseUri, data);
};

export const removeTeam = (teamId: string) => {
  return http.delete<http.Response<Boolean>>(`${baseUri}/${teamId}`);
};

/**
 * 移交团队负责人
 */
export const transferOwner = (data: { teamId: string; userId: string; username: string }) => {
  return http.post<http.Response<Boolean>>(`${baseUri}/transferOwner/${data.teamId}/${data.userId}/${data.username}`);
};
