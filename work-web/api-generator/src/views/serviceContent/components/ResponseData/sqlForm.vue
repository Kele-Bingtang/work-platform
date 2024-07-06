<template>
  <div class="flex card">
    <CodeMirror v-model="model.selectSql" :localTheme="oneDark" :lang="sql()" :height="500" fullScreen class="flex-1" />
    <el-form :model="model" label-width="100" class="w-[300px] ml-2.5">
      <el-form-item label="Insert Table">
        <el-autocomplete
          v-model="model.insertTable"
          :fetch-suggestions="querySearch"
          clearable
          placeholder="请输入表名"
          :trigger-on-focus="false"
        />
      </el-form-item>
      <el-form-item label="Update Table">
        <el-autocomplete
          v-model="model.updateTable"
          :fetch-suggestions="querySearch"
          clearable
          placeholder="请输入表名"
          :trigger-on-focus="false"
        />
      </el-form-item>
      <el-form-item label="Delete Table">
        <el-autocomplete
          v-model="model.deleteTable"
          :fetch-suggestions="querySearch"
          clearable
          placeholder="请输入表名"
          :trigger-on-focus="false"
        />
      </el-form-item>
      <el-button v-if="!serviceInfo?.exitCol" type="primary" @click="handleGenerateCol">保存并生成列配置项</el-button>
      <el-button type="primary" v-throttle="{ onClick: handleSave, time: 4000 }">保 存</el-button>
    </el-form>
  </div>
</template>

<script setup lang="ts" name="SqlForm">
import { CodeMirror, message } from "work";
import { oneDark } from "@codemirror/theme-one-dark";
import { sql } from "@codemirror/lang-sql";
import { ServiceKey } from "@/config/symbol";
import { editService, generateCol } from "@/api/service";

const props = defineProps<{
  tableNameList: string[];
  dataSourceId: string;
}>();

const serviceInfo = inject(ServiceKey);

const model = reactive({
  selectSql: "",
  insertTable: "",
  updateTable: "",
  deleteTable: "",
});

watch(
  () => unref(serviceInfo),
  () => {
    model.selectSql = unref(serviceInfo)?.selectSql || "";
    model.insertTable = unref(serviceInfo)?.insertTable || "";
    model.updateTable = unref(serviceInfo)?.updateTable || "";
    model.deleteTable = unref(serviceInfo)?.deleteTable || "";
  }
);

/**
 * ElAutocomplete 查询回调
 */
const querySearch = (queryString: string, cb: any) => {
  const result = queryString
    ? props.tableNameList.filter(item => item.toLowerCase().includes(queryString.toLowerCase()))
    : props.tableNameList;

  cb(result.map(item => ({ value: item })));
};

const handleSave = async () => {
  if (!props.dataSourceId) return message.warning("数据源必选");
  const service = unref(serviceInfo);
  if (!service) return message.warning("服务不存在");

  const { id } = service;

  const res = await editService({ id, dataSourceId: props.dataSourceId, ...unref(model) });
  if (res.code === 200) message.success("保存成功，4s 内无法再次点击");
};

const handleGenerateCol = async () => {
  if (!props.dataSourceId) return message.warning("数据源必选");
  const service = unref(serviceInfo);
  if (!service) return message.warning("服务不存在");

  const { id } = service;

  const res = await generateCol({
    id,
    dataSourceId: props.dataSourceId,
    ...unref(model),
    serviceId: unref(serviceInfo)?.serviceId || "",
    categoryId: unref(serviceInfo)?.categoryId || "",
    projectId: unref(serviceInfo)?.projectId || "",
    teamId: unref(serviceInfo)?.teamId || "",
  });
  unref(serviceInfo)!.exitCol = true;

  if (res.code === 200) message.success(res.message);
};

defineExpose({ model });
</script>

<style lang="scss" scoped></style>
