import type { Post } from "@/api/system/post";
import { listWithDisabledByGroupId } from "@/api/system/role";
import { TransferSelect, type FormOptionsProps, type TransferTableColumn } from "@work/components";
import type { FormRules } from "element-plus";
import { User } from "@element-plus/icons-vue";

const rules = reactive<FormRules>({
  roleIds: [{ required: true, message: "请选择角色名", trigger: "blur" }],
});

const transferSelectColumn: TransferTableColumn[] = [{ prop: "roleName", label: "角色名称" }];

export const useFormOptions = (userGroupId: string) => {
  const options: FormOptionsProps<Post.PostInfo> = {
    form: {
      inline: true,
      labelPosition: "top",
      labelWidth: 80,
      size: "default",
      fixWidth: true,
      rules: rules,
    },
    columns: [
      {
        formItem: { label: "角色名", prop: "role", br: true },
        attrs: {
          render: ({ scope }) => {
            return (
              <TransferSelect
                v-model={scope.form.role}
                v-model:ids={scope.form.roleIds}
                columns={transferSelectColumn}
                request-api={listWithDisabledByGroupId}
                request-params={{ userGroupId }}
                multiple
                list-icon={User}
                id="roleId"
              ></TransferSelect>
            );
          },
        },
      },
    ],
  };

  return {
    options,
  };
};
