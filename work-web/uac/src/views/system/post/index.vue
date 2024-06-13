<template>
  <div :class="prefixClass">
    <ProTable
      ref="proTableRef"
      :request-api="listPage"
      :columns="columns"
      :search-cols="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
      :dialogForm="dialogForm"
      :border="false"
      :exportFile
    ></ProTable>
  </div>
</template>

<script setup lang="tsx" name="Post">
import { ProTable, downloadByData } from "work";
import { listPage, addOne, editOne, deleteOne, deleteBatch, type Post, exportExcel } from "@/api/system/post";
import { type DialogForm, type ProTableInstance, type TableColumnProps } from "@work/components";
import { elFormProps, schema } from "./formSchema";
import { useLayoutStore } from "@/stores";
import { useChange } from "@/hooks/useChange";
import { ElMessageBox, ElSwitch } from "element-plus";
import { useDesign } from "@work/hooks";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("post");

const proTableRef = shallowRef<ProTableInstance>();

const { statusChange } = useChange(
  "postName",
  "岗位",
  (row, status) => editOne({ id: row.id, postId: row.postId, status }),
  () => proTableRef.value?.getTableList()
);

const columns: TableColumnProps<Post.PostInfo>[] = [
  { type: "selection", fixed: "left", width: 80 },
  { prop: "postCode", label: "岗位编码", search: { el: "el-input" } },
  { prop: "postName", label: "岗位名称", search: { el: "el-input" } },
  { prop: "orderNum", label: "排序" },
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

const dialogForm: DialogForm = {
  formProps: { elFormProps, schema },
  id: ["id", "postId"],
  addApi: addOne,
  editApi: editOne,
  removeApi: deleteOne,
  removeBatchApi: deleteBatch,
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "45%",
    height: 220,
    top: "5vh",
    closeOnClickModal: false,
  },
};

const exportFile = (_: Record<string, any>[], searchParam: Record<string, any>) => {
  ElMessageBox.confirm("确认导出吗？", "温馨提示", { type: "warning" }).then(() => {
    exportExcel(searchParam).then(res => {
      downloadByData(res, `post_${new Date().getTime()}.xlsx`);
    });
  });
};
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-post;

.#{$prefix-class} {
  flex: 1;
}
</style>
