<template>
  <div :class="prefixClass">
    <ProTable
      ref="proTableRef"
      :request-api="listServicePage"
      :columns
      :init-request-param
      :requestAuto="false"
      :dialogForm
      :search-cols="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
    >
      <template #operationExtra="{ row }">
        <el-dropdown @command="command => handleCommand(command, row)" class="!align-middle ml-2">
          <el-button link type="info" :icon="MoreFilled">更多</el-button>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="handleEditReport" :icon="Edit">报表编辑</el-dropdown-item>
              <el-dropdown-item command="handleSearchServiceData" :icon="Search">服务查询</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </template>
    </ProTable>
  </div>
</template>

<script setup lang="tsx" name="Service">
import { listServicePage, addService, editService, removeService, type Service } from "@/api/service";
import { ProjectKey } from "@/config/symbol";
import { ElTooltip, ElTag } from "element-plus";
import {
  ProTable,
  message,
  Point,
  useDialog,
  type DialogForm,
  type DialogFormSchemaProps,
  type TableColumnProps,
} from "work";
import { useDesign, useClipboard } from "@work/hooks";
import { Search, MoreFilled, Edit } from "@element-plus/icons-vue";
import { useDictStore } from "@/stores";
import { getReportByServiceId } from "@/api/report";
import ReportForm from "./ReportForm.vue";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("service");

const { copy } = useClipboard();
const { open } = useDialog();

const props = defineProps<{ categoryId: string }>();

const router = useRouter();
const { getDictData } = useDictStore();

const projectInfo = inject(ProjectKey);
const proTableRef = shallowRef<InstanceType<typeof ProTable>>();
const initRequestParam = reactive({ categoryId: "", projectId: "" });

watch(
  () => [unref(projectInfo), props.categoryId],
  () => {
    initRequestParam.projectId = unref(projectInfo)?.projectId || "";
    initRequestParam.categoryId = props.categoryId;
  }
);

const handleCopyLink = (row: Service.ServiceInfo) => {
  copy(`${import.meta.env.VITE_API_URL}/generic-api/list${unref(projectInfo)?.baseUrl}${row.serviceUrl}`);
  message.success("复制成功");
};

const handleCommand = (command: string, row: Service.ServiceInfo) => {
  if (command === "handleEditReport") {
    editReport(row);
  } else if (command === "handleSearchServiceData") {
    handleSearchServiceData(row);
  }
};

const reportFormRef = shallowRef<InstanceType<typeof ReportForm>>();

const editReport = async (row: Service.ServiceInfo) => {
  const res = await getReportByServiceId(row.serviceId);
  if (res.code === 200) {
    open({
      title: "报表编辑",
      render: () => <ReportForm ref={reportFormRef} reportInfo={res.data} />,
      onConfirm: async () => {
        const result = await unref(reportFormRef)?.handleEditReport();
        if (result) {
          unref(proTableRef)?.getTableList();
          return message.success("更新成功");
        }
        message.error("更新失败");
      },
    });
  }
};

const handleSearchServiceData = (row: Service.ServiceInfo) => {
  window.open(`/serviceQuery/${row.serviceId}/${row.serviceName}`);
};

const statusColor = {
  0: "var(--el-color-info)",
  1: "var(--el-color-primary)",
};

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
  {
    prop: "reportTitle",
    label: "报表名称",
    width: 180,
    link: true,
    linkProps: {
      type: "info",
      onClick: ({ row }) => window.open(`/report/${row.serviceId}`),
    },
  },
  {
    prop: "status",
    label: "接口状态",
    width: 100,
    enum: () => getDictData("sys_normal_status"),
    fieldNames: { value: "dictValue", label: "dictLabel" },
    search: { el: "el-select" },
    render: ({ row }) => <Point color={statusColor[row.status]} text={row._enum.status} />,
  },
  {
    prop: "serviceUrl",
    label: "接口地址",
    render: ({ row }) => (
      <ElTooltip effect="dark" content="点击复制完整地址" placement="top">
        <ElTag type="primary" onClick={() => handleCopyLink(row)} class="cursor-pointer">
          {row.serviceUrl}
        </ElTag>
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
  disableAdd: unref(projectInfo)?.projectRole === "只读成员",
  disableEdit: unref(projectInfo)?.projectRole === "只读成员",
  disableRemove: unref(projectInfo)?.projectRole === "只读成员",
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
