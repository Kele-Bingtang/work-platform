import type { FormSchemaProps } from "work";
import { QuestionFilled } from "@element-plus/icons-vue";

export const schema: FormSchemaProps[] = [
  {
    prop: "projectName",
    label: "项目名称",
    el: "el-input",
    props: { placeholder: "请输入接口基础路径" },
  },
  {
    prop: "baseUrl",
    label: "接口基础路径",
    el: "el-input",
    tip: {
      // effect: "dark",
      // placement: "right",
      icon: QuestionFilled,
      contentRender: () => {
        return (
          <div style="line-height: 1.5715">
            接口基础路径为 '/' 开头有字母或 数
            <br />
            字 组成的字符串如：'/shop01' 不能
            <br />
            有多个 '/'
          </div>
        );
      },
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
  {
    prop: "databaseName",
    label: "数据库",
    el: "el-select",
    enum: [{ value: "mysql", label: "mysql" }],
  },
];
