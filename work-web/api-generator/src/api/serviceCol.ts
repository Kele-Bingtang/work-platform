import http from "@/config/request";

export declare namespace ServiceCol {
  interface ServiceColInfo {
    id: number; // 主键
    colId: string; // 字段 ID
    tableCol: string; // 表字段名称
    jsonCol: string; // 请求返回的 JSON 字段名称
    reportCol: string; // 报表字段名称
    colType: string; // 字段类型
    colLength: number; // 字段类型长度
    isWhereKey: number; // 增删改时，是否作为 where 条件的主键，0 不作为，1 作为（值为空传）2 作为（值为空不传）
    defaultValue: string; // 字段默认值
    dataEncrypt: number; // 数据是否加密（0 不加密 1 加密）
    /**
     * Select 时的条件筛选，如 where 字段 = xx、like %xx%。
     * 0 为不筛选
     * 1 为 =，精准匹配
     * 2 为 !=，不等于
     * 3 为 like %xx
     * 4 为 like xx%
     * 5 为 like %xx%
     * 6 为 lt，即小于
     * 7 为 gt，即大于
     * 8 为 le，即小于等于
     * 9 为 ge，即大于等于
     * 10 为 value range，即值范围查询
     */
    queryFilter: number;
    orderBy: number; // 排序，负数表示 desc，正数表示 asc
    allowInsert: number; // 是否允许插入（0 不允许 1 允许）
    allowUpdate: number; // 是否允许更新（0 不允许 1 允许）
    allowFilter: number; // 是否允许查询（0 不允许 1 允许）
    allowRequest: number; // 是否允许返回在请求里（0 不允许 1 允许）
    allowShowInReport: number; // 是否允许出现在报表（0 不允许 1 允许）
    allowShowInDetail: number; // 是否允许出现在报表的增删改弹出框（0 不允许 1 允许）
    displaySeq: number; // 报表字段出现的顺序
    reportColWidth: number; // 报表字段显示的宽度，-1 为 auto，其他为准确的数值+px
    detailColWidth: number; // 报表的增删改弹出框，该字段的输入框宽度，-1 为 auto，其他为准确的数值 + px
    colAlign: number; // 报表显示的列对齐（0 为左对齐 1 为居中 2 为右对齐）
    dropdownValue: string; // 自定义下拉值
    dropdownService: string; // 读取接口获取下拉值
    dropdownSql: string; // 通过SQL 获取下拉值
    serviceId: string; // 服务 ID
    categoryId: string; // 分类 ID
    projectId: string; // 项目 ID
    teamId: string; // 团队 ID
  }

  type ServiceColSearch = Partial<ServiceColInfo>;
  type ServiceColInsert = RequiredKeyPartialOther<
    Omit<ServiceColInfo, "id">,
    "tableCol" | "jsonCol" | "serviceId" | "categoryId" | "projectId" | "teamId"
  >;
  type ServiceColUpdate = RequiredKeyPartialOther<
    Omit<ServiceColInfo, "serviceId" | "categoryId" | "projectId" | "teamId">,
    "id"
  >;
  type ServiceColDelete = RequiredKeyPartialOther<ServiceColInfo, "id">;
}

const baseUri = "/serviceCol";

export const listServiceColPage = (params?: http.PageData<ServiceCol.ServiceColSearch>) => {
  return http.get<http.Response<ServiceCol.ServiceColInfo[]>>(`${baseUri}/listPage`, params);
};

export const addServiceCol = (data: ServiceCol.ServiceColInsert) => {
  return http.post<http.Response<string>>(baseUri, data);
};

export const editServiceCol = (data: ServiceCol.ServiceColUpdate) => {
  return http.put<http.Response<string>>(baseUri, data);
};

export const removeServiceCol = (data: ServiceCol.ServiceColDelete) => {
  return http.delete<http.Response<string>>(`${baseUri}/${data.id}`);
};

export const reGenCol = (serviceId: string) => {
  return http.post<http.Response<string>>(`${baseUri}/reGenCol/${serviceId}`);
};
export const removeInvalidCol = (serviceId: string) => {
  return http.post<http.Response<string>>(`${baseUri}/removeInvalidCol/${serviceId}`);
};
