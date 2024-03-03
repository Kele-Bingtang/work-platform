import { getDeptTreeList } from "@/api/dept";
import type { FormOptionsProps } from "@work/components";
import type { FormRules } from "element-plus";
import { useFormRules } from "@/hooks/useFormRules";

const { validatePhone } = useFormRules();

const rules = reactive<FormRules>({
  deptName: [{ required: true, message: "请输入部门名称", trigger: "blur" }],
  phone: [{ validator: validatePhone, trigger: "blur" }],
  email: [{ type: "email", message: "请输入正确的邮箱", trigger: ["blur", "change"] }],
});

export const options: FormOptionsProps = {
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
      formItem: { label: "上级部门", prop: "parentId", br: true },
      attrs: {
        el: "el-tree-select",
        props: {
          placeholder: "请输入 角色编码",
          filterable: true,
          checkStrictly: true,
          valueKey: "id",
        },
        fieldNames: { value: "id", label: "label" },
        enum: getDeptTreeList,
        isHidden: (form: any) => form.parentId === "0",
      },
    },
    {
      formItem: { label: "部门名称", prop: "deptName" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 角色编码" } },
    },
    {
      formItem: { label: "领导", prop: "leader" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 领导" } },
    },
    {
      formItem: { label: "电话", prop: "phone" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 电话" } },
    },
    {
      formItem: { label: "邮箱", prop: "email" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 邮箱" } },
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
