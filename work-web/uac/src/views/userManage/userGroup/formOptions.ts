import type { Post } from "@/api/system/post";
import type { FormOptionsProps } from "@work/components";
import type { FormRules } from "element-plus";

const rules = reactive<FormRules>({
  groupName: [{ required: true, message: "请输入用户组名", trigger: "blur" }],
});

export const options: FormOptionsProps<Post.PostInfo> = {
  form: {
    inline: true,
    labelPosition: "right",
    labelWidth: 80,
    size: "default",
    fixWidth: true,
    rules: rules,
  },
  columns: [
    {
      formItem: { label: "用户组名", prop: "groupName" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 用户组名" } },
    },
    {
      formItem: { label: "负责人", prop: "ownerId" },
      attrs: { el: "el-select", props: { clearable: true, placeholder: "请选择 负责人" } },
    },
    {
      formItem: { label: "描述", prop: "intro", br: true },
      attrs: { el: "el-input", props: { type: "textarea", clearable: true, placeholder: "请输入 介绍" } },
    },
  ],
};
