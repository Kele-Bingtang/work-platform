import http from "@/config/request";

export namespace LoginLog {
  export interface LoginLogInfo {
    loginId: string; // 部门 ID
    userName: string; // 父级部门 ID
    appId: string; // 父级部门名字
    deviceType: string; // 祖级列表
    loginIp: string; // 部门名
    loginLocation: string; // 部门图标
    browser: string; // 部门显示顺序
    os: string; // 部门用户数量
    msg: string; // 部门负责人
    status: number; // 状态
    loginTime: string; // 创建时间
  }
}

const baseUri = "/system/loginLog";

export const list = () => {
  return http.get<http.Response<LoginLog.LoginLogInfo[]>>(`${baseUri}/listPage`);
};
