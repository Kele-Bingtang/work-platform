<template>
  <div class="client-container">
    <ProTable
      ref="proTableRef"
      :request-api="list"
      :columns="columns"
      :search-col="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
      style="height: 90%"
      :detailForm="detailForm"
    ></ProTable>
  </div>
</template>

<script setup lang="tsx" name="Client">
import { ProTable } from "work";
import { list, addOne, editOne, deleteOne, deleteBatch, type Client } from "@/api/application/client";
import { type DialogForm, type ProTableInstance, type TableColumnProps } from "@work/components";
import { options } from "./formOptions";
import { useLayoutStore } from "@/stores/layout";
import { useChange } from "@/hooks/useChange";
import { ElSwitch } from "element-plus";

const proTableRef = shallowRef<ProTableInstance>();

const { statusChange } = useChange(
  "clientName",
  "客户端",
  (row, status) => editOne({ id: row.id, clientId: row.clientId, status }),
  () => proTableRef.value?.getTableList()
);

const columns: TableColumnProps<Client.ClientInfo>[] = [
  { type: "selection", fixed: "left", width: 40 },
  { prop: "clientId", label: "客户端 ID", width: 270, search: { el: "el-input" } },
  { prop: "clientKey", label: "客户端 Key", width: 160, search: { el: "el-input" } },
  { prop: "clientSecret", label: "客户端秘钥", width: 270, search: { el: "el-input" } },
  { prop: "clientName", label: "客户端名称", width: 160, search: { el: "el-input" } },
  {
    prop: "grantTypeList",
    label: "授权类型",
    align: "left",
    minWidth: 120,
    enum: () => useLayoutStore().getDictData("sys_grant_type"),
    tag: true,
    fieldNames: { value: "dictValue", label: "dictLabel" },
    search: {
      el: "el-select",
      props: { multiple: true, collapseTags: true, collapseTagsTooltip: true, maxCollapseTags: 2 },
    },
  },
  { prop: "activeTimeout", label: "Token 活跃超时时间", width: 170 },
  { prop: "timeout", label: "Token 固定超时时间", width: 170 },
  {
    prop: "status",
    label: "状态",
    width: 80,
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
  { prop: "createTime", label: "创建时间", width: 160 },
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
.client-container {
  width: 100%;
  height: 100%;
  padding: 10px;
}
</style>
