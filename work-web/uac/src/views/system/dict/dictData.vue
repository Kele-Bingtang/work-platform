<template>
  <ProTable
    ref="proTableRef"
    :request-api="list"
    :columns="columns"
    :init-request-param="initRequestParam"
    :search-col="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
    style="height: 90%"
    :detailForm="detailForm"
  ></ProTable>
</template>

<script setup lang="ts" name="DictData">
import { ProTable } from "work";
import { list, addOne, editOne, removeOne, type DictData } from "@/api/system/dictData";
import { type DialogForm, type FormOptionsProps, type TableColumnProps } from "@work/components";
import type { FormRules } from "element-plus";
import { useLayoutStore } from "@/stores/layout";

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

const { getDictData } = useLayoutStore();

const rules = reactive<FormRules>({
  dictValue: [{ required: true, message: "请输入字典键值", trigger: "blur" }],
  dictLabel: [{ required: true, message: "请输入字典标签", trigger: "blur" }],
});

const options: FormOptionsProps<DictData.DictDataInfo> = {
  form: {
    inline: false,
    labelPosition: "right",
    labelWidth: "110px",
    size: "default",
    rules: rules,
  },
  columns: [
    {
      formItem: { prop: "dictCode", label: "字典编码" },
      attrs: { el: "el-input", defaultValue: computed(() => props.dictCode), props: { disabled: true } },
    },
    {
      formItem: { prop: "dictValue", label: "字典键值" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 字典键值" } },
    },
    {
      formItem: { prop: "dictLabel", label: "字典标签" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 字典标签" } },
    },
    {
      formItem: { prop: "dictSort", label: "字典排序" },
      attrs: { el: "el-input-number", defaultValue: 0 },
    },
    {
      formItem: { prop: "tagEl", label: "tag 标签" },
      attrs: {
        el: "el-select",
        enum: () => getDictData("sys_dict_tag_el"),
        fieldNames: { value: "dictValue", label: "dictLabel" },
        props: { placeholder: "请选择 tag 标签" },
      },
    },
    {
      formItem: { prop: "tagType", label: "tag 类型" },
      attrs: {
        el: "el-select",
        isDestroy: form => !form.tagEl,
        enum: () => getDictData("sys_dict_tag_type"),
        fieldNames: { value: "dictValue", label: "dictLabel" },
        props: { placeholder: "请选择 tag 类型" },
      },
    },
    {
      formItem: { prop: "tagEffect", label: "tag 主题" },
      attrs: {
        el: "el-select",
        isDestroy: form => form.tagEl !== "el-tag",
        enum: () => getDictData("sys_dict_tag_effect"),
        fieldNames: { value: "dictValue", label: "dictLabel" },
        props: { placeholder: "请选择 tag 主题" },
      },
    },
    {
      formItem: { prop: "tagAttributes", label: "tag 额外属性" },
      attrs: {
        el: "el-input",
        isHidden: form => form.tagEl !== "el-tag",
        props: { clearable: true, placeholder: "请输入 tag 额外属性" },
      },
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
        props: { clearable: true, placeholder: "请选择" },
      },
    },
  ],
};

const detailForm = reactive<DialogForm>({
  options: options,
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
