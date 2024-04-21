import { listMenuTreeSelectByApp, type Menu } from "@/api/system/menu";
import type { FormOptionsProps } from "@work/components";
import { ElInput, ElOption, ElRadio, ElRadioGroup, ElSelect, type FormRules } from "element-plus";
import { httpPrefix, httpsPrefix } from "@work/constants";

const rules = reactive<FormRules>({
  appId: [{ required: true, message: "请选择 App", trigger: "blur" }],
  menuCode: [{ required: true, message: "请输入菜单编码", trigger: "blur" }],
  menuName: [{ required: true, message: "请输入菜单名称", trigger: "blur" }],
  path: [{ required: true, message: "请输入菜单/路由地址", trigger: "blur" }],
});

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
          enum: [
            { value: "M", label: "目录" },
            { value: "C", label: "菜单" },
            { value: "F", label: "按钮" },
          ],
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
          },
          enum: listMenuTreeSelectByApp,
          isHidden: form => form.parentId === "0",
        },
      },
      {
        formItem: { label: form => `${getLabel(form)}编码`, prop: "menuCode" },
        attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 编码" } },
      },
      {
        formItem: { label: form => `${getLabel(form)}名称`, prop: "menuName" },
        attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 名称" } },
      },
      {
        formItem: { label: "路由地址", prop: "path", br: true },
        attrs: {
          isHidden: (form: any) => form.menuType !== "C",
          render: ({ scope }) => {
            return (
              <>
                <ElInput
                  vModel={scope.form.path}
                  placeholder="请输入 路由地址"
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
        formItem: { label: form => `${getLabel(form)}图标`, prop: "icon" },
        attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 图标" } },
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
        formItem: { label: "是否缓存", prop: "isCache" },
        attrs: {
          el: "el-radio-group",
          enum: [
            { value: 1, label: "是" },
            { value: 0, label: "否" },
          ],
          defaultValue: 0,
          isDestroy: form => form.menuType !== "C",
        },
      },
      {
        formItem: { label: "是否嵌入", prop: "isFrame" },
        attrs: {
          el: "el-radio-group",
          enum: [
            { value: 1, label: "是" },
            { value: 0, label: "否" },
          ],
          defaultValue: 0,
          isDestroy: form => form.menuType !== "C",
        },
      },
      {
        formItem: { label: "meta 配置", prop: "meta", br: true },
        attrs: {
          el: "el-input",
          isDestroy: form => form.menuType === "F",
          props: {
            type: "textarea",
            clearable: true,
            placeholder: "请输入 配置",
          },
        },
      },
      {
        formItem: { label: "介绍", prop: "intro", br: true },
        attrs: { el: "el-input", props: { type: "textarea", clearable: true, placeholder: "请输入 介绍" } },
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
