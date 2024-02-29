<template>
  <div class="menu-container">
    <TreeFilter
      ref="treeFilterRef"
      title="应用清单"
      :requestApi="getAppTreeList"
      @change="handleTreeChange"
      id="appId"
      label="appName"
      :enable-total="false"
    >
      <template #default="{ node }">
        <Icon v-if="node.data.icon" :icon="node.data.icon"></Icon>
        <span>{{ node.label }}</span>
      </template>
    </TreeFilter>

    <div class="menu-table">
      <div class="empty-box" v-if="!initRequestParam.appId"><el-empty description="请先选择一个应用" /></div>
      <ProTable
        v-show="initRequestParam.appId"
        ref="proTableRef"
        :request-api="listMenuTreeTableByApp"
        :columns="columns"
        :init-request-param="initRequestParam"
        :request-auto="false"
        :search-col="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
        style="height: 90.5%"
        :detailForm="detailForm"
        :border="false"
      >
        <template #operationExtra="{ row }">
          <el-button
            link
            size="small"
            :icon="Plus"
            @click="proTableRef?.dialogOperateRef?.handleAdd({ parentId: row.id })"
          >
            新增
          </el-button>
        </template>
      </ProTable>
    </div>
  </div>
</template>

<script setup lang="tsx" name="Menu">
import { TreeFilter, ProTable } from "work";
import { httpPrefix, httpsPrefix } from "@work/constants";
import { getAppTreeList } from "@/api/app";
import { listMenuTreeTableByApp, addOne, editOne, deleteOne, type Menu } from "@/api/menu";
import { type DialogForm, type TableColumnProps, type ProTableInstance } from "@work/components";
import { options } from "./formOptions";
import { useLayoutStore } from "@/stores/layout";
import { Plus } from "@element-plus/icons-vue";
import { useChange } from "@/hooks/useChange";

const proTableRef = shallowRef<ProTableInstance>();

const { statusChange } = useChange(
  "menuName",
  "菜单",
  (row, status) => editOne({ id: row.id, menuId: row.menuId, parentId: row.parentId, status }),
  () => proTableRef.value?.getTableList()
);

const initRequestParam = reactive({
  appId: "",
});

const columns: TableColumnProps<Menu.MenuInfo[]>[] = [
  { prop: "menuName", label: "菜单名称", align: "left", search: { el: "el-input" } },
  { prop: "icon", label: "图标", width: 100 },
  { prop: "orderNum", label: "排序", width: 80 },
  { prop: "component", label: "组件路径", width: 200 },
  {
    prop: "status",
    label: "状态",
    fieldNames: { value: "dictValue", label: "dictLabel" },
    enum: () => useLayoutStore().getDictData("sys_normal_status"),
    search: { el: "el-select" },
    render: ({ row }) => {
      return (
        <>
          {row.status !== undefined && (
            <el-switch
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
  { prop: "intro", label: "介绍", width: 200 },
  { prop: "createTime", label: "创建时间", width: 160 },
  { prop: "operation", label: "操作", width: 200, fixed: "right" },
];

const detailForm: DialogForm = {
  options: options,
  addApi: data => addOne({ ...data, path: (data.pathPrefix || "") + (data.path || ""), appId: initRequestParam.appId }),
  editApi: data =>
    editOne({ ...data, path: (data.pathPrefix || "") + (data.path || ""), appId: initRequestParam.appId }),
  deleteApi: deleteOne,
  clickEdit: form => {
    if ([httpPrefix, httpsPrefix].find(item => form.path.includes(item))) {
      form.pathPrefix = form.path.split("//")[0] + "//";
      form.path = form.path.split("//")[1];
    } else form.pathPrefix = "";
  },
  apiFilterParams: ["pathPrefix"],
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "45%",
    top: "5vh",
    closeOnClickModal: false,
  },
};

const handleTreeChange = (nodeId: number) => {
  initRequestParam.appId = nodeId + "";
};
</script>

<style lang="scss" scoped>
.menu-container {
  display: flex;
  width: 100%;
  height: 100%;
  padding: 10px;

  .empty-box {
    display: flex;
    justify-content: center;
    width: 100%;
    height: 100%;
    background-color: #ffffff;
  }

  .menu-table {
    width: calc(100% - 230px);
    height: 100%;
  }
}
</style>
