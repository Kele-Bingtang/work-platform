import { listMenuTreeSelectByApp, type Menu } from "@/api/system/menu";
import type { DialogFormSchemaProps } from "@work/components";
import { ElInput, ElOption, ElSelect, type FormRules } from "element-plus";
import { httpPrefix, httpsPrefix } from "@work/constants";
import { layoutFormSchema } from "./layoutSchema";
import { iframeFormSchema } from "./iframeSchema";
import { isObject } from "@work/utils";

const rules = reactive<FormRules>({
  appId: [{ required: true, message: "请选择 App", trigger: "blur" }],
  menuCode: [{ required: true, message: "请输入菜单编码", trigger: "blur" }],
  menuName: [{ required: true, message: "请输入菜单名称", trigger: "blur" }],
  path: [{ required: true, message: "请输入菜单/路由地址", trigger: "blur" }],
});

export const menuTypeEnum = [
  { value: "C", label: "目录" },
  { value: "M", label: "菜单" },
  { value: "F", label: "按钮" },
];

export const commonEnum = [
  { value: 1, label: "是" },
  { value: 0, label: "否" },
];

export const elFormProps = {
  labelWidth: 80,
  rules: rules,
};

export const useFormSchema = (enumData: ComputedRef<any>, defaultValue: ComputedRef<string>) => {
  const schema: DialogFormSchemaProps<Menu.MenuInfo>[] = [
    {
      prop: "base",
      label: "基础配置",
      el: "ElDivider",
    },
    {
      prop: "appId",
      label: "所属 App",
      el: "el-select",
      enum: enumData,
      fieldNames: { value: "appId", label: "appName" },
      defaultValue: defaultValue,
      disabledIn: ["edit"],
      props: { clearable: true, placeholder: "请选择 App" },
      col: { span: 24 },
    },
    {
      prop: "menuType",
      label: "菜单类型",
      el: "el-radio-group",
      enum: menuTypeEnum,
      defaultValue: "C",
      col: { span: 24 },
    },
    {
      prop: "parentId",
      label: model => (model.menuType === "C" ? `上级目录` : "上级菜单"),
      el: "el-tree-select",
      props: {
        placeholder: "请选择 上级",
        filterable: true,
        valueKey: "id",
        checkStrictly: true,
      },
      enum: () => listMenuTreeSelectByApp({ appId: defaultValue.value }),
      hidden: model => model.parentId === "0",
      col: { span: 24 },
    },
    {
      prop: "menuCode",
      label: model => `${getLabel(model)}编码`,
      el: "el-input",
      props: { clearable: true, placeholder: "请输入 编码(如 UserManage)" },
    },
    {
      prop: "menuName",
      label: model => `${getLabel(model)}名称`,
      el: "el-input",
      props: { clearable: true, placeholder: "请输入 名称（如用户管理）" },
    },
    {
      prop: "path",
      label: "路由地址",
      destroy: model => model.menuType === "F",
      render: ({ model }) => {
        return (
          <>
            <ElInput
              vModel={model.path}
              placeholder="请输入 路由地址(如 user-manage)"
              v-slots={{
                prepend: () => {
                  return (
                    <ElSelect vModel={model.pathPrefix} style="width: 120px">
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
      col: { span: 24 },
    },
    {
      prop: "permission",
      label: "权限标识",
      el: "el-input",
      props: { clearable: true, placeholder: "请输入 权限标识（system:user:list）" },
      hidden: model => model.menuType === "C",
      col: { span: 24 },
    },
    {
      prop: "component",
      label: "组件路径",
      el: "el-input",
      props: { clearable: true, placeholder: "请输入 组件路径" },
      hidden: model => model.menuType !== "M",
    },
    {
      prop: "icon",
      label: model => `${getLabel(model)}图标`,
      el: "icon-picker",
      props: { clearable: true, placeholder: "请选择 图标" },
      hidden: model => model.menuType === "F",
    },
    {
      prop: "param",
      label: "路由参数",
      el: "el-input",
      props: { clearable: true, placeholder: "请输入 路由参数" },
      hidden: model => model.menuType !== "M",
    },
    {
      prop: "orderNum",
      label: "显示顺序",
      el: "el-input-number",
      defaultValue: 1,
    },
    {
      prop: "intro",
      label: "介绍",
      el: "el-input",
      props: { type: "textarea", clearable: true, placeholder: "请输入 介绍" },
      col: { span: 24 },
    },
    {
      prop: "iframe",
      label: "META 配置",
      el: "ElDivider",
      destroy: model => model.menuType === "F",
    },
    {
      label: "显示",
      prop: "useMeta",
      el: "el-radio-group",
      destroy: model => model.menuType === "F",
      enum: commonEnum,
      defaultValue: model => {
        if (!model.meta) return 0;
        const m = { ...model.meta };
        ["title", "icon", "rank"].forEach(key => delete m[key]);

        for (const key in m) {
          const val = m[key];
          if (val === "default") delete m[key];
        }
        return Object.keys(m).length ? 1 : 0;
      },
      col: { span: 24 },
    },

    ...layoutFormSchema,
    ...iframeFormSchema,
  ];

  const getLabel = (model: Menu.MenuInfo) => {
    if (model.menuType === "C") return "目录";
    else if (model.menuType === "M") return "菜单";
    else if (model.menuType === "F") return "按钮";
  };

  return { schema };
};
