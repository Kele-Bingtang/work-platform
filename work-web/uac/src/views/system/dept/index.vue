<template>
  <div :class="prefixClass">
    <ProTable
      ref="proTableRef"
      :request-api="listDeptTreeTable"
      :columns="columns"
      :search-cols="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
      :dialogForm="dialogForm"
      :border="false"
      :pagination="false"
      :exportFile
    >
      <template #operationExtra="{ row, operate }">
        <el-button link size="small" :icon="Plus" @click="operate?.handleAdd({ parentId: row.deptId })">新增</el-button>
      </template>
    </ProTable>
  </div>
</template>

<script setup lang="tsx" name="Dept">
import { ProTable, downloadByData } from "work";
import { listDeptTreeTable, addOne, editOne, deleteOne, type Dept, exportExcel } from "@/api/system/dept";
import { type DialogForm, type ProTableInstance, type TableColumnProps } from "@work/components";
import { elFormProps, schema } from "./formSchema";
import { useLayoutStore } from "@/stores";
import { useChange } from "@/hooks/useChange";
import { ElMessageBox, ElSwitch } from "element-plus";
import { Plus } from "@element-plus/icons-vue";
import { useDesign } from "@work/hooks";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("dept");

const proTableRef = shallowRef<ProTableInstance>();

const { statusChange } = useChange(
  "deptName",
  "部门",
  (row, status) => editOne({ id: row.id, deptId: row.deptId, parentId: row.parentId, status }),
  () => proTableRef.value?.getTableList()
);

const columns: TableColumnProps<Dept.DeptTreeTable>[] = [
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
  { prop: "userCount", label: "成员数量", width: 100 },
  { prop: "leader", label: "领导", width: 120 },
  { prop: "phone", label: "电话", width: 120 },
  { prop: "email", label: "邮箱", width: 120 },
  { prop: "createTime", label: "创建时间", width: 160 },
  { prop: "operation", label: "操作", width: 200, fixed: "right" },
];

const dialogForm: DialogForm = {
  formProps: { elFormProps, schema },
  id: ["id", "deptId"],
  addApi: addOne,
  editApi: editOne,
  removeApi: deleteOne,
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "45%",
    height: 300,
    top: "5vh",
    closeOnClickModal: false,
  },
};

const exportFile = (_: Record<string, any>[], searchParam: Record<string, any>) => {
  ElMessageBox.confirm("确认导出吗？", "温馨提示", { type: "warning" }).then(() => {
    exportExcel(searchParam).then(res => {
      downloadByData(res, `dept_${new Date().getTime()}.xlsx`);
    });
  });
};
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-dept;

.#{$prefix-class} {
  flex: 1;
}
</style>
