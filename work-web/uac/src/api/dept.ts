import http from "@/config/request";

export namespace Dept {
  export interface DeptList {
    id: number;
    deptId: string; // 部门 ID
    parentId: string; // 父级部门 ID
    parentName: string; // 父级部门名字
    ancestors: string; // 祖级列表
    deptName: string; // 部门名
    icon: string; // 部门图标
    orderNum: string; // 部门显示顺序
    userCount: string; // 部门用户数量
    leader: string; // 部门负责人
    phone: string; // 负责电话
    email: string; // 邮箱
    intro: string; // 部门介绍
    level: string; // 部门层级
    status: number; // 状态
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

  export interface DeptTreeTable extends DeptList {
    children: DeptTreeTable[];
  }
}

const baseUri = "/system/dept";

export const getDeptTreeList = () => {
  return http.get<Dept.DeptTreeList>(`${baseUri}/deptTreeList`);
};

export const getDeptTreeTable = () => {
  return http.get<Dept.DeptTreeTable>(`${baseUri}/deptTreeTable`);
};

export const addOne = (data: Dept.DeptList) => {
  return http.post<http.Response<string>>(baseUri, data);
};

export const editOne = (data: RequiredKeyPartialOther<Dept.DeptList, "id">) => {
  return http.put<http.Response<string>>(baseUri, data);
};

export const deleteOne = (data: Dept.DeptList) => {
  return http.delete<http.Response<string>>(`${baseUri}/${data.deptId}`);
};
