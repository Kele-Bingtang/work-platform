<template>
  <div class="link-user-container">
    <ProTable
      ref="proTableRef"
      :request-api="listUserLinkByRoleId"
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

<script setup lang="tsx" name="RoleLinkUser">
import { ProTable } from "work";
import { addUsersToRole, editUserRoleLinkInfo, removeUserFromRole } from "@/api/system/role";
import { listUserLinkByRoleId, type User } from "@/api/user/user";
import { type DialogForm, type ProTableInstance, type TableColumnProps } from "@work/components";
import { useFormOptions } from "./linkUserFormOptions";

export interface LinkUserProps {
  appId: string;
  roleId: string;
}

const props = defineProps<LinkUserProps>();

const requestParam = reactive({ roleId: props.roleId });

// 监听 userGroupId，变化后修改关联的表格查询默认值
watchEffect(() => (requestParam.roleId = props.roleId));

const proTableRef = shallowRef<ProTableInstance>();

// 表格列配置项
const columns: TableColumnProps<User.UserLinkInfo>[] = [
  { type: "selection", fixed: "left", width: 10 },
  { prop: "username", label: "用户名称", minWidth: 120, search: { el: "el-input" } },
  { prop: "nickname", label: "用户昵称", minWidth: 120, search: { el: "el-input" } },
  { prop: "validFrom", label: "生效时间", minWidth: 120 },
  { prop: "expireOn", label: "过期时间", minWidth: 120 },
  { prop: "createTime", label: "创建时间", minWidth: 160 },
  { prop: "operation", label: "操作", width: 160, fixed: "right" },
];

// 新增、编辑弹框配置项
const detailForm: DialogForm = {
  options: useFormOptions(requestParam).options,
  addApi: form =>
    addUsersToRole({
      ...form,
      userIds: form.userIds,
      roleId: requestParam.roleId,
      appId: props.appId,
    }),
  editApi: form => editUserRoleLinkInfo({ ...form, id: form.linkId }),
  editFilterParams: ["userId", "appId", "userIds"],
  deleteApi: form => removeUserFromRole([form.linkId]),
  deleteBatchApi: removeUserFromRole,
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "45%",
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
