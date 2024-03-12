import http from "@/config/request";

export namespace UserGroup {
  export interface UserGroupInfo {
    id: number; // 主键
    groupId: string; // 用户组 ID
    groupName: string; // 用户组名
    intro: string; // 用户组描述
    appId: string; // 应用 ID
    createTime: string; // 创建时间
  }
}

const baseUri = "/system/userGroup";

export const list = (params: Partial<UserGroup.UserGroupInfo>) => {
  return http.get<http.Response<UserGroup.UserGroupInfo[]>>(`${baseUri}/list`, params);
};

export const listUserGroupByUserId = (params: { appId: string; userId: string }) => {
  return http.get<http.Response<UserGroup.UserGroupInfo[]>>(`${baseUri}/listByUserId/${params.appId}/${params.userId}`);
};

export const listUserGroupWithDisabledByUserId = (params: { appId: string; userId: string }) => {
  return http.get<http.Response<UserGroup.UserGroupInfo[]>>(
    `${baseUri}/listWithDisabledByUserId/${params.appId}/${params.userId}`
  );
};

export const addOne = (data: UserGroup.UserGroupInfo) => {
  return http.post<http.Response<string>>(baseUri, data);
};

export const editOne = (data: RequiredKeyPartialOther<UserGroup.UserGroupInfo, "id">) => {
  return http.put<http.Response<string>>(baseUri, data);
};

export const deleteOne = (data: UserGroup.UserGroupInfo) => {
  return http.delete<http.Response<string>>(`${baseUri}/${data.id}`);
};

export const deleteBatch = (ids: string[]) => {
  return http.delete<http.Response<string>>(`${baseUri}/${ids.join(",")}`);
};
