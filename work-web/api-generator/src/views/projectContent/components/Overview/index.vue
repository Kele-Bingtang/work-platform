<template>
  <div :class="prefixClass">
    <el-card shadow="never">
      <div v-for="item in projectBaseInfo" :key="item.value" class="flex h-8 mb-2.5 text-sm leading-8 rounded-2xl">
        <span class="w-120 text-white text-center bg-blue-500 rounded-2xl">
          {{ item.label }}
        </span>
        <span class="flx-align-center flex-1 pl-4 text-white bg-primary rounded-2xl">
          <span class="pr-2">{{ item.value }}</span>
          <el-icon class="copy-icon cursor-pointer" v-copy="item.value"><DocumentCopy /></el-icon>
        </span>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts" name="ProjectOverview">
import { ProjectKey } from "@/config/symbol";
import { useDesign } from "@work/hooks";
import { DocumentCopy } from "@element-plus/icons-vue";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("project-overview");

const projectInfo = inject(ProjectKey);

const url = computed(() => import.meta.env.VITE_API_URL + "/generic-api" + unref(projectInfo)?.baseUrl);

const projectBaseInfo = [
  { label: "接口地址", value: unref(url) },
  { label: "项目密钥", value: unref(projectInfo)?.secretKey },
];
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-project-main;

.#{$prefix-class} {
  flex: 1;
}
</style>
