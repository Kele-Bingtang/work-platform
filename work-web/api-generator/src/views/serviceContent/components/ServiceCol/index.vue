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
      <el-button v-waves type="info" plain :icon="Link" @click="handleToReport">跳转至报表</el-button>
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
  listByServiceId,
} from "@/api/serviceCol";
import { ServiceKey } from "@/config/symbol";
import { colTypeComponentForm, queryFilter } from "@/config/constant";
import {
  ProTable,
  CodeMirror,
  type DialogForm,
  type TableColumnProps,
  type FormSchemaProps,
  message,
  type ProTableInstance,
  useDialog,
} from "work";
import { Pointer, Delete, Files, Link, Plus, Minus } from "@element-plus/icons-vue";
import BatchOperate from "./BatchOperate.vue";
import { oneDark } from "@codemirror/theme-one-dark";
import { sql } from "@codemirror/lang-sql";
import { listSelectInProject } from "@/api/service";

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

const handleToReport = () => {
  window.open(`/report/${unref(serviceInfo)?.serviceId}`);
};

const customDropDownList = ref<{ value: string; label: string }[]>([reactive({ value: "", label: "" })]);

const addCustomSelect = () => {
  unref(customDropDownList).push(reactive({ value: "", label: "" }));
};
const removeCustomSelect = (index: number) => {
  unref(customDropDownList).splice(index, 1);
};

const dropdownServiceColList = ref<ServiceCol.ServiceColInfo[]>([]);
const serviceSelectChange = async (value: string) => {
  if (!value) dropdownServiceColList.value = [];
  const res = await listByServiceId(value);
  if (res.code === 200) dropdownServiceColList.value = res.data;
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

const dropdownService = reactive({ service: "", value: "", label: "" });
const dropdownSql = ref("");

const schema: FormSchemaProps[] = [
  { prop: "base", label: "基本配置", el: "ElDivider" },
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
  { prop: "allowInsert", label: "新增", el: "el-select", enum: commonEnum, defaultValue: 1 },
  { prop: "allowUpdate", label: "更新", el: "el-select", enum: commonEnum, defaultValue: 1 },
  { prop: "allowRequest", label: "请求", el: "el-select", enum: commonEnum, defaultValue: 1 },
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
  { prop: "report", label: "报表配置", el: "ElDivider" },
  {
    prop: "colAlign",
    label: "列对齐",
    el: "el-select",
    enum: [
      { label: "左对齐", value: 1 },
      { label: "居中", value: 2 },
      { label: "右对齐", value: 3 },
    ],
    defaultValue: 1,
  },
  { prop: "allowShowInReport", label: "是否在报表显示", el: "el-select", enum: commonEnum, defaultValue: 1 },
  { prop: "allowShowInDetail", label: "是否在弹框显示", el: "el-select", enum: commonEnum, defaultValue: 1 },
  { prop: "displaySeq", label: "返回出现顺序", el: "el-input-number", defaultValue: 99 },
  { prop: "reportColWidth", label: "报表列宽度", el: "el-input-number", defaultValue: -1 },
  { prop: "detailColWidth", label: "弹框组件宽度", el: "el-input-number", defaultValue: -1 },
  {
    prop: "dropDownType",
    label: "下拉模板",
    el: "el-radio-group",
    enum: [
      { value: "local", label: "本地下拉配置" },
      { value: "service", label: "从其他服务获取" },
      { value: "sql", label: "从 SQL 里获取" },
    ],
    defaultValue: "local",
    col: { span: 24 },
  },
  {
    prop: "dropdownList",
    label: "",
    col: { span: 24 },
    render: () => (
      <div class="relative">
        <el-button
          type="primary"
          icon={Plus}
          circle
          onClick={() => addCustomSelect()}
          class="mr-11 absolute left-[-70px]"
        />
        {unref(customDropDownList).map((item, index) => (
          <div class="flex mb-2">
            <el-input v-model={item.value} placeholder="请输入存储内容（value）" class="mr-4" />
            <el-input v-model={item.label} placeholder="请输入展示内容（label）" class="mr-4" />
            <el-button type="danger" icon={Minus} circle onClick={() => removeCustomSelect(index)} />
          </div>
        ))}
      </div>
    ),
    hidden: model => model.dropDownType !== "local",
  },
  {
    prop: "dropdownService",
    label: "",
    el: "el-select",
    enum: () => listSelectInProject(unref(serviceInfo)?.projectId || "", unref(serviceInfo)?.serviceId || ""),
    col: { span: 24 },
    hidden: model => model.dropDownType !== "service",
    render: ({ enumData }) => {
      return (
        <div>
          <el-select
            v-model={dropdownService.service}
            placeholder="请选择接口"
            clearable
            onChange={serviceSelectChange}
          >
            {enumData.map(item => (
              <el-option label={item.serviceName} value={item.serviceId} />
            ))}
          </el-select>
          <div class="flex mt-2">
            <el-select v-model={dropdownService.value} placeholder="存储内容字段：Value" clearable>
              {unref(dropdownServiceColList).map(item => (
                <el-option label={item.jsonCol} value={item.colId} />
              ))}
            </el-select>
            <el-select v-model={dropdownService.label} placeholder="展示内容内容字段：Label" clearable>
              {unref(dropdownServiceColList).map(item => (
                <el-option label={item.jsonCol} value={item.colId} />
              ))}
            </el-select>
          </div>
        </div>
      );
    },
  },
  {
    prop: "dropdownSql",
    label: "",
    col: { span: 24 },
    render: () => {
      return <CodeMirror v-model={dropdownSql.value} localTheme={oneDark} lang={sql()} height={300} fullScreen />;
    },
    hidden: model => model.dropDownType !== "sql",
  },
];

const elFormProps = {
  labelWidth: 110,
  rules: {
    tableCol: [{ required: true, message: "请输入字段名称", trigger: "blur" }],
    jsonCol: [{ required: true, message: "请输入请求名称", trigger: "blur" }],
    reportCol: [{ required: true, message: "请输入报表名称", trigger: "blur" }],
  },
};

const getDropdownConfig = (data: any) => {
  let dropdownConfig: { type: string; value: any } | {} = {};
  if (data.dropDownType === "local") {
    if (data.dropdownList.length > 0) dropdownConfig = { type: "local", value: unref(customDropDownList) };
  }
  if (data.dropDownType === "service") {
    if (dropdownService.service) dropdownConfig = { type: "service", value: { ...dropdownService } };
  }
  if (data.dropDownType === "sql") {
    if (unref(dropdownSql)) dropdownConfig = { type: "sql", value: unref(dropdownSql) };
  }

  // 重置数据为初始值
  customDropDownList.value = [reactive({ value: "", label: "" })];
  dropdownService.service = "";
  dropdownService.value = "";
  dropdownService.label = "";
  dropdownSql.value = "";

  return dropdownConfig;
};

const dialogForm: DialogForm = {
  formProps: { elFormProps, schema: schema },
  id: ["id", "colId"],
  addApi: data => {
    const dropdownConfig = getDropdownConfig(data);

    return addServiceCol({
      ...data,
      ...initRequestParam,
      categoryId: unref(serviceInfo)?.categoryId,
      dropdownConfig,
      teamId: unref(serviceInfo)?.teamId,
    });
  },
  editApi: data => {
    const dropdownConfig = getDropdownConfig(data);

    return editServiceCol({
      ...data,
      ...initRequestParam,
      dropdownConfig,
      categoryId: unref(serviceInfo)?.categoryId,
      teamId: unref(serviceInfo)?.teamId,
    });
  },
  removeApi: removeServiceCol,
  clickEdit: model => {
    const dropdownConfig = model.dropdownConfig;
    if (!dropdownConfig) return;

    model.dropDownType = dropdownConfig?.type;

    if (dropdownConfig?.type === "local") customDropDownList.value = dropdownConfig.value;
    else if (dropdownConfig?.type === "service") {
      serviceSelectChange(dropdownConfig.value.service);
      dropdownService.service = dropdownConfig.value.service;
      dropdownService.value = dropdownConfig.value.value;
      dropdownService.label = dropdownConfig.value.label;
    } else if (dropdownConfig?.type === "sql") dropdownSql.value = dropdownConfig.value;
  },
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "50%",
    height: 700,
    top: "2vh",
    closeOnClickModal: false,
  },
};
</script>

<style lang="scss" scoped></style>
