<template>
  <div class="tenant-container">
    <ProTable
      ref="proTableRef"
      :request-api="list"
      :columns="columns"
      :search-cols="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
      class="pro-table"
      :detailForm="detailForm"
      :border="false"
    ></ProTable>
  </div>
</template>

<script setup lang="tsx" name="Tenant">
import { ProTable } from "work";
import { list, addOne, editOne, deleteOne, deleteBatch, type Tenant } from "@/api/system/tenant";
import { type DialogForm, type ProTableInstance, type TableColumnProps } from "@work/components";
import { options } from "./formOptions";
import { useLayoutStore } from "@/stores/layout";
import { useChange } from "@/hooks/useChange";
import { ElSwitch } from "element-plus";

const proTableRef = shallowRef<ProTableInstance>();

const { statusChange } = useChange(
  "tenantName",
  "租户",
  (row, status) => editOne({ id: row.id, tenantId: row.tenantId, status }),
  () => proTableRef.value?.getTableList()
);

const columns: TableColumnProps<Tenant.TenantInfo>[] = [
  { type: "selection", fixed: "left", width: 80 },
  { prop: "tenantId", label: "租户编号", search: { el: "el-input" } },
  { prop: "tenantName", label: "企业名称", search: { el: "el-input" } },
  { prop: "icon", label: "企业图标" },
  { prop: "contactUserName", label: "联系人" },
  { prop: "contactPhone", label: "联系电话" },
  { prop: "founder", label: "企业创始人" },
  { prop: "licenseNumber", label: "社会信用代码" },
  {
    prop: "status",
    label: "状态",
    fieldNames: { value: "dictValue", label: "dictLabel" },
    enum: () => useLayoutStore().getDictData("sys_normal_status"),
    search: { el: "el-select" },
    render: ({ row }) => {
      return (
        <>
          {row.status !== undefined && (
            <ElSwitch
              vModel={row.status}
              activeValue={1}
              inactiveValue={0}
              activeText="启用"
              inactiveText="停用"
              inlinePrompt
              onChange={(value: number) => statusChange(value, row)}
            />
          )}
        </>
      );
    },
  },
  { prop: "createTime", label: "创建时间" },
  { prop: "operation", label: "操作", width: 160, fixed: "right" },
];

const detailForm: DialogForm = {
  options: options,
  addApi: addOne,
  editApi: editOne,
  deleteApi: deleteOne,
  deleteBatchApi: deleteBatch,
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "45%",
    top: "5vh",
    closeOnClickModal: false,
  },
};
</script>

<style lang="scss" scoped>
.tenant-container {
  width: 100%;
}
</style>
