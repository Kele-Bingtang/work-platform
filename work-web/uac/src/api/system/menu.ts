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
    param: string; // 路由参数
    icon: string; // 图标
    orderNum: number; // 显示顺序
    menuType: string; // 菜单类型（M目录 C菜单 F按钮）
    component: string; // 组件路径
    visible: number; // 显示状态（0 隐藏 1 显示）
    isCache: number; // 是否缓存（0 不缓存 1 缓存）
    isFrame: number; // 是否为外链（0 否 1 是）
    meta: string; // 菜单前端额外配置
    intro: string; // 菜单介绍
    appId: string; // 应用 ID
    status: number; // 状态
    createTime: string; // 创建时间
  }

  export interface MenuTreeList {
    id: string;
    label: string;
    parentId: string;
    weight: number;
    icon: string;
    children: MenuTreeList[];
    status: number;
  }

  export interface MenuTreeTable extends MenuInfo {
    children: MenuTreeTable[];
  }

  export interface BackstageMenuList {
    imageIcon: string;
    menuCode: string;
    pagePath: string;
    menuName: string;
    menuUrl: string;
    parentMenuCode: string;
    seq: number;
    children?: BackstageMenuList[];
  }
}

export const getMenuList = () => {
  // 模拟请求菜单
  return Promise.resolve([] as Menu.BackstageMenuList[]);
  // return http.request<http.JsonResponse<BackstageMenuList[]>>({
  //   url: "/getMenuList",
  //   method: "get",
  // });
};

const baseUri = "/system/menu";

export const listMenuTreeTableByApp = (params: http.Page<{ appId: number }>) => {
  return http.get<http.Response<Menu.MenuInfo[]>>(`${baseUri}/treeTable`, params);
};

export const listMenuTreeSelectByApp = (params: http.Page<{ appId: number }>) => {
  return http.get<http.Response<Menu.MenuInfo[]>>(`${baseUri}/treeSelect`, params);
};

export const addOne = (data: Menu.MenuInfo) => {
  return http.post<http.Response<string>>(baseUri, data);
};

export const editOne = (data: RequiredKeyPartialOther<Menu.MenuInfo, "id">) => {
  return http.put<http.Response<string>>(baseUri, data);
};

export const deleteOne = (data: Menu.MenuInfo) => {
  return http.delete<http.Response<string>>(`${baseUri}/${data.id}/${data.menuId}`);
};
