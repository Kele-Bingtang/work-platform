<template>
  <div class="link-user-container">
    <ProTable
      ref="proTableRef"
      :request-api="listUserLinkByGroupId"
      :init-request-param="requestParam"
      :columns="columns"
      :search-cols="{ xs: 1, sm: 1, md: 3, lg: 3, xl: 3 }"
      :dialogForm="dialogForm"
      :border="false"
      row-key="linkId"
      height="100%"
      :isShowSearch="false"
      :disabled-button="!hasAuth('system:userGroup:linkUser') ? ['export'] : []"
    ></ProTable>
  </div>
</template>

<script setup lang="tsx" name="UserGroupLinkUser">
import { ProTable } from "work";
import { addUsersToGroup, editUserGroupLinkInfo, removeUserFromUserGroup } from "@/api/user/userGroup";
import { listUserLinkByGroupId, type User } from "@/api/user/user";
import { type DialogForm, type ProTableInstance, type TableColumnProps } from "@work/components";
import { elFormProps, useFormSchema } from "./linkUserFormSchema";
import { usePermission } from "@/hooks";

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
const columns: TableColumnProps<User.UserLinkInfo>[] = [
  { type: "selection", fixed: "left", width: 10 },
  { prop: "username", label: "用户名称", minWidth: 120, search: { el: "el-input" } },
  { prop: "nickname", label: "用户昵称", minWidth: 120, search: { el: "el-input" } },
  { prop: "validFrom", label: "生效时间", minWidth: 120 },
  { prop: "expireOn", label: "过期时间", minWidth: 120 },
  { prop: "createTime", label: "创建时间", minWidth: 160 },
  { prop: "operation", label: "操作", width: 160, fixed: "right" },
];

const { hasAuth } = usePermission();

// 新增、编辑弹框配置项
const dialogForm: DialogForm = {
  formProps: {
    elFormProps,
    schema: useFormSchema(requestParam).schema,
    rowProps: { col: { span: 24 } },
  },
  id: ["linkId"],
  addApi: form =>
    addUsersToGroup({
      ...form,
      userIds: form.userIds,
      userGroupId: requestParam.userGroupId,
      appId: props.appId,
    }),
  editApi: form => editUserGroupLinkInfo({ ...form, id: form.linkId }),
  editFilterKeys: ["userId", "appId", "userIds"],
  removeApi: form => removeUserFromUserGroup([form.linkId]),
  removeBatchApi: removeUserFromUserGroup,
  disableAdd: !hasAuth("system:userGroup:linkUser"),
  disableEdit: !hasAuth("system:userGroup:linkUser"),
  disableRemove: !hasAuth("system:userGroup:linkUser"),
  disableRemoveBatch: !hasAuth("system:userGroup:linkUser"),
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "45%",
    height: 170,
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
