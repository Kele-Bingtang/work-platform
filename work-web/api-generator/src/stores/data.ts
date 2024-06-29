import type { Project } from "@/api/project";
import type { Team } from "@/api/team";
import { defineStore } from "pinia";

export const useDataStore = defineStore("dataStore", () => {
  const teamInfo = ref<Team.TeamInfo>();
  const projectInfo = ref<Project.ProjectInfo>();

  const setTeamInfo = (teamInfoParam: Team.TeamInfo) => {
    teamInfo.value = teamInfoParam;
  };

  const setProjectInfo = (projectInfoParam: Project.ProjectInfo) => {
    projectInfo.value = projectInfoParam;
  };

  return {
    teamInfo,
    projectInfo,
    setTeamInfo,
    setProjectInfo,
  };
});
