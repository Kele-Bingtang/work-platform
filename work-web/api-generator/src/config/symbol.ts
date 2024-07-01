import type { Project } from "@/api/project";
import type { Service } from "@/api/service";

export const ProjectKey: InjectionKey<Ref<Project.ProjectInfo | undefined>> = Symbol("project");
export const ServiceKey: InjectionKey<Ref<Service.ServiceInfo | undefined>> = Symbol("service");
