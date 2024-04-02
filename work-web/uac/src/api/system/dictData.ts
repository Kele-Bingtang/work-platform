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

export const list = (params: Partial<DictData.DictDataInfo>) => {
  return http.get<http.Response<DictData.DictDataInfo[]>>(`${baseUri}/list`, params);
};

export const listPage = (params: Partial<DictData.DictDataInfo>) => {
  return http.get<http.Page<DictData.DictDataInfo[]>>(`${baseUri}/listPage`, params);
};

export const addOne = (data: DictData.DictDataInfo) => {
  return http.post<http.Response<string>>(baseUri, data);
};

export const editOne = (data: DictData.DictDataInfo) => {
  return http.put<http.Response<string>>(baseUri, data);
};

export const removeOne = (data: DictData.DictDataInfo) => {
  return http.delete<http.Response<string>>(`${baseUri}/${data.id}`);
};
