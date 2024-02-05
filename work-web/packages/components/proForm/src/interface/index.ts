import type { FormProps } from "element-plus/es/components/form/src/form";
import type { FormItemProps } from "element-plus/es/components/form/src/form-item";
import type { VNode, ComputedRef } from "vue";

type ValueType = string | number | boolean | any[];

export interface FormOptionsProps {
  form?: Partial<FormProps> & { fixWidth?: boolean; width?: number | string };
  columns: FormColumnProps[];
}

export interface FormEnumProps {
  label?: string; // 选项框显示的文字
  value?: ValueType; // 选项框值
  disabled?: boolean; // 是否禁用此选项
  children?: FormEnumProps[]; // 为树形选择时，可以通过 children 属性指定子选项
  [key: string]: any;
}
type FormItem = Partial<Omit<FormItemProps, "prop">> & { prop: string; class?: string } & { br?: boolean };

export type FormType =
  | "el-input"
  | "el-input-number"
  | "el-select"
  | "el-select-v 2"
  | "el-tree-select"
  | "el-cascader"
  | "el-date-picker"
  | "el-time-picker"
  | "el-time-select"
  | "el-switch"
  | "el-slider"
  | "wang-editor";

export type FormFieldNamesProps = { label: string; value: string; children?: string };

export type FormRenderScope = {
  scope: {
    form: { [key: string]: any };
    data: ValueType;
  };
  placeholder: string;
  clearable: boolean;
  options: FormEnumProps[];
  data: FormEnumProps[];
};

export interface FormColumnProps {
  formItem: FormItem;
  attrs: {
    el?: FormType;
    enum?: FormEnumProps[] | ((enumMap?: any) => Promise<any>) | ComputedRef<FormEnumProps[]>;
    enumKey?: string; // 如果 enum 是接口调用，那么可以指定哪个 key 获取 enum 数据，默认返回的数据作为 enum
    useEnumMap?: string | ((enumMap?: any) => any); // 从 enumMap 中获取其他的 enum 数据
    // 枚举类型（字典）
    fieldNames?: FormFieldNamesProps; // 字典指定 label && value && children 的 key 值
    props?: any; // 搜索项参数，根据 element plus 官方文档来传递，该属性所有值会透传到组件
    order?: number; // 表单排序（从大到小）
    defaultValue?: ValueType | (() => ValueType | any) | ComputedRef<ValueType>; // 默认值
    width?: string | number;
    subProp?: string; // 级联表单的 prop
    subEnum?: FormEnumProps[] | ((params?: any) => Promise<any>); // 级联表单的 prop
    render?: (scope: FormRenderScope) => VNode; // 自定义搜索内容渲染（tsx 语法）
    isHidden?: boolean | any; // 是否渲染，true 不渲染，false 渲染
    show?: Array<"add" | "edit">; // 给 ProTable 的 DialogOperate.vue 使用
    [key: string]: any;
  };
}
