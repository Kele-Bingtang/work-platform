import type { FormOptionsProps } from "@work/components";
import type { FormRules } from "element-plus";

const rules = reactive<FormRules>({
  roleCode: [{ required: true, message: "请输入角色编码", trigger: "blur" }],
  roleName: [{ required: true, message: "请输入角色名称", trigger: "blur" }],
});

export const options: FormOptionsProps = {
  form: {
    inline: true,
    labelPosition: "right",
    labelWidth: "80px",
    size: "default",
    fixWidth: true,
    rules: rules,
  },
  columns: [
    {
      formItem: { label: "角色编码", prop: "roleCode" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 角色编码" } },
    },
    {
      formItem: { label: "角色名称", prop: "roleName" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 角色名称" } },
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
