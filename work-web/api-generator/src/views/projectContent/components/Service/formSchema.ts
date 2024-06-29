import type { Service } from "@/api/service";
import type { FormRules } from "element-plus";
import type { DialogFormSchemaProps } from "work";

const rules: FormRules = {
  appId: [{ required: true, message: "请选择 App", trigger: "blur" }],
  dictCode: [{ required: true, message: "请输入字典编码", trigger: "blur" }],
  dictName: [{ required: true, message: "请输入字典名称", trigger: "blur" }],
};

export const elFormProps = {
  labelWidth: 80,
  rules: rules,
};

export const schema: DialogFormSchemaProps<Service.ServiceInfo>[] = [
  {
    prop: "serviceName",
    label: "服务名称",
    el: "el-input",
    props: { placeholder: "请输入 服务名称" },
  },
  {
    prop: "reportTitle",
    label: "报表名称",
    el: "el-input",
    props: { placeholder: "请输入 报表名称" },
  },
  {
    prop: "serviceUrl",
    label: "服务地址",
    el: "el-input",
    props: { placeholder: "请输入 服务名称" },
		slots: {
			prepend:() =>{};
		}
  },
  {
    prop: "status",
    label: "服务状态",
    el: "el-select",
    enum: [
      { value: 0, label: "禁用" },
      { value: 1, label: "启用" },
    ],
    props: { placeholder: "请选择 服务状态" },
  },
  {
    prop: "isAuth",
    label: "是否认证",
    el: "el-select",
    enum: [
      { value: 0, label: "禁用" },
      { value: 1, label: "启用" },
    ],
    props: { placeholder: "请选择 是否认证" },
  },
  {
    prop: "selectSql",
    label: "SQL",
    el: "el-input",
  },
  {
    prop: "description",
    label: "服务描述",
    el: "el-input",
    props: { type: "textarea" },
  },
  {
    prop: "insertTable",
    label: "Insert Table",
    el: "el-select",
    enum: [{ value: "Table1", label: "Table1" }],
    props: { placeholder: "请选择接支持插入的表名" },
  },
  {
    prop: "updateTable",
    label: "Update Table",
    el: "el-select",
    enum: [{ value: "Table1", label: "Table1" }],
    props: { placeholder: "请选择接支持更新的表名" },
  },
  {
    prop: "deleteTable",
    label: "Delete Table",
    el: "el-select",
    enum: [{ value: "Table1", label: "Table1" }],
    props: { placeholder: "请选择接支持删除的表名" },
  },
];
