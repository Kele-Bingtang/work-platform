<template>
  <div :class="prefixClass">
    <el-tabs v-model="projectTabActiveName">
      <el-tab-pane v-for="tab in projectTabList" :key="tab.name" :label="tab.label" :name="tab.name" :lazy="tab.lazy">
        <component :is="tab.component" class="pt-2.5"></component>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup lang="ts" name="ProjectMain">
import { Category, Overview, ProjectSetting, Service } from "./components";
import { message, useDesign } from "work";
import { getProjectByProjectId, type Project } from "@/api/project";
import { ProjectKey, ProjectOnGetKey } from "@/config/symbol";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("project-main");

const route = useRoute();

const projectInfo = ref<Project.ProjectInfo>();

onMounted(() => {
  initProject();
});

const initProject = async () => {
  // 初始化项目信息，给子组件使用
  const projectId = route.params.projectId as string;
  const res = await getProjectByProjectId(projectId);
  if (!res.data) return message.error("项目不存在");

  projectInfo.value = res.data;
};

provide(ProjectKey, projectInfo);
provide(ProjectOnGetKey, initProject);

const projectTabActiveName = ref("服务管理");
const projectTabList = [
  { label: "服务管理", name: "服务管理", lazy: false, component: Service },
  { label: "目录管理", name: "目录管理", lazy: true, component: Category },
  { label: "项目概览", name: "项目概览", lazy: true, component: Overview },
  { label: "项目设置", name: "项目设置", lazy: true, component: ProjectSetting },
];
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-project-main;

.#{$prefix-class} {
  flex: 1;

  .el-tabs {
    display: flex;
    flex-direction: column;
    height: 100%;

    :deep(.el-tabs__content, .el-tab-pane) {
      flex: 1;
    }

    :deep(.el-tab-pane) {
      height: 100%;
    }
  }

  :deep(.el-tabs__header) {
    padding: 0 15px;
    margin: 0;
    background: #ffffff;
  }
}
</style>
