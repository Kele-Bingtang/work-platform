import http from "@/config/request";
export declare namespace Report {
  interface ReportInfo {
    id: number;
    reportId: string; // 报表 ID
    reportTitle: string; // 报表名称
    description: string; // 报名描述
    allowAdd: number; // 是否允许新增（0 不允许，1 允许）
    allowEdit: number; // 是否允许编辑（0 不允许，1 允许）
    allowRemove: number; // 是否允许删除（0 不允许，1 允许）
    allowFilter: number; // 是否允许查询（0 不允许，1 允许）
    allowExport: number; // 是否允许导出（0 不允许，1 允许）
    allowRow: number; // 报表是否出现行数（0 不允许，1 允许）
    dialogWidth: string; // 弹出框宽度
    pageSize: number; // 一页显示多少条数据
    chartType: number; // 是否开启图标，0 不开启，1 饼图，2 折线图
    serviceId: string; // 服务 ID
    categoryId: string; // 分类 ID
    projectId: string; // 项目 ID
    teamId: string; // 团队 ID
  }

  interface ReportConfig {
    report: Report.ReportInfo;
    proTableColumnsList: Record<string, any>[];
    proFormSchemaList: Record<string, any>[];
  }
}

const baseUri = "/report";
export const listReportConfig = (serviceId: string) => {
  return http.get<http.Response<Report.ReportConfig>>(`${baseUri}/listReportConfig/${serviceId}`);
};
