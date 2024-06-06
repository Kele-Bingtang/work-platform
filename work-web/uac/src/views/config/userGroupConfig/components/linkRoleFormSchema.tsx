import { listWithDisabledByGroupId } from "@/api/system/role";
import { TransferSelect, type DialogFormSchemaProps, type TransferTableColumn } from "@work/components";
import type { FormRules } from "element-plus";
import { User } from "@element-plus/icons-vue";

const rules = reactive<FormRules>({
  roleIds: [{ required: true, message: "请选择角色名", trigger: "blur" }],
});

const transferSelectColumn: TransferTableColumn[] = [{ prop: "roleName", label: "角色名称" }];

export const elFormProps = {
  labelPosition: "top",
  labelWidth: 80,
  rules: rules,
};

export const useFormSchema = (requestParams: { userGroupId: string }) => {
  const schema: DialogFormSchemaProps<{ roleIds: string[] }>[] = [
    {
      prop: "roleIds",
      label: "角色选择",
      render: ({ model }) => {
        return (
          <TransferSelect
            v-model={model.roleIds}
            columns={transferSelectColumn}
            request-api={listWithDisabledByGroupId}
            request-params={requestParams}
            multiple
            list-icon={User}
            id="roleId"
          ></TransferSelect>
        );
      },
      col: { span: 24 },
    },
  ];

  return {
    schema,
  };
};
