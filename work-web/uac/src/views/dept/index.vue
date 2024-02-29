<template>
  <div class="dept-container">
    <ProTable
      ref="proTableRef"
      :request-api="getDeptTreeTable"
      :columns="columns"
      :search-col="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
      style="height: 90%"
      :detailForm="detailForm"
      :border="false"
    ></ProTable>
  </div>
</template>

<script setup lang="tsx" name="Dept">
import { ProTable } from "work";
import { getDeptTreeTable, addOne, editOne, deleteOne, type Dept } from "@/api/dept";
import { type DialogForm, type ProTableInstance, type TableColumnProps } from "@work/components";
import { options } from "./formOptions";
import { useLayoutStore } from "@/stores/layout";
import { useChange } from "@/hooks/useChange";

const proTableRef = shallowRef<ProTableInstance>();

const { statusChange } = useChange(
  "deptName",
  "部门",
  (row, status) => editOne({ id: row.id, deptId: row.deptId, parentId: row.parentId, status }),
  () => proTableRef.value?.getTableList()
);

const columns: TableColumnProps<Dept.DeptTreeTable[]>[] = [
  { prop: "deptName", label: "部门名称", align: "left", search: { el: "el-input" } },
  { prop: "orderNum", label: "排序", width: 80 },
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
            <el-switch
              vModel={row.status}
              activeValue={1}
              inactiveValue={0}
              activeText="启用"
              inactiveText="停用"
              inlinePrompt
              onChange={(value: number | string) => statusChange(value, row)}
            />
          )}
        </>
      );
    },
  },
  { prop: "userCount", label: "成员数量", width: 100 },
  { prop: "leader", label: "领导", width: 120 },
  { prop: "phone", label: "电话", width: 120 },
  { prop: "email", label: "邮箱", width: 120 },
  { prop: "createTime", label: "创建时间", width: 160 },
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
</script>

<style lang="scss" scoped>
.dept-container {
  width: 100%;
  height: 100%;
  padding: 10px;
}
</style>
