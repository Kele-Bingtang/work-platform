<template>
  <div class="user-group-container">
    <TreeFilter
      class="tree-filter"
      ref="treeFilterRef"
      title="App 清单"
      :requestApi="getAppTreeList"
      @change="handleTreeChange"
      id="appId"
      label="appName"
      :enable-total="false"
      default-first
    >
      <template #default="{ node }">
        <Icon v-if="node.data.icon" :icon="node.data.icon"></Icon>
        <span>{{ node.label }}</span>
      </template>
    </TreeFilter>

    <div class="user-group-box">
      <ProTable
        ref="proTableRef"
        :request-api="list"
        :init-request-param="requestParam"
        :columns="columns"
        :search-col="{ xs: 1, sm: 1, md: 1, lg: 1, xl: 1 }"
        :detailForm="detailForm"
        :border="false"
        @row-click="handleRowClick"
        :data-callback="dataCallback"
        highlight-current-row
      ></ProTable>
    </div>
    <div class="link-box" v-if="clickRowInfo">
      <Description :title="clickRowInfo?.groupName" :data="descriptionData"></Description>
      <el-tabs v-model="activeName" style="height: calc(100% - 70px)">
        <el-tab-pane label="Authorized User" name="User" style="height: 100%">
          <LinkUser :appId="requestParam.appId" :userGroupId="clickRowInfo.groupId"></LinkUser>
        </el-tab-pane>
        <el-tab-pane label="Authorized Role" name="Role">
          <LinkRole :appId="requestParam.appId" :userGroupId="clickRowInfo.groupId"></LinkRole>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script setup lang="tsx" name="UserGroup">
import { ProTable, TreeFilter } from "work";
import { list, addOne, editOne, deleteOne, deleteBatch, type UserGroup } from "@/api/user/userGroup";
import { type DialogForm, type ProTableInstance, type TableColumnProps } from "@work/components";
import { options } from "./formOptions";
import { getAppTreeList, type App } from "@/api/application/app";
import Description from "../userLink/components/description.vue";
import LinkUser from "./components/linkUser.vue";
import LinkRole from "./components/linkRole.vue";

const proTableRef = shallowRef<ProTableInstance>();
const requestParam = reactive({
  appId: "",
});
const activeName = ref("User");
const clickRowInfo = ref<UserGroup.UserGroupInfo>();
const descriptionData = ref([
  {
    label: "负责人：",
    value: clickRowInfo.value?.ownerId ? `${clickRowInfo.value?.ownerName} ${clickRowInfo.value?.ownerId}` : "",
  },
]);

const handleRowClick = (row: UserGroup.UserGroupInfo) => {
  clickRowInfo.value = row;
};

const dataCallback = (data: UserGroup.UserGroupInfo[]) => {
  clickRowInfo.value = data[0] || undefined;
  data[0] && proTableRef.value?.element?.setCurrentRow(data[0]);
};

const columns: TableColumnProps<UserGroup.UserGroupInfo>[] = [
  { type: "selection", fixed: "left", width: 10 },
  { prop: "groupName", label: "用户组名", minWidth: 120, search: { el: "el-input" } },
  { prop: "intro", label: "描述", minWidth: 120 },
  { prop: "ownerId", label: "负责人", minWidth: 160 },
  { prop: "createTime", label: "创建时间", minWidth: 160 },
  { prop: "operation", label: "操作", width: 160, fixed: "right" },
];
const detailForm: DialogForm = {
  options: options,
  addApi: form => addOne({ ...form, appId: requestParam.appId }),
  editApi: editOne,
  deleteApi: deleteOne,
  deleteBatchApi: deleteBatch,
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "45%",
    top: "5vh",
    closeOnClickModal: false,
  },
};

const handleTreeChange = (_: number, data: App.AppTree) => {
  requestParam.appId = data.appId;
};
</script>

<style lang="scss" scoped>
.user-group-container {
  display: flex;

  .tree-filter {
    width: 10%;
  }

  .user-group-box {
    width: 45%;
    padding-right: 12px;
    border-right: 1px solid #dfdfdf;
  }

  .link-box {
    width: 45%;
    padding-left: 12px;
    background-color: #ffffff;
  }
}
</style>
<style lang="scss">
.user-group-container {
  .link-box {
    .el-tabs__content {
      height: calc(100% - 60px);
    }
  }
}
</style>
