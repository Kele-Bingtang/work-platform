import type { DialogFormSchemaProps } from "@work/components";
import type { FormRules } from "element-plus";
import { useLayoutStore } from "@/stores";

const rules = reactive<FormRules>({
  clientKey: [{ required: true, message: "请输入客户端 Key", trigger: "blur" }],
  clientName: [{ required: true, message: "请输入客户端名称", trigger: "blur" }],
  grantTypes: [{ required: true, message: "请选择授权类型", trigger: "blur" }],
});

export const elFormProps = {
  labelWidth: 140,
  rules: rules,
};

export const schema: DialogFormSchemaProps[] = [
  {
    prop: "clientKey",
    label: "客户端 Key",
    el: "el-input",
    disabledIn: ["edit"],
    props: { clearable: true, placeholder: "请输入 客户端 Key" },
  },
  {
    prop: "clientSecret",
    label: "客户端秘钥",
    el: "el-input",
    disabledIn: ["edit"],
    props: { clearable: true, placeholder: "请输入 客户端秘钥" },
  },
  {
    prop: "clientName",
    label: "客户端名称",
    el: "el-input",
    props: { clearable: true, placeholder: "请输入 客户端名称" },
  },
  {
    prop: "grantTypeList",
    label: "授权类型",
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
  {
    prop: "activeTimeout",
    label: "Token 活跃超时时间",
    el: "el-input-number",
    defaultValue: 10800,
    props: { clearable: true },
  },
  {
    prop: "timeout",
    label: "Token 固定超时时间",
    el: "el-input-number",
    defaultValue: 10800,
    props: { clearable: true },
  },
];
