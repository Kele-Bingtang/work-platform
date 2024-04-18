import { listDataTreeList, type DictData } from "@/api/system/dictData";
import type { DictType } from "@/api/system/dictType";
import { useLayoutStore } from "@/stores";
import type { FormOptionsProps } from "@work/components";
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

export const useFormOptions = (
  enumData: ComputedRef<any>,
  defaultValue: ComputedRef<string>,
  isCascade?: ComputedRef<number>
) => {
  const { getDictData } = useLayoutStore();

  const dictTypeOptions: FormOptionsProps<DictType.DictTypeInfo> = {
    form: {
      inline: true,
      labelPosition: "top",
      labelWidth: 80,
      size: "default",
      fixWidth: true,
      width: "100%",
      rules: dictTypeRules,
    },
    columns: [
      {
        formItem: { label: "所属 App", prop: "appId" },
        attrs: {
          el: "el-select",
          enum: enumData,
          fieldNames: { value: "appId", label: "appName" },
          defaultValue: defaultValue,
          disabled: ["edit"],
          props: { clearable: true, placeholder: "请选择 App" },
        },
      },
      {
        formItem: { label: "字典编码", prop: "dictCode" },
        attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 字典编码" } },
      },
      {
        formItem: { label: "字典名称", prop: "dictName" },
        attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 字典名称" } },
      },
      {
        formItem: { label: "开启级联", prop: "isCascade" },
        attrs: {
          el: "el-radio-group",
          enum: baseEnum,
          defaultValue: 0,
          props: { clearable: true, placeholder: "请选择 是否开启级联" },
        },
      },
      {
        formItem: { label: "描述", prop: "intro", br: true },
        attrs: { el: "el-input", props: { type: "textarea", clearable: true, placeholder: "请输入 描述" } },
      },
    ],
  };

  const dictDataOptions: FormOptionsProps<DictData.DictDataInfo> = {
    form: {
      inline: false,
      labelPosition: "right",
      labelWidth: 100,
      size: "default",
      rules: dictDataRules,
    },
    columns: [
      {
        formItem: { label: "上级字典", prop: "parentId", br: true },
        attrs: {
          el: "el-tree-select",
          props: {
            placeholder: "请选择 上级字典",
            filterable: true,
            valueKey: "id",
          },
          enum: () => listDataTreeList({ dictCode: defaultValue.value }),
          isHidden: form => form.parentId === "0",
          isDestroy: () => !isCascade?.value,
        },
      },
      {
        formItem: { prop: "dictCode", label: "字典编码" },
        attrs: { el: "el-input", defaultValue: defaultValue, props: { disabled: true } },
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
          props: { clearable: true, placeholder: "请选择 是否默认选中" },
        },
      },
    ],
  };

  return {
    dictTypeOptions,
    dictDataOptions,
  };
};
