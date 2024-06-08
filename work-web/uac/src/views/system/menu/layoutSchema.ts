import type { DialogFormSchemaProps } from "@work/components";

export const metaEnumYes = [
  { value: "default", label: "是（默认）" },
  { value: false, label: "否" },
];

export const metaEnumNo = [
  { value: true, label: "是" },
  { value: "default", label: "否（默认）" },
];

const defaultValue = "default";
const destroy = form => form.useMeta !== 1;

export const layoutFormSchema: DialogFormSchemaProps[] = [
  {
    prop: "layout",
    label: "布局配置",
    labelSize: "small",
    el: "ElDivider",
    destroy,
  },
  {
    prop: "meta.notClickBread",
    label: "支持面包屑点击",
    el: "el-radio-group",
    destroy,
    enum: metaEnumNo,
    defaultValue,
    formItem: { labelWidth: 120 },
  },
  {
    prop: "meta.hideInBread",
    label: "隐藏在面包屑",
    el: "el-radio-group",
    destroy,
    enum: metaEnumNo,
    defaultValue,
    formItem: { labelWidth: 120 },
  },
  {
    prop: "meta.hideInMenu",
    label: "隐藏在菜单栏",
    el: "el-radio-group",
    destroy,
    enum: metaEnumNo,
    defaultValue,
    formItem: { labelWidth: 120 },
  },
  {
    prop: "meta.hideInTab",
    label: "隐藏在标签页",
    el: "el-radio-group",
    destroy,
    enum: metaEnumNo,
    defaultValue,
    formItem: { labelWidth: 120 },
  },
  {
    prop: "meta.alwaysShowRoot",
    label: "展示根菜单",
    el: "el-radio-group",
    destroy,
    enum: metaEnumNo,
    defaultValue,
    formItem: { labelWidth: 120 },
  },
  {
    prop: "meta.isAffix",
    label: "固定在标签页",
    el: "el-radio-group",
    destroy,
    enum: metaEnumNo,
    defaultValue,
    formItem: { labelWidth: 120 },
  },
  {
    prop: "meta.isKeepAlive",
    label: "缓存",
    el: "el-radio-group",
    destroy,
    enum: metaEnumNo,
    defaultValue,
    formItem: { labelWidth: 120 },
  },
  {
    prop: "meta.isFull",
    label: "全屏",
    el: "el-radio-group",
    destroy,
    enum: metaEnumNo,
    defaultValue,
    formItem: { labelWidth: 120 },
  },
  {
    prop: "meta.useI18n",
    label: "是否开启 i18n",
    el: "el-radio-group",
    destroy,
    enum: metaEnumYes,
    defaultValue,
    formItem: { labelWidth: 120 },
  },
  {
    prop: "meta.useTooltip",
    label: "菜单栏溢出开启 Tip",
    el: "el-radio-group",
    destroy,
    enum: metaEnumNo,
    defaultValue,
    formItem: { labelWidth: 140 },
  },
  {
    prop: "meta.dynamicLevel",
    label: "动态路由最大数量",
    el: "el-input-number",
    destroy,
    formItem: { labelWidth: 130 },
  },
  {
    prop: "meta.activeMenu",
    label: "高亮菜单名",
    el: "el-input",
    destroy,
    formItem: { labelWidth: 120 },
  },
  {
    prop: "meta.beforeCloseName",
    label: "关闭路由回调名",
    el: "el-input",
    destroy,
    formItem: { labelWidth: 120 },
  },
];
