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
  }

  export interface DeptTreeList {
    id: string;
    label: string;
    parentId: string;
    weight: number;
    icon: string;
    children: DeptTreeList[];
    status: number;
  }

  export interface DeptTreeTable extends DeptInfo {
    children: DeptTreeTable[];
  }
}

const baseUri = "/system/dept";

export const listDeptTreeList = () => {
  return http.get<http.Response<Dept.DeptTreeList[]>>(`${baseUri}/treeList`);
};

export const listDeptTreeTable = () => {
  return http.get<http.Response<Dept.DeptTreeTable[]>>(`${baseUri}/treeTable`);
};

export const addOne = (data: Dept.DeptInfo) => {
  return http.post<http.Response<string>>(baseUri, data);
};

export const editOne = (data: RequiredKeyPartialOther<Dept.DeptInfo, "id">) => {
  return http.put<http.Response<string>>(baseUri, data);
};

export const deleteOne = (data: Dept.DeptInfo) => {
  return http.delete<http.Response<string>>(`${baseUri}/${data.id}/${data.deptId}`);
};
