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
  }

  export interface DeptTree {
    id: string;
    label: string;
    parentId: string;
    weight: number;
    icon: string;
    children: DeptTree[];
  }
}

export const getDeptTreeList = () => {
  return http.get("/system/dept/deptTreeList");
};
