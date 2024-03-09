import type { App } from "@/api/application/app";
import type { FormOptionsProps } from "@work/components";
import type { FormRules } from "element-plus";

const rules = reactive<FormRules>({
  clientId: [{ required: true, message: "请选择客户端", trigger: "blur" }],
  appCode: [{ required: true, message: "请输入应用编码", trigger: "blur" }],
  appName: [{ required: true, message: "请输入应用名称", trigger: "blur" }],
});

export const useFormOptions = (enumData: ComputedRef<any>, defaultValue: ComputedRef<string>) => {
  const options: FormOptionsProps<App.AppInfo> = {
    form: {
      inline: true,
      labelPosition: "right",
      labelWidth: 100,
      size: "default",
      fixWidth: true,
      rules: rules,
    },
    columns: [
      {
        formItem: { label: "所属客户端", prop: "clientId", br: true },
        attrs: {
          el: "el-select",
          enum: enumData,
          fieldNames: { value: "clientId", label: "clientName" },
          defaultValue: defaultValue,
          isDisabled: form => !!form.clientId,
          props: { clearable: true, placeholder: "请选择 所属客户端" },
        },
      },
      {
        formItem: { label: "应用编码", prop: "appCode" },
        attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 角色编码" } },
      },
      {
        formItem: { label: "应用名称", prop: "appName" },
        attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 角色名称" } },
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

  return {
    options,
  };
};
