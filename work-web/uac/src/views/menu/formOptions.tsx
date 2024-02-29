import { listMenuTreeSelectByApp } from "@/api/menu";
import type { FormOptionsProps } from "@work/components";
import type { FormRules } from "element-plus";
import { httpPrefix, httpsPrefix } from "@work/constants";

const rules = reactive<FormRules>({
  menuCode: [{ required: true, message: "请输入菜单编码", trigger: "blur" }],
  menuName: [{ required: true, message: "请输入菜单名称", trigger: "blur" }],
  path: [{ required: true, message: "请输入菜单/路由地址", trigger: "blur" }],
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
      formItem: { label: "上级菜单", prop: "parentId", br: true },
      attrs: {
        el: "el-tree-select",
        props: {
          placeholder: "请输入 角色编码",
          filterable: true,
          checkStrictly: true,
          valueKey: "id",
        },
        fieldNames: { value: "id", label: "label" },
        enum: listMenuTreeSelectByApp,
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
      formItem: { label: "菜单类型", prop: "menuType", br: true },
      attrs: {
        defaultValue: "C",
        render: ({ scope }) => {
          return (
            <>
              <el-radio-group v-model={scope.form.menuType}>
                <el-radio label="M">目录</el-radio>
                <el-radio label="C">菜单</el-radio>
                <el-radio label="F">按钮</el-radio>
              </el-radio-group>
            </>
          );
        },
      },
    },
    {
      formItem: { label: "菜单/路由地址", prop: "path", br: true },
      attrs: {
        render: ({ scope }) => {
          return (
            <>
              <el-input
                vModel={scope.form.path}
                placeholder="请输入 菜单地址"
                v-slots={{
                  prepend: () => {
                    return (
                      <el-select vModel={scope.form.pathPrefix} style="width: 120px">
                        <el-option label="" value="" />
                        <el-option label={httpPrefix} value={httpPrefix} />
                        <el-option label={httpsPrefix} value={httpsPrefix} />
                      </el-select>
                    );
                  },
                }}
              ></el-input>
            </>
          );
        },
        isHidden: (form: any) => form.menuType !== "C",
      },
    },
    {
      formItem: { label: "图标", prop: "icon" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 图标" } },
    },
    {
      formItem: { label: "组件路径", prop: "component" },
      attrs: {
        el: "el-input",
        props: { clearable: true, placeholder: "请输入 组件路径" },
        isHidden: (form: any) => form.menuType !== "C",
      },
    },
    {
      formItem: { label: "路由参数", prop: "param" },
      attrs: {
        el: "el-input",
        props: { clearable: true, placeholder: "请输入 路由参数" },
        isHidden: (form: any) => form.menuType !== "C",
      },
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
        isHidden: (form: any) => form.menuType !== "C",
      },
    },
    {
      formItem: { label: "是否为嵌入", prop: "isFrame" },
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
        isHidden: (form: any) => form.menuType !== "C",
      },
    },
    {
      formItem: { label: "meta 配置", prop: "meta", br: true },
      attrs: { el: "el-input", props: { type: "textarea", clearable: true, placeholder: "请输入 配置" } },
    },
    {
      formItem: { label: "介绍", prop: "intro", br: true },
      attrs: { el: "el-input", props: { type: "textarea", clearable: true, placeholder: "请输入 介绍" } },
    },
  ],
};
