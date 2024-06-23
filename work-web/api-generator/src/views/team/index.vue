<template>
  <el-card :class="prefixClass" shadow="never">
    <template #header>
      <el-space>
        <span>{{ route.meta.params?.teamName }}</span>
        <ElTag type="warning">{{ route.meta.params?.teamRole }}</ElTag>
      </el-space>
    </template>
    <el-tabs v-model="teamTabActiveName">
      <el-tab-pane v-for="tab in teamTabList" :key="tab.name" :label="tab.label" :name="tab.name" :lazy="tab.lazy">
        <component :is="tab.component" class="p-10"></component>
      </el-tab-pane>
    </el-tabs>
  </el-card>
</template>

<script setup lang="ts" name="Team">
import { useDesign } from "@work/hooks";
import Project from "./components/Project/index.vue";
import Members from "./components/Members/index.vue";
import TeamSetting from "./components/TeamSetting/index.vue";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("team");

const route = useRoute();

const teamTabActiveName = ref("团队项目");

const teamTabList = [
  { label: "团队项目", name: "团队项目", lazy: false, component: Project },
  { label: "成员/权限", name: "成员/权限", lazy: true, component: Members },
  { label: "团队设置", name: "团队设置", lazy: true, component: TeamSetting },
];
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-team;

.#{$prefix-class} {
  flex: 1;
  padding: 10px;
  background-color: #ffffff;

  :deep(.el-card__header) {
    padding: 10px;
    border-bottom: none;
  }

  :deep(.el-card__body) {
    padding: 0 10px;
  }
}
</style>
