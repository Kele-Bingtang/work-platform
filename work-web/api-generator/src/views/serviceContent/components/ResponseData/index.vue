<template>
  <el-space :class="prefixClass" fill>
    <el-space wrap>
      <el-select v-model="dataSourceType" placeholder="请选择数据源" style="width: 240px">
        <el-option v-for="item in dataSourceTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
      </el-select>
      <el-select v-model="schema" placeholder="请选择 Schema" style="width: 240px">
        <el-option v-for="item in schemaOptions" :key="item.value" :label="item.label" :value="item.value" />
      </el-select>
      <div class="flex-1 text-right">
        <el-button>格式化</el-button>
        <el-button type="primary">运 行</el-button>
      </div>
    </el-space>
    <CodeMirror :localTheme="oneDark" :lang="sql()" :height="600" fullScreen />
  </el-space>
</template>

<script setup lang="ts" name="ResponseData">
import { CodeMirror, useDesign } from "work";
import { oneDark } from "@codemirror/theme-one-dark";
import { sql } from "@codemirror/lang-sql";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("response-data");

const dataSourceType = ref("");
const schema = ref("");

const dataSourceTypeOptions = [
  { value: "MySQL", label: "MySQL" },
  { value: "Oracle", label: "Oracle" },
];

const schemaOptions = [
  { value: "work_ag", label: "work_ag" },
  { value: "work_uac", label: "work_uac" },
];
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-response-data;

.#{$prefix-class} {
  flex: 1;
  width: 100%;
}
</style>
