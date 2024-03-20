import http from "@/config/request";

export namespace Role {
  export interface RoleInfo {
    id: number; // 主键
    roleId: string; // 角色 ID
    roleCode: string; // 角色码
    roleName: string; // 角色名
    status: number; // 状态
    orderNum: number; // 显示顺序
    intro: string; // 角色介绍
    appId: string; // 应用 ID
    createTime: string; // 创建时间
  }

  export interface UserLinkRole {
    userId: string; // 用户 ID
    roleIds: string[]; // 角色 ID
    validFrom: string; // 负责人 ID
    expireOn: string; // 负责人 username
    appId: string; // 应用 ID
  }

  export interface RoleLinkInfo {
    id: number; // 主键
    roleId: string; // 角色 ID
    roleCode: string; // 角色码
    roleName: string; // 角色名
    status: number; // 状态
    orderNum: number; // 显示顺序
    intro: string; // 角色介绍
    appId: string; // 应用 ID
    createTime: string; // 创建时间
    linkId: number; // UserRoleLink 表的主键
    validFrom: string; // 生效时间
    expireOn: string; // 过期时间
  }
}

const baseUri = "/system/role";

export const list = (params: Partial<Role.RoleInfo>) => {
  return http.get<http.Response<Role.RoleInfo[]>>(`${baseUri}/list`, params);
};

export const listRoleListByUserId = (params: { appId: string; userId: string }) => {
  return http.get<http.Response<Role.RoleLinkInfo[]>>(`${baseUri}/listByUserId/${params.appId}/${params.userId}`);
};

export const listRoleListWithDisabledByUserId = (params: { appId: string; userId: string }) => {
  return http.get<http.Response<Role.RoleInfo[]>>(
    `${baseUri}/listWithDisabledByUserId/${params.appId}/${params.userId}`
  );
};

export const addOne = (data: Role.RoleInfo) => {
  return http.post<http.Response<string>>(baseUri, data);
};

export const addUserToRoles = (data: Role.UserLinkRole) => {
  return http.post<http.Response<string>>(`${baseUri}/addUserToRoles`, data);
};

export const editOne = (data: RequiredKeyPartialOther<Role.RoleInfo, "id">) => {
  return http.put<http.Response<string>>(baseUri, data);
};

export const editUserRoleLinkInfo = (data: RequiredKeyPartialOther<Role.RoleLinkInfo, "id">) => {
  return http.put<http.Response<string>>(`${baseUri}/updateLinkInfo`, data);
};

export const deleteOne = (data: Role.RoleInfo) => {
  return http.delete<http.Response<string>>(`${baseUri}/${data.id}`);
};

export const deleteBatch = (ids: string[]) => {
  return http.delete<http.Response<string>>(`${baseUri}/${ids.join(",")}`);
};

export const removeUserFromRole = (ids: string[]) => {
  return http.delete<http.Response<string>>(`${baseUri}/removeUserFromRole/${ids.join(",")}`);
};
