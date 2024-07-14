<template>
  <div :class="prefixClass">
    <ProTable
      :request-api="listServicePage"
      :columns
      :init-request-param
      :requestAuto="false"
      :dialogForm
      :search-cols="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
    ></ProTable>
  </div>
</template>

<script setup lang="tsx" name="Service">
import { listServicePage, addService, editService, removeService, type Service } from "@/api/service";
import { ProjectKey } from "@/config/symbol";
import { ElTooltip, ElTag } from "element-plus";
import { ProTable, useDesign, type DialogForm, type DialogFormSchemaProps, type TableColumnProps } from "work";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("service");

const props = defineProps<{ categoryId: string }>();

const router = useRouter();

const projectInfo = inject(ProjectKey);
const initRequestParam = reactive({ categoryId: "", projectId: "" });

watch(projectInfo, () => {
  initRequestParam.projectId = unref(projectInfo)?.projectId;
  initRequestParam.categoryId = props.categoryId;
});

const columns: TableColumnProps[] = [
  { type: "index", label: "#", width: 80 },
  {
    prop: "serviceName",
    label: "接口名称",
    width: 180,
    search: { el: "el-input" },
    link: true,
    linkProps: {
      onClick: ({ row }) => router.push(`/service/${row.serviceId}/${row.serviceName}`),
    },
  },
  { prop: "reportTitle", label: "报表名称", width: 180 },
  { prop: "status", label: "接口状态", width: 100 },
  {
    prop: "serviceUrl",
    label: "接口地址",
    render: ({ row }) => (
      <ElTooltip effect="dark" content="复制接口相对地址" placement="top">
        <ElTag type="primary">{row.serviceUrl}</ElTag>
      </ElTooltip>
    ),
  },
  { prop: "description", label: "接口描述" },
  { prop: "createTime", label: "创建时间" },
  { prop: "operation", label: "操作", width: 280, fixed: "right" },
];

const schema: DialogFormSchemaProps<Service.ServiceInfo>[] = [
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
    props: { placeholder: "请输入 服务地址" },
    slots: {
      prepend: () => unref(projectInfo)?.baseUrl,
    },
    col: { span: 24 },
  },
  {
    prop: "status",
    label: "服务状态",
    el: "el-select",
    defaultValue: 1,
    enum: [
      { value: 0, label: "下线" },
      { value: 1, label: "上线" },
    ],
    props: { placeholder: "请选择 服务状态" },
  },
  {
    prop: "isAuth",
    label: "是否认证",
    el: "el-select",
    defaultValue: 0,
    enum: [
      { value: 0, label: "禁用" },
      { value: 1, label: "启用" },
    ],
    props: { placeholder: "请选择 是否认证" },
  },
  {
    prop: "description",
    label: "服务描述",
    el: "el-input",
    props: { type: "textarea" },
    col: { span: 24 },
  },
];

const dialogForm: DialogForm = {
  formProps: {
    elFormProps: {
      labelWidth: 80,
      rules: {
        serviceName: [{ required: true, message: "请选择服务名称", trigger: "blur" }],
        serviceUrl: [{ required: true, message: "请输入服务地址", trigger: "blur" }],
        status: [{ required: true, message: "请选择服务状态", trigger: "blur" }],
        isAuth: [{ required: true, message: "请选择是否认证", trigger: "blur" }],
      },
    },
    schema: schema,
  },
  id: ["id", "serviceId"],
  addApi: data =>
    addService({
      ...data,
      categoryId: props.categoryId,
      projectId: unref(projectInfo)?.projectId,
      teamId: unref(projectInfo)?.teamId,
    }),
  editApi: data => editService({ ...data, projectId: unref(projectInfo)?.projectId }),
  removeApi: removeService,
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "45%",
    height: 250,
    top: "5vh",
    closeOnClickModal: false,
  },
};
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-service;

.#{$prefix-class} {
  flex: 1;
}
</style>
