import http from "@/config/request";

export namespace DictType {
  export interface DictTypeInfo {
    id: number; // 主键
    dictId: string; // 字典 ID
    dictCode: string; // 字典类型
    dictName: string; // 字典名称
    appId: string; // 应用 ID
  }
}

const baseUri = "/system/dictType";

export const listDictTypeByApp = (params: http.Page<{ appId: string }>) => {
  return http.get<http.Response<DictType.DictTypeInfo[]>>(`${baseUri}/list`, params);
};

export const addOneDictType = (data: DictType.DictTypeInfo) => {
  return http.post<http.Response<string>>(baseUri, data);
};

export const editOneDictType = (data: DictType.DictTypeInfo) => {
  return http.put<http.Response<string>>(baseUri, data);
};

export const removeOneDictType = (data: DictType.DictTypeInfo) => {
  return http.delete<http.Response<string>>(`${baseUri}/${data.id}`);
};
