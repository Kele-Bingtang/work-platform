import http from "@/config/request";

export namespace Auth {
  export interface LoginBody {
    tenantId: string;
    appId: string;
    username: string;
    password: string;
    grantType: string;
  }

  export interface LoginResponse {
    accessToken: string;
    refreshToken: string;
    expireIn: number;
    refreshExpireIn: number;
  }

  export interface TenantSelectList {
    tenantEnabled: boolean;
    tenantSelectList: TenantSelect[];
  }

  export interface TenantSelect {
    tenantId: string;
    tenantName: string;
    domain: string;
  }

  export interface UserInfo {
    user: LoginUserInfo;
    permissions: string[];
    roles: string[];
  }

  export interface LoginUserInfo {
    userId: string;
    tenantId: string;
    deptId: string;
    username: string;
    nickname: string;
    email: string;
    phone: string;
    sex: string;
    avatar: string;
    loginIp: string;
    loginTime: string;
    registerTime: string;
  }
}

export const login = (loginBody: Auth.LoginBody) => {
  return http.post<http.Response<Auth.LoginResponse>>("/auth/login", loginBody);
};

export const getTenantSelectList = () => {
  return http.get<http.Response<Auth.TenantSelectList>>("/auth/tenant/list");
};

export const getUserInfo = () => {
  return http.get<http.Response<Auth.UserInfo>>("/auth/getUserInfo");
};
