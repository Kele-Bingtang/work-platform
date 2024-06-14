import type { App } from "@/api/application/app";
import { UserSelect, type DialogFormSchemaProps } from "@work/components";
import type { FormRules } from "element-plus";
import { list } from "@/api/user/user";

const rules = reactive<FormRules>({
  clientId: [{ required: true, message: "请选择客户端", trigger: "blur" }],
  appCode: [{ required: true, message: "请输入应用编码", trigger: "blur" }],
  appName: [{ required: true, message: "请输入应用名称", trigger: "blur" }],
});

export const elFormProps = {
  labelWidth: 100,
  rules: rules,
};
export const useFormSchema = (enumData: ComputedRef<any>, defaultValue: ComputedRef<string>) => {
  const schema: DialogFormSchemaProps<App.AppInfo>[] = [
    {
      prop: "clientId",
      label: "所属客户端",
      el: "el-select",
      enum: enumData,
      fieldNames: { value: "clientId", label: "clientName" },
      defaultValue: defaultValue,
      isDisabled: form => !!form.clientId,
      props: { clearable: true, placeholder: "请选择 所属客户端" },
      col: { span: 24 },
    },
    {
      prop: "appCode",
      label: "应用编码",
      el: "el-input",
      props: { clearable: true, placeholder: "请输入 角色编码" },
    },
    {
      prop: "appName",
      label: "应用名称",
      el: "el-input",
      props: { clearable: true, placeholder: "请输入 角色名称" },
    },
    {
      prop: "owner",
      label: "负责人",
      renderUseProp: ["ownerId", "user"],
      render: ({ model }) => {
        return (
          <UserSelect v-model={model.ownerId} v-model:user={model.user} requestApi={list} id="username"></UserSelect>
        );
      },
    },
    {
      prop: "orderNum",
      label: "显示顺序",
      el: "el-input-number",
      defaultValue: 0,
    },
    {
      prop: "intro",
      label: "介绍",
      el: "el-input",
      props: { type: "textarea", clearable: true, placeholder: "请输入 介绍" },
      col: { span: 24 },
    },
  ];

  return {
    schema,
  };
};
