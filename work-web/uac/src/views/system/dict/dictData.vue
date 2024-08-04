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
    :disabled-button="!hasAuth('system:dict:export') ? ['export'] : []"
  >
    <template #operationExtra="{ row, operate }" v-if="isCascade">
      <el-button
        v-auth="['system:dict:add']"
        link
        size="small"
        :icon="Plus"
        @click="operate?.handleAdd({ parentId: row.dataId })"
      >
        新增
      </el-button>
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
} from "@/api/system/dictData";
import { type DialogForm, type TableColumnProps } from "@work/components";
import { dictDataElFormProps, useFormSchema } from "./useFormSchema";
import { Plus } from "@element-plus/icons-vue";
import { ElMessageBox } from "element-plus";
import { usePermission } from "@/hooks";

export interface DictDataProps {
  dictCode: string;
  appId: string;
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

const { hasAuth } = usePermission();

const dialogForm: DialogForm = {
  formProps: {
    elFormProps: dictDataElFormProps,
    schema: useFormSchema(
      computed(() => ""),
      computed(() => props.dictCode),
      computed(() => props.isCascade)
    ).dictDataSchema,
  },
  id: ["id", "dataId"],
  addApi: params => addDictData({ ...params, appId: props.appId }),
  editApi: editDictData,
  removeApi: removeDictData,
  beforeEdit: form => {
    if (form.tagEl === undefined) form.tagEl = "";
  },
  disableAdd: !hasAuth("system:dict:add"),
  disableEdit: !hasAuth("system:dict:edit"),
  disableRemove: !hasAuth("system:dict:remove"),
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
