<template>
  <div class="user-container">
    <TreeFilter :requestApi="getDeptTreeList" @change="handleTreeChange">
      <template #default="{ node }">
        <Icon v-if="node.data.icon" :icon="node.data.icon"></Icon>
        <span>{{ node.label }}</span>
      </template>
    </TreeFilter>

    <div class="user-table">
      <ProTable
        ref="proTable"
        :request-api="getUserListByDept"
        :columns="columns"
        :init-request-param="initRequestParam"
        :search-col="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
        style="height: 90%"
        :detailForm="detailForm"
      ></ProTable>
    </div>
  </div>
</template>

<script setup lang="ts" name="User">
import { TreeFilter, type TableColumnProps } from "work";
import { getDeptTreeList } from "@/api/dept";
import { ProTable } from "work";
import { addOne, editOne, deleteOne, getUserListByDept } from "@/api/user";
import { options } from "./formOptions";
import type { DialogForm } from "@work/components";

const columns: TableColumnProps[] = [
  { type: "index", label: "#", width: 80 },
  { prop: "username", label: "用户名称", search: { el: "el-input" } },
  { prop: "nickname", label: "用户昵称", search: { el: "el-input" } },
  { prop: "deptName", label: "部门", search: { el: "el-input" } },
  { prop: "phone", label: "手机号码", search: { el: "el-input" } },
  { prop: "email", label: "邮箱", search: { el: "el-input" } },
  {
    prop: "status",
    label: "状态",
    enum: [
      { label: "启用", value: 1 },
      { label: "禁用", value: 0 },
    ],
    search: { el: "el-select" },
  },
  { prop: "registerTime", label: "注册时间", search: { el: "el-input" } },
  { prop: "operation", label: "操作" },
];

const detailForm: DialogForm = {
  options: options,
  addApi: addOne,
  editApi: editOne,
  deleteApi: deleteOne,
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "30%",
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
  height: 100%;
  padding: 10px;

  .iconify {
    margin-right: 5px;
    vertical-align: -2px;
  }

  .user-table {
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
