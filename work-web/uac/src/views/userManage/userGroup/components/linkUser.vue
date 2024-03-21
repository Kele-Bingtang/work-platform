<template>
  <div class="link-user-container">
    <ProTable
      ref="proTableRef"
      :request-api="listUserLinkByGroupId"
      :init-request-param="requestParam"
      :columns="columns"
      :search-cols="{ xs: 1, sm: 1, md: 3, lg: 3, xl: 3 }"
      :detailForm="detailForm"
      :border="false"
      row-key="linkId"
      height="100%"
    ></ProTable>
  </div>
</template>

<script setup lang="tsx" name="LinkUser">
import { ProTable } from "work";
import {
  listUserLinkByGroupId,
  addUsersToGroup,
  editUserGroupLinkInfo,
  removeUserFromUserGroup,
  type UserGroup,
} from "@/api/user/userGroup";
import { type DialogForm, type ProTableInstance, type TableColumnProps } from "@work/components";
import { useFormOptions } from "./linkUserFormOptions";

export interface LinkUserProps {
  appId: string;
  userGroupId: string;
}

const props = defineProps<LinkUserProps>();

const requestParam = reactive({
  appId: props.appId,
  userGroupId: props.userGroupId,
});

// 监听 userGroupId，变化后修改关联的表格查询默认值
watch(
  () => props.userGroupId,
  () => {
    if (props.userGroupId) {
      requestParam.appId = props.appId;
      requestParam.userGroupId = props.userGroupId;
    }
  }
);

const proTableRef = shallowRef<ProTableInstance>();

// 表格列配置项
const columns: TableColumnProps<UserGroup.UserLinkInfo>[] = [
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
  options: useFormOptions(requestParam.userGroupId).options,
  addApi: form =>
    addUsersToGroup({
      ...form,
      userIds: form.userIds,
      userGroupId: requestParam.userGroupId,
      appId: requestParam.appId,
    }),
  editApi: form => editUserGroupLinkInfo({ ...form, id: form.linkId }),
  editFilterParams: ["userId", "appId", "userIds"],
  deleteApi: form => removeUserFromUserGroup([form.linkId]),
  deleteBatchApi: removeUserFromUserGroup,
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
