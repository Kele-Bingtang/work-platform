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

<script setup lang="tsx" name="User">
import { TreeFilter, ProTable, type TableColumnProps, findItemNested, message } from "work";
import { getDeptTreeList } from "@/api/dept";
import { addOne, editOne, deleteOne, getUserListByDept, type User } from "@/api/user";
import { options } from "./formOptions";
import type { DialogForm } from "@work/components";
import { useLayoutStore } from "@/stores/layout";
import { ElMessageBox } from "element-plus";

const columns: TableColumnProps<User.UserInfo>[] = [
  { type: "index", label: "#", width: 80 },
  { prop: "username", label: "用户名称", width: 170, search: { el: "el-input" } },
  { prop: "nickname", label: "用户昵称", width: 170, search: { el: "el-input" } },
  { prop: "deptName", label: "部门", width: 170, search: { el: "el-input" } },
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
            <el-switch
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

const statusChange = async (value: number, row: User.UserInfo) => {
  const statusEnum = await useLayoutStore().getDictData("sys_normal_status");
  const filterData = findItemNested(statusEnum.data, value + "", "dictValue", "");

  if (!filterData?.dictLabel) return (row.status = 1) && message.warning("不存在状态");

  ElMessageBox.confirm(
    `确认要 <span style="color: teal">${filterData?.dictLabel}</span> 【${row.username}】角色吗`,
    "系统提示",
    {
      dangerouslyUseHTMLString: true,
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
    }
  )
    .then(() => {
      editOne({ id: row.id, userId: row.userId, status: value }).then(res => {
        if (res.status === "success") message.success("修改成功");
      });
    })
    .catch(() => {
      if (value === 0) return (row.status = 1);
      if (value === 1) return (row.status = 0);
    });
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
    height: 97%;
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
