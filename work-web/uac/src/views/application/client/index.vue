<template>
  <div :class="prefixClass">
    <ProTable
      ref="proTableRef"
      :request-api="listPage"
      :columns="columns"
      :search-cols="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
      :dialogForm="dialogForm"
      :exportFile
    ></ProTable>
  </div>
</template>

<script setup lang="tsx" name="Client">
import { ProTable, downloadByData } from "work";
import { listPage, addOne, editOne, deleteOne, deleteBatch, type Client, exportExcel } from "@/api/application/client";
import { type DialogForm, type ProTableInstance, type TableColumnProps } from "@work/components";
import { elFormProps, schema } from "./formSchema";
import { useLayoutStore } from "@/stores";
import { useChange } from "@/hooks/useChange";
import { ElMessageBox, ElSwitch } from "element-plus";
import { useDesign } from "@work/hooks";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("client");

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

const dialogForm: DialogForm = {
  formProps: { elFormProps, schema },
  id: ["id", "appId"],
  addApi: addOne,
  editApi: editOne,
  removeApi: deleteOne,
  removeBatchApi: deleteBatch,
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "45%",
    height: 200,
    top: "5vh",
    closeOnClickModal: false,
  },
};

const exportFile = (_: Record<string, any>[], searchParam: Record<string, any>) => {
  ElMessageBox.confirm("确认导出吗？", "温馨提示", { type: "warning" }).then(() => {
    exportExcel(searchParam).then(res => {
      downloadByData(res, `client_${new Date().getTime()}.xlsx`);
    });
  });
};
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-client;

.#{$prefix-class} {
  flex: 1;
}
</style>
