<template>
  <ProTable
    ref="proTable"
    :request-api="listDictData"
    :columns="columns"
    :init-request-param="initRequestParam"
    :search-col="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
    style="height: 90%"
    :detailForm="detailForm"
  ></ProTable>
</template>

<script setup lang="ts" name="DictData">
import { ProTable } from "work";
import { addOneDictData, editOneDictData, removeOneDictData, listDictData } from "@/api/dictData";
import { type DialogForm, type FormOptionsProps, type TableColumnProps } from "@work/components";

export interface DictDataProps {
  dictCode: string;
  appId: string;
}

// 接受父组件参数，配置默认值
const props = defineProps<DictDataProps>();

const initRequestParam = reactive({
  dictCode: computed(() => props.dictCode),
});

const columns: TableColumnProps[] = [
  { type: "index", label: "#", width: 80 },
  { prop: "dictValue", label: "字典键值" },
  { prop: "dictLabel", label: "字典标签", search: { el: "el-input" } },
  { prop: "dictSort", label: "字典排序" },
  { prop: "createTime", label: "创建时间" },
  { prop: "operation", label: "操作" },
];

const options: FormOptionsProps = {
  form: {
    inline: false,
    labelPosition: "right",
    labelWidth: "110px",
    size: "default",
  },
  columns: [
    {
      formItem: { prop: "dictCode", label: "字典编码" },
      attrs: { el: "el-input", defaultValue: props.dictCode, props: { disabled: true } },
    },
    {
      formItem: { prop: "dictValue", label: "字典键值", required: true },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 字典键值" } },
    },
    {
      formItem: { prop: "dictLabel", label: "字典标签", required: true },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 字典标签" } },
    },
    {
      formItem: { prop: "dictSort", label: "字典排序" },
      attrs: { el: "el-input-number", defaultValue: 0 },
    },
    {
      formItem: { prop: "cssClass", label: "样式属性" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 样式属性" } },
    },
    {
      formItem: { prop: "listClass", label: "回显样式" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 回显样式" } },
    },
    {
      formItem: { prop: "isDefault", label: "是否默认选中" },
      attrs: {
        el: "el-select",
        defaultValue: "N",
        enum: [
          { value: "Y", label: "选中" },
          { value: "N", label: "不选中" },
        ],
        props: { clearable: true, placeholder: "请输入 是否默认选中" },
      },
    },
  ],
};

const detailForm = reactive<DialogForm>({
  options: options,
  addApi: params => addOneDictData({ ...params, appId: props.appId }),
  editApi: editOneDictData,
  deleteApi: removeOneDictData,
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "30%",
    top: "5vh",
    closeOnClickModal: false,
  },
});
</script>

<style lang="scss" scoped></style>
