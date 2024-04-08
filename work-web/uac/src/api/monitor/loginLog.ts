import http from "@/config/request";

export namespace LoginLog {
  export interface LoginLogInfo {
    userName: string; // 用户账号
    clientName: string; // 客户端名
    loginIp: string; // 登录 IP 地址
    loginLocation: string; // 登录地点
    browser: string; // 浏览器类型
    os: string; // 操作系统
    msg: string; // 提示消息
    status: number; // 状态（0 异常 1 正常 ）
    loginTime: string; // 访问时间
  }
}

const baseUri = "/monitor/loginLog";

export const listPage = (params: Partial<LoginLog.LoginLogInfo>) => {
  return http.get<http.Response<LoginLog.LoginLogInfo[]>>(`${baseUri}/listPage`, params);
};
