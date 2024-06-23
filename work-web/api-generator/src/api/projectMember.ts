import http from "@/config/request";

export namespace ProjectMember {
  export interface ProjectMemberInfo {
    id: number; // 主键
    userId: string; // 用户名
    projectId: number; // 项目 ID
    projectName: number; // 项目名称
    projectRole: number; // 项目角色（1 管理员 2 普通成员 3 只读成员 4 禁止访问）
    belongType: number; // 0 项目创建者 1 项目加入者
    teamId: string; // 团队 ID

    createTime: string; // 创建时间
  }
}

const baseUri = "/projectMember";

export const listProjectRole = (teamId: string, userId: string) => {
  return http.get<http.Response<ProjectMember.ProjectMemberInfo[]>>(`${baseUri}/listProjectRole/${teamId}/${userId}`);
};

export const editProjectMember = (data: Partial<ProjectMember.ProjectMemberInfo>) => {
  return http.put<http.Response<Boolean>>(baseUri, data);
};
