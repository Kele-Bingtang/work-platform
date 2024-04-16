import http from "@/config/request";

export namespace UserGroup {
  // 用户组基本信息
  export interface UserGroupInfo {
    id: number; // 主键
    groupId: string; // 用户组 ID
    groupName: string; // 用户组名
    ownerId: string; // 负责人 ID
    ownerName: string; // 负责人 username
    intro: string; // 用户组描述
    appId: string; // 应用 ID
    createTime: string; // 创建时间
  }

  // 用户组关联用户信息（多个用户）
  export interface UserGroupLinkUser {
    id: number;
    linkId: number; // 关联 ID
    userIds: string[]; // 用户组 ID
    userGroupId: string; // 用户组名
    validFrom: string; // 负责人 ID
    expireOn: string; // 负责人 username
    appId: string; // 应用 ID
  }

  // 角色被关联数据，如被用户关联、被用户组关联
  export interface UserGroupLinkInfo {
    id: number;
    groupId: string; // 用户组 ID
    groupName: string; // 用户组名
    linkId: number; // 关联 ID
    expireOn: string; // 生效时间
    validFrom: string; // 过期时间
    appId: string; // 应用 ID
    createTime: string; // 创建时间
  }

  // 用户组关联角色信息（多个角色）
  export interface UserGroupLinkRole {
    id: number;
    userGroupId: string; // 用户组 ID
    roleIds: string[]; // 角色 ID
    appId: string; // 应用 ID
  }

  // 用户组穿梭框数据，如果 disabled 为 true，则禁选
  export interface UserGroupBindSelect {
    groupId: string;
    groupName: string;
    disabled: boolean;
  }
}

const baseUri = "/system/userGroup";

export const list = (params: Partial<UserGroup.UserGroupInfo>) => {
  return http.get<http.Response<UserGroup.UserGroupInfo[]>>(`${baseUri}/list`, params);
};

export const listPage = (params: Partial<UserGroup.UserGroupInfo>) => {
  return http.get<http.Page<UserGroup.UserGroupInfo[]>>(`${baseUri}/listPage`, params);
};

/**
 * 查询某个用户所在的用户组列表
 */
export const listUserGroupByUserId = (params: { appId: string; userId: string }) => {
  return http.get<http.Response<UserGroup.UserGroupLinkInfo[]>>(
    `${baseUri}/listUserGroupByUserId/${params.appId}/${params.userId}`
  );
};

/**
 * 查询某个角色绑定的用户组列表
 */
export const listUserGroupByRoleId = (params: { roleId: string }) => {
  return http.get<http.Response<UserGroup.UserGroupLinkInfo[]>>(`${baseUri}/listUserGroupByRoleId/${params.roleId}`, {
    ...params,
    roleId: undefined,
  });
};

/**
 * 查询所有用户组列表，如果用户组存在用户，则 disabled 属性为 true
 */
export const listWithDisabledByUserId = (params: { appId: string; userId: string }) => {
  return http.get<http.Response<UserGroup.UserGroupBindSelect[]>>(
    `${baseUri}/listWithDisabledByUserId/${params.appId}/${params.userId}`
  );
};

/**
 * 查询所有用户组列表，如果用户组绑定角色，则 disabled 属性为 true
 */
export const listWithDisabledByRoleId = (params: { roleId: string }) => {
  return http.get<http.Response<UserGroup.UserGroupBindSelect[]>>(
    `${baseUri}/listWithDisabledByRoleId/${params.roleId}`
  );
};

export const addOne = (data: UserGroup.UserGroupInfo) => {
  return http.post<http.Response<string>>(baseUri, data);
};

/**
 * 添加角色到用户组（多个角色）
 */
export const addRolesToUserGroup = (data: UserGroup.UserGroupLinkRole) => {
  return http.post<http.Response<string>>(`${baseUri}/addRolesToUserGroup`, data);
};

/**
 * 添加用户到用户组（多个用户）
 */
export const addUsersToGroup = (data: UserGroup.UserGroupLinkUser) => {
  return http.post<http.Response<string>>(`${baseUri}/addUsersToGroup`, data);
};

export const editOne = (data: RequiredKeyPartialOther<UserGroup.UserGroupInfo, "id">) => {
  return http.put<http.Response<string>>(baseUri, data);
};

/**
 * 修改用户组和用户䣌关联信息
 */
export const editUserGroupLinkInfo = (data: RequiredKeyPartialOther<UserGroup.UserGroupLinkInfo, "id">) => {
  return http.put<http.Response<string>>(`${baseUri}/updateLinkInfo`, data);
};

export const deleteOne = (data: UserGroup.UserGroupInfo) => {
  return http.delete<http.Response<string>>(
    `${baseUri}/${data.id}`,
    {},
    {
      data: [data.groupId],
    }
  );
};

export const deleteBatch = ({ idList, dataList }: { idList: string[]; dataList: UserGroup.UserGroupInfo[] }) => {
  return http.delete<http.Response<string>>(
    `${baseUri}/${idList.join(",")}`,
    {},
    {
      data: dataList.map(item => item.groupId),
    }
  );
};

/**
 * 将用户移出项目组
 */
export const removeUserFromUserGroup = (ids: string[]) => {
  return http.delete<http.Response<string>>(`${baseUri}/removeUserFromUserGroup/${ids.join(",")}`);
};
