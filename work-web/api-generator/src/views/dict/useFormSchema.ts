import { listDataTreeList, type DictData } from "@/api/dictData";
import type { DictType } from "@/api/dictType";
import { useDictStore } from "@/stores";
import type { DialogFormSchemaProps } from "@work/components";
import { baseEnum } from "@work/constants";
import type { FormRules } from "element-plus";

const dictTypeRules = reactive<FormRules>({
  appId: [{ required: true, message: "请选择 App", trigger: "blur" }],
  dictCode: [{ required: true, message: "请输入字典编码", trigger: "blur" }],
  dictName: [{ required: true, message: "请输入字典名称", trigger: "blur" }],
});

const dictDataRules = reactive<FormRules>({
  dictValue: [{ required: true, message: "请输入字典键值", trigger: "blur" }],
  dictLabel: [{ required: true, message: "请输入字典标签", trigger: "blur" }],
});

export const dictTypeElFormProps = {
  labelPosition: "top",
  labelWidth: 80,
  rules: dictTypeRules,
};

export const dictDataElFormProps = {
  labelWidth: 100,
  rules: dictDataRules,
};

export const useFormSchema = (defaultValue: ComputedRef<string>, isCascade?: ComputedRef<number>) => {
  const { getDictData } = useDictStore();

  const dictTypeSchema: DialogFormSchemaProps<DictType.DictTypeInfo>[] = [
    {
      prop: "dictCode",
      label: "字典编码",
      el: "el-input",
      props: { clearable: true, placeholder: "请输入 字典编码" },
    },
    {
      prop: "dictName",
      label: "字典名称",
      el: "el-input",
      props: { clearable: true, placeholder: "请输入 字典名称" },
    },
    {
      prop: "isCascade",
      label: "开启级联",
      el: "el-radio-group",
      enum: baseEnum,
      defaultValue: 0,
      props: { clearable: true, placeholder: "请选择 是否开启级联" },
    },
    {
      prop: "intro",
      label: "描述",
      el: "el-input",
      props: { type: "textarea", clearable: true, placeholder: "请输入 描述" },
      col: { span: 24 },
    },
  ];

  const dictDataSchema: DialogFormSchemaProps<DictData.DictDataInfo>[] = [
    {
      prop: "parentId",
      label: "上级字典",
      el: "el-tree-select",
      props: {
        placeholder: "请选择 上级字典",
        filterable: true,
        valueKey: "id",
        checkStrictly: true,
      },
      enum: () => listDataTreeList({ dictCode: defaultValue.value }),
      hidden: model => model.parentId === "0",
      destroy: () => !isCascade?.value,
      col: { span: 24 },
    },
    {
      prop: "dictCode",
      label: "字典编码",
      el: "el-input",
      defaultValue: defaultValue,
      props: { disabled: true },
    },
    {
      prop: "dictValue",
      label: "字典键值",
      el: "el-input",
      props: { clearable: true, placeholder: "请输入 字典键值" },
    },
    {
      prop: "dictLabel",
      label: "字典标签",
      el: "el-input",
      props: { clearable: true, placeholder: "请输入 字典标签" },
    },
    {
      prop: "dictSort",
      label: "字典排序",
      el: "el-input-number",
      defaultValue: 1,
    },
    {
      prop: "tagEl",
      label: "tag 标签",
      el: "el-select",
      enum: () => getDictData("sys_dict_tag_el"),
      fieldNames: { value: "dictValue", label: "dictLabel" },
      props: { placeholder: "请选择 tag 标签" },
    },
    {
      prop: "tagType",
      label: "tag 类型",
      el: "el-select",
      destroy: model => !model.tagEl,
      enum: () => getDictData("sys_dict_tag_type"),
      fieldNames: { value: "dictValue", label: "dictLabel" },
      props: { placeholder: "请选择 tag 类型" },
    },
    {
      prop: "tagEffect",
      label: "tag 主题",
      el: "el-select",
      destroy: model => model.tagEl !== "el-tag",
      enum: () => getDictData("sys_dict_tag_effect"),
      fieldNames: { value: "dictValue", label: "dictLabel" },
      props: { placeholder: "请选择 tag 主题" },
    },
    {
      prop: "tagAttributes",
      label: "tag 额外属性",
      el: "el-input",
      hidden: model => model.tagEl !== "el-tag",
      props: { clearable: true, placeholder: "请输入 tag 额外属性" },
    },
    {
      prop: "isDefault",
      label: "是否默认选中",
      el: "el-select",
      defaultValue: "N",
      enum: [
        { value: "Y", label: "选中" },
        { value: "N", label: "不选中" },
      ],
      props: { clearable: true, placeholder: "请选择 是否默认选中" },
    },
  ];

  return {
    dictTypeSchema,
    dictDataSchema,
  };
};
