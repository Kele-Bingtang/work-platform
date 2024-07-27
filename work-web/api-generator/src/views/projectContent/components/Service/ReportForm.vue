<template>
  <ProForm v-model="form" :schema :el-form-props="{ rule }" :include-model-keys="['id', 'projectId']" />
</template>

<script setup lang="ts" name="ReportForm">
import { type FormSchemaProps, ProForm } from "work";
import { editReport, type Report } from "@/api/report";
import type { FormRules } from "element-plus";

const props = defineProps<{
  reportInfo: Report.ReportInfo;
}>();

const form = ref<Partial<Report.ReportInfo>>({});

watch(
  () => props.reportInfo,
  val => {
    form.value = val;
  },
  { immediate: true }
);

const handleEditReport = async () => {
  const res = await editReport(form.value);
  if (res.code === 200) return true;
  return false;
};

const selectEnum = [
  { value: 0, label: "关闭" },
  { value: 1, label: "开启" },
];

const rule: FormRules = {
  reportTitle: [{ required: true, message: "请输入报表名称", trigger: "blur" }],
  description: [{ required: true, message: "请输入报表描述", trigger: "blur" }],
};

const schema: FormSchemaProps[] = [
  { prop: "reportTitle", label: "报表名称", el: "el-input", col: { span: 24 } },
  {
    prop: "description",
    label: "报表描述",
    el: "el-input",
    props: { type: "textarea", placeholder: "请输入 团队描述" },
    col: { span: 24 },
  },
  {
    prop: "pageSize",
    label: "显示条数",
    el: "el-select",
    enum: [10, 20, 50, 100, 200].map(item => ({ value: item, label: item + "" })),
    defaultValue: 20,
  },
  { prop: "dialogWidth", label: "弹框宽度", el: "el-input", defaultValue: "50%" },
  { prop: "allowAdd", label: "添加开启", el: "el-select", enum: selectEnum, defaultValue: 1 },
  { prop: "allowEdit", label: "编辑开启", el: "el-select", enum: selectEnum, defaultValue: 1 },
  { prop: "allowDelete", label: "删除开启", el: "el-select", enum: selectEnum, defaultValue: 1 },
  { prop: "allowFilter", label: "查询开启", el: "el-select", enum: selectEnum, defaultValue: 1 },
  { prop: "allowExport", label: "导出开启", el: "el-select", enum: selectEnum, defaultValue: 1 },
  { prop: "allowRow", label: "行数开启", el: "el-select", enum: selectEnum, defaultValue: 1 },
];

defineExpose({ handleEditReport });
</script>

<style lang="scss" scoped></style>
