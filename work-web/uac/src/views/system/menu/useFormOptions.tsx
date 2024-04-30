import { listMenuTreeSelectByApp, type Menu } from "@/api/system/menu";
import type { FormOptionsProps } from "@work/components";
import { ElInput, ElOption, ElSelect, type FormRules } from "element-plus";
import { httpPrefix, httpsPrefix } from "@work/constants";
import { layoutFormColumn } from "./layoutOptions";
import { iframeFormColumn } from "./iframeOptions";

const rules = reactive<FormRules>({
  appId: [{ required: true, message: "请选择 App", trigger: "blur" }],
  menuCode: [{ required: true, message: "请输入菜单编码", trigger: "blur" }],
  menuName: [{ required: true, message: "请输入菜单名称", trigger: "blur" }],
  path: [{ required: true, message: "请输入菜单/路由地址", trigger: "blur" }],
});

export const menuTypeEnum = [
  { value: "M", label: "目录" },
  { value: "C", label: "菜单" },
  { value: "F", label: "按钮" },
];

export const commonEnum = [
  { value: 1, label: "是" },
  { value: 0, label: "否" },
];

export const useFormOptions = (enumData: ComputedRef<any>, defaultValue: ComputedRef<string>) => {
  const options: FormOptionsProps<Menu.MenuInfo> = {
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
      { formItem: { title: "基础配置", label: "", prop: "" }, attrs: {} },
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
        formItem: { label: "菜单类型", prop: "menuType", br: true },
        attrs: {
          el: "el-radio-group",
          enum: menuTypeEnum,
          defaultValue: "C",
        },
      },
      {
        formItem: { label: form => (form.menuType === "M" ? `上级目录` : "上级菜单"), prop: "parentId", br: true },
        attrs: {
          el: "el-tree-select",
          props: {
            placeholder: "请选择 上级",
            filterable: true,
            valueKey: "id",
            checkStrictly: true,
          },
          enum: () => listMenuTreeSelectByApp({ appId: defaultValue.value }),
          isHidden: form => form.parentId === "0",
        },
      },
      {
        formItem: { label: form => `${getLabel(form)}编码`, prop: "menuCode" },
        attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 编码(如 UserManage)" } },
      },
      {
        formItem: { label: form => `${getLabel(form)}名称`, prop: "menuName" },
        attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 名称（如用户管理）" } },
      },
      {
        formItem: { label: "路由地址", prop: "path", br: true },
        attrs: {
          isDestroy: (form: any) => form.menuType === "F",
          render: ({ scope }) => {
            return (
              <>
                <ElInput
                  vModel={scope.form.path}
                  placeholder="请输入 路由地址(如 user-manage)"
                  v-slots={{
                    prepend: () => {
                      return (
                        <ElSelect vModel={scope.form.pathPrefix} style="width: 120px">
                          <ElOption value="" />
                          <ElOption value={httpPrefix} />
                          <ElOption value={httpsPrefix} />
                        </ElSelect>
                      );
                    },
                  }}
                ></ElInput>
              </>
            );
          },
        },
      },
      {
        formItem: { label: "权限标识", prop: "permission", br: true },
        attrs: {
          el: "el-input",
          props: { clearable: true, placeholder: "请输入 权限标识（system:user:list）" },
          isHidden: form => form.menuType === "M",
        },
      },
      {
        formItem: { label: "组件路径", prop: "component" },
        attrs: {
          el: "el-input",
          props: { clearable: true, placeholder: "请输入 组件路径" },
          isHidden: form => form.menuType !== "C",
        },
      },
      {
        formItem: { label: form => `${getLabel(form)}图标`, prop: "icon" },
        attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 图标" } },
      },
      {
        formItem: { label: "路由参数", prop: "param" },
        attrs: {
          el: "el-input",
          props: { clearable: true, placeholder: "请输入 路由参数" },
          isHidden: form => form.menuType !== "C",
        },
      },
      {
        formItem: { label: "显示顺序", prop: "orderNum" },
        attrs: { el: "el-input-number", defaultValue: 0 },
      },
      {
        formItem: { label: "介绍", prop: "intro", br: true },
        attrs: { el: "el-input", props: { type: "textarea", clearable: true, placeholder: "请输入 介绍" } },
      },
      { formItem: { title: "META 配置", label: "", prop: "" }, attrs: { isDestroy: form => form.menuType === "F" } },
      {
        formItem: { label: "启用", prop: "useMeta", br: true },
        attrs: {
          el: "el-radio-group",
          isDestroy: form => form.menuType === "F",
          enum: commonEnum,
          defaultValue: 0,
        },
      },

      ...layoutFormColumn,
      ...iframeFormColumn,
    ],
  };

  const getLabel = (form: Menu.MenuInfo) => {
    if (form.menuType === "M") return "目录";
    else if (form.menuType === "C") return "菜单";
    else if (form.menuType === "F") return "按钮";
  };

  return { options };
};
