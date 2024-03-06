import type { Post } from "@/api/system/post";
import type { FormOptionsProps } from "@work/components";
import type { FormRules } from "element-plus";

const rules = reactive<FormRules>({
  postCode: [{ required: true, message: "请输入部门编码", trigger: "blur" }],
  postName: [{ required: true, message: "请输入部门名称", trigger: "blur" }],
});

export const options: FormOptionsProps<Post.PostInfo> = {
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
      formItem: { label: "岗位编码", prop: "postCode" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 角色编码" } },
    },
    {
      formItem: { label: "岗位名称", prop: "postName" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 领导" } },
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
