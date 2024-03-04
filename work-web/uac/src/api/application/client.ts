import http from "@/config/request";

export namespace Client {
  export interface ClientInfo {
    id: number; // 主键
    clientId: string; // 客户端 ID
    clientKey: string; // 客户端 Key
    clientName: string; // 客户端名称
    clientSecret: string; // 客户端秘钥
    grantTypes: string; // 授权类型
    grantTypeList: string[]; // 授权类型
    activeTimeout: number; // token 最低活跃频率时间，超出则 token 失效（-1 不限制，单位秒）
    timeout: number; // token 有效期，超出则 token 失效，默认 30 天（单位秒）
    status: number; // 状态
    createTime: string; // 创建时间
  }
}

const baseUri = "/system/client";

export const list = () => {
  return http.get<http.Response<Client.ClientInfo[]>>(`${baseUri}/list`);
};

export const addOne = (data: Client.ClientInfo) => {
  return http.post<http.Response<string>>(baseUri, data);
};

export const editOne = (data: RequiredKeyPartialOther<Client.ClientInfo, "id">) => {
  return http.put<http.Response<string>>(baseUri, data);
};

export const deleteOne = (data: Client.ClientInfo) => {
  return http.delete<http.Response<string>>(`${baseUri}/${data.id}`);
};

export const deleteBatch = (ids: string[]) => {
  return http.delete<http.Response<string>>(`${baseUri}/${ids.join(",")}`);
};
