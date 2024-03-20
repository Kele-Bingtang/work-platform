import http from "@/config/request";

export namespace UserGroup {
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

  export interface UserLinkUserGroup {
    linkId: number; // 关联 ID
    userId: string; // 用户 ID
    userGroupIds: string[]; // 用户组 ID
    validFrom: string; // 负责人 ID
    expireOn: string; // 负责人 username
    appId: string; // 应用 ID
  }

  export interface UserGroupLinkUser {
    linkId: number; // 关联 ID
    userIds: string[]; // 用户组 ID
    userGroupId: string; // 用户组名
    validFrom: string; // 负责人 ID
    expireOn: string; // 负责人 username
    appId: string; // 应用 ID
  }

  export interface UserGroupList {
    id: number; // 主键
    groupId: string; // 用户组 ID
    groupName: string; // 用户组名
    ownerId: string; // 负责人 ID
    ownerName: string; // 负责人 username
    intro: string; // 用户组描述
    appId: string; // 应用 ID
    createTime: string; // 创建时间
    linkId: number; // UserGroupLink 表的主键
    validFrom: string; // 生效时间
    expireOn: string; // 过期时间
  }

  export interface UserGroupLinkInfo {
    appId: string;
    createTime: string;
    expireOn: string;
    groupId: string;
    groupName: string;
    id: string;
    intro: string;
    linkId: number;
    ownerId: string;
    ownerName: string;
    validFrom: string;
  }

  export interface UserLinkInfo {
    userId: string; // 用户 ID
    username: string; // 用户名
    linkId: number; // 关联 ID
    validFrom: string; // 负责人 ID
    expireOn: string; // 负责人 username
    appId: string; // 应用 ID
    createTime: string; // 创建时间
  }
}

const baseUri = "/system/userGroup";

export const list = (params: Partial<UserGroup.UserGroupInfo>) => {
  return http.get<http.Response<UserGroup.UserGroupInfo[]>>(`${baseUri}/list`, params);
};

export const listUserGroupByUserId = (params: { appId: string; userId: string }) => {
  return http.get<http.Response<UserGroup.UserGroupLinkInfo[]>>(
    `${baseUri}/listByUserId/${params.appId}/${params.userId}`
  );
};

export const listUserGroupWithDisabledByUserId = (params: { appId: string; userId: string }) => {
  return http.get<http.Response<UserGroup.UserGroupInfo[]>>(
    `${baseUri}/listWithDisabledByUserId/${params.appId}/${params.userId}`
  );
};

export const listUserLinkByGroupId = (params: { appId: string; userGroupId: string }) => {
  return http.get<http.Response<UserGroup.UserLinkInfo[]>>(
    `${baseUri}/listUserLinkByGroupId/${params.appId}/${params.userGroupId}`
  );
};

export const addOne = (data: UserGroup.UserGroupInfo) => {
  return http.post<http.Response<string>>(baseUri, data);
};

export const addUserToGroups = (data: UserGroup.UserLinkUserGroup) => {
  return http.post<http.Response<string>>(`${baseUri}/addUserToGroups`, data);
};

export const addUsersToGroup = (data: UserGroup.UserGroupLinkUser) => {
  return http.post<http.Response<string>>(`${baseUri}/addUsersToGroup`, data);
};

export const editOne = (data: RequiredKeyPartialOther<UserGroup.UserGroupInfo, "id">) => {
  return http.put<http.Response<string>>(baseUri, data);
};

export const editUserGroupLinkInfo = (data: RequiredKeyPartialOther<UserGroup.UserGroupLinkInfo, "id">) => {
  return http.put<http.Response<string>>(`${baseUri}/updateLinkInfo`, data);
};

export const deleteOne = (data: UserGroup.UserGroupInfo) => {
  return http.delete<http.Response<string>>(`${baseUri}/${data.id}`);
};

export const deleteBatch = (ids: string[]) => {
  return http.delete<http.Response<string>>(`${baseUri}/${ids.join(",")}`);
};

export const removeUserFromUserGroup = (ids: string[]) => {
  return http.delete<http.Response<string>>(`${baseUri}/removeUserFromUserGroup/${ids.join(",")}`);
};
