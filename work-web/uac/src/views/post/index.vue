<template>
  <div class="post-container">
    <ProTable
      ref="proTable"
      :request-api="list"
      :columns="columns"
      :search-col="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
      style="height: 90%"
      :detailForm="detailForm"
      :border="false"
    ></ProTable>
  </div>
</template>

<script setup lang="tsx" name="Post">
import { ProTable, message } from "work";
import { list, addOne, editOne, deleteOne, type Post } from "@/api/post";
import { findItemNested, type DialogForm, type TableColumnProps } from "@work/components";
import { options } from "./formOptions";
import { useLayoutStore } from "@/stores/layout";
import { ElMessageBox } from "element-plus";

const columns: TableColumnProps<Post.PostInfo[]>[] = [
  { prop: "postCode", label: "部门编码", search: { el: "el-input" } },
  { prop: "postName", label: "部门名称", search: { el: "el-input" } },
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
  { prop: "createTime", label: "创建时间" },
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

const statusChange = async (value: number, row: Post.PostInfo) => {
  const statusEnum = await useLayoutStore().getDictData("sys_normal_status");
  const filterData = findItemNested(statusEnum.data, value + "", "dictValue", "");

  if (!filterData?.dictLabel) return (row.status = 1) && message.warning("不存在状态");

  ElMessageBox.confirm(
    `确认要 <span style="color: teal">${filterData?.dictLabel}</span> 【${row.postName}】部门吗`,
    "系统提示",
    {
      dangerouslyUseHTMLString: true,
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
    }
  )
    .then(() => {
      editOne({ id: row.id, postId: row.postId, status: value })
        .then(res => {
          if (res.status === "success") message.success("修改成功");
          else {
            statusRecover(value, row);
          }
        })
        .catch(() => statusRecover(value, row));
    })
    .catch(() => statusRecover(value, row));
};

const statusRecover = (value: number, row: Post.PostInfo) => {
  if (value === 0) return (row.status = 1);
  if (value === 1) return (row.status = 0);
};
</script>

<style lang="scss" scoped>
.post-container {
  width: 100%;
  height: 100%;
  padding: 10px;
}
</style>
