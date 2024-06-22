import { listMenuIdsByRoleId, listMenuTreeSelectByApp } from "@/api/system/menu";
import type { Role } from "@/api/system/role";
import type { DialogFormSchemaProps } from "@work/components";
import type { FormRules } from "element-plus";

const rules = reactive<FormRules>({
  appId: [{ required: true, message: "请选择 App", trigger: "blur" }],
  roleCode: [{ required: true, message: "请输入角色编码", trigger: "blur" }],
  roleName: [{ required: true, message: "请输入角色名称", trigger: "blur" }],
});

export const elFormProps = {
  labelWidth: 80,
  rules: rules,
};

export const useFormSchema = (enumData: ComputedRef<any>, defaultValue: ComputedRef<string>) => {
  const schema: DialogFormSchemaProps<Role.RoleInfo>[] = [
    {
      label: "所属 App",
      prop: "appId",
      el: "el-select",
      enum: enumData,
      fieldNames: { value: "appId", label: "appName" },
      defaultValue: defaultValue,
      disabledIn: ["edit"],
      props: { clearable: true, placeholder: "请选择 App" },
      col: { span: 24 },
    },
    {
      prop: "roleCode",
      label: "角色编码",
      el: "el-input",
      props: { clearable: true, placeholder: "请输入 角色编码" },
    },
    {
      prop: "roleName",
      label: "角色名称",
      el: "el-input",
      props: { clearable: true, placeholder: "请输入 角色名称" },
    },
    {
      prop: "orderNum",
      label: "显示顺序",
      el: "el-input-number",
      defaultValue: 1,
    },
    {
      prop: "selectedMenuIds",
      label: "菜单分配",
      el: "el-tree",
      defaultValue: async (model: Record<string, any>) => {
        if (!model.appId) return [];
        const res = await listMenuIdsByRoleId(model.appId, model.roleId);
        return res.data || [];
      },
      enum: model => listMenuTreeSelectByApp({ appId: model.appId }),
      props: { nodeKey: "value", search: true, checkbox: true },
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
  return { schema };
};
