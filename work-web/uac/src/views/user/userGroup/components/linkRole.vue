<template>
  <div class="link-role-container">
    <ProTable
      ref="proTableRef"
      :request-api="listRoleLinkByGroupId"
      :init-request-param="requestParam"
      :columns="columns"
      :search-cols="{ xs: 1, sm: 1, md: 3, lg: 3, xl: 3 }"
      :detailForm="detailForm"
      :border="false"
      row-key="linkId"
      height="100%"
      :isShowSearch="false"
    ></ProTable>
  </div>
</template>

<script setup lang="tsx" name="LinkRole">
import { ProTable } from "work";
import { type DialogForm, type ProTableInstance, type TableColumnProps } from "@work/components";
import { useFormOptions } from "./linkRoleFormOptions";
import { listRoleLinkByGroupId, addUserGroupToRoles, removeUserGroupFromRole, type Role } from "@/api/role/role";

export interface LinkUserProps {
  appId: string;
  userGroupId: string;
}

const props = defineProps<LinkUserProps>();

const requestParam = reactive({ userGroupId: props.userGroupId });

// 监听 userGroupId，变化后修改关联的表格查询默认值
watchEffect(() => (requestParam.userGroupId = props.userGroupId));

const proTableRef = shallowRef<ProTableInstance>();

// 表格列配置项
const columns: TableColumnProps<Role.RoleLinkInfo>[] = [
  { type: "selection", fixed: "left", width: 10 },
  { prop: "roleName", label: "角色名称", minWidth: 120, search: { el: "el-input" } },
  { prop: "roleCode", label: "角色编码", minWidth: 120, search: { el: "el-input" } },
  { prop: "createTime", label: "创建时间", minWidth: 160 },
  { prop: "operation", label: "操作", width: 100, fixed: "right" },
];

// 新增、编辑弹框配置项
const detailForm: DialogForm = {
  options: useFormOptions(requestParam.userGroupId).options,
  addApi: form =>
    addUserGroupToRoles({
      ...form,
      roleIds: form.roleIds,
      userGroupId: requestParam.userGroupId,
      appId: props.appId,
    }),
  addFilterParams: ["role"],
  deleteApi: form => removeUserGroupFromRole([form.linkId]),
  deleteBatchApi: removeUserGroupFromRole,
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "45%",
    top: "5vh",
    closeOnClickModal: false,
  },
};
</script>

<style lang="scss" scoped>
.link-role-container {
  height: 100%;
}
</style>
