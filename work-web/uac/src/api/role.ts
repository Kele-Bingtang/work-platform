import http from "@/config/request";

export namespace Role {
  export interface RoleInfo {
    id: number; // 主键
    roleId: string; // 角色 ID
    roleCode: string; // 角色码
    roleName: string; // 角色名
    status: number; // 状态
    orderNum: string; // 显示顺序
    intro: string; // 角色介绍
    appId: string; // 应用 ID
    createTime: string; // 创建时间
  }
}

const baseUri = "/system/role";

export const listRoleByApp = (params: http.Page<{ appId: number }>) => {
  return http.get<http.Response<Role.RoleInfo[]>>(`${baseUri}/list`, params);
};

export const addOne = (data: Role.RoleInfo) => {
  return http.post<http.Response<string>>(baseUri, data);
};

export const editOne = (data: RequiredKeyPartialOther<Role.RoleInfo, "id">) => {
  return http.put<http.Response<string>>(baseUri, data);
};

export const deleteOne = (data: Role.RoleInfo) => {
  return http.delete<http.Response<string>>(`${baseUri}/${data.id}`);
};

export const deleteBatch = (ids: string[]) => {
  return http.delete<http.Response<string>>(`${baseUri}/${ids.join(",")}`);
};
