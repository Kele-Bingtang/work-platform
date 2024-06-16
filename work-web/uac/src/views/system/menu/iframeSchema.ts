import type { DialogFormSchemaProps } from "@work/components";

export const metaEnumYes = [
  { value: "default", label: "是（默认）" },
  { value: false, label: "否" },
];

export const metaEnumNo = [
  { value: true, label: "是" },
  { value: "default", label: "否（默认）" },
];

const destroy = (form: any) => form.useMeta === 0;

export const iframeFormSchema: DialogFormSchemaProps[] = [
  {
    prop: "iframe",
    label: "IFrame 配置",
    labelSize: "small",
    el: "ElDivider",
    destroy: destroy,
  },
  {
    prop: "meta.frameSrc",
    label: "IFrame 链接",
    el: "el-input",
    destroy: destroy,
    formItem: { labelWidth: 100 },
    col: { span: 24 },
  },
  {
    prop: "meta.frameLoading",
    label: "IFrame 加载动画",
    el: "el-radio-group",
    destroy: destroy,
    enum: metaEnumYes,
    defaultValue: "default",
    formItem: { labelWidth: 120 },
  },
  {
    prop: "meta.frameKeepAlive",
    label: "IFrame 开启缓存",
    el: "el-radio-group",
    destroy: destroy,
    enum: metaEnumNo,
    defaultValue: "default",
    formItem: { labelWidth: 120 },
  },
  {
    prop: "meta.frameOpen",
    label: "IFrame 新标签页打开",
    el: "el-radio-group",
    destroy: destroy,
    enum: metaEnumNo,
    defaultValue: "default",
    formItem: { labelWidth: 150 },
  },
];
