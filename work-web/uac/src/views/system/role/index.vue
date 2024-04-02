<template>
  <div class="role-container">
    <TreeFilter
      ref="treeFilterRef"
      title="App 清单"
      :requestApi="getAppTreeList"
      @change="handleTreeChange"
      id="appId"
      label="appName"
    >
      <template #default="{ node }">
        <Icon v-if="node.data.icon" :icon="node.data.icon"></Icon>
        <span>{{ node.label }}</span>
      </template>
    </TreeFilter>

    <div class="role-table">
      <ProTable
        ref="proTableRef"
        :request-api="listPage"
        :columns="columns"
        :init-request-param="initRequestParam"
        :search-cols="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
        :detailForm="detailForm"
      ></ProTable>
    </div>
  </div>
</template>

<script setup lang="tsx" name="Role">
import { TreeFilter, ProTable } from "work";
import { getAppTreeList } from "@/api/application/app";
import { listPage, addOne, editOne, deleteOne, deleteBatch, type Role } from "@/api/system/role";
import {
  type DialogForm,
  type ProTableInstance,
  type TableColumnProps,
  type TreeFilterInstance,
} from "@work/components";
import { useFormOptions } from "./useFormOptions";
import { useLayoutStore } from "@/stores";
import { useChange } from "@/hooks/useChange";
import { ElSwitch } from "element-plus";

const treeFilterRef = shallowRef<TreeFilterInstance>();
const proTableRef = shallowRef<ProTableInstance>();

const { statusChange } = useChange(
  "roleName",
  "角色",
  (row, status) => editOne({ id: row.id, roleId: row.roleId, status }),
  () => proTableRef.value?.getTableList()
);

const initRequestParam = reactive({
  appId: "",
});

const columns: TableColumnProps<Role.RoleInfo>[] = [
  { type: "selection", fixed: "left", width: 80 },
  { type: "index", label: "#", width: 80 },
  { prop: "roleCode", label: "角色编码", search: { el: "el-input" } },
  { prop: "roleName", label: "角色名称", search: { el: "el-input" } },
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
  { prop: "orderNum", label: "显示顺序" },
  { prop: "createTime", label: "创建时间" },
  { prop: "operation", label: "操作", width: 160, fixed: "right" },
];

const detailForm: DialogForm = {
  options: useFormOptions(
    computed(() => treeFilterRef.value?.treeData),
    computed(() => initRequestParam.appId)
  ).options,
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

const handleTreeChange = (nodeId: number) => {
  initRequestParam.appId = nodeId + "";
};
</script>

<style lang="scss" scoped>
.role-container {
  display: flex;
  width: 100%;

  .role-table {
    width: calc(100% - 230px);
    height: 100%;
  }
}
</style>
