<template>
  <ProTable
    ref="proTableRef"
    :request-api="listServiceColPage"
    :init-request-param
    highlight-current-row
    :columns
    :dialogForm
    :search-cols="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
    @row-click="handleRowClick"
  >
    <template #tableHeaderExtra>
      <el-button
        v-waves
        type="primary"
        plain
        :icon="Pointer"
        :disabled="serviceInfo?.projectRole === '只读成员'"
        @click="handleRegenerate"
      >
        生成新增字段
      </el-button>
      <el-button
        v-waves
        type="danger"
        plain
        :icon="Delete"
        :disabled="serviceInfo?.projectRole === '只读成员'"
        @click="handleDeleteInvalid"
      >
        删除失效字段
      </el-button>
      <el-button
        v-waves
        type="success"
        plain
        :icon="Files"
        :disabled="serviceInfo?.projectRole === '只读成员'"
        @click="handleBatchOperate"
      >
        批量操作
      </el-button>
    </template>
  </ProTable>
</template>

<script setup lang="tsx" name="ServiceCol">
import {
  listServiceColPage,
  addServiceCol,
  editServiceCol,
  removeServiceCol,
  type ServiceCol,
  reGenCol,
  removeInvalidCol,
  editBatch,
} from "@/api/serviceCol";
import { ServiceKey } from "@/config/symbol";
import { colTypeComponentForm, queryFilter } from "@/config/constant";
import {
  ProTable,
  type DialogForm,
  type TableColumnProps,
  type FormSchemaProps,
  message,
  type ProTableInstance,
  useDialog,
} from "work";
import { Pointer, Delete, Files } from "@element-plus/icons-vue";
import BatchOperate from "./batchOperate.vue";

const serviceInfo = inject(ServiceKey);
const { open } = useDialog();

const initRequestParam = reactive({
  serviceId: unref(serviceInfo)?.serviceId,
  projectId: unref(serviceInfo)?.projectId,
});

const proTableRef = shallowRef<ProTableInstance>();

const model = ref<Partial<ServiceCol.ServiceColInfo>>({});
const clickRow = ref<Partial<ServiceCol.ServiceColInfo>>({}); // 点击某一行后缓存起来

const colTypeOptions = computed(() =>
  Object.keys(colTypeComponentForm).map((key, index) => ({ label: key, value: index }))
);
const queryFilterOptions = computed(() => queryFilter.map((item, index) => ({ label: item, value: index })));

const handleRowClick = (row: ServiceCol.ServiceColInfo) => {
  clickRow.value = row;
};

const handleRegenerate = async () => {
  const res = await reGenCol(unref(serviceInfo)?.serviceId || "");

  if (res.code === 200) {
    message.success(res.message);
    unref(proTableRef)?.getTableList();
  }
};

const handleDeleteInvalid = async () => {
  const res = await removeInvalidCol(unref(serviceInfo)?.serviceId || "");

  if (res.code === 200) {
    message.success(res.message);
    unref(proTableRef)?.getTableList();
  }
};

const batchOperate = shallowRef<typeof BatchOperate>();

const handleBatchOperate = () => {
  open({
    title: "批量操作",
    height: 300,
    render: () => <BatchOperate ref={batchOperate} data={unref(proTableRef)?.tableData} />,
    onConfirm: async () => {
      const data = await unref(batchOperate)?.getData();

      if (data) {
        const res = await editBatch(data);
        if (res.code === 200) {
          unref(proTableRef)?.getTableList();
          return message.success("批量更新成功");
        }
      }
    },
  });
};

const commonEnum = [
  { label: "不允许", value: 0 },
  { label: "允许", value: 1 },
];

const columns: TableColumnProps[] = [
  { type: "index", label: "#", width: 60 },
  { prop: "tableCol", label: "字段名", minWidth: 120 },
  { prop: "jsonCol", label: "请求名", minWidth: 120 },
  { prop: "reportCol", label: "报表名", minWidth: 120 },
  {
    prop: "isWhereKey",
    label: "主键",
    enum: [
      { label: "不作为", value: 0 },
      { label: "作为", value: 1 },
    ],
  },
  { prop: "allowInsert", label: "新增", enum: commonEnum, width: 100 },
  { prop: "allowUpdate", label: "更新", enum: commonEnum, width: 100 },
  { prop: "allowRequest", label: "请求", enum: commonEnum, width: 100 },
  { prop: "orderBy", label: "排序", width: 100 },
  { prop: "displaySeq", label: "返回顺序", width: 100 },
  { prop: "queryFilter", label: "筛选条件", enum: unref(queryFilterOptions), width: 100 },
  { prop: "defaultValue", label: "默认值", width: 110 },
  { prop: "colType", label: "类型", isFilterEnum: false },
  { prop: "colLength", label: "长度", width: 100 },
  { prop: "operation", label: "操作", width: 140, fixed: "right" },
];

const schema: FormSchemaProps[] = [
  { prop: "tableCol", label: "字段名称", el: "el-input" },
  { prop: "jsonCol", label: "请求名称", el: "el-input" },
  { prop: "reportCol", label: "报表名称", el: "el-input" },
  {
    prop: "isWhereKey",
    label: "where 条件",
    el: "el-select",
    defaultValue: 0,
    enum: [
      { label: "不作为", value: 0 },
      { label: "作为", value: 1 },
    ],
  },
  { prop: "allowInsert", label: "新增", el: "el-select", defaultValue: 1, enum: commonEnum },
  { prop: "allowUpdate", label: "更新", el: "el-select", defaultValue: 1, enum: commonEnum },
  { prop: "allowRequest", label: "请求", el: "el-select", defaultValue: 1, enum: commonEnum },
  { prop: "queryFilter", label: "筛选条件", el: "el-select", defaultValue: 0, enum: unref(queryFilterOptions) },
  { prop: "orderBy", label: "排序顺序", el: "el-input-number", defaultValue: 99 },
  {
    prop: "defaultValue",
    label: "默认值",
    el: "el-input",
    hidden: () => unref(model).isWhereKey === 0,
    props: {
      placeholder: "新增时的默认值",
    },
  },
  { prop: "colType", label: "字段类型", el: "el-select", defaultValue: "String", enum: unref(colTypeOptions) },
  { prop: "displaySeq", label: "返回出现顺序", el: "el-input-number", defaultValue: 99 },
];

const elFormProps = {
  labelWidth: 100,
  rules: {
    tableCol: [{ required: true, message: "请输入字段名称", trigger: "blur" }],
    jsonCol: [{ required: true, message: "请输入请求名称", trigger: "blur" }],
    reportCol: [{ required: true, message: "请输入报表名称", trigger: "blur" }],
  },
};

const dialogForm: DialogForm = {
  formProps: { elFormProps, schema: schema },
  id: ["id", "colId"],
  addApi: data =>
    addServiceCol({
      ...data,
      ...initRequestParam,
      categoryId: unref(serviceInfo)?.categoryId,
      teamId: unref(serviceInfo)?.teamId,
    }),
  editApi: data =>
    editServiceCol({
      ...data,
      ...initRequestParam,
      categoryId: unref(serviceInfo)?.categoryId,
      teamId: unref(serviceInfo)?.teamId,
    }),
  removeApi: removeServiceCol,
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "45%",
    height: 400,
    top: "5vh",
    closeOnClickModal: false,
  },
};
</script>

<style lang="scss" scoped></style>