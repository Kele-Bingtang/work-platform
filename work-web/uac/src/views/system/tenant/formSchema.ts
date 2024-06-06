import type { DialogFormSchemaProps } from "@work/components";
import type { FormRules } from "element-plus";
import { useFormRules } from "@/hooks/useFormRules";
import type { Tenant } from "@/api/system/tenant";

const { validatePassword, validatePhone } = useFormRules();

const rules = reactive<FormRules>({
  tenantName: [{ required: true, message: "请输入企业名称", trigger: "blur" }],
  username: [{ required: true, message: "请输入用户名称", trigger: "blur" }],
  password: [{ required: true, validator: validatePassword, trigger: "blur" }],
  contactUserName: [{ required: true, message: "请输入联系人", trigger: "blur" }],
  contactPhone: [{ required: true, validator: validatePhone, trigger: "blur" }],
});

export const elFormProps = {
  labelWidth: 100,
  rules: rules,
};

export const schema: DialogFormSchemaProps<Tenant.TenantInfo>[] = [
  {
    prop: "tenantName",
    label: "企业名称",
    el: "el-input",
    props: { clearable: true, placeholder: "请输入 企业名称" },
  },
  {
    prop: "founder",
    label: "企业创始人",
    el: "el-input",
    props: { clearable: true, placeholder: "请输入 企业创始人" },
  },
  {
    prop: "contactUserName",
    label: "联系人",
    el: "el-input",
    props: { clearable: true, placeholder: "请输入 联系人" },
  },
  {
    prop: "contactPhone",
    label: "联系电话",
    el: "el-input",
    props: { clearable: true, placeholder: "请输入 联系电话" },
  },
  {
    prop: "username",
    label: "系统用户名",
    el: "el-input",
    destroyIn: ["edit"],
    props: { clearable: true, placeholder: "请输入 系统用户名" },
  },
  {
    prop: "password",
    label: "系统密码",
    el: "el-input",
    destroyIn: ["edit"],
    props: { clearable: true, placeholder: "请输入 系统密码", type: "password", showPassword: true },
  },
  {
    prop: "domain",
    label: "企业域名",
    el: "el-input",
    props: { clearable: true, placeholder: "请输入 企业域名" },
  },
  {
    prop: "expireTime",
    label: "租户过期时间",
    el: "el-date-picker",
    props: { type: "datetime", clearable: true, placeholder: "请选择 租户过期时间" },
  },
  {
    prop: "userCountCapacity",
    label: "企业用户数量",
    el: "el-input-number",
    defaultValue: 0,
  },
  {
    prop: "icon",
    label: "企业图标",
    el: "icon-picker",
    props: { clearable: true, placeholder: "请选择 企业图标" },
  },
  {
    prop: "address",
    label: "企业所在地",
    el: "el-input",
    props: { clearable: true, placeholder: "请输入 企业所在地" },
    col: { span: 24 },
  },
  {
    prop: "licenseNumber",
    label: "社会信用代码",
    el: "el-input",
    props: { clearable: true, placeholder: "请输入 社会信用代码" },
    col: { span: 24 },
  },
  {
    prop: "intro",
    label: "介绍",
    el: "el-input",
    props: { type: "textarea", clearable: true, placeholder: "请输入 介绍" },
    col: { span: 24 },
  },
];
