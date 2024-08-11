<template>
  <div :class="prefixClass">
    <ProTable
      ref="proTableRef"
      :request-api="listPage"
      :init-request-param="initRequestParam"
      :columns="columns"
      :search-cols="{ xs: 1, sm: 1, md: 3, lg: 5, xl: 5 }"
      :border="false"
      @sort-change="sortChange"
      :default-sort="{ prop: 'loginTime', order: 'descending' }"
      :exportFile
    >
      <template #tableHeader="scope">
        <el-button type="danger" :icon="Delete" plain @click="handleDeleteBatch(scope)">批量删除</el-button>
        <el-button type="danger" :icon="Delete" plain @click="handleClean()">清除</el-button>
      </template>
    </ProTable>
  </div>
</template>

<script setup lang="tsx" name="OperatorLog">
import { ProTable, downloadByData } from "work";
import { listPage, removeBatch, cleanAllLog, type OperaLog, exportExcel } from "@/api/operaLog";
import type { ProTableInstance, TableColumnProps } from "@work/components";
import { Delete } from "@element-plus/icons-vue";
import { ElMessageBox, ElMessage } from "element-plus";
import { useDesign } from "@work/hooks";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("operator-log");

const proTableRef = shallowRef<ProTableInstance>();

const initRequestParam = reactive({
  orderRuleList: [{}],
});

const handleDeleteBatch = async (scope: any) => {
  ElMessageBox.confirm(`删除所选信息?`, "温馨提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
    draggable: true,
  }).then(async () => {
    removeBatch(scope.selectedListIds)
      .then(res => {
        if (res.status === "success") {
          ElMessage.success("批量删除成功");
          proTableRef.value?.clearSelection();
          proTableRef.value?.getTableList();
        } else {
          ElMessage.success("批量删除失败");
        }
      })
      .catch(err => {
        ElMessage.success("批量删除失败");
        console.log(err);
      });
  });
};

const handleClean = () => {
  ElMessageBox.confirm(`清除全部信息?`, "温馨提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
    draggable: true,
  }).then(async () => {
    cleanAllLog()
      .then(res => {
        if (res.status === "success") {
          ElMessage.success("清除日志成功");
          proTableRef.value?.clearSelection();
          proTableRef.value?.getTableList();
        } else {
          ElMessage.success("清除日志失败");
        }
      })
      .catch(err => {
        ElMessage.success("清除日志失败");
        console.log(err);
      });
  });
};

const sortChange = (data: { column: any; prop: string; order: any }) => {
  initRequestParam.orderRuleList = [{ column: data.prop, type: data.order === "descending" ? "desc" : "asc" }];
};

const columns: TableColumnProps<OperaLog.OperaLogInfo>[] = [
  { type: "selection", fixed: "left", width: 80 },
  { prop: "operaId", label: "操作编号", width: 170 },
  { prop: "appId", label: "App ID", search: { el: "el-input" } },
  { prop: "fileKey", label: "附件标识", search: { el: "el-input" } },
  {
    prop: "operaType",
    label: "操作类型",
    width: 100,
    search: { el: "el-select" },
    enum: [
      { value: 1, label: "上传" },
      { value: 2, label: "下载" },
    ],
  },
  { prop: "operaUser", label: "操作人员", search: { el: "el-input" } },
  { prop: "operaIp", label: "操作 IP 地址", search: { el: "el-input" } },
  { prop: "operaLocation", label: "操作地点", search: { el: "el-input" } },
  {
    prop: "status",
    label: "状态",
    width: 100,
    tag: true,
    enum: [
      { value: 0, label: "失败", tagEl: "el-check-tag", tagType: "danger" },
      { value: 1, label: "成功", tagEl: "el-check-tag", tagType: "success" },
    ],
    search: { el: "el-select" },
  },
  {
    prop: "createTime",
    label: "操作时间",
    sortable: "custom",
    search: {
      el: "el-date-picker",
      order: 3,
      props: { type: "daterange" },
    },
  },
  { prop: "costTime", label: "消耗时间/毫秒", search: { el: "el-input" } },
];

const exportFile = (_: Record<string, any>[], searchParam: Record<string, any>) => {
  ElMessageBox.confirm("确认导出吗？", "温馨提示", { type: "warning" }).then(() => {
    exportExcel(searchParam).then(res => {
      downloadByData(res, `operatorLog_${new Date().getTime()}.xlsx`);
    });
  });
};
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-operator-login;

.#{$prefix-class} {
  flex: 1;
}
</style>
