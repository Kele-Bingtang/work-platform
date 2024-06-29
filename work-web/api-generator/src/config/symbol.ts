import type { Project } from "@/api/project";

export const ProjectKey: InjectionKey<Ref<Project.ProjectInfo | undefined>> = Symbol("project");
