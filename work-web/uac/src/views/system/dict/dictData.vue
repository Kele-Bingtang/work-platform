<template>
  <ProTable
    ref="proTableRef"
    :request-api="list"
    :columns="columns"
    :init-request-param="initRequestParam"
    :search-cols="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
    :detailForm="detailForm"
  ></ProTable>
</template>

<script setup lang="ts" name="DictData">
import { ProTable } from "work";
import { list, addOne, editOne, removeOne, type DictData } from "@/api/system/dictData";
import { type DialogForm, type TableColumnProps } from "@work/components";
import { useFormOptions } from "./useFormOptions";

export interface DictDataProps {
  dictCode: string;
  appId: string;
}

// 接受父组件参数，配置默认值
const props = defineProps<DictDataProps>();

const initRequestParam = reactive({
  dictCode: computed(() => props.dictCode),
});

const columns: TableColumnProps<DictData.DictDataInfo>[] = [
  { type: "index", label: "#", width: 80 },
  { prop: "dictValue", label: "字典键值" },
  { prop: "dictLabel", label: "字典标签", search: { el: "el-input" } },
  { prop: "dictSort", label: "字典排序" },
  { prop: "createTime", label: "创建时间" },
  { prop: "operation", label: "操作" },
];

const detailForm = reactive<DialogForm>({
  options: useFormOptions(
    computed(() => ""),
    computed(() => props.dictCode)
  ).dictDataOptions,
  addApi: params => addOne({ ...params, appId: props.appId }),
  editApi: editOne,
  deleteApi: removeOne,
  beforeEdit: form => {
    if (form.tagEl === undefined) form.tagEl = "";
  },
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "30%",
    top: "5vh",
    closeOnClickModal: false,
  },
});
</script>

<style lang="scss" scoped></style>
