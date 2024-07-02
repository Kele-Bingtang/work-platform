import type { FormSchemaProps } from "work";

export const searchSchema: FormSchemaProps[] = [
  {
    prop: "projectName",
    label: "项目名称",
    el: "el-input",
    props: { placeholder: "请输入 项目名称" },
  },
  {
    prop: "dataSourceId",
    label: "数据源",
    el: "el-input",
    props: { placeholder: "请选择 数据源" },
  },
];
