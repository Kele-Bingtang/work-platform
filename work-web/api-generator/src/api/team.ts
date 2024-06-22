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
  return http.post<http.Response<Boolean>>(`${baseUri}/addTeam`, data);
};

export const updateTeam = (data: Record<string, any>) => {
  return http.put<http.Response<Boolean>>(`${baseUri}/updateTeam`, data);
};

export const deleteTeam = (id: number) => {
  return http.delete<http.Response<Boolean>>(`${baseUri}/deleteTeam/${id}`);
};
