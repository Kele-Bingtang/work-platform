<template>
  <div class="link-user-container">
    <ProTable
      ref="proTableRef"
      :request-api="listUserLinkByGroupId"
      :init-request-param="requestParam"
      :columns="columns"
      :search-col="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
      :detailForm="detailForm"
      :border="false"
      height="100%"
    ></ProTable>
  </div>
</template>

<script setup lang="tsx" name="LinkUser">
import { ProTable } from "work";
import {
  listUserLinkByGroupId,
  addUsersToGroup,
  updateLinkInfo,
  removeUserFromUserGroup,
  deleteBatch,
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

const columns: TableColumnProps<UserGroup.UserLinkInfo>[] = [
  { type: "selection", fixed: "left", width: 10 },
  { prop: "username", label: "用户名", minWidth: 120, search: { el: "el-input" } },
  { prop: "validFrom", label: "生效时间", minWidth: 120 },
  { prop: "expireOn", label: "过期时间", minWidth: 120 },
  { prop: "createTime", label: "创建时间", minWidth: 160 },
  { prop: "operation", label: "操作", width: 160, fixed: "right" },
];

const detailForm: DialogForm = {
  options: useFormOptions(requestParam.appId, requestParam.userGroupId).options,
  addApi: form =>
    addUsersToGroup({
      ...form,
      userIds: form.userIds,
      userGroupId: requestParam.userGroupId,
      appId: requestParam.appId,
    }),
  editApi: form => updateLinkInfo({ ...form, id: form.linkId }),
  editFilterParams: ["userId", "appId", "userIds"],
  deleteApi: form => removeUserFromUserGroup([form.linkId]),
  deleteBatchApi: deleteBatch,
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
