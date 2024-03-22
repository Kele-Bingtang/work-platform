<template>
  <div class="user-container">
    <TreeFilter :requestApi="listDeptTreeList" @change="handleTreeChange">
      <template #default="{ node }">
        <Icon v-if="node.data.icon" :icon="node.data.icon"></Icon>
        <span>{{ node.label }}</span>
      </template>
    </TreeFilter>

    <div class="user-table">
      <ProTable
        ref="proTableRef"
        :request-api="list"
        :columns="columns"
        :init-request-param="initRequestParam"
        :search-cols="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
        style="display: flex; flex-direction: column"
        :detailForm="detailForm"
      ></ProTable>
    </div>
  </div>
</template>

<script setup lang="tsx" name="UserInfo">
import { TreeFilter, ProTable, type TableColumnProps } from "work";
import { listDeptTreeList } from "@/api/system/dept";
import { addOne, editOne, deleteOne, deleteBatch, list, type User } from "@/api/user/base";
import { options } from "./formOptions";
import type { DialogForm, ProTableInstance } from "@work/components";
import { useLayoutStore } from "@/stores";
import { useChange } from "@/hooks/useChange";
import { ElSwitch } from "element-plus";

const proTableRef = shallowRef<ProTableInstance>();

const { statusChange } = useChange(
  "username",
  "用户",
  (row, status) => editOne({ id: row.id, userId: row.userId, status }),
  () => proTableRef.value?.getTableList()
);

const columns: TableColumnProps<User.UserInfo>[] = [
  { type: "selection", fixed: "left", width: 80 },
  { type: "index", label: "#", width: 80 },
  { prop: "username", label: "用户名称", width: 170, search: { el: "el-input" } },
  { prop: "nickname", label: "用户昵称", width: 170, search: { el: "el-input" } },
  { prop: "dept.deptName", label: "部门", width: 170, search: { el: "el-input" } },
  { prop: "phone", label: "手机号码", width: 130, search: { el: "el-input" } },
  { prop: "email", label: "邮箱", width: 170, search: { el: "el-input" } },
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
  { prop: "registerTime", width: 160, label: "注册时间" },
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

const initRequestParam = reactive({
  deptId: "",
});

const handleTreeChange = (nodeId: number) => {
  initRequestParam.deptId = nodeId + "";
};
</script>

<style lang="scss" scoped>
.user-container {
  display: flex;
  width: 100%;

  .iconify {
    margin-right: 5px;
    vertical-align: -2px;
  }

  .user-table {
    display: flex;
    width: calc(100% - 230px);
    height: 100%;
  }
}
</style>

<style lang="scss">
.user-container {
  .user-table .el-dialog__body {
    margin-left: 20px;
  }
}
</style>
@/api/user/user
