import http from "@/config/request";

export namespace OperaLog {
  export interface OperaLogInfo {
    id: string; // 主键
    operaId: string; // 日志 ID
    title: string; // 模块标题
    businessType: number; // 业务类型（0 其它 1 新增 2 修改 3 删除 ...）
    method: string; // 方法名称
    requestMethod: string; // 请求方式
    operatorType: string; // 操作类别（0 其它 1 后台用户 2 手机端用户）
    operaName: string; // 操作人员
    operaUrl: string; // 请求 URL
    operaIp: string; // 主机地址
    operaLocation: string; // 操作地点
    operaParam: string; // 请求参数
    jsonResult: string; // 返回参数
    errorMsg: string; // 错误消息
    status: number; // 状态（0 异常 1 正常 ）
    operaTime: string; // 操作时间
    costTime: string; // 消耗时间
  }
}

const baseUri = "/monitor/operaLog";

export const listPage = (params: Partial<OperaLog.OperaLogInfo>) => {
  return http.get<http.Response<OperaLog.OperaLogInfo[]>>(`${baseUri}/listPage`, params);
};

export const removeBatch = (idList: string[]) => {
  return http.delete<http.Response<boolean>>(`${baseUri}/${idList.join(",")}`);
};

export const cleanAllLog = () => {
  return http.delete<http.Response<boolean>>(`${baseUri}/clean`);
};

/**
 * 操作日志导出
 */
export const exportExcel = (params: Partial<OperaLog.OperaLogInfo>) => {
  return http.post<any>(`${baseUri}/export`, params, { responseType: "blob" });
};
