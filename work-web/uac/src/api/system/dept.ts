import http from "@/config/request";

export namespace Dept {
  export interface DeptInfo {
    id: number;
    deptId: string; // 部门 ID
    parentId: string; // 父级部门 ID
    parentName: string; // 父级部门名字
    ancestors: string; // 祖级列表
    deptName: string; // 部门名
    icon: string; // 部门图标
    orderNum: number; // 部门显示顺序
    userCount: string; // 部门用户数量
    leader: string; // 部门负责人
    phone: string; // 负责电话
    email: string; // 邮箱
    intro: string; // 部门介绍
    level: string; // 部门层级
    status: number; // 状态
    createTime: string; // 创建时间
    children: DeptInfo[]; // 子数据
  }

  export interface DeptTreeList {
    id: string;
    label: string;
    parentId: string;
    weight: number;
    icon: string;
    children: DeptTreeList[];
    value: string;
  }

  export interface DeptTreeTable extends DeptInfo {
    children: DeptTreeTable[];
  }
}

const baseUri = "/system/dept";

/**
 * 查询部门树列表
 */
export const listDeptTreeList = () => {
  return http.get<http.Response<Dept.DeptTreeList[]>>(`${baseUri}/treeList`);
};

/**
 * 查询部门树表格
 */
export const listDeptTreeTable = () => {
  return http.get<http.Response<Dept.DeptTreeTable[]>>(`${baseUri}/treeTable`);
};

/**
 * 通过角色 ID 查询部门 ID 列表
 */
export const listDeptIdsByRoleId = (appId: string, roleId: string) => {
  return http.get<http.Response<string[]>>(`${baseUri}/listDeptIdsByRoleId/${appId}/${roleId}`);
};

/**
 * 通过角色 ID 查询部门列表
 */
export const listDeptListByRoleId = (appId: string, roleId: string) => {
  return http.get<http.Response<Dept.DeptTreeList[]>>(`${baseUri}/listDeptListByRoleId/${appId}/${roleId}`);
};

export const addDept = (data: Dept.DeptInfo) => {
  return http.post<http.Response<boolean>>(baseUri, data);
};

export const editDept = (data: RequiredKeyPartialOther<Dept.DeptInfo, "id">) => {
  return http.put<http.Response<boolean>>(baseUri, data);
};

export const removeDept = (data: Dept.DeptInfo) => {
  return http.delete<http.Response<boolean>>(`${baseUri}/${data.id}/${data.deptId}`);
};

/**
 * 部门导出
 */
export const exportExcel = (params: Partial<Dept.DeptInfo>) => {
  return http.post<any>(`${baseUri}/export`, params, { responseType: "blob" });
};
