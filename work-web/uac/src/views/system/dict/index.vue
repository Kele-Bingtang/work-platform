<template>
  <div class="dict-container">
    <TreeFilter
      ref="treeFilterRef"
      title="App 清单"
      :requestApi="getAppTreeList"
      @change="handleTreeChange"
      id="appId"
      label="appName"
    >
      <template #default="{ node }">
        <Icon v-if="node.data.icon" :icon="node.data.icon"></Icon>
        <span>{{ node.label }}</span>
      </template>
    </TreeFilter>

    <div class="dict-table">
      <ProTable
        ref="proTableRef"
        :request-api="listPage"
        :columns="columns"
        :init-request-param="initRequestParam"
        :search-cols="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
        :detailForm="detailForm"
      ></ProTable>
    </div>

    <BasicDrawer v-model="drawer" size="55%" title="字典数据配置">
      <div class="drawer-content">
        <DictData
          :dict-code="dictInfo?.dictCode || ''"
          :app-id="dictInfo?.appId || ''"
          :is-cascade="dictInfo?.isCascade || 0"
        />
      </div>
    </BasicDrawer>
  </div>
</template>

<script setup lang="tsx" name="DictType">
import { ProTable, TreeFilter, BasicDrawer } from "work";
import { getAppTreeList } from "@/api/application/app";
import { listPage, addOne, editOne, deleteOne, type DictType } from "@/api/system/dictType";
import { type DialogForm, type TableColumnProps, type TreeFilterInstance } from "@work/components";
import DictData from "./dictData.vue";
import { ElLink } from "element-plus";
import { useFormOptions } from "./useFormOptions";
import { baseEnum } from "@work/constants";

const initRequestParam = reactive({
  appId: "",
});

const dictInfo = ref<DictType.DictTypeInfo>();
const drawer = ref(false);
const treeFilterRef = shallowRef<TreeFilterInstance>();

const clickDictCode = (row: DictType.DictTypeInfo) => {
  dictInfo.value = row;
  drawer.value = true;
};

const handleTreeChange = (nodeId: number) => {
  initRequestParam.appId = nodeId + "";
};

const columns: TableColumnProps<DictType.DictTypeInfo>[] = [
  { type: "index", label: "#", width: 80 },
  {
    prop: "dictCode",
    label: "字典编码",
    search: { el: "el-input" },
    render: ({ row }) => {
      return (
        <>
          <ElLink type="primary" underline={false} onClick={() => clickDictCode(row)}>
            {row.dictCode}
          </ElLink>
        </>
      );
    },
  },
  { prop: "dictName", label: "字典名称", search: { el: "el-input" } },
  { prop: "isCascade", label: "是否级联", width: 110, enum: baseEnum, search: { el: "el-input" } },
  { prop: "intro", label: "描述" },
  { prop: "createTime", label: "创建时间", sortable: true },
  { prop: "operation", label: "操作", width: 160, fixed: "right" },
];

const detailForm: DialogForm = {
  options: useFormOptions(
    computed(() => treeFilterRef.value?.treeData),
    computed(() => initRequestParam.appId)
  ).dictTypeOptions,
  addApi: addOne,
  editApi: editOne,
  deleteApi: deleteOne,
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "30%",
    top: "5vh",
    closeOnClickModal: false,
  },
};
</script>

<style lang="scss" scoped>
.dict-container {
  display: flex;
  width: 100%;

  .dict-table {
    width: calc(100% - 230px);
    height: 100%;
  }

  .drawer-content {
    width: 100%;
    height: 100%;
    background-color: #f0f2f5;
  }
}
</style>

<style lang="scss">
.dict-container {
  .dict-table .el-dialog__body {
    margin-left: 20px;
  }
}
</style>
