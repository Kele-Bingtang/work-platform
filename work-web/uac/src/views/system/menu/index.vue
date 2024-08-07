<template>
  <div :class="prefixClass">
    <TreeFilter
      ref="treeFilterRef"
      title="App 清单"
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

    <div :class="`${prefixClass}__table`">
      <div :class="`${prefixClass}__table--empty`" v-if="!initRequestParam.appId">
        <el-empty description="请先选择一个应用" />
      </div>
      <ProTable
        ref="proTableRef"
        v-show="initRequestParam.appId"
        :request-api="listMenuTreeTableByApp"
        :columns="columns"
        :init-request-param="initRequestParam"
        :request-auto="false"
        :search-cols="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
        :dialogForm="dialogForm"
        :border="false"
        :pagination="false"
        :exportFile
        :disabled-button="!hasAuth('system:menu:export') ? ['export'] : []"
      >
        <template #operationExtra="{ row, operate }">
          <el-button
            v-auth="['system:menu:add']"
            link
            size="small"
            :icon="Plus"
            @click="operate?.handleAdd({ parentId: row.menuId })"
          >
            新增
          </el-button>
        </template>
      </ProTable>
    </div>
  </div>
</template>

<script setup lang="tsx" name="Menu">
import { TreeFilter, ProTable, Icon, downloadByData } from "work";
import { httpPrefix, httpsPrefix } from "@work/constants";
import { getAppTreeList } from "@/api/application/app";
import { listMenuTreeTableByApp, addMenu, editMenu, removeMenu, type Menu, exportExcel } from "@/api/system/menu";
import {
  type DialogForm,
  type TableColumnProps,
  type ProTableInstance,
  type TreeFilterInstance,
} from "@work/components";
import { menuTypeEnum, elFormProps, useFormSchema } from "./useFormSchema";
import { useLayoutStore } from "@/stores";
import { Plus } from "@element-plus/icons-vue";
import { useChange, usePermission } from "@/hooks";
import { ElMessageBox, ElSwitch } from "element-plus";
import { useDesign } from "@work/hooks";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("menu");

const treeFilterRef = shallowRef<TreeFilterInstance>();
const proTableRef = shallowRef<ProTableInstance>();

const { statusChange } = useChange(
  "menuName",
  "菜单",
  (row, status) => editMenu({ id: row.id, menuId: row.menuId, parentId: row.parentId, status }),
  () => proTableRef.value?.getTableList()
);

const initRequestParam = reactive({
  appId: "",
});

const columns: TableColumnProps<Menu.MenuInfo>[] = [
  { prop: "menuName", label: "菜单名称", align: "left", search: { el: "el-input" } },
  {
    prop: "icon",
    label: "图标",
    width: 60,
    render: ({ row }) => <Icon icon={row.icon}></Icon>,
  },
  { prop: "orderNum", label: "排序", width: 80 },
  { prop: "component", label: "组件路径", width: 200 },
  { prop: "permission", label: "权限标识", width: 180 },
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
  { prop: "menuType", label: "类型", width: 80, enum: menuTypeEnum },
  { prop: "intro", label: "介绍", width: 180 },
  { prop: "createTime", label: "创建时间", width: 160 },
  { prop: "operation", label: "操作", width: 200, fixed: "right" },
];

const installMeta = (data: any) => {
  if (data.meta) {
    const keys = Object.keys(data.meta);
    keys?.forEach((key: any) => {
      if (data.meta[key] === "default") delete data.meta[key];
    });
    return data.meta;
  }
};

const { hasAuth } = usePermission();

const dialogForm: DialogForm = {
  formProps: {
    elFormProps,
    schema: useFormSchema(
      computed(() => treeFilterRef.value?.treeData),
      computed(() => initRequestParam.appId)
    ).schema,
    includeModelKeys: ["meta"],
  },
  id: ["id", "menuId"],
  addApi: data =>
    addMenu({
      ...data,
      path: (data.pathPrefix || "") + (data.path || ""),
      meta: installMeta(data),
      appId: initRequestParam.appId,
    }),
  editApi: data =>
    editMenu({
      ...data,
      path: (data.pathPrefix || "") + (data.path || ""),
      meta: installMeta(data),
      appId: initRequestParam.appId,
    }),
  removeApi: removeMenu,
  clickEdit: form => {
    if ([httpPrefix, httpsPrefix].find(item => form.path.includes(item))) {
      form.pathPrefix = form.path.split("//")[0] + "//";
      form.path = form.path.split("//")[1];
    } else form.pathPrefix = "";
  },
  disableAdd: !hasAuth("system:menu:add"),
  disableEdit: !hasAuth("system:menu:edit"),
  disableRemove: !hasAuth("system:menu:remove"),
  apiFilterKeys: ["pathPrefix"],
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "45%",
    height: 630,
    top: "5vh",
    closeOnClickModal: false,
  },
};

const handleTreeChange = (nodeId: number) => {
  initRequestParam.appId = nodeId + "";
};

const exportFile = (_: Record<string, any>[], searchParam: Record<string, any>) => {
  ElMessageBox.confirm("确认导出吗？", "温馨提示", { type: "warning" }).then(() => {
    exportExcel(searchParam).then(res => {
      downloadByData(res, `menu_${new Date().getTime()}.xlsx`);
    });
  });
};
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-menu;

.#{$prefix-class} {
  display: flex;
  flex: 1;

  &__table {
    width: calc(100% - 230px);
    height: 100%;

    &--empty {
      display: flex;
      justify-content: center;
      width: 100%;
      height: 100%;
      background-color: #ffffff;
    }
  }
}
</style>
