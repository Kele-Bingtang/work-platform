import http from "@/config/request";

export declare namespace Service {
  interface ServiceInfo {
    id: number; // 主键
    serviceId: string; // 服务 ID
    serviceName: string; // 服务名
    serviceUrl: string; // 服务地址
    fullUrl: string; // 服务完整地址
    description: string; // 服务描述
    selectSql: string; // 接口的查询 SQL 语句
    selectTable: string; // 执行查询语句的表名
    updateTable: string; // 执行插入语句的表名
    insertTable: string; // 执行更新语句的表名
    deleteTable: string; // 执行删除语句的表名
    isAuth: number; // 是否进行认证（0 不认证 1 认证）
    reportTitle: string; // 报表名称
    serviceVersion: string; // 接口版本号（修改一次 +1）
    status: number; // 服务状态，0 禁用 1 启用
    createTime: string; // 创建时间
    categoryId: string; // 目录 ID
    projectId: string; // 项目 ID
    teamId: string; // 团队 ID
  }

  type ServiceSearch = Partial<ServiceInfo>;
  type ServiceInsert = RequiredKeyPartialOther<
    Omit<ServiceInfo, "id">,
    "serviceName" | "serviceUrl" | "projectId" | "teamId"
  >;
  type ServiceUpdate = RequiredKeyPartialOther<Omit<ServiceInfo, "projectId" | "categoryId">, "id">;
  type ServiceDelete = RequiredKeyPartialOther<ServiceInfo, "serviceId">;
}

const baseUri = "/service";

export const listServicePage = (params?: http.PageData<Service.ServiceSearch>) => {
  return http.get<http.Response<Service.ServiceInfo[]>>(`${baseUri}/listPage`, params);
};

export const addService = (data: Service.ServiceInsert) => {
  return http.post<http.Response<string>>(baseUri, data);
};

export const editService = (data: Service.ServiceUpdate) => {
  return http.put<http.Response<string>>(baseUri, data);
};

export const removeService = (data: Service.ServiceDelete) => {
  return http.delete<http.Response<string>>(`${baseUri}/${data.projectId}`);
};