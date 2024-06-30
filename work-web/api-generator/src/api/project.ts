import http from "@/config/request";

export declare namespace Project {
  interface ProjectInfo {
    id: number; // 主键
    projectId: string; // 项目 ID
    projectName: string; // 项目名
    baseUrl: string; // 接口基础路径
    description: string; // 项目描述
    secretKey: string; // 项目密钥，唯一
    dataSourceId: string; // 数据库名称
    teamId: string; // 团队 ID
  }

  type ProjectSearch = Partial<ProjectInfo & { belongType: number }>;
  type ProjectInsert = RequiredKeyPartialOther<Omit<ProjectInfo, "id" | "secretKey">, "teamId">;
  type ProjectUpdate = RequiredKeyPartialOther<Omit<ProjectInfo, "secretKey">, "id" | "teamId">;
  type ProjectDelete = RequiredKeyPartialOther<ProjectInfo, "projectId">;
}

const baseUri = "/project";

export const getProjectByProjectId = (projectId: string) => {
  return http.get<http.Response<Project.ProjectInfo>>(`${baseUri}/getByProjectId/${projectId}`);
};

export const listProject = (params?: Project.ProjectSearch) => {
  return http.get<http.Response<Project.ProjectInfo[]>>(`${baseUri}/list`, params);
};

export const addProject = (data: Project.ProjectInsert) => {
  return http.post<http.Response<string>>(baseUri, data);
};

export const editProject = (data: Project.ProjectUpdate) => {
  return http.put<http.Response<string>>(baseUri, data);
};

export const removeProject = (data: Project.ProjectDelete) => {
  return http.delete<http.Response<string>>(`${baseUri}/${data.projectId}`);
};
