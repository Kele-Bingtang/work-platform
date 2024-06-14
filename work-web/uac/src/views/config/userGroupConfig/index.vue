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

    <div :class="`${prefixClass}__box`">
      <ProTable
        ref="proTableRef"
        :request-api="listPage"
        :init-request-param="requestParam"
        :columns="columns"
        :search-cols="{ xs: 1, sm: 1, md: 2, lg: 2, xl: 2 }"
        :dialogForm="dialogForm"
        :border="false"
        @row-click="handleRowClick"
        :data-callback="dataCallback"
        highlight-current-row
        :exportFile
        :disabled-button="!hasAuth('system:userGroup:export') ? ['export'] : []"
      ></ProTable>
    </div>

    <div :class="`${prefixClass}__link`" v-if="clickRowInfo">
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
import { ProTable, TreeFilter, downloadByData } from "work";
import { listPage, addOne, editOne, deleteOne, deleteBatch, type UserGroup, exportExcel } from "@/api/user/userGroup";
import { type DialogForm, type ProTableInstance, type TableColumnProps } from "@work/components";
import { elFormProps, useFormSchema } from "./useFormSchema";
import { getAppTreeList, type App } from "@/api/application/app";
import { Description } from "@/components";
import LinkUser from "./components/linkUser.vue";
import LinkRole from "./components/linkRole.vue";
import { useDesign } from "@work/hooks";
import { ElMessageBox } from "element-plus";
import { usePermission } from "@/hooks";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("user-group");

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
  clickRowInfo.value = clickRowInfo.value || data.list[0] || undefined;
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

const { hasAuth } = usePermission();

// 新增、编辑弹框配置项
const dialogForm: DialogForm = {
  formProps: {
    elFormProps,
    schema: useFormSchema().schema,
  },
  id: ["id", "groupId"],
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
  disableAdd: !hasAuth("system:userGroup:add"),
  disableEdit: !hasAuth("system:userGroup:edit"),
  disableRemove: !hasAuth("system:userGroup:remove"),
  disableRemoveBatch: !hasAuth("system:userGroup:remove"),
  removeApi: deleteOne,
  removeBatchApi: deleteBatch,
  apiFilterKeys: ["user", "createTime"],
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "45%",
    height: 150,
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

const exportFile = (_: Record<string, any>[], searchParam: Record<string, any>) => {
  ElMessageBox.confirm("确认导出吗？", "温馨提示", { type: "warning" }).then(() => {
    exportExcel(searchParam).then(res => {
      downloadByData(res, `userGroup_${new Date().getTime()}.xlsx`);
    });
  });
};
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-user-group;

.#{$prefix-class} {
  display: flex;
  flex: 1;

  &__tree {
    width: 10%;
  }

  &__box {
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
