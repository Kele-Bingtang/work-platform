import http from "@/config/request";

export namespace Role {
  // 角色基本信息
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
    selectedMenuIds: string[]; // 选中的菜单 ID
  }

  // 角色关联用户组信息（多个用户组）
  export interface RoleLinkUserGroup {
    userGroupIds: string[]; // 用户组 ID
    roleId: string; // 角色 ID
    appId: string; // 应用 ID
  }

  // 角色被关联数据，如被用户关联、被用户组关联
  export interface RoleLinkInfo {
    id: number;
    roleId: string; // 角色 ID
    roleName: string; // 角色名
    roleCode: string; // 角色码
    linkId: number; // 关联 ID
    validFrom: string; // 生效时间
    expireOn: string; // 过期时间
    appId: string; // 应用 ID
    createTime: string; // 创建时间
  }

  // 角色被用户组关联数据（没有生效和过期时间）
  export interface UserGroupLinkRoleVO {
    id: number;
    roleId: string; // 角色 ID
    roleName: string; // 角色名
    roleCode: string; // 角色码
    linkId: number; // 关联 ID
    appId: string; // 应用 ID
    createTime: string; // 创建时间
  }

  // 添加用户到角色（多个用户）
  export interface RoleLinkUser {
    id: number;
    roleId: string[]; // 角色 ID
    userIds: string; // 用户 ID
    validFrom: string; // 负责人 ID
    expireOn: string; // 负责人 username
    appId: string; // 应用 ID
  }

  // 角色穿梭框数据，如果 disabled 为 true，则禁选
  export interface RoleBindSelect {
    roleId: string; // 角色 ID
    roleName: string; // 角色名
    roleCode: string; // 角色码
    disabled: boolean; // 是否禁用
  }
}

const baseUri = "/system/role";

/**
 * 通过条件查询角色列表
 */
export const list = (params: Partial<Role.RoleInfo>) => {
  return http.get<http.Response<Role.RoleInfo[]>>(`${baseUri}/list`, params);
};

/**
 * 通过条件查询角色列表（支持分页）
 */
export const listPage = (params: Partial<Role.RoleInfo>) => {
  return http.get<http.Page<Role.RoleInfo[]>>(`${baseUri}/listPage`, params);
};

/**
 * 查询某个用户所在的角色列表
 */
export const listRoleLinkByUserId = (params: { appId: string; userId: string }) => {
  return http.get<http.Response<Role.RoleLinkInfo[]>>(
    `${baseUri}/listRoleLinkByUserId/${params.appId}/${params.userId}`
  );
};

/**
 * 通过用户组 ID 查询角色列表
 */
export const listRoleLinkByGroupId = (params: { userGroupId: string }) => {
  return http.get<http.Response<Role.RoleLinkInfo[]>>(`${baseUri}/listRoleLinkByGroupId/${params.userGroupId}`, {
    ...params,
    userGroupId: undefined,
  });
};

/**
 * 查询所有角色列表，如果角色绑定了用户，则 disabled 属性为 true
 */
export const listWithDisabledByUserId = (params: { appId: string; userId: string }) => {
  return http.get<http.Response<Role.RoleBindSelect[]>>(
    `${baseUri}/listWithDisabledByUserId/${params.appId}/${params.userId}`
  );
};

/**
 * 查询所有角色列表，如果角色绑定了用户组，则 disabled 属性为 true
 */
export const listWithDisabledByGroupId = (params: { userGroupId: string }) => {
  return http.get<http.Response<Role.RoleBindSelect[]>>(`${baseUri}/listWithDisabledByGroupId/${params.userGroupId}`);
};

/**
 * 新增一个角色
 */
export const addOne = (data: Role.RoleInfo) => {
  return http.post<http.Response<string>>(baseUri, data);
};

/**
 * 添加角色到用户组（多个用户组）
 */
export const addUserGroupsToRole = (data: Role.RoleLinkUserGroup) => {
  return http.post<http.Response<string>>(`${baseUri}/addUserGroupsToRole`, data);
};

/**
 * 添加用户到角色（多个用户）
 */
export const addUsersToRole = (data: Role.RoleLinkUserGroup) => {
  return http.post<http.Response<string>>(`${baseUri}/addUsersToRole`, data);
};

/**
 * 修改一个角色
 */
export const editOne = (data: RequiredKeyPartialOther<Role.RoleInfo, "id">) => {
  return http.put<http.Response<string>>(baseUri, data);
};

/**
 * 修改用户和角色关联信息
 */
export const editUserRoleLinkInfo = (data: RequiredKeyPartialOther<Role.RoleLinkInfo, "id">) => {
  return http.put<http.Response<string>>(`${baseUri}/updateLinkInfo`, data);
};

/**
 * 删除一个角色
 */
export const deleteOne = (data: Role.RoleInfo) => {
  return http.delete<http.Response<string>>(
    `${baseUri}/${data.id}`,
    {},
    {
      data: [data.roleId],
    }
  );
};

/**
 * 通过主键批量删除角色列表
 */
export const deleteBatch = ({ idList, dataList }: { idList: string[]; dataList: Role.RoleLinkInfo[] }) => {
  return http.delete<http.Response<string>>(
    `${baseUri}/${idList.join(",")}`,
    {},
    {
      data: dataList.map(item => item.roleId),
    }
  );
};

/**
 * 将用户移出角色
 */
export const removeUserFromRole = (ids: string[]) => {
  return http.delete<http.Response<string>>(`${baseUri}/removeUserFromRole/${ids.join(",")}`);
};

/**
 * 将用户组移出角色
 */
export const removeUserGroupFromRole = (ids: string[]) => {
  return http.delete<http.Response<string>>(`${baseUri}/removeUserGroupFromRole/${ids.join(",")}`);
};
