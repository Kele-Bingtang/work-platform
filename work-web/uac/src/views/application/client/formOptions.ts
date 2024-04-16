import type { FormOptionsProps } from "@work/components";
import type { FormRules } from "element-plus";
import { useLayoutStore } from "@/stores";

const rules = reactive<FormRules>({
  clientKey: [{ required: true, message: "请输入客户端 Key", trigger: "blur" }],
  clientName: [{ required: true, message: "请输入客户端名称", trigger: "blur" }],
  grantTypes: [{ required: true, message: "请选择授权类型", trigger: "blur" }],
});

export const options: FormOptionsProps = {
  form: {
    inline: true,
    labelPosition: "right",
    labelWidth: 140,
    size: "default",
    fixWidth: true,
    rules: rules,
  },
  row: {
    col: { span: 12 },
  },
  columns: [
    {
      formItem: { label: "客户端 Key", prop: "clientKey" },
      attrs: {
        el: "el-input",
        disabled: ["edit"],
        props: { clearable: true, placeholder: "请输入 客户端 Key" },
      },
    },
    {
      formItem: { label: "客户端秘钥", prop: "clientSecret" },
      attrs: {
        el: "el-input",
        disabled: ["edit"],
        props: { clearable: true, placeholder: "请输入 客户端秘钥" },
      },
    },
    {
      formItem: { label: "客户端名称", prop: "clientName" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 客户端名称" } },
    },
    {
      formItem: { label: "授权类型", prop: "grantTypeList" },
      attrs: {
        el: "el-select",
        enum: () => useLayoutStore().getDictData("sys_grant_type"),
        fieldNames: { value: "dictValue", label: "dictLabel" },
        props: {
          clearable: true,
          multiple: true,
          placeholder: "请选择 授权类型",
          collapseTags: true,
          collapseTagsTooltip: true,
          maxCollapseTags: 1,
        },
      },
    },
    {
      formItem: { label: "Token 活跃超时时间", prop: "activeTimeout" },
      attrs: {
        el: "el-input-number",
        defaultValue: 10800,
        props: { clearable: true },
      },
    },
    {
      formItem: { label: "Token 固定超时时间", prop: "timeout" },
      attrs: {
        el: "el-input-number",
        defaultValue: 10800,
        props: { clearable: true },
      },
    },
  ],
};
