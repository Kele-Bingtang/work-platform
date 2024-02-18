<template>
  <div class="role-container">
    <TreeFilter
      ref="treeFilterRef"
      title="应用清单"
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
        ref="proTable"
        :request-api="listRoleByApp"
        :columns="columns"
        :init-request-param="initRequestParam"
        :search-col="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
        style="height: 90%"
        :detailForm="detailForm"
      ></ProTable>
    </div>
  </div>
</template>

<script setup lang="tsx" name="Role">
import { TreeFilter, ProTable, message } from "work";
import { getAppTreeList } from "@/api/app";
import { listRoleByApp, addOne, editOne, deleteOne, type Role } from "@/api/role";
import { findItemNested, type DialogForm, type TableColumnProps } from "@work/components";
import { options } from "./formOptions";
import { useLayoutStore } from "@/stores/layout";
import { ElMessageBox } from "element-plus";

const initRequestParam = reactive({
  appId: "",
});

const columns: TableColumnProps<Role.RoleInfo[]>[] = [
  { type: "index", label: "#", width: 80 },
  { prop: "roleCode", label: "角色编码", search: { el: "el-input" } },
  { prop: "roleName", label: "角色名称", search: { el: "el-input" } },
  {
    prop: "status",
    label: "状态",
    fieldNames: { value: "dictValue", label: "dictLabel" },
    enum: () => useLayoutStore().getDictData("sys_normal_disable"),
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
  { prop: "orderNum", label: "显示顺序" },
  { prop: "createTime", label: "创建时间" },
  { prop: "operation", label: "操作" },
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

const handleTreeChange = (nodeId: number) => {
  initRequestParam.appId = nodeId + "";
};

const statusChange = async (value: number, row: Role.RoleInfo) => {
  const statusEnum = await useLayoutStore().getDictData("sys_normal_disable");
  const filterData = findItemNested(statusEnum.data, value + "", "dictValue", "");

  ElMessageBox.confirm(
    `确认要 <span style="color: teal">${filterData.dictLabel}</span> 【${row.roleName}】角色吗`,
    "系统提示",
    {
      dangerouslyUseHTMLString: true,
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
    }
  )
    .then(() => {
      editOne({ id: row.id, status: value }).then(res => {
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
.role-container {
  display: flex;
  width: 100%;
  height: 100%;
  padding: 10px;

  .role-table {
    width: calc(100% - 230px);
    height: 97%;
  }
}
</style>
