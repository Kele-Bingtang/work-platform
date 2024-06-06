<template>
  <div :class="prefixClass">
    <TreeFilter :requestApi="listDeptTreeList" @change="handleTreeChange" id="value">
      <template #default="{ node }">
        <Icon v-if="node.data.icon" :icon="node.data.icon"></Icon>
        <span>{{ node.label }}</span>
      </template>
    </TreeFilter>

    <div class="user-table">
      <ProTable
        ref="proTableRef"
        :request-api="listPage"
        :columns="columns"
        :init-request-param="initRequestParam"
        :search-cols="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
        style="display: flex; flex-direction: column"
        :dialogForm="dialogForm"
      >
        <template #operationExtra>
          <el-button link size="small" :icon="Key" @click="resetPassword">重置密码</el-button>
        </template>
      </ProTable>
    </div>
  </div>
</template>

<script setup lang="tsx" name="UserInfo">
import { TreeFilter, ProTable, useDialog, type TableColumnProps } from "work";
import { listDeptTreeList } from "@/api/system/dept";
import { addOne, editOne, deleteOne, deleteBatch, listPage, type User } from "@/api/user/user";
import { elFormProps, useFormSchema } from "./useFormSchema";
import type { DialogForm, ProTableInstance } from "@work/components";
import { useLayoutStore } from "@/stores";
import { useChange } from "@/hooks/useChange";
import { ElSwitch, ElInput } from "element-plus";
import { Key } from "@element-plus/icons-vue";
import { useDesign } from "@work/hooks";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("user");

const proTableRef = shallowRef<ProTableInstance>();
const newPassword = ref("");

const { open } = useDialog();

const resetPassword = () => {
  open({
    title: "重置密码",
    onClose: () => (newPassword.value = ""),
    onConfirm: handleConfirm,
    render: () => {
      return <ElInput v-model={newPassword.value} placeholder="请输入新密码"></ElInput>;
    },
  });
};

const handleConfirm = () => {
  if (!newPassword.value) {
    return;
  }
  // editOne({ id: row.id, userId: row.userId, password: newPassword.value }).then(() => {
  //   proTableRef.value?.getTableList();
  // });
};

const { statusChange } = useChange(
  "username",
  "用户",
  (row, status) => editOne({ id: row.id, userId: row.userId, status }),
  () => proTableRef.value?.getTableList()
);

const columns: TableColumnProps<User.UserInfo>[] = [
  { type: "selection", fixed: "left", width: 60 },
  { type: "index", label: "#", width: 60 },
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
  { prop: "operation", label: "操作", width: 220, fixed: "right" },
];

const dialogForm: DialogForm = {
  formProps: {
    elFormProps,
    schema: useFormSchema(computed(() => initRequestParam.deptId)).schema,
  },
  id: ["id", "userId"],
  addApi: addOne,
  editApi: editOne,
  editFilterKeys: ["dept", "disabled", "loginIp", "loginTime", "registerTime"],
  removeApi: deleteOne,
  removeBatchApi: deleteBatch,
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "45%",
    height: 420,
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
$prefix-class: #{$admin-namespace}-user;

.#{$prefix-class} {
  display: flex;
  flex: 1;

  .iconify {
    margin-right: 5px;
    vertical-align: -2px;
  }

  .user-table {
    display: flex;
    width: calc(100% - 230px);
    height: 100%;

    :deep(.#{$el-namespace}-dialog__body) {
      margin-left: 20px;
    }
  }
}
</style>
