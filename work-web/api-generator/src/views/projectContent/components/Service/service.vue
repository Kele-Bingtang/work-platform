<template>
  <div :class="prefixClass">
    <ProTable
      :request-api="listServicePage"
      :columns
      :init-request-param
      :dialogForm
      :search-cols="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
    ></ProTable>
  </div>
</template>

<script setup lang="tsx" name="Service">
import { listServicePage, addService, editService, removeService } from "@/api/service";
import { ProTable, useDesign, type DialogForm, type TableColumnProps } from "work";
import { schema, elFormProps } from "./formSchema";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("service");

const props = defineProps<{ categoryId: string }>();

const initRequestParam = computed(() => ({ categoryId: props.categoryId }));

const columns: TableColumnProps[] = [
  { type: "index", label: "#", width: 80 },
  { prop: "serviceName", label: "接口名称", width: 180, search: { el: "el-input" } },
  { prop: "reportTitle", label: "报表名称", width: 180 },
  { prop: "status", label: "接口状态", width: 100 },
  {
    prop: "serviceUrl",
    label: "接口地址",
    render: ({ row }) => (
      <ElTooltip effect="dark" content="复制接口相对地址" placement="top">
        <ElTag type="info">
          {row.serviceUrl}
          <i class="DocumentCopy" />
        </ElTag>
      </ElTooltip>
    ),
  },
  { prop: "description", label: "接口描述" },
  { prop: "createTime", label: "创建时间" },
  { prop: "operation", label: "操作", width: 280, fixed: "right" },
];

const dialogForm: DialogForm = {
  formProps: {
    elFormProps: elFormProps,
    schema: schema,
  },
  id: ["id", "serviceId"],
  addApi: addService,
  editApi: editService,
  removeApi: removeService,
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "45%",
    height: 300,
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
