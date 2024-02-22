import { getDeptTreeList } from "@/api/dept";
import type { FormOptionsProps } from "@work/components";
import type { FormRules } from "element-plus";
import { validatePhone } from "@/views/user/rules";

const rules = reactive<FormRules>({
  deptName: [{ required: true, message: "请输入部门名称", trigger: "blur" }],
  phone: [{ validator: validatePhone, trigger: "blur" }],
  email: [{ type: "email", message: "请输入正确的邮箱", trigger: ["blur", "change"] }],
});

export const options: FormOptionsProps = {
  form: {
    inline: true,
    labelPosition: "right",
    labelWidth: "120px",
    size: "default",
    fixWidth: true,
    rules: rules,
  },
  columns: [
    {
      formItem: { label: "上级菜单", prop: "parentId" },
      attrs: {
        el: "el-tree-select",
        props: {
          placeholder: "请输入 角色编码",
          filterable: true,
          checkStrictly: true,
          valueKey: "id",
        },
        fieldNames: { value: "id", label: "label" },
        enum: getDeptTreeList,
        isHidden: (form: any) => form.parentId === "0",
      },
    },
    {
      formItem: { label: "菜单编码", prop: "menuCode" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 菜单编码" } },
    },
    {
      formItem: { label: "菜单名称", prop: "menuName" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 菜单名称" } },
    },
    {
      formItem: { label: "菜单地址", prop: "path", br: true },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 菜单地址" } },
    },
    {
      formItem: { label: "查询参数", prop: "param" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 查询参数" } },
    },
    {
      formItem: { label: "图标", prop: "icon" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 图标" } },
    },
    {
      formItem: { label: "组件路径", prop: "component" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 组件路径" } },
    },
    {
      formItem: { label: "显示顺序", prop: "orderNum" },
      attrs: { el: "el-input-number", defaultValue: 0 },
    },
    {
      formItem: { label: "显示状态", prop: "visible" },
      attrs: {
        defaultValue: 1,
        render: ({ scope }) => {
          return (
            <>
              <el-radio-group v-model={scope.form.visible}>
                <el-radio label={1}>显示</el-radio>
                <el-radio label={0}>隐藏</el-radio>
              </el-radio-group>
            </>
          );
        },
      },
    },
    {
      formItem: { label: "是否缓存", prop: "isCache" },
      attrs: {
        defaultValue: 0,
        render: ({ scope }) => {
          return (
            <>
              <el-radio-group v-model={scope.form.isCache}>
                <el-radio label={1}>是</el-radio>
                <el-radio label={0}>否</el-radio>
              </el-radio-group>
            </>
          );
        },
      },
    },
    {
      formItem: { label: "是否为外链", prop: "isFrame" },
      attrs: {
        defaultValue: 0,
        render: ({ scope }) => {
          return (
            <>
              <el-radio-group v-model={scope.form.isFrame}>
                <el-radio label={1}>是</el-radio>
                <el-radio label={0}>否</el-radio>
              </el-radio-group>
            </>
          );
        },
      },
    },
    {
      formItem: { label: "meta 配置", prop: "meta", br: true },
      attrs: { el: "el-input", props: { type: "textarea", clearable: true, placeholder: "请输入 电话" } },
    },
    {
      formItem: { label: "介绍", prop: "intro", br: true },
      attrs: { el: "el-input", props: { type: "textarea", clearable: true, placeholder: "请输入 介绍" } },
    },
  ],
};
