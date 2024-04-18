import { listMenuIdsByRoleId, listMenuTreeSelectByApp } from "@/api/system/menu";
import type { Role } from "@/api/system/role";
import type { FormOptionsProps } from "@work/components";
import type { FormRules } from "element-plus";

const rules = reactive<FormRules>({
  appId: [{ required: true, message: "请选择 App", trigger: "blur" }],
  roleCode: [{ required: true, message: "请输入角色编码", trigger: "blur" }],
  roleName: [{ required: true, message: "请输入角色名称", trigger: "blur" }],
});

export const useFormOptions = (enumData: ComputedRef<any>, defaultValue: ComputedRef<string>) => {
  const options: FormOptionsProps<Role.RoleInfo> = {
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
        formItem: { label: "所属 App", prop: "appId", br: true },
        attrs: {
          el: "el-select",
          enum: enumData,
          fieldNames: { value: "appId", label: "appName" },
          defaultValue: defaultValue,
          disabled: ["edit"],
          props: { clearable: true, placeholder: "请选择 App" },
        },
      },
      {
        formItem: { label: "角色编码", prop: "roleCode" },
        attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 角色编码" } },
      },
      {
        formItem: { label: "角色名称", prop: "roleName" },
        attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 角色名称" } },
      },
      {
        formItem: { label: "显示顺序", prop: "orderNum" },
        attrs: { el: "el-input-number", defaultValue: 0 },
      },
      {
        formItem: { label: "菜单分配", prop: "selectedMenuIds", br: true },
        attrs: {
          el: "el-tree",
          defaultValue: async (form: Record<string, any>) => {
            if (!form.appId) return [];
            const res = await listMenuIdsByRoleId(form.appId, form.roleId);
            return res.data || [];
          },
          enum: listMenuTreeSelectByApp,
          props: { nodeKey: "value", search: true, checkbox: true },
        },
      },
      {
        formItem: { label: "介绍", prop: "intro", br: true },
        attrs: { el: "el-input", props: { type: "textarea", clearable: true, placeholder: "请输入 介绍" } },
      },
    ],
  };
  return { options };
};
