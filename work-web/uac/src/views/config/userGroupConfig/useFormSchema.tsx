import type { Post } from "@/api/system/post";
import { list } from "@/api/user/user";
import { UserSelect, type DialogFormSchemaProps } from "@work/components";
import type { FormRules } from "element-plus";

const rules = reactive<FormRules>({
  groupName: [{ required: true, message: "请输入用户组名", trigger: "blur" }],
});

export const elFormProps = {
  labelWidth: 80,
  rules: rules,
};

export const useFormSchema = () => {
  const schema: DialogFormSchemaProps<Post.PostInfo>[] = [
    {
      prop: "groupName",
      label: "用户组名",
      el: "el-input",
      props: { clearable: true, placeholder: "请输入 用户组名" },
    },
    {
      prop: "owner",
      label: "负责人",
      render: ({ model }) => {
        return (
          <UserSelect v-model={model.ownerId} v-model:user={model.user} requestApi={list} id="username"></UserSelect>
        );
      },
    },
    {
      prop: "intro",
      label: "描述",
      el: "el-input",
      props: { type: "textarea", clearable: true, placeholder: "请输入 介绍" },
      col: { span: 24 },
    },
  ];

  return { schema };
};
