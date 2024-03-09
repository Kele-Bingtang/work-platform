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
        :request-api="list"
        :columns="columns"
        :init-request-param="initRequestParam"
        :search-col="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
        style="height: 90%"
        :detailForm="detailForm"
      ></ProTable>
    </div>

    <Drawer v-model="drawer" size="55%" title="字典数据配置">
      <div class="drawer-content">
        <DictData :dict-code="dictCode" :app-id="dictDataAppId" />
      </div>
    </Drawer>
  </div>
</template>

<script setup lang="tsx" name="DictType">
import { ProTable, TreeFilter, Drawer } from "work";
import { getAppTreeList } from "@/api/application/app";
import { list, addOne, editOne, removeOne, type DictType } from "@/api/system/dictType";
import { type DialogForm, type TableColumnProps, type TreeFilterInstance } from "@work/components";
import DictData from "./dictData.vue";
import { ElLink } from "element-plus";
import { useFormOptions } from "./useFormOptions";

const initRequestParam = reactive({
  appId: "",
});

const dictCode = ref("");
const dictDataAppId = ref("");
const drawer = ref(false);
const treeFilterRef = shallowRef<TreeFilterInstance>();

const clickDictCode = (code: string, appId: string) => {
  dictCode.value = code;
  dictDataAppId.value = appId;
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
          <ElLink type="primary" underline={false} onClick={() => clickDictCode(row.dictCode, row.appId)}>
            {row.dictCode}
          </ElLink>
        </>
      );
    },
  },
  { prop: "dictName", label: "字典名称", search: { el: "el-input" } },
  { prop: "createTime", label: "创建时间" },
  { prop: "operation", label: "操作", width: 160, fixed: "right" },
];

const detailForm: DialogForm = {
  options: useFormOptions(
    computed(() => treeFilterRef.value?.treeData),
    computed(() => initRequestParam.appId)
  ).dictTypeOptions,
  addApi: addOne,
  editApi: editOne,
  deleteApi: removeOne,
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
  height: 100%;
  padding: 10px;

  .dict-table {
    width: calc(100% - 230px);
    height: 97%;
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
