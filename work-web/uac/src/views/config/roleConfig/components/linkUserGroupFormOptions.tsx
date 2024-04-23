import type { FormOptionsProps, TransferTableColumn } from "@work/components";
import { type FormRules } from "element-plus";
import { TransferSelect } from "work";
import { User } from "@element-plus/icons-vue";
import { listWithDisabledByRoleId } from "@/api/user/userGroup";

const rules = reactive<FormRules>({
  groupIds: [{ required: true, message: "请选择用户组", trigger: "blur" }],
});

const transferSelectColumn: TransferTableColumn[] = [{ prop: "groupName", label: "用户组名" }];

export const useFormOptions = (requestParams: { roleId: string }) => {
  const options: FormOptionsProps<{ userGroupIds: string }> = {
    form: { inline: true, labelPosition: "top", labelWidth: 80, size: "default", rules: rules },
    columns: [
      {
        formItem: { label: "用户组选择", prop: "userGroupIds", br: true },
        attrs: {
          render: ({ scope }) => {
            return (
              <TransferSelect
                v-model={scope.form.userGroupIds}
                columns={transferSelectColumn}
                request-api={listWithDisabledByRoleId}
                request-params={requestParams}
                multiple
                list-icon={User}
                id="groupId"
              ></TransferSelect>
            );
          },
        },
      },
    ],
  };

  return { options };
};
