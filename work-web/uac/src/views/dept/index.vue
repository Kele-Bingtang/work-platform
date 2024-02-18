<template>
  <div class="dept-container">
    <div class="dept-table">
      <ProTable
        ref="proTable"
        :request-api="getDeptTreeTable"
        :columns="columns"
        :search-col="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
        style="height: 90%"
        :detailForm="detailForm"
        :border="false"
      ></ProTable>
    </div>
  </div>
</template>

<script setup lang="tsx" name="Dept">
import { ProTable, message } from "work";
import { getDeptTreeTable, addOne, editOne, deleteOne, type Dept } from "@/api/dept";
import { findItemNested, type DialogForm, type TableColumnProps } from "@work/components";
import { options } from "./formOptions";
import { useLayoutStore } from "@/stores/layout";
import { ElMessageBox } from "element-plus";

const columns: TableColumnProps<Dept.DeptTreeTable[]>[] = [
  { prop: "deptName", label: "部门名称", align: "left", search: { el: "el-input" } },
  { prop: "orderNum", label: "排序", width: "80px" },
  {
    prop: "status",
    label: "状态",
    width: "80px",
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
  { prop: "createTime", label: "创建时间", width: "160px" },
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

const statusChange = async (value: number, row: Dept.DeptTreeTable) => {
  const statusEnum = await useLayoutStore().getDictData("sys_normal_disable");
  const filterData = findItemNested(statusEnum.data, value + "", "dictValue", "");

  ElMessageBox.confirm(
    `确认要 <span style="color: teal">${filterData.dictLabel}</span> 【${row.deptName}】部门吗`,
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
.dept-container {
  width: 100%;
  height: 100%;
  padding: 10px;
  .dept-table {
    width: 100%;
    height: 97%;
  }
}
</style>
