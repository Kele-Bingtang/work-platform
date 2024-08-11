import http from "@/config/request";

export namespace DictType {
  export interface DictTypeInfo {
    id: number; // 主键
    dictId: string; // 字典 ID
    dictCode: string; // 字典类型
    dictName: string; // 字典名称
    isCascade: number; // 是否开启级联（0 不开启，1 开启）
  }
}

const baseUri = "/dict/type";

export const list = (params: Partial<DictType.DictTypeInfo>) => {
  return http.get<http.Response<DictType.DictTypeInfo[]>>(`${baseUri}/list`, params);
};

export const listPage = (params: Partial<DictType.DictTypeInfo>) => {
  return http.get<http.Page<DictType.DictTypeInfo[]>>(`${baseUri}/listPage`, params);
};

export const addDictType = (data: DictType.DictTypeInfo) => {
  return http.post<http.Response<boolean>>(baseUri, data);
};

export const editDictType = (data: DictType.DictTypeInfo) => {
  return http.put<http.Response<boolean>>(baseUri, data);
};

export const deleteDictType = (data: DictType.DictTypeInfo) => {
  return http.delete<http.Response<boolean>>(`${baseUri}/${data.id}`);
};

export const deleteBatchDictType = ({ idList }: { idList: string[] }) => {
  return http.delete<http.Response<boolean>>(`${baseUri}/${idList.join(",")}`);
};

/**
 * 字典类型导出
 */
export const exportExcel = (params: Partial<DictType.DictTypeInfo>) => {
  return http.post<any>(`${baseUri}/export`, params, { responseType: "blob" });
};
