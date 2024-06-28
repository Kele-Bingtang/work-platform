import http from "@/config/request";

export declare namespace Category {
  interface CategoryInfo {
    id: number; // 主键
    categoryId: string; // 分类 ID
    categoryCode: string; // 目录编码
    categoryName: string; // 目录名称
    isMain: number; // 是否是主目录（0 不是 1 是）
    orderNum: number; // 显示顺序
    projectId: string; // 项目 id
    teamId: string; // 团队 ID
  }

  type CategorySearch = Partial<CategoryInfo>;
  type CategoryInsert = RequiredKeyPartialOther<Omit<CategoryInfo, "id">, "teamId" | "projectId">;
  type CategoryUpdate = RequiredKeyPartialOther<CategoryInfo, "id" | "categoryId">;
  type CategoryDelete = RequiredKeyPartialOther<CategoryInfo, "categoryId">;
}

const baseUri = "/category";

export const listCategory = (params?: Category.CategorySearch) => {
  return http.get<http.Response<Category.CategoryInfo[]>>(`${baseUri}/list`, params);
};

export const listCategoryPage = (params?: http.PageData<Category.CategorySearch>) => {
  return http.get<http.Response<Category.CategoryInfo[]>>(`${baseUri}/listPage`, params);
};

export const addCategory = (data: Category.CategoryInsert) => {
  return http.post<http.Response<string>>(baseUri, data);
};

export const editCategory = (data: Category.CategoryUpdate) => {
  return http.put<http.Response<string>>(baseUri, data);
};

export const removeCategory = (data: Category.CategoryDelete) => {
  return http.delete<http.Response<string>>(`${baseUri}/${data.projectId}`);
};
