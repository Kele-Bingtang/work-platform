import { listMenuTreeSelectByApp, type Menu } from "@/api/system/menu";
import type { FormOptionsProps } from "@work/components";
import { ElInput, ElOption, ElSelect, type FormRules } from "element-plus";
import { httpPrefix, httpsPrefix } from "@work/constants";

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
      { title: "基础配置" },
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
        formItem: { label: "显示状态", prop: "visible" },
        attrs: {
          el: "el-radio-group",
          enum: [
            { value: 1, label: "显示" },
            { value: 0, label: "隐藏" },
          ],
          defaultValue: 1,
        },
      },
      {
        formItem: { label: "介绍", prop: "intro", br: true },
        attrs: { el: "el-input", props: { type: "textarea", clearable: true, placeholder: "请输入 介绍" } },
      },
      { title: "META 配置", formItem: {}, attrs: {} },
      {
        formItem: { label: "META 配置", prop: "useMeta", br: true },
        attrs: {
          el: "el-radio-group",
          isDestroy: form => form.menuType === "F",
          enum: commonEnum,
          defaultValue: 0,
        },
      },
      {
        formItem: { label: "允许点击面包屑", prop: "notClickBread", labelWidth: 120 },
        attrs: {
          el: "el-radio-group",
          isDestroy: form => form.useMeta === 0,
          enum: commonEnum,
          defaultValue: 0,
        },
      },
      {
        formItem: { label: "不添加到面包屑", prop: "hideInBread", labelWidth: 120 },
        attrs: {
          el: "el-radio-group",
          isDestroy: form => form.useMeta === 0,
          enum: commonEnum,
          defaultValue: 0,
        },
      },
      {
        formItem: { label: "不添加到菜单", prop: "hideInMenu", labelWidth: 120 },
        attrs: {
          el: "el-radio-group",
          isDestroy: form => form.useMeta === 0,
          enum: commonEnum,
          defaultValue: 0,
        },
      },
      {
        formItem: { label: "总是渲染为菜单", prop: "alwaysShowRoot", labelWidth: 120 },
        attrs: {
          el: "el-radio-group",
          isDestroy: form => form.useMeta === 0,
          enum: commonEnum,
          defaultValue: 0,
        },
      },
      {
        formItem: { label: "缓存", prop: "isKeepAlive", labelWidth: 120 },
        attrs: {
          el: "el-radio-group",
          isDestroy: form => form.useMeta === 0,
          enum: commonEnum,
          defaultValue: 0,
        },
      },
      {
        formItem: { label: "固定在标签页", prop: "isAffix", labelWidth: 120 },
        attrs: {
          el: "el-radio-group",
          isDestroy: form => form.useMeta === 0,
          enum: commonEnum,
          defaultValue: 0,
        },
      },
      {
        formItem: { label: "全屏", prop: "isFull", labelWidth: 120 },
        attrs: {
          el: "el-radio-group",
          isDestroy: form => form.useMeta === 0,
          enum: commonEnum,
          defaultValue: 0,
        },
      },
      {
        formItem: { label: "高亮菜单", prop: "activeMenu", labelWidth: 120 },
        attrs: {
          el: "el-input",
          isDestroy: form => form.useMeta === 0,
        },
      },
      {
        formItem: { label: "关闭路由回调名", prop: "beforeCloseName", labelWidth: 120 },
        attrs: {
          el: "el-input",
          isDestroy: form => form.useMeta === 0,
        },
      },
      {
        formItem: { label: "IFrame 链接", prop: "frameSrc", labelWidth: 120 },
        attrs: {
          el: "el-input",
          isDestroy: form => form.useMeta === 0,
        },
      },
      {
        formItem: { label: "IFrame 加载动画", prop: "frameLoading", labelWidth: 120 },
        attrs: {
          el: "el-radio-group",
          isDestroy: form => form.useMeta === 0,
          enum: commonEnum,
          defaultValue: 1,
        },
      },
      {
        formItem: { label: "IFrame 开启缓存", prop: "frameKeepAlive", labelWidth: 120 },
        attrs: {
          el: "el-radio-group",
          isDestroy: form => form.useMeta === 0,
          enum: commonEnum,
          defaultValue: 0,
        },
      },
      {
        formItem: { label: "IFrame 新标签页打开", prop: "frameOpen", labelWidth: 150 },
        attrs: {
          el: "el-radio-group",
          isDestroy: form => form.useMeta === 0,
          enum: commonEnum,
          defaultValue: 0,
        },
      },
      {
        formItem: { label: "添加到标签页", prop: "hideInTab", labelWidth: 120 },
        attrs: {
          el: "el-radio-group",
          isDestroy: form => form.useMeta === 0,
          enum: commonEnum,
          defaultValue: 0,
        },
      },
      {
        formItem: { label: "动态路由最大数量", prop: "dynamicLevel", labelWidth: 130 },
        attrs: {
          el: "el-input",
          isDestroy: form => form.useMeta === 0,
        },
      },
      {
        formItem: { label: "是否开启 i18n", prop: "useI18n", labelWidth: 120 },
        attrs: {
          el: "el-radio-group",
          isDestroy: form => form.useMeta === 0,
          enum: commonEnum,
          defaultValue: 0,
        },
      },
      {
        formItem: { label: "菜单溢出开启 Tip", prop: "useTooltip", labelWidth: 120 },
        attrs: {
          el: "el-radio-group",
          isDestroy: form => form.useMeta === 0,
          enum: commonEnum,
          defaultValue: 0,
        },
      },
    ],
  };

  const getLabel = (form: Menu.MenuInfo) => {
    if (form.menuType === "M") return "目录";
    else if (form.menuType === "C") return "菜单";
    else if (form.menuType === "F") return "按钮";
  };
  return { options };
};
