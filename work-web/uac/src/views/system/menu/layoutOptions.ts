import type { FormColumnProps } from "@work/components";

export const metaEnumYes = [
  { value: "default", label: "是（默认）" },
  { value: false, label: "否" },
];

export const metaEnumNo = [
  { value: true, label: "是" },
  { value: "default", label: "否（默认）" },
];

const isDestroy = (form: any) => form.useMeta !== 1;

export const layoutFormColumn: FormColumnProps[] = [
  {
    formItem: { title: "布局配置", size: "small", label: "", prop: "" },
    attrs: { isDestroy: isDestroy },
  },
  {
    formItem: { label: "支持面包屑点击", prop: "meta.notClickBread", labelWidth: 120 },
    attrs: {
      el: "el-radio-group",
      isDestroy: isDestroy,
      enum: metaEnumNo,
      defaultValue: "default",
    },
  },
  {
    formItem: { label: "隐藏在面包屑", prop: "meta.hideInBread", labelWidth: 120 },
    attrs: {
      el: "el-radio-group",
      isDestroy: isDestroy,
      enum: metaEnumNo,
      defaultValue: "default",
    },
  },
  {
    formItem: { label: "隐藏在菜单栏", prop: "meta.hideInMenu", labelWidth: 120 },
    attrs: {
      el: "el-radio-group",
      isDestroy: isDestroy,
      enum: metaEnumNo,
      defaultValue: "default",
    },
  },
  {
    formItem: { label: "隐藏在标签页", prop: "meta.hideInTab", labelWidth: 120 },
    attrs: {
      el: "el-radio-group",
      isDestroy: isDestroy,
      enum: metaEnumNo,
      defaultValue: "default",
    },
  },
  {
    formItem: { label: "展示根菜单", prop: "meta.alwaysShowRoot", labelWidth: 120 },
    attrs: {
      el: "el-radio-group",
      isDestroy: isDestroy,
      enum: metaEnumNo,
      defaultValue: "default",
    },
  },
  {
    formItem: { label: "固定在标签页", prop: "meta.isAffix", labelWidth: 120 },
    attrs: {
      el: "el-radio-group",
      isDestroy: isDestroy,
      enum: metaEnumNo,
      defaultValue: "default",
    },
  },
  {
    formItem: { label: "缓存", prop: "meta.isKeepAlive", labelWidth: 120 },
    attrs: {
      el: "el-radio-group",
      isDestroy: isDestroy,
      enum: metaEnumNo,
      defaultValue: "default",
    },
  },
  {
    formItem: { label: "全屏", prop: "meta.isFull", labelWidth: 120 },
    attrs: {
      el: "el-radio-group",
      isDestroy: isDestroy,
      enum: metaEnumNo,
      defaultValue: "default",
    },
  },
  {
    formItem: { label: "是否开启 i18n", prop: "meta.useI18n", labelWidth: 120 },
    attrs: {
      el: "el-radio-group",
      isDestroy: isDestroy,
      enum: metaEnumYes,
      defaultValue: "default",
    },
  },
  {
    formItem: { label: "菜单栏溢出开启 Tip", prop: "meta.useTooltip", labelWidth: 140 },
    attrs: {
      el: "el-radio-group",
      isDestroy: isDestroy,
      enum: metaEnumNo,
      defaultValue: "default",
    },
  },
  {
    formItem: { label: "动态路由最大数量", prop: "meta.dynamicLevel", labelWidth: 130 },
    attrs: {
      el: "el-input",
      isDestroy: isDestroy,
    },
  },
  {
    formItem: { label: "高亮菜单名", prop: "meta.activeMenu", labelWidth: 120 },
    attrs: {
      el: "el-input",
      isDestroy: isDestroy,
    },
  },
  {
    formItem: { label: "关闭路由回调名", prop: "meta.beforeCloseName", labelWidth: 120 },
    attrs: {
      el: "el-input",
      isDestroy: isDestroy,
    },
  },
];
