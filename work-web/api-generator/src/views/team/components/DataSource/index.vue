<template>
  <div :class="prefixClass">
    <ProTable
      :request-api="listDataSourcePage"
      :initRequestParam
      :columns
      :search-cols="{ xs: 1, sm: 1, md: 2, lg: 5, xl: 5 }"
      :dialogForm
    >
      <template #operationExtra="{ row }">
        <el-button link type="primary" size="small" :icon="Loading" v-throttle="() => handleConnect(row)">
          测试
        </el-button>
      </template>
      <template #footerExtraPre="model">
        <el-button type="primary" :icon="Loading" v-throttle="() => handleConnect(model)">测试连接</el-button>
      </template>
    </ProTable>
  </div>
</template>

<script setup lang="tsx" name="DataSource">
import {
  listDataSourcePage,
  addDataSource,
  editDataSource,
  removeDataSource,
  type DataSource,
  testConnect,
} from "@/api/dataSource";
import type { DialogFormProps } from "@work/components/proTable/src/components/DialogForm.vue";
import { useDesign } from "@work/hooks";
import { ProTable, isArray, message, type FormSchemaProps, type TableColumnProps } from "work";
import { useDictStore } from "@/stores";
import { Loading } from "@element-plus/icons-vue";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("dataSource");

const route = useRoute();
const { getDictData } = useDictStore();

const initRequestParam = { teamId: route.meta.params?.teamId };

const handleConnect = async (row: DataSource.DataSourceInfo & { dataSourceTypeDriveClass: string }) => {
  if (!row.dataSourceTypeDriveClass || !row.jdbcUrl) return message.warning("请先填写数据源连接信息");
  const model = { ...row };
  parseDataSourceTypeDriveClass(model);
  const res = await testConnect(model);
  if (res.code === 200) message.success("测试连接成功。2s 后可以再次测试连接，请勿一直重复点击！");
};

const columns: TableColumnProps<DataSource.DataSourceInfo>[] = [
  { type: "index", label: "#", width: 50 },
  { prop: "dataSourceName", label: "数据源名称", width: 200, search: { el: "el-input" } },
  {
    prop: "dataSourceType",
    label: "数据源类型",
    width: 120,
    search: { el: "el-select" },
    enum: async () => getDictData("data_source_type", true),
    fieldNames: { value: "label", label: "label" },
  },
  { prop: "jdbcUrl", label: "链接地址", align: "left" },
  { prop: "driverClassName", label: "驱动类名", width: 250, align: "left" },
  { prop: "username", label: "账号", width: 160 },
  { prop: "createTime", label: "创建时间", width: 160 },
  { prop: "operation", label: "设置", width: 190 },
];

const schema: FormSchemaProps[] = [
  { prop: "dataSourceName", label: "数据源名称", el: "el-input" },
  {
    prop: "dataSourceTypeDriveClass",
    label: "数据源类型",
    el: "el-cascader",
    enum: () => getDictData("data_source_type", true),
    fieldNames: { value: "label", label: "label" },
  },
  {
    prop: "jdbcUrl",
    label: "数据源 URL",
    el: "el-input",
    props: { type: "textarea", placeholder: "请输入 数据源 URL" },
  },
  { prop: "username", label: "数据源账号", el: "el-input" },
  { prop: "password", label: "数据源密码", el: "el-input" },
  {
    prop: "description",
    label: "数据源描述",
    el: "el-input",
    props: { type: "textarea", placeholder: "请输入 团队描述" },
  },
];

const parseDataSourceTypeDriveClass = (model: Record<string, any>) => {
  const { dataSourceTypeDriveClass } = model;
  if (isArray(dataSourceTypeDriveClass)) {
    model.dataSourceType = dataSourceTypeDriveClass[0];
    model.driverClassName = dataSourceTypeDriveClass[1];
    delete model.dataSourceTypeDriveClass;
  }
};

const elFormProps = {
  labelWidth: 100,
  rules: {
    dataSourceName: [{ required: true, message: "请输入数据源名称", trigger: "blur" }],
    dataSourceType: [{ required: true, message: "请输入数据源类型", trigger: "blur" }],
    jdbcUrl: [{ required: true, message: "请输入数据源地址", trigger: "blur" }],
  },
};

const dialogForm: DialogFormProps = {
  formProps: { elFormProps, schema: schema, colRow: true },
  id: ["id", "dataSourceId"],
  addApi: data => addDataSource({ ...data, ...initRequestParam }),
  editApi: data => editDataSource({ ...data, ...initRequestParam }),
  removeApi: removeDataSource,
  beforeAdd: model => {
    // dataSourceTypeDriveClass 是级联选择器的值，绑定了后端需要的 dataSourceType 和 driverClassName
    parseDataSourceTypeDriveClass(model);
  },
  beforeEdit: model => {
    // dataSourceTypeDriveClass 是级联选择器的值，绑定了后端需要的 dataSourceType 和 driverClassName
    parseDataSourceTypeDriveClass(model);
  },
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "45%",
    height: 400,
    top: "5vh",
    closeOnClickModal: false,
  },
};
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-members;

.#{$prefix-class} {
  flex: 1;
}
</style>
