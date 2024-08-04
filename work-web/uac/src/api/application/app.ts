import http from "@/config/request";

export namespace App {
  export interface AppInfo {
    id: number; // 主键
    appId: string; // 应用 ID
    appCode: string; // 应用码
    appName: string; // 应用名
    intro: string; // 应用介绍
    orderNum: number; // 显示顺序
    status: number; // 状态
    createTime: string; // 创建时间
    clientId: string; // 客户端 ID
  }
  export interface AppTree {
    appId: string; // 应用 ID
    appName: string; // 应用名
  }
}

const baseUri = "/system/app";

export const getAppTreeList = () => {
  return http.get<http.Response<App.AppTree[]>>(`${baseUri}/treeList`);
};

export const list = (params: Partial<App.AppInfo>) => {
  return http.get<http.Response<App.AppInfo[]>>(`${baseUri}/list`, params);
};

export const listPage = (params: Partial<App.AppInfo>) => {
  return http.get<http.Page<App.AppInfo[]>>(`${baseUri}/listPage`, params);
};

export const addApp = (data: App.AppInfo) => {
  return http.post<http.Response<boolean>>(baseUri, data);
};

export const editApp = (data: RequiredKeyPartialOther<App.AppInfo, "id">) => {
  return http.put<http.Response<boolean>>(baseUri, data);
};

export const removeApp = (data: App.AppInfo) => {
  return http.delete<http.Response<boolean>>(
    `${baseUri}/${data.id}`,
    {},
    {
      data: [data.appId],
    }
  );
};

export const removeBatch = ({ idList, dataList }: { idList: string[]; dataList: App.AppInfo[] }) => {
  return http.delete<http.Response<boolean>>(
    `${baseUri}/${idList.join(",")}`,
    {},
    {
      data: dataList.map(item => item.appId),
    }
  );
};

/**
 * 应用导出
 */
export const exportExcel = (params: Partial<App.AppInfo>) => {
  return http.post<any>(`${baseUri}/export`, params, { responseType: "blob" });
};
