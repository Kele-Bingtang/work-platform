import type { FormOptionsProps } from "@work/components";
import type { FormRules } from "element-plus";
import { useFormRules } from "@/hooks/useFormRules";
import type { Tenant } from "@/api/system/tenant";

const { validatePhone } = useFormRules();

const rules = reactive<FormRules>({
  tenantName: [{ required: true, message: "请输入企业名称", trigger: "blur" }],
  contactUserName: [{ required: true, message: "请输入联系人", trigger: "blur" }],
  contactPhone: [{ required: true, validator: validatePhone, trigger: "blur" }],
});

export const options: FormOptionsProps<Tenant.TenantInfo> = {
  form: {
    inline: true,
    labelPosition: "right",
    labelWidth: 100,
    size: "default",
    fixWidth: true,
    rules: rules,
  },
  row: {
    col: { span: 12 },
  },
  columns: [
    {
      formItem: { label: "企业名称", prop: "tenantName" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 企业名称" } },
    },
    {
      formItem: { label: "企业创始人", prop: "founder" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 企业创始人" } },
    },
    {
      formItem: { label: "联系人", prop: "contactUserName" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 联系人" } },
    },
    {
      formItem: { label: "联系电话", prop: "contactPhone" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 联系电话" } },
    },
    {
      formItem: { label: "企业域名", prop: "domain" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 企业域名" } },
    },
    {
      formItem: { label: "租户过期时间", prop: "expireTime" },
      attrs: {
        el: "el-date-picker",
        props: { type: "datetime", clearable: true, placeholder: "请选择 租户过期时间" },
      },
    },
    {
      formItem: { label: "企业用户数量", prop: "userCountCapacity" },
      attrs: { el: "el-input-number", defaultValue: 0 },
    },
    {
      formItem: { label: "企业所在地", prop: "address" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 企业所在地" } },
    },
    {
      formItem: { label: "企业图标", prop: "icon" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 企业图标" } },
    },
    {
      formItem: { label: "社会信用代码", prop: "licenseNumber" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 社会信用代码" } },
    },
    {
      formItem: { label: "介绍", prop: "intro", br: true },
      attrs: { el: "el-input", props: { type: "textarea", clearable: true, placeholder: "请输入 介绍" } },
    },
  ],
};
