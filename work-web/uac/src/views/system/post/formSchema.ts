import type { Post } from "@/api/system/post";
import type { DialogFormSchemaProps } from "@work/components";
import type { FormRules } from "element-plus";

const rules = reactive<FormRules>({
  postCode: [{ required: true, message: "请输入部门编码", trigger: "blur" }],
  postName: [{ required: true, message: "请输入部门名称", trigger: "blur" }],
});

export const elFormProps = {
  labelWidth: 80,
  rules: rules,
};

export const schema: DialogFormSchemaProps<Post.PostInfo>[] = [
  {
    prop: "postCode",
    label: "岗位编码",
    el: "el-input",
    props: { clearable: true, placeholder: "请输入 角色编码" },
  },
  {
    prop: "postName",
    label: "岗位名称",
    el: "el-input",
    props: { clearable: true, placeholder: "请输入 领导" },
  },
  {
    prop: "orderNum",
    label: "显示顺序",
    el: "el-input-number",
    defaultValue: 1,
  },
  {
    prop: "intro",
    label: "介绍",
    el: "el-input",
    props: { type: "textarea", clearable: true, placeholder: "请输入 介绍" },
    col: { span: 24 },
  },
];
