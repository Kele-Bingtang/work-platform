import type { Role } from "@/api/system/role";
import { useLayoutStore } from "@/stores/layout";
import type { FormOptionsProps } from "@work/components";
import type { FormRules } from "element-plus";

const rules = reactive<FormRules>({
  appCode: [{ required: true, message: "请输入应用编码", trigger: "blur" }],
  appName: [{ required: true, message: "请输入应用名称", trigger: "blur" }],
});

export const options: FormOptionsProps<Role.RoleInfo> = {
  form: {
    inline: true,
    labelPosition: "right",
    labelWidth: "120px",
    size: "default",
    fixWidth: true,
    rules: rules,
  },
  columns: [
    {
      formItem: { label: "应用编码", prop: "appCode" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 角色编码" } },
    },
    {
      formItem: { label: "应用名称", prop: "appName" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 角色名称" } },
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
          placeholder: "请输入 授权类型",
          collapseTags: true,
          collapseTagsTooltip: true,
          maxCollapseTags: 1,
        },
      },
    },
    {
      formItem: { label: "显示顺序", prop: "orderNum" },
      attrs: { el: "el-input-number", defaultValue: 0 },
    },
    {
      formItem: { label: "介绍", prop: "intro", br: true },
      attrs: { el: "el-input", props: { type: "textarea", clearable: true, placeholder: "请输入 介绍" } },
    },
  ],
};
