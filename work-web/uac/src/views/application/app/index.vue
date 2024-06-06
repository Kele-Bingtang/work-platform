<template>
  <div :class="prefixClass">
    <TreeFilter
      ref="treeFilterRef"
      title="客户端清单"
      :requestApi="getClientTreeList"
      @change="handleTreeChange"
      id="clientId"
      label="clientName"
    >
      <template #default="{ node }">
        <Icon v-if="node.data.icon" :icon="node.data.icon"></Icon>
        <span>{{ node.label }}</span>
      </template>
    </TreeFilter>

    <div :class="`${prefixClass}__role-table`">
      <ProTable
        ref="proTableRef"
        :request-api="listPage"
        :columns="columns"
        :init-request-param="initRequestParam"
        :search-cols="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
        :dialogForm="dialogForm"
      ></ProTable>
    </div>
  </div>
</template>

<script setup lang="tsx" name="App">
import { TreeFilter, ProTable } from "work";
import { getClientTreeList } from "@/api/application/client";
import { listPage, addOne, editOne, deleteOne, deleteBatch, type App } from "@/api/application/app";
import {
  type DialogForm,
  type ProTableInstance,
  type TableColumnProps,
  type TreeFilterInstance,
} from "@work/components";
import { elFormProps, useFormSchema } from "./useFormSchema";
import { useLayoutStore } from "@/stores";
import { useChange } from "@/hooks/useChange";
import { ElSwitch } from "element-plus";
import { useDesign } from "@work/hooks";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("app");

const treeFilterRef = shallowRef<TreeFilterInstance>();
const proTableRef = shallowRef<ProTableInstance>();

const { statusChange } = useChange(
  "appName",
  "应用",
  (row, status) => editOne({ id: row.id, clientId: row.clientId, status }),
  () => proTableRef.value?.getTableList()
);

const initRequestParam = reactive({
  clientId: "",
});

const columns: TableColumnProps<App.AppInfo>[] = [
  { type: "selection", fixed: "left", width: 80 },
  { prop: "appCode", label: "应用编码", search: { el: "el-input" } },
  { prop: "appName", label: "应用名称", search: { el: "el-input" } },
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
  { prop: "orderNum", label: "显示顺序" },
  { prop: "createTime", label: "创建时间" },
  { prop: "operation", label: "操作", width: 160, fixed: "right" },
];

const dialogForm: DialogForm = {
  formProps: {
    elFormProps,
    schema: useFormSchema(
      computed(() => treeFilterRef.value?.treeData),
      computed(() => initRequestParam.clientId)
    ).schema,
  },
  id: ["id", "clientId"],
  addApi: addOne,
  editApi: data => editOne({ ...data, clientId: initRequestParam.clientId || data.clientId }),
  removeApi: deleteOne,
  removeBatchApi: deleteBatch,
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "45%",
    height: 200,
    top: "5vh",
    closeOnClickModal: false,
  },
};

const handleTreeChange = (nodeId: number) => {
  initRequestParam.clientId = nodeId + "";
};
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-app;

.#{$prefix-class} {
  display: flex;
  flex: 1;

  &__role-table {
    width: calc(100% - 230px);
    height: 100%;
  }
}
</style>
