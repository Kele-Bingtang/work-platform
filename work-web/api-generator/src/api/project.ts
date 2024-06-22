import http from "@/config/request";

export declare namespace ProjectModule {
  interface Project {
    id: number;
    projectName: string;
    baseUrl: string;
    description: string;
    secretKey: string;
    databaseName: string;
    createUser: string;
    createTime: string;
    modifyUser: string;
    modifyTime: string;
  }

  interface UserProject {
    id: number;
    userId: number;
    projectId: number;
    roleId: number;
    enterType: number;
    createTime: string;
    modifyTime: string;
  }

  type ProjectInsert = Omit<Project, "id" | "secretKey" | "createTime" | "modifyTime">;

  type ProjectUpdate = RequiredKeyPartialOther<
    Omit<Project, "secretKey" | "createUser" | "createTime" | "modifyTime">,
    "id"
  >;

  type ProjectDelete = RequiredKeyPartialOther<Project, "id">;

  type ProjectSearch = Partial<Project>;

  type UserProjectSearch = Partial<UserProject>;
}

export const defaultProjectData: Partial<ProjectModule.Project> = {
  id: -1,
  projectName: "",
  baseUrl: "",
  description: "",
  secretKey: "",
  databaseName: "",
};

export const queryGenericOneProject = (secretKey: string) => {
  return http.request<http.Response<ProjectModule.Project>>({
    url: `/project/queryOneProject/${secretKey}`,
    method: "get",
  });
};

export const queryProjectListOwner = (project?: ProjectModule.UserProjectSearch) => {
  return http.request<http.Response<ProjectModule.Project[]>>({
    url: "/project/queryProjectListOwner",
    method: "get",
    params: { ...project },
  });
};

export const queryProjectListPages = (project?: ProjectModule.ProjectSearch, page?: http.PageParams) => {
  return http.request<http.Response<ProjectModule.Project[]>>({
    url: "/project/queryProjectListPages",
    method: "get",
    params: {
      ...project,
      ...page,
    },
  });
};

export const insertProject = (project: ProjectModule.ProjectInsert, secretKey: string) => {
  return http.request<http.Response<string>>({
    url: "/project/insertProject",
    method: "post",
    data: project,
    headers: {
      secretKey,
    },
  });
};

export const updateProject = (project: ProjectModule.ProjectUpdate, secretKey: string) => {
  return http.request<http.Response<string>>({
    url: "/project/updateProject",
    method: "post",
    data: project,
    headers: {
      secretKey,
    },
  });
};

export const deleteProject = (project: ProjectModule.ProjectDelete, secretKey: string) => {
  return http.request<http.Response<string>>({
    url: "/project/deleteProjectById",
    method: "post",
    data: project,
    headers: {
      secretKey,
    },
  });
};

export const queryDatabaseName = (databaseName?: string) => {
  const url = databaseName ? `/project/queryDatabaseName/${databaseName}` : "/project/queryDatabaseName";
  return http.request<http.Response<string[]>>({
    url,
    method: "get",
  });
};
