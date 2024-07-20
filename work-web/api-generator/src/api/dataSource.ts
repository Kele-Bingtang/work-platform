import http from "@/config/request";

export declare namespace DataSource {
  interface DataSourceInfo {
    id: number; // 主键
    dataSourceId: string; // 数据源 ID
    dataSourceName: string; // 数据源名称
    dataSourceType: string; // 数据源类型
    driverClassName: string; // 数据源驱动类
    jdbcUrl: string; // 链接地址
    host: string; // 服务器地址
    port: string; // 端口
    username: string; // 账号
    password: string; // 密码
    description: string; // 描述
    secretKey: string; // 密码加解密密钥
    status: number; // 状态（0 异常 1 正常）
    createTime: string; // 创建时间
    teamId: string; // 团队 ID
  }

  type DataSourceSearch = Partial<DataSourceInfo>;
  type DataSourceInsert = RequiredKeyPartialOther<Omit<DataSourceInfo, "id">, "teamId">;
  type DataSourceUpdate = RequiredKeyPartialOther<DataSourceInfo, "id" | "dataSourceId">;
  type DataSourceDelete = RequiredKeyPartialOther<DataSourceInfo, "dataSourceId">;

  interface Table {
    tableType: string; // Table 类型，1 表，2 视图
    tableNameList: string[]; // 表名列表或者视图列表
  }

  interface SqlExecute {
    dataSourceId: string; // 数据源 ID
    schema: string; // Schema
    sql: string; // SQL 语句
  }
}

const baseUri = "/dataSource";

export const listDataSourcePage = (params?: http.PageData<DataSource.DataSourceSearch>) => {
  return http.get<http.Response<DataSource.DataSourceInfo[]>>(`${baseUri}/listPage`, params);
};

export const listSelectDataSource = (teamId: string) => {
  return http.get<http.Response<DataSource.DataSourceInfo[]>>(`${baseUri}/listSelect/${teamId}`);
};

export const listByProjectId = (projectId: string) => {
  return http.get<http.Response<DataSource.DataSourceInfo[]>>(`${baseUri}/listByProjectId/${projectId}`);
};

export const listSchemaByDataSource = (dataSourceId: string) => {
  return http.get<http.Response<string[]>>(`${baseUri}/listSchemaByDataSource/${dataSourceId}`);
};

export const listTableBySchema = (dataSourceId: string, schema: string) => {
  return http.get<http.Response<DataSource.Table[]>>(`${baseUri}/listTableBySchema/${dataSourceId}/${schema}`);
};

export const addDataSource = (data: DataSource.DataSourceInsert) => {
  return http.post<http.Response<string>>(baseUri, data);
};

export const editDataSource = (data: DataSource.DataSourceUpdate) => {
  return http.put<http.Response<string>>(baseUri, data);
};

export const removeDataSource = (data: DataSource.DataSourceDelete) => {
  return http.delete<http.Response<string>>(`${baseUri}/${data.dataSourceId}`);
};

export const testConnect = (data: DataSource.DataSourceSearch) => {
  return http.post<http.Response<string>>(`${baseUri}/testConnect`, data);
};

export const executeSelect = (data: DataSource.SqlExecute) => {
  return http.post<http.Response<Record<string, any>[]>>(`${baseUri}/executeSelect`, data);
};

export const generateTemple = (dataSourceId: string, schema: string, tableName: string, type: string) => {
  return http.post<http.Response<string>>(`${baseUri}/generateTemple/${dataSourceId}/${schema}/${tableName}/${type}`);
};
