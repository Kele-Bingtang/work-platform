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

  export interface ClientTree {
    clientId: string; // 客户端 ID
    clientName: string; // 客户端名称
  }
}

const baseUri = "/system/client";

export const getClientTreeList = () => {
  return http.get<Client.ClientTree>(`${baseUri}/treeList`);
};

export const list = (params?: Partial<Client.ClientInfo>) => {
  return http.get<http.Response<Client.ClientInfo[]>>(`${baseUri}/list`, params);
};

export const listPage = (params?: Partial<Client.ClientInfo>) => {
  return http.get<http.Page<Client.ClientInfo[]>>(`${baseUri}/listPage`, params);
};

export const listClientGrantTypeList = (clientId: string) => {
  return http.get<http.Response<Client.ClientInfo[]>>(`${baseUri}/grantTypeList/${clientId}`);
};

export const addClient = (data: Client.ClientInfo) => {
  return http.post<http.Response<boolean>>(baseUri, data);
};

export const editClient = (data: RequiredKeyPartialOther<Client.ClientInfo, "id">) => {
  return http.put<http.Response<boolean>>(baseUri, data);
};

export const removeClient = (data: Client.ClientInfo) => {
  return http.delete<http.Response<boolean>>(
    `${baseUri}/${data.id}`,
    {},
    {
      data: [data.clientId],
    }
  );
};

export const removeBatch = ({ idList, dataList }: { idList: string[]; dataList: Client.ClientInfo[] }) => {
  return http.delete<http.Response<boolean>>(
    `${baseUri}/${idList.join(",")}`,
    {},
    {
      data: dataList.map(item => item.clientId),
    }
  );
};

/**
 * 客户端导出
 */
export const exportExcel = (params: Partial<Client.ClientInfo>) => {
  return http.post<any>(`${baseUri}/export`, params, { responseType: "blob" });
};
