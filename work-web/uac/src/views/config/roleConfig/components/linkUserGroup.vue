<template>
  <div class="link-user-container">
    <ProTable
      ref="proTableRef"
      :request-api="listUserGroupByRoleId"
      :init-request-param="requestParam"
      :columns="columns"
      :search-cols="{ xs: 1, sm: 1, md: 3, lg: 3, xl: 3 }"
      :dialogForm="dialogForm"
      :border="false"
      row-key="linkId"
      height="100%"
      :isShowSearch="false"
    ></ProTable>
  </div>
</template>

<script setup lang="tsx" name="RoleLinkUserGroup">
import { ProTable } from "work";
import { addUserGroupsToRole, removeUserGroupFromRole } from "@/api/system/role";
import { listUserGroupByRoleId, type UserGroup } from "@/api/user/userGroup";
import { type DialogForm, type ProTableInstance, type TableColumnProps } from "@work/components";
import { elFormProps, useFormSchema } from "./linkUserGroupFormSchema";

export interface LinkUserProps {
  appId: string;
  roleId: string;
}

const props = defineProps<LinkUserProps>();

const requestParam = reactive({ roleId: props.roleId });

// 监听 roleId，变化后修改关联的表格查询默认值
watchEffect(() => (requestParam.roleId = props.roleId));

const proTableRef = shallowRef<ProTableInstance>();

// 表格列配置项
const columns: TableColumnProps<UserGroup.UserGroupLinkInfo>[] = [
  { type: "selection", fixed: "left", width: 10 },
  { prop: "groupName", label: "用户组名", minWidth: 120, search: { el: "el-input", key: "userGroupName" } },
  { prop: "intro", label: "描述", minWidth: 120 },
  {
    prop: "ownerId",
    label: "负责人",
    minWidth: 160,
    search: { el: "el-input", key: "owner" },
    render: ({ row }) => {
      return (
        <>
          {row.ownerName} {row.ownerId}
        </>
      );
    },
  },
  { prop: "createTime", label: "创建时间", minWidth: 160 },
  { prop: "operation", label: "操作", width: 160, fixed: "right" },
];

// 新增、编辑弹框配置项
const dialogForm: DialogForm = {
  formProps: {
    elFormProps,
    schema: useFormSchema(requestParam).schema,
    rowProps: { col: { span: 24 } },
  },
  id: ["linkId"],
  addApi: form =>
    addUserGroupsToRole({
      ...form,
      userGroupIds: form.userGroupIds,
      roleId: requestParam.roleId,
      appId: props.appId,
    }),
  removeApi: form => removeUserGroupFromRole([form.linkId]),
  removeBatchApi: removeUserGroupFromRole,
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "45%",
    height: 510,
    top: "5vh",
    closeOnClickModal: false,
  },
};
</script>

<style lang="scss" scoped>
.link-user-container {
  height: 100%;
}
</style>
