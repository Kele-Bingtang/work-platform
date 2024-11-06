import type { FormSchemaProps } from "work";
import { QuestionFilled } from "@element-plus/icons-vue";
import type { FormRules } from "element-plus";

export const rules: FormRules = {
  projectName: [{ required: true, message: "请输入 项目名称", trigger: "blur" }],
  baseUrl: [{ required: true, message: "请输入 接口基础路径", trigger: "blur" }],
};

export const schema: FormSchemaProps[] = [
  {
    prop: "projectName",
    label: "项目名称",
    el: "el-input",
    props: { placeholder: "请输入 项目名称" },
  },
  {
    prop: "baseUrl",
    label: "接口基础路径",
    el: "el-input",
    props: { placeholder: "请输入 接口基础路径" },
    tip: {
      icon: QuestionFilled,
      content: "接口基础路径为「/」开头，由「字母」或「数字」组成字符串，如「/api01」。不能有多个「/」，如「/api01/」",
    },
  },
  {
    prop: "description",
    label: "项目描述",
    el: "el-input",
    props: {
      type: "textarea",
      placeholder: "请输入 项目描述",
      autosize: {
        minRows: 4,
      },
    },
  },
];
