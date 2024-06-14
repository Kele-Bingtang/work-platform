<template>
  <div class="link-role-container">
    <ProTable
      ref="proTableRef"
      :request-api="listRoleLinkByGroupId"
      :init-request-param="requestParam"
      :columns="columns"
      :search-cols="{ xs: 1, sm: 1, md: 3, lg: 3, xl: 3 }"
      :dialogForm="dialogForm"
      :border="false"
      row-key="linkId"
      height="100%"
      :isShowSearch="false"
      :disabled-button="!hasAuth('system:userGroup:linkRole') ? ['export'] : []"
    ></ProTable>
  </div>
</template>

<script setup lang="tsx" name="UserGroupLinkRole">
import { ProTable } from "work";
import { type DialogForm, type ProTableInstance, type TableColumnProps } from "@work/components";
import { elFormProps, useFormSchema } from "./linkRoleFormSchema";
import { listRoleLinkByGroupId, removeUserGroupFromRole, type Role } from "@/api/system/role";
import { addRolesToUserGroup } from "@/api/user/userGroup";
import { usePermission } from "@/hooks";

export interface LinkRoleProps {
  appId: string;
  userGroupId: string;
}

const props = defineProps<LinkRoleProps>();

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

const { hasAuth } = usePermission();

// 新增、编辑弹框配置项
const dialogForm: DialogForm = {
  formProps: {
    elFormProps,
    schema: useFormSchema(requestParam).schema,
  },
  id: ["linkId"],
  addApi: form =>
    addRolesToUserGroup({
      ...form,
      roleIds: form.roleIds,
      userGroupId: requestParam.userGroupId,
      appId: props.appId,
    }),
  addFilterKeys: ["role"],
  removeApi: form => removeUserGroupFromRole([form.linkId]),
  removeBatchApi: removeUserGroupFromRole,
  disableAdd: !hasAuth("system:userGroup:linkRole"),
  disableEdit: !hasAuth("system:userGroup:linkRole"),
  disableRemove: !hasAuth("system:userGroup:linkRole"),
  disableRemoveBatch: !hasAuth("system:userGroup:linkRole"),
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "45%",
    height: 520,
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
