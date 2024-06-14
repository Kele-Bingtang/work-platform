<template>
  <div :class="prefixClass">
    <ProTable
      ref="proTableRef"
      :request-api="listPage"
      :columns="columns"
      :search-cols="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
      class="pro-table"
      :dialogForm="dialogForm"
      :border="false"
      :exportFile
      :disabled-button="!hasAuth('system:tenant:export') ? ['export'] : []"
    ></ProTable>
  </div>
</template>

<script setup lang="tsx" name="Tenant">
import { ProTable, Icon, downloadByData } from "work";
import { listPage, addOne, editOne, deleteOne, deleteBatch, type Tenant, exportExcel } from "@/api/system/tenant";
import { type DialogForm, type ProTableInstance, type TableColumnProps } from "@work/components";
import { elFormProps, schema } from "./formSchema";
import { useLayoutStore } from "@/stores";
import { useChange, usePermission } from "@/hooks";
import { ElMessageBox, ElSwitch } from "element-plus";
import { useDesign } from "@work/hooks";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("tenant");

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
  { prop: "icon", label: "企业图标", width: 100, render: ({ row }) => <Icon icon={row.icon}></Icon> },
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

const { hasAuth } = usePermission();

const dialogForm: DialogForm = {
  formProps: { elFormProps, schema },
  id: ["id", "tenantId"],
  addApi: addOne,
  editApi: editOne,
  removeApi: deleteOne,
  removeBatchApi: deleteBatch,
  disableAdd: !hasAuth("system:tenant:add"),
  disableEdit: !hasAuth("system:tenant:edit"),
  disableRemove: !hasAuth("system:tenant:remove"),
  disableRemoveBatch: !hasAuth("system:tenant:remove"),
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "45%",
    height: 450,
    top: "5vh",
    closeOnClickModal: false,
  },
};

const exportFile = (_: Record<string, any>[], searchParam: Record<string, any>) => {
  ElMessageBox.confirm("确认导出吗？", "温馨提示", { type: "warning" }).then(() => {
    exportExcel(searchParam).then(res => {
      downloadByData(res, `tenant_${new Date().getTime()}.xlsx`);
    });
  });
};
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-tenant;

.#{$prefix-class} {
  flex: 1;
}
</style>
