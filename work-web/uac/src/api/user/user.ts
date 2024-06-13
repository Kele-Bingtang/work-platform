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
    loginTime: string; // 最后登录时间
    deptId: string; // 部门 ID
    status: number; // 状态
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

  // 用户关联角色信息（多个角色）
  export interface UserLinkRole {
    userId: string; // 用户 ID
    roleIds: string[]; // 角色 ID
    validFrom: string; // 负责人 ID
    expireOn: string; // 负责人 username
    appId: string; // 应用 ID
  }

  // 用户关联用户组信息（多个用户组）
  export interface UserLinkUserGroup {
    linkId: number; // 关联 ID
    userId: string; // 用户 ID
    userGroupIds: string[]; // 用户组 ID
    validFrom: string; // 负责人 ID
    expireOn: string; // 负责人 username
    appId: string; // 应用 ID
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

export const list = (params?: Partial<User.UserInfo>) => {
  return http.get<http.Response<User.UserInfo[]>>(`${baseUri}/list`, params);
};

export const listPage = (params: Partial<User.UserInfo>) => {
  return http.get<http.Page<User.UserInfo[]>>(`${baseUri}/listPage`, params);
};

/**
 * 下拉查询用户列表，如果用户绑定了用户组，则 disabled 属性为 true
 */
export const listWithDisabledByGroupId = (params: { userGroupId: string }) => {
  return http.get<http.Response<User.UserBindSelect[]>>(`${baseUri}/listWithDisabledByGroupId/${params.userGroupId}`);
};

/**
 * 下拉查询用户列表，如果用户绑定了角色，则 disabled 属性为 true
 */
export const listWithDisabledByRoleId = (params: { roleId: string }) => {
  return http.get<http.Response<User.UserBindSelect[]>>(`${baseUri}/listWithDisabledByRoleId/${params.roleId}`);
};

/**
 * 通过用户组 ID 查询用户列表
 */
export const listUserLinkByGroupId = (params: { userGroupId: string }) => {
  return http.get<http.Response<User.UserLinkInfo[]>>(`${baseUri}/listUserLinkByGroupId/${params.userGroupId}`, {
    ...params,
    userGroupId: undefined,
  });
};

/**
 * 通过角色 ID 查询用户列表
 */
export const listUserLinkByRoleId = (params: { roleId: string }) => {
  return http.get<http.Response<User.UserLinkInfo[]>>(`${baseUri}/listUserLinkByRoleId/${params.roleId}`, {
    ...params,
    roleId: undefined,
  });
};

/**
 * 添加用户组到用户（多个用户组）
 */
export const addUserGroupsToUser = (data: User.UserLinkUserGroup) => {
  return http.post<http.Response<boolean>>(`${baseUri}/addUserGroupsToUser`, data);
};

/**
 * 添加角色到用户（多个角色）
 */
export const addRolesToUser = (data: User.UserLinkRole) => {
  return http.post<http.Response<boolean>>(`${baseUri}/addRolesToUser`, data);
};

export const addOne = (data: User.UserInfo) => {
  return http.post<http.Response<boolean>>(baseUri, data);
};

export const editOne = (data: RequiredKeyPartialOther<User.UserInfo, "id">) => {
  return http.put<http.Response<boolean>>(baseUri, data);
};

export const deleteOne = (data: User.UserInfo) => {
  return http.delete<http.Response<boolean>>(
    `${baseUri}/${data.id}`,
    {},
    {
      data: [data.userId],
    }
  );
};

export const deleteBatch = ({ idList, dataList }: { idList: string[]; dataList: User.UserInfo[] }) => {
  return http.delete<http.Response<boolean>>(
    `${baseUri}/${idList.join(",")}`,
    {},
    {
      data: dataList.map(item => item.userId),
    }
  );
};

/**
 * 用户导出
 */
export const exportExcel = (params: Partial<User.UserInfo>) => {
  return http.post<any>(`${baseUri}/export`, params, { responseType: "blob" });
};
