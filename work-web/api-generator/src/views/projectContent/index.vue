<template>
  <div :class="prefixClass">
    <el-tabs v-model="serviceTabActiveName">
      <el-tab-pane v-for="tab in teamTabList" :key="tab.name" :label="tab.label" :name="tab.name" :lazy="tab.lazy">
        <component :is="tab.component" class="pt-2.5"></component>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup lang="ts" name="ProjectMain">
import Service from "./components/Service/index.vue";
import Category from "./components/Category/index.vue";
import Overview from "./components/Overview/index.vue";
import ProjectSetting from "./components/ProjectSetting/index.vue";
import { useDesign } from "work";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("project-main");

const serviceTabActiveName = ref("服务管理");

const teamTabList = [
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
