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
        v-model="selected.dataSourceId"
        placeholder="请选择数据源（必选）"
        style="width: 240px"
        @change="handleDataSourceTypeChange"
        clearable
        filterable
        :disabled
      >
        <el-option
          v-for="item in dataSourceList"
          :key="item.dataSourceId"
          :label="item.dataSourceName"
          :value="item.dataSourceId"
        />
      </el-select>
      <el-select
        v-model="selected.schema"
        placeholder="请选择 Schema（可选）"
        style="width: 240px"
        @change="handleSchemaChange"
        clearable
        filterable
        :disabled
      >
        <el-option v-for="item in schemaList" :key="item" :label="item" :value="item" />
      </el-select>
      <el-select
        v-model="selected.tableName"
        placeholder="请选择 Table（可选）"
        style="width: 240px"
        @change="handleTableChange"
        filterable
        :disabled
      >
        <el-option-group v-for="table in tableList" :key="table.tableType" :label="table.tableType">
          <el-option v-for="item in table.tableNameList" :key="item" :label="item" :value="item" />
        </el-option-group>
      </el-select>

      <el-select
        v-model="sqlTemplate"
        placeholder="请选择模板生成类型（可选）"
        style="width: 240px"
        @change="handleSqlTemplateChange"
        clearable
        filterable
        :disabled
      >
        <el-option v-for="item in sqlGeneratorTemplate" :key="item.value" :label="item.label" :value="item.value" />
      </el-select>

      <div class="flex-1 text-right">
        <el-button :disabled>格式化</el-button>
        <el-button type="primary" :disabled v-throttle="{ onClick: handleRun, time: 4000 }">运 行</el-button>
      </div>
    </el-space>

    <SqlForm ref="sqlFormRef" :tableNameList :dataSourceId="selected.dataSourceId" />

    <DataTable v-if="data?.length" :data />
  </el-space>
</template>

<script setup lang="ts" name="ResponseData">
import { message, useDesign } from "work";
import { ServiceKey } from "@/config/symbol";
import {
  executeSelect,
  listByProjectId,
  listSchemaByDataSource,
  listTableBySchema,
  type DataSource,
} from "@/api/dataSource";
import DataTable from "./dataTable.vue";
import SqlForm from "./sqlForm.vue";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("response-data");

const sqlFormRef = shallowRef<InstanceType<typeof SqlForm>>();
const sqlTemplate = ref("");
const serviceInfo = inject(ServiceKey);
const disabled = computed(() => unref(serviceInfo)?.projectRole === "只读成员");

// 数据源列表
const dataSourceList = ref<DataSource.DataSourceInfo[]>([]);
const schemaList = ref<string[]>([]);
const tableList = ref<DataSource.Table[]>([]);

// 表名列表
const tableNameList = computed(() => {
  let tableNameList: string[] = [];
  unref(tableList).forEach(item => (tableNameList = [...tableNameList, ...item.tableNameList]));
  return tableNameList;
});

// 缓存选中的值
const selected = reactive({
  dataSourceId: "",
  schema: "",
  tableName: "",
});

watch(
  () => unref(serviceInfo),
  async () => {
    const { projectId, dataSourceId } = unref(serviceInfo) || {};
    selected.dataSourceId = dataSourceId || "";
    dataSourceId && handleDataSourceTypeChange(dataSourceId);

    const res = await listByProjectId(projectId || "");
    if (res.code === 200) dataSourceList.value = res.data;
  }
);

const handleDataSourceTypeChange = async (value: string) => {
  if (!value) {
    selected.schema = "";
    selected.tableName = "";
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
    selected.tableName = "";
    tableList.value = [];
    return;
  }
  selected.schema = value;
  const res = await listTableBySchema(selected.dataSourceId, value);
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
  const sql = unref(sqlFormRef)?.model.selectSql;
  if (!sql) return message.error("请输入 SQL");

  const res = await executeSelect({
    dataSourceId: selected.dataSourceId,
    schema: selected.schema,
    sql,
  });
  if (res.code === 200) {
    data.value = res.data;
    if (res.data?.length) message.success("执行成功，4 秒内无法再次点击");
    else message.success("执行成功，数据为空，4 秒内无法再次点击");
  }
};
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-response-data;

.#{$prefix-class} {
  width: 100%;
}
</style>
