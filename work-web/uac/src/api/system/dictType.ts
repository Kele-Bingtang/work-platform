import http from "@/config/request";

export namespace DictType {
  export interface DictTypeInfo {
    id: number; // 主键
    dictId: string; // 字典 ID
    dictCode: string; // 字典类型
    dictName: string; // 字典名称
    isCascade: number; // 是否开启级联（0 不开启，1 开启）
    appId: string; // 应用 ID
  }
}

const baseUri = "/system/dictType";

export const list = (params: Partial<DictType.DictTypeInfo>) => {
  return http.get<http.Response<DictType.DictTypeInfo[]>>(`${baseUri}/list`, params);
};

export const listPage = (params: Partial<DictType.DictTypeInfo>) => {
  return http.get<http.Page<DictType.DictTypeInfo[]>>(`${baseUri}/listPage`, params);
};

export const addOne = (data: DictType.DictTypeInfo) => {
  return http.post<http.Response<string>>(baseUri, data);
};

export const editOne = (data: DictType.DictTypeInfo) => {
  return http.put<http.Response<string>>(baseUri, data);
};

export const removeOne = (data: DictType.DictTypeInfo) => {
  return http.delete<http.Response<string>>(`${baseUri}/${data.id}`);
};
