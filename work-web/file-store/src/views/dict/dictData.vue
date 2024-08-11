<template>
  <ProTable
    ref="proTableRef"
    :request-api="!isCascade ? listPage : listDataTreeTable"
    :columns="columns"
    :init-request-param="initRequestParam"
    :search-cols="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
    :pagination="!isCascade"
    :dialogForm="dialogForm"
    :exportFile
  >
    <template #operationExtra="{ row, operate }" v-if="isCascade">
      <el-button link size="small" :icon="Plus" @click="operate?.handleAdd({ parentId: row.dataId })">新增</el-button>
    </template>
  </ProTable>
</template>

<script setup lang="ts" name="DictData">
import { ProTable, downloadByData } from "work";
import {
  listPage,
  listDataTreeTable,
  addDictData,
  editDictData,
  removeDictData,
  type DictData,
  exportExcel,
} from "@/api/dictData";
import { type DialogForm, type TableColumnProps } from "@work/components";
import { dictDataElFormProps, useFormSchema } from "./useFormSchema";
import { Plus } from "@element-plus/icons-vue";
import { ElMessageBox } from "element-plus";

export interface DictDataProps {
  dictCode: string;
  isCascade: number;
}

// 接受父组件参数，配置默认值
const props = defineProps<DictDataProps>();

const initRequestParam = reactive({
  dictCode: computed(() => props.dictCode),
});

const columns: TableColumnProps<DictData.DictDataInfo>[] = [
  { type: "index", label: "#", width: 80 },
  { prop: "dictLabel", label: "字典标签", align: "left", search: { el: "el-input" } },
  { prop: "dictValue", label: "字典键值" },
  { prop: "dictSort", label: "字典排序" },
  { prop: "createTime", label: "创建时间" },
  { prop: "operation", label: "操作", width: computed(() => (props.isCascade ? 200 : 160)) },
];

const dialogForm: DialogForm = {
  formProps: {
    elFormProps: dictDataElFormProps,
    schema: useFormSchema(
      computed(() => props.dictCode),
      computed(() => props.isCascade)
    ).dictDataSchema,
  },
  id: ["id"],
  addApi: params => addDictData({ ...params }),
  editApi: editDictData,
  removeApi: removeDictData,
  beforeEdit: form => {
    if (form.tagEl === undefined) form.tagEl = "";
  },
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "45%",
    height: 300,
    top: "5vh",
    closeOnClickModal: false,
  },
};

const exportFile = (_: Record<string, any>[], searchParam: Record<string, any>) => {
  ElMessageBox.confirm("确认导出吗？", "温馨提示", { type: "warning" }).then(() => {
    exportExcel({ ...searchParam, dictCode: props.dictCode }).then(res => {
      downloadByData(res, `dictData_${new Date().getTime()}.xlsx`);
    });
  });
};
</script>
