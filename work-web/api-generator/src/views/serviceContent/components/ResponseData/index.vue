<template>
  <el-space :class="prefixClass" fill>
    <el-alert type="info" effect="dark">
      <template #title>
        数据源为「必选」，Schema 和 Table 「可选」，因为 SQL 可以在数据源内查询任意 Schema 的表，如
        <span class="text-[14px]">select * from schema.table</span>
        。选择 Schema 和 Table 仅仅为了方便生成模板 SQL
      </template>
    </el-alert>
    <el-space wrap class="bg-white p-2.5">
      <el-select
        v-model="dataSourceType"
        placeholder="请选择数据源（必选）"
        style="width: 240px"
        @change="handleDataSourceTypeChange"
        clearable
        filterable
      >
        <el-option
          v-for="item in dataSourceList"
          :key="item.dataSourceId"
          :label="item.dataSourceName"
          :value="item.dataSourceId"
        />
      </el-select>
      <el-select
        v-model="schema"
        placeholder="请选择 Schema（可选）"
        style="width: 240px"
        @change="handleSchemaChange"
        clearable
        filterable
      >
        <el-option v-for="item in schemaList" :key="item" :label="item" :value="item" />
      </el-select>
      <el-select
        v-model="table"
        placeholder="请选择 Table（可选）"
        style="width: 240px"
        @change="handleTableChange"
        filterable
      >
        <el-option-group v-for="table in tableList" :key="table.tableType" :label="table.tableType">
          <el-option v-for="item in table.tableNameList" :key="item" :label="item" :value="item" />
        </el-option-group>
      </el-select>

      <el-select
        v-model="sqlTemplate"
        placeholder="请选择生成模板类型"
        style="width: 240px"
        @change="handleSqlTemplateChange"
        clearable
        filterable
      >
        <el-option v-for="item in sqlGeneratorTemplate" :key="item.value" :label="item.label" :value="item.value" />
      </el-select>

      <div class="flex-1 text-right">
        <el-button>格式化</el-button>
        <el-button type="primary" @click="handleRun">运 行</el-button>
      </div>
    </el-space>

    <CodeMirror v-model="code" :localTheme="oneDark" :lang="sql()" :height="500" fullScreen />

    <DataTable v-if="data" :data />
  </el-space>
</template>

<script setup lang="ts" name="ResponseData">
import { CodeMirror, useDesign } from "work";
import { oneDark } from "@codemirror/theme-one-dark";
import { sql } from "@codemirror/lang-sql";
import { ServiceKey } from "@/config/symbol";
import {
  executeSelect,
  listByProjectId,
  listSchemaByDataSource,
  listTableBySchema,
  type DataSource,
} from "@/api/dataSource";
import DataTable from "./dataTable.vue";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("response-data");

const code = ref("");
const dataSourceType = ref("");
const schema = ref("");
const table = ref("");
const sqlTemplate = ref("");
const serviceInfo = inject(ServiceKey);

// 数据源列表
const dataSourceList = ref<DataSource.DataSourceInfo[]>([]);
const schemaList = ref<string[]>([]);
const tableList = ref<DataSource.Table[]>([]);

// 缓存选中的值
const selected = reactive({
  dataSourceId: "",
  schema: "",
  tableName: "",
});

watchEffect(async () => {
  const projectId = unref(serviceInfo)?.projectId as string;
  const res = await listByProjectId(projectId);
  if (res.code === 200) dataSourceList.value = res.data;
});

const handleDataSourceTypeChange = async (value: string) => {
  if (!value) {
    schema.value = "";
    table.value = "";
    schemaList.value = [];
    tableList.value = [];
    return;
  }
  selected.dataSourceId = value;
  const res = await listSchemaByDataSource(value);
  if (res.code === 200) schemaList.value = res.data;
};

const handleSchemaChange = async (value: string) => {
  if (!value) {
    table.value = "";
    tableList.value = [];
    return;
  }
  selected.schema = value;
  const res = await listTableBySchema(unref(selected.dataSourceId), value);
  if (res.code === 200) tableList.value = res.data;
};

const handleTableChange = (value: string) => {
  selected.tableName = value;
};

const sqlGeneratorTemplate = [
  { value: "Select", label: "Select" },
  { value: "Inset", label: "Inset" },
  { value: "Update", label: "Update" },
  { value: "Delete", label: "Delete" },
  { value: "DDL", label: "DDL" },
];

const handleSqlTemplateChange = (value: string) => {
  console.log(value);
};

const data = ref<Record<string, any>[]>([]);
const handleRun = async () => {
  const res = await executeSelect({
    dataSourceId: selected.dataSourceId,
    schema: selected.schema,
    sql: unref(code),
  });
  if (res.code === 200) data.value = res.data;
};
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-response-data;

.#{$prefix-class} {
  width: 100%;
}
</style>
