<template>
  <ProTable
    v-if="reportInfo"
    :request-api="pageByServiceId"
    :init-request-param
    :columns
    :dialogForm
    :search-cols="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
    :disabled-button="reportInfo?.allowExport == 0 ? ['export'] : []"
    :page-config="{ pageSize: reportInfo?.pageSize || 20 }"
    :exportFile
    class="p-3 bg-[#f0f2f5]"
  ></ProTable>
</template>

<script setup lang="ts" name="Report">
import { ProTable, downloadByData, type DialogForm, type DialogFormSchemaProps } from "work";
import { listReportConfig, type Report } from "@/api/report";
import { pageByServiceId, operateByServiceId, exportExcel } from "@/api/api";
import { ElMessageBox } from "element-plus";

const route = useRoute();
const serviceId = computed(() => (route.params.serviceId as string) || "");
const reportName = computed(() => (route.params.reportName as string) || "");

const reportInfo = ref<Report.ReportInfo>();
const columns = ref<Record<string, any>[]>([]);
const schema = ref<Record<string, any>[]>([]);

const initRequestParam = reactive({
  serviceId: unref(serviceId),
});

const exportFile = (_: Record<string, any>[], searchParam: Record<string, any>) => {
  ElMessageBox.confirm("确认导出吗？", "温馨提示", { type: "warning" }).then(() => {
    exportExcel(unref(serviceId), searchParam).then(res => {
      downloadByData(res, `${unref(reportName)}_${new Date().getTime()}.xlsx`);
    });
  });
};

const dialogForm: DialogForm = reactive({
  formProps: {
    schema: computed(() => unref(schema) as DialogFormSchemaProps[]),
    dynamicModel: false,
  },
  addApi: data => operateByServiceId({ ...data, operateType: "add", serviceId: unref(serviceId) }),
  editApi: data => operateByServiceId({ ...data, operateType: "edit", serviceId: unref(serviceId) }),
  removeApi: data => operateByServiceId({ ...data, operateType: "remove", serviceId: unref(serviceId) }),
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: unref(reportInfo)?.dialogWidth,
    height: "auto",
    maxHeight: 500,
    top: "5vh",
    closeOnClickModal: false,
  },
});

onMounted(async () => {
  const res = await listReportConfig(unref(serviceId));

  if (res.code === 200) {
    reportInfo.value = res.data.report;
    columns.value = res.data.proTableColumnsList.length
      ? [...res.data.proTableColumnsList, { prop: "operation", label: "操作", width: 160, fixed: "right" }]
      : [];
    schema.value = res.data.proFormSchemaList;
    dialogForm.disableAdd = unref(reportInfo)?.allowAdd === 0;
    dialogForm.disableEdit = unref(reportInfo)?.allowEdit === 0;
    dialogForm.disableRemove = unref(reportInfo)?.allowRemove === 0;
  }
});
</script>

<style lang="scss" scoped></style>
