import type { FormColumnProps } from "@work/components";

export const metaEnumYes = [
  { value: "default", label: "是（默认）" },
  { value: false, label: "否" },
];

export const metaEnumNo = [
  { value: true, label: "是" },
  { value: "default", label: "否（默认）" },
];

const isDestroy = (form: any) => form.useMeta === 0 || !form.useMeta;

export const iframeFormColumn: FormColumnProps[] = [
  {
    formItem: { title: "IFrame 配置", size: "small", label: "", prop: "" },
    attrs: { isDestroy: isDestroy },
  },
  {
    formItem: { label: "IFrame 链接", prop: "meta.frameSrc", labelWidth: 100, br: true },
    attrs: {
      el: "el-input",
      isDestroy: isDestroy,
    },
  },
  {
    formItem: { label: "IFrame 加载动画", prop: "meta.frameLoading", labelWidth: 120 },
    attrs: {
      el: "el-radio-group",
      isDestroy: isDestroy,
      enum: metaEnumYes,
      defaultValue: "default",
    },
  },
  {
    formItem: { label: "IFrame 开启缓存", prop: "meta.frameKeepAlive", labelWidth: 120 },
    attrs: {
      el: "el-radio-group",
      isDestroy: isDestroy,
      enum: metaEnumNo,
      defaultValue: "default",
    },
  },
  {
    formItem: { label: "IFrame 新标签页打开", prop: "meta.frameOpen", labelWidth: 150 },
    attrs: {
      el: "el-radio-group",
      isDestroy: isDestroy,
      enum: metaEnumNo,
      defaultValue: "default",
    },
  },
];
