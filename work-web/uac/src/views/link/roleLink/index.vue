<template>
  <div class="role-container">
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

    <div class="role-box">
      <ProTable
        ref="proTableRef"
        :request-api="listPage"
        :init-request-param="requestParam"
        :columns="columns"
        :search-cols="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
        :detailForm="detailForm"
        :border="false"
        @row-click="handleRowClick"
        :data-callback="dataCallback"
        highlight-current-row
      ></ProTable>
    </div>

    <div class="link-box" v-if="clickRowInfo">
      <Description :title="clickRowInfo?.roleName"></Description>

      <el-tabs v-model="activeName" style="height: calc(100% - 70px)">
        <el-tab-pane label="关联用户" name="User" style="height: 100%">
          <LinkUser :appId="requestParam.appId" :roleId="clickRowInfo.roleId"></LinkUser>
        </el-tab-pane>

        <el-tab-pane lazy label="关联用户组" name="UserGroup" style="height: 100%">
          <LinkUserGroup :appId="requestParam.appId" :roleId="clickRowInfo.roleId"></LinkUserGroup>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>
<script setup lang="tsx" name="RoleLink">
import { TreeFilter, ProTable } from "work";
import { getAppTreeList } from "@/api/application/app";
import { listPage, addOne, editOne, deleteOne, deleteBatch, type Role } from "@/api/system/role";
import {
  type DialogForm,
  type ProTableInstance,
  type TableColumnProps,
  type TreeFilterInstance,
} from "@work/components";
import { useFormOptions } from "@/views/system/role/useFormOptions";
import { useLayoutStore } from "@/stores";
import { useChange } from "@/hooks/useChange";
import { ElSwitch } from "element-plus";
import Description from "@/components/Description/index.vue";
import LinkUser from "./components/linkUser.vue";
import LinkUserGroup from "./components/linkUserGroup.vue";

const treeFilterRef = shallowRef<TreeFilterInstance>();
const proTableRef = shallowRef<ProTableInstance>();
const activeName = ref("User");
const clickRowInfo = ref<Role.RoleInfo>();
const requestParam = reactive({ appId: "" });

const { statusChange } = useChange(
  "roleName",
  "角色",
  (row, status) => editOne({ id: row.id, roleId: row.roleId, status }),
  () => proTableRef.value?.getTableList()
);

// 表格行点击回调
const handleRowClick = (row: Role.RoleInfo) => {
  clickRowInfo.value = row;
};

// ProTable 获取数据后的回调
const dataCallback = (data: http.PageData<Role.RoleInfo[]>) => {
  clickRowInfo.value = data.list[0] || undefined;
  data.list[0] && proTableRef.value?.element?.setCurrentRow(data.list[0]);
};

const columns: TableColumnProps<Role.RoleInfo>[] = [
  { type: "selection", fixed: "left", width: 40 },
  { prop: "roleCode", label: "角色编码", search: { el: "el-input" } },
  { prop: "roleName", label: "角色名称", search: { el: "el-input" } },
  {
    prop: "status",
    label: "状态",
    width: 80,
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
  { prop: "createTime", label: "创建时间", width: 160 },
  { prop: "operation", label: "操作", width: 130, fixed: "right" },
];

const detailForm: DialogForm = {
  options: useFormOptions(
    computed(() => treeFilterRef.value?.treeData),
    computed(() => requestParam.appId)
  ).options,
  addApi: addOne,
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
const handleTreeChange = (nodeId: number) => {
  requestParam.appId = nodeId + "";
};
</script>

<style lang="scss" scoped>
.role-container {
  display: flex;
  width: 100%;

  .tree-filter {
    width: 10%;
  }

  .role-box {
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
.role-container {
  .link-box {
    .el-tabs__content {
      height: calc(100% - 60px);
    }
  }
}
</style>
