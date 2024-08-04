<template>
  <el-space fill :class="prefixClass">
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

    <el-card>
      <template #header>项目统计</template>
      <el-space :size="100">
        <el-statistic :value="baseProjectStatistic?.serviceCount" title="接口数"></el-statistic>
        <el-statistic :value="baseProjectStatistic?.categoryCount" title="目录数"></el-statistic>
      </el-space>
    </el-card>
  </el-space>
</template>

<script setup lang="ts" name="ProjectOverview">
import { ProjectKey } from "@/config/symbol";
import { useDesign } from "@work/hooks";
import { DocumentCopy } from "@element-plus/icons-vue";
import { getBaseProjectStatistic, type ProjectStatistic } from "@/api/projectStatistic";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("project-overview");

const projectInfo = inject(ProjectKey);

const url = computed(() => import.meta.env.VITE_API_URL + "/generic-api" + unref(projectInfo)?.baseUrl);

const projectBaseInfo = [
  { label: "接口地址", value: unref(url) },
  { label: "项目密钥", value: unref(projectInfo)?.secretKey },
];

const baseProjectStatistic = ref<ProjectStatistic.ProjectStatisticInfo>();

onMounted(async () => {
  if (!unref(projectInfo)?.projectId) return;
  const res = await getBaseProjectStatistic(unref(projectInfo)?.projectId!);
  if (res.code === 200) baseProjectStatistic.value = res.data;
});
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-project-overview;

.#{$prefix-class} {
  width: 100%;
}
</style>
