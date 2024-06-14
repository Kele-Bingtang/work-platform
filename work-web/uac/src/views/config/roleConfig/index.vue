<template>
  <div :class="prefixClass">
    <TreeFilter
      :class="`${prefixClass}__tree`"
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

    <div :class="`${prefixClass}__role`">
      <ProTable
        ref="proTableRef"
        :request-api="listPage"
        :init-request-param="requestParam"
        :columns="columns"
        :search-cols="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
        :dialogForm="dialogForm"
        :border="false"
        @row-click="handleRowClick"
        :data-callback="dataCallback"
        highlight-current-row
        :exportFile
        :disabled-button="!hasAuth('system:role:export') ? ['export'] : []"
      ></ProTable>
    </div>

    <div v-if="clickRowInfo" :class="`${prefixClass}__link`">
      <Description :title="clickRowInfo?.roleName"></Description>

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

<script setup lang="tsx" name="RoleLink">
import { TreeFilter, ProTable, downloadByData } from "work";
import { getAppTreeList } from "@/api/application/app";
import { listPage, addOne, editOne, deleteOne, deleteBatch, type Role, exportExcel } from "@/api/system/role";
import {
  type DialogForm,
  type ProTableInstance,
  type TableColumnProps,
  type TreeFilterInstance,
} from "@work/components";
import { elFormProps, useFormSchema } from "@/views/system/role/useFormSchema";
import { useLayoutStore } from "@/stores";
import { useChange, usePermission } from "@/hooks";
import { ElMessageBox, ElSwitch } from "element-plus";
import { Description } from "@/components";
import LinkUser from "./components/linkUser.vue";
import LinkUserGroup from "./components/linkUserGroup.vue";
import LinkMenu from "./components/linkMenu.vue";
import LinkDept from "./components/linkDept.vue";
import { useDesign } from "@work/hooks";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("role-link");

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

const { hasAuth } = usePermission();

const dialogForm: DialogForm = {
  formProps: {
    elFormProps,
    schema: useFormSchema(
      computed(() => treeFilterRef.value?.treeData),
      computed(() => requestParam.appId)
    ).schema,
  },
  id: ["id", "roleId", "appId"],
  addApi: addOne,
  editApi: editOne,
  removeApi: deleteOne,
  removeBatchApi: deleteBatch,
  disableAdd: !hasAuth("system:role:add"),
  disableEdit: !hasAuth("system:role:edit"),
  disableRemove: !hasAuth("system:role:remove"),
  disableRemoveBatch: !hasAuth("system:role:remove"),
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "45%",
    height: 450,
    top: "5vh",
    closeOnClickModal: false,
  },
};

const handleTreeChange = (nodeId: number) => {
  requestParam.appId = nodeId + "";
};

type TabEnum = {
  name: string;
  label: string;
  components: any;
  props: ComputedRef<{
    appId?: string;
    id?: number;
    roleId?: string;
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
        roleId: clickRowInfo.value?.roleId,
      };
    }),
  },
  {
    name: "UserGroup",
    label: "关联用户组",
    components: LinkUserGroup,
    props: computed(() => {
      return {
        appId: requestParam.appId,
        roleId: clickRowInfo.value?.roleId,
      };
    }),
  },
  {
    name: "Menu",
    label: "关联菜单",
    components: LinkMenu,
    props: computed(() => {
      return {
        appId: requestParam.appId,
        id: clickRowInfo.value?.id,
        roleId: clickRowInfo.value?.roleId,
      };
    }),
  },
  {
    name: "Dept",
    label: "关联部门",
    components: LinkDept,
    props: computed(() => {
      return {
        appId: requestParam.appId,
        roleId: clickRowInfo.value?.roleId,
      };
    }),
  },
];

const exportFile = (_: Record<string, any>[], searchParam: Record<string, any>) => {
  ElMessageBox.confirm("确认导出吗？", "温馨提示", { type: "warning" }).then(() => {
    exportExcel(searchParam).then(res => {
      downloadByData(res, `role_${new Date().getTime()}.xlsx`);
    });
  });
};
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-role-link;

.#{$prefix-class} {
  display: flex;
  flex: 1;

  &__tree {
    width: 10%;
  }

  &__role {
    width: 45%;
    padding-right: 12px;
    border-right: 1px solid #dfdfdf;
  }

  &__link {
    width: 45%;
    padding-left: 12px;
    background-color: #ffffff;

    :deep(.#{$el-namespace}-tabs__content) {
      height: calc(100% - 60px);
    }
  }
}
</style>
