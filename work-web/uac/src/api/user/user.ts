import http from "@/config/request";

export namespace User {
  // 用户基本信息
  export interface UserInfo {
    id: number; // 主键
    userId: string; // 用户 ID
    username: string; // 用户名
    nickname: string; // 用户昵称
    password: string; // 密码
    email: string; // 邮箱
    sex: number; // 性别（0 保密 1 男 2 女）
    birthday: string; // 生日
    phone: string; // 手机号码
    userStatus: number; // 状态（0 离线 1 在线）
    avatar: string; // 头像
    registerTime: string; //  注册时间
    loginIp: string; // 最后登录 IP
    loginDate: string; // 最后登录时间
    deptId: string; // 部门 ID
    status: number; // 状态
  }

  // 用户关联用户组信息
  export interface UserLinkUserGroupInfo {
    userId: string; // 用户 ID
    username: string; // 用户名
    nickname: string; // 用户昵称
    linkId: number; // 关联 ID
    validFrom: string; // 生效时间
    expireOn: string; // 失效时间
    appId: string; // 应用 ID
    createTime: string; // 创建时间
  }

  // 用户被关联信息，如被用户组关联、被角色关联
  export interface UserLinkInfo {
    userId: string; // 用户 ID
    username: string; // 用户名
    nickname: string; // 用户昵称
    linkId: number; // 关联 ID
    validFrom: string; // 生效时间
    expireOn: string; // 过期时间
    appId: string; // 应用 ID
    createTime: string; // 创建时间
  }

  // 用户穿梭框数据，如果 disabled 为 true，则禁选
  export interface UserBindSelect {
    userId: string; // 用户 ID
    username: string; // 用户名
    nickname: string; // 用户昵称
    disabled: boolean; // 是否禁用
  }
}

const baseUri = "/system/user";

/**
 * 查询角色列表和岗位列表
 */
export const getRolePostList = () => {
  return http.get<http.Response<User.UserInfo[]>>(`${baseUri}/rolePostList`);
};

export const list = (params: Partial<User.UserInfo>) => {
  return http.get<http.Response<User.UserInfo[]>>(`${baseUri}/list`, params);
};

/**
 * 下拉查询用户列表，如果用户绑定了用户组，则 disabled 属性为 true
 */
export const listWithDisabledByGroupId = (params: { userGroupId: string }) => {
  return http.get<http.Response<User.UserBindSelect[]>>(`${baseUri}/listWithDisabledByGroupId/${params.userGroupId}`);
};

/**
 * 通过用户组 ID 查询用户列表
 */
export const listUserLinkByGroupId = (params: { userGroupId: string }) => {
  return http.get<http.Response<User.UserLinkUserGroupInfo[]>>(
    `${baseUri}/listUserLinkByGroupId/${params.userGroupId}`,
    { ...params, userGroupId: undefined }
  );
};

/**
 * 通过角色 ID 查询用户列表
 */
export const listUserLinkByRoleId = (params: { roleId: string }) => {
  return http.get<http.Response<User.UserLinkInfo[]>>(`${baseUri}/listUserLinkByRoleId/${params.roleId}`);
};

export const addOne = (data: User.UserInfo) => {
  return http.post<http.Response<string>>(baseUri, data);
};

export const editOne = (data: RequiredKeyPartialOther<User.UserInfo, "id">) => {
  return http.put<http.Response<string>>(baseUri, data);
};

export const deleteOne = (data: User.UserInfo) => {
  return http.delete<http.Response<string>>(`${baseUri}/${data.id}`);
};

export const deleteBatch = (ids: string[]) => {
  return http.delete<http.Response<string>>(`${baseUri}/${ids.join(",")}`);
};
