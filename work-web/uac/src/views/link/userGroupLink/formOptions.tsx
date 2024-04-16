import type { Post } from "@/api/system/post";
import { list } from "@/api/user/user";
import { UserSelect, type FormOptionsProps } from "@work/components";
import type { FormRules } from "element-plus";

const rules = reactive<FormRules>({
  groupName: [{ required: true, message: "请输入用户组名", trigger: "blur" }],
});

export const useFormOptions = () => {
  const options: FormOptionsProps<Post.PostInfo> = {
    form: {
      inline: true,
      labelPosition: "right",
      labelWidth: 80,
      size: "default",
      fixWidth: true,
      rules: rules,
    },
    row: {
      col: { span: 12 },
    },
    columns: [
      {
        formItem: { label: "用户组名", prop: "groupName" },
        attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 用户组名" } },
      },
      {
        formItem: { label: "负责人", prop: "owner" },
        attrs: {
          render: ({ scope }) => {
            return (
              <UserSelect v-model={scope.form.ownerId} v-model:user={scope.form.user} requestApi={list}></UserSelect>
            );
          },
        },
      },
      {
        formItem: { label: "描述", prop: "intro", br: true },
        attrs: { el: "el-input", props: { type: "textarea", clearable: true, placeholder: "请输入 介绍" } },
      },
    ],
  };

  return { options };
};
