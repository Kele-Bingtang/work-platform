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
        :request-api="listPage"
        :init-request-param="requestParam"
        :columns="columns"
        :search-cols="{ xs: 1, sm: 1, md: 2, lg: 2, xl: 2 }"
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
        <template v-for="item in tabEnums" :key="item.name">
          <el-tab-pane lazy :label="item.label" :name="item.name" style="height: 100%">
            <component :is="item.components" v-bind="item.props.value"></component>
          </el-tab-pane>
        </template>
      </el-tabs>
    </div>
  </div>
</template>

<script setup lang="tsx" name="UserGroup">
import { ProTable, TreeFilter } from "work";
import { listPage, addOne, editOne, deleteOne, deleteBatch, type UserGroup } from "@/api/user/userGroup";
import { type DialogForm, type ProTableInstance, type TableColumnProps } from "@work/components";
import { useFormOptions } from "./formOptions";
import { getAppTreeList, type App } from "@/api/application/app";
import Description from "@/components/Description/index.vue";
import LinkUser from "./components/linkUser.vue";
import LinkRole from "./components/linkRole.vue";

const proTableRef = shallowRef<ProTableInstance>();

const requestParam = reactive({ appId: "" });

const activeName = ref("User");
const clickRowInfo = ref<UserGroup.UserGroupInfo>();

const descriptionData = ref([{ label: "负责人：", value: "", span: 7 }]);

// 表格行点击回调
const handleRowClick = (row: UserGroup.UserGroupInfo) => {
  clickRowInfo.value = row;
  descriptionData.value[0].value = row?.ownerId ? `${row?.ownerName} ${row?.ownerId}` : "";
};

// ProTable 获取数据后的回调
const dataCallback = (data: http.PageData<UserGroup.UserGroupInfo[]>) => {
  clickRowInfo.value = data.list[0] || undefined;
  descriptionData.value[0].value = data.list[0]?.ownerId ? `${data.list[0]?.ownerName} ${data.list[0]?.ownerId}` : "";
  data.list[0] && proTableRef.value?.element?.setCurrentRow(data.list[0]);
};

// 表格列配置项
const columns: TableColumnProps<UserGroup.UserGroupInfo>[] = [
  { type: "selection", fixed: "left", width: 10 },
  { prop: "groupName", label: "用户组名", minWidth: 120, search: { el: "el-input" } },
  { prop: "intro", label: "描述", minWidth: 120 },
  {
    prop: "ownerId",
    label: "负责人",
    minWidth: 160,
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
const detailForm: DialogForm = {
  options: useFormOptions().options,
  addApi: form => addOne({ ...form, appId: requestParam.appId }),
  beforeAdd: form => {
    form.ownerId = form.user?.username;
    form.ownerName = form.user?.nickname;
  },
  editApi: editOne,
  beforeEdit: form => {
    form.ownerId = form.user?.username;
    form.ownerName = form.user?.nickname;
  },
  deleteApi: deleteOne,
  deleteBatchApi: deleteBatch,
  apiFilterParams: ["user", "createTime"],
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "45%",
    top: "5vh",
    closeOnClickModal: false,
  },
};

// 选择 App 清单节点回调
const handleTreeChange = (_: number, data: App.AppTree) => {
  requestParam.appId = data.appId;
};

type TabEnum = {
  name: string;
  label: string;
  components: any;
  props: ComputedRef<{
    appId?: string;
    groupId?: string;
  }>;
};

const tabEnums: TabEnum[] = [
  {
    name: "User",
    label: "关联用户",
    components: LinkUser,
    props: computed(() => {
      return {
        appId: requestParam.appId,
        userGroupId: clickRowInfo.value?.groupId,
      };
    }),
  },
  {
    name: "Role",
    label: "关联角色",
    components: LinkRole,
    props: computed(() => {
      return {
        appId: requestParam.appId,
        userGroupId: clickRowInfo.value?.groupId,
      };
    }),
  },
];
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