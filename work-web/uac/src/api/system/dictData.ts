import http from "@/config/request";

export namespace DictData {
  export interface DictDataInfo {
    id: number; // 主键
    dictId: string; // 字典 ID
    dictLabel: string; // 字典标签
    dictValue: string; // 字典键值
    dictSort: number; // 字典排序
    tagEl: string; // tag 标签：el-tag | el-check-tag
    tagType: string; // tag 类型：primary | success | info | warning | danger
    tagEffect: string; // tag 主题：dark | light | plain
    tagAttributes: string; // tag 其他属性
    isDefault: string; // 是否默认（Y是 N否）
    appId: string; // 应用 ID
    dictCode: string; // 字典类型
  }
}

const baseUri = "/system/dictData";

export const listDictData = (params: http.Page<{ dictCode?: string; appId: string }>) => {
  return http.get<http.Response<DictData.DictDataInfo[]>>(`${baseUri}/list`, params);
};

export const listDictDataByDictCode = (params: { dictCode: string; appId: string }) => {
  return http.get<http.Response<DictData.DictDataInfo[]>>(`${baseUri}/list`, params);
};

export const addOneDictData = (data: DictData.DictDataInfo) => {
  return http.post<http.Response<string>>(baseUri, data);
};

export const editOneDictData = (data: DictData.DictDataInfo) => {
  return http.put<http.Response<string>>(baseUri, data);
};

export const removeOneDictData = (data: DictData.DictDataInfo) => {
  return http.delete<http.Response<string>>(`${baseUri}/${data.id}`);
};
