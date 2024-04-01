import http from "@/config/request";

export namespace Tenant {
  export interface TenantInfo {
    id: number; // 主键
    tenantId: string; // 租户编号
    licenseNumber: string; // 统一社会信用代码
    tenantName: string; // 企业名称
    icon: string; // 企业图标
    address: string; // 企业所在地
    userCountCapacity: number; // 企业用户数量（-1 不限制）
    userCount: number; // 用户实际数量
    founder: string; // 企业创始人
    intro: string; // 企业介绍
    contactUserName: string; // 联系人
    contactPhone: string; // 联系电话
    domain: string; // 企业域名
    expireTime: string; // 租户过期时间（-1 无限期）
    status: number; // 状态
    createTime: string; // 创建时间
  }
}

const baseUri = "/system/tenant";

export const list = () => {
  return http.get<http.Response<Tenant.TenantInfo[]>>(`${baseUri}/list`);
};

export const listPage = () => {
  return http.get<http.Page<Tenant.TenantInfo[]>>(`${baseUri}/listPage`);
};

export const addOne = (data: Tenant.TenantInfo) => {
  return http.post<http.Response<string>>(baseUri, data);
};

export const editOne = (data: RequiredKeyPartialOther<Tenant.TenantInfo, "id">) => {
  return http.put<http.Response<string>>(baseUri, data);
};

export const deleteOne = (data: Tenant.TenantInfo) => {
  return http.delete<http.Response<string>>(`${baseUri}/${data.id}`);
};

export const deleteBatch = (ids: string[]) => {
  return http.delete<http.Response<string>>(`${baseUri}/${ids.join(",")}`);
};
