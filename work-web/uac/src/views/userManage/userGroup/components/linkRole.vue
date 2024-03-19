<template>
  <div class="link-user-container">
    <ProTable
      ref="proTableRef"
      :request-api="list"
      :columns="columns"
      :search-col="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
      :detailForm="detailForm"
      :border="false"
    ></ProTable>
  </div>
</template>

<script setup lang="tsx" name="LinkRole">
import { ProTable } from "work";
import { list, addOne, editOne, deleteOne, deleteBatch, type UserGroup } from "@/api/user/userGroup";
import { type DialogForm, type ProTableInstance, type TableColumnProps } from "@work/components";
import { options } from "./linkRoleFormOptions";

const proTableRef = shallowRef<ProTableInstance>();
const columns: TableColumnProps<UserGroup.UserGroupInfo>[] = [
  { type: "selection", fixed: "left", width: 80 },
  { prop: "roleName", label: "角色名", align: "left", search: { el: "el-input" } },
  { prop: "createTime", label: "创建时间", align: "left" },
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

<style lang="scss" scoped></style>
