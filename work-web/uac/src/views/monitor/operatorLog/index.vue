<template>
  <div class="opera-log-container">
    <ProTable
      ref="proTableRef"
      :request-api="listPage"
      :init-request-param="initRequestParam"
      :columns="columns"
      :search-cols="{ xs: 1, sm: 1, md: 3, lg: 5, xl: 5 }"
      :border="false"
      @sort-change="sortChange"
      :default-sort="{ prop: 'loginTime', order: 'descending' }"
    >
      <template #tableHeader="scope">
        <el-button type="danger" :icon="Delete" plain @click="handleDeleteBatch(scope)" :disabled="!scope?.isSelected">
          批量删除
        </el-button>
        <el-button type="danger" :icon="Delete" plain @click="handleClean()">清除</el-button>
      </template>
    </ProTable>
  </div>
</template>

<script setup lang="tsx" name="LoginLog">
import { ProTable } from "work";
import { listPage, removeBatch, cleanAllLog, type OperaLog } from "@/api/monitor/operaLog";
import type { ProTableInstance, TableColumnProps } from "@work/components";
import { useLayoutStore } from "@/stores";
import { Delete } from "@element-plus/icons-vue";
import { ElMessageBox, ElMessage } from "element-plus";

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
  { prop: "title", label: "模块标题", search: { el: "el-input" } },
  {
    prop: "businessType",
    label: "操作类型",
    width: 100,
    tag: true,
    search: { el: "el-select" },
    fieldNames: { value: "dictValue", label: "dictLabel" },
    enum: () => useLayoutStore().getDictData("sys_opera_business_type"),
  },
  { prop: "operaName", label: "操作人员", search: { el: "el-input" } },
  { prop: "deptName", label: "操作人部门" },
  { prop: "operaIp", label: "操作 IP 地址", search: { el: "el-input" } },
  { prop: "operaLocation", label: "操作地点", search: { el: "el-input" } },
  {
    prop: "status",
    label: "状态",
    width: 100,
    tag: true,
    enum: [
      { value: "0", label: "失败", tagEl: "el-check-tag", tagType: "danger" },
      { value: "1", label: "成功", tagEl: "el-check-tag", tagType: "success" },
    ],
    search: { el: "el-select" },
  },
  {
    prop: "operaTime",
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
</script>

<style lang="scss" scoped>
.opera-log-container {
  width: 100%;
}
</style>
