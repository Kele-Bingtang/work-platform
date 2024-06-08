import http from "@/config/request";

export namespace OnlineUser {
  export interface OnlineUserInfo {
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
}

const baseUri = "/monitor/online";

export const listPage = (params: Partial<OnlineUser.OnlineUserInfo>) => {
  return http.get<http.Response<OnlineUser.OnlineUserInfo[]>>(`${baseUri}/listPage`, params);
};

export const forceLogout = (username: string) => {
  return http.delete<boolean>(`${baseUri}/${username}`);
};
