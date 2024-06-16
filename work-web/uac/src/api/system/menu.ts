import http from "@/config/request";

export namespace Menu {
  export interface MenuInfo {
    id: number; // id
    menuId: string; // 菜单 ID
    menuCode: string; // 菜单编码
    menuName: string; // 菜单名
    parentId: string; // 父级菜单 ID
    pathPrefix: string; // 菜单地址前缀
    path: string; // 菜单地址
    icon: string; // 图标
    orderNum: number; // 显示顺序
    permission: string; // 菜单权限
    menuType: string; // 菜单类型（C目录 M菜单 F按钮）
    component: string; // 组件路径
    meta: MetaProp; // 菜单前端额外配置
    intro: string; // 菜单介绍
    appId: string; // 应用 ID
    status: number; // 状态
    createTime: string; // 创建时间
    children: MenuInfo[]; // 子数据
  }

  export interface MenuTreeList {
    id: string;
    label: string;
    parentId: string;
    weight: number;
    icon: string;
    children: MenuTreeList[];
    value: string;
  }

  export interface MenuTreeTable extends MenuInfo {
    children: MenuTreeTable[];
  }
}

const baseUri = "/system/menu";

export const listRoutes = (appId: string) => {
  return http.get<http.Response<RouterConfigRaw[]>>(`${baseUri}/listRoutes/${appId}`);
};

export const listMenuTreeTableByApp = (params: { appId: string }) => {
  return http.get<http.Response<Menu.MenuInfo[]>>(`${baseUri}/treeTable`, params);
};

export const listMenuTreeSelectByApp = (params: { appId: string }) => {
  return http.get<http.Response<Menu.MenuInfo[]>>(`${baseUri}/treeSelect`, params);
};

export const listMenuIdsByRoleId = (appId: string, roleId: string) => {
  return http.get<http.Response<string[]>>(`${baseUri}/listMenuIdsByRoleId/${appId}/${roleId}`);
};

export const listMenuListByRoleId = (appId: string, roleId: string) => {
  return http.get<http.Response<Menu.MenuTreeList[]>>(`${baseUri}/listMenuListByRoleId/${appId}/${roleId}`);
};

export const addOne = (data: Menu.MenuInfo) => {
  return http.post<http.Response<boolean>>(baseUri, data);
};

export const editOne = (data: RequiredKeyPartialOther<Menu.MenuInfo, "id">) => {
  return http.put<http.Response<boolean>>(baseUri, data);
};

export const deleteOne = (data: Menu.MenuInfo) => {
  return http.delete<http.Response<boolean>>(`${baseUri}/${data.id}/${data.menuId}`);
};

/**
 * 菜单导出
 */
export const exportExcel = (params: Partial<Menu.MenuInfo>) => {
  return http.post<any>(`${baseUri}/export`, params, { responseType: "blob" });
};
