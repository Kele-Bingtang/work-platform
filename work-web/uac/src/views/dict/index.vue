<template>
  <div class="user-container">
    <TreeFilter
      ref="treeFilterRef"
      title="应用清单"
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

    <div class="user-table">
      <ProTable
        ref="proTable"
        :request-api="listDictTypeByApp"
        :columns="columns"
        :init-request-param="initRequestParam"
        :search-col="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
        style="height: 90%"
        :detailForm="detailForm"
      ></ProTable>
    </div>

    <Drawer v-model="drawer" size="55%" title="字典数据配置">
      <div class="drawer-content">
        <DictData :dict-code="dictCode" :app-id="initRequestParam.appId" />
      </div>
    </Drawer>
  </div>
</template>

<script setup lang="tsx" name="DictType">
import { TreeFilter, Drawer, type TableColumnProps } from "work";
import { getAppTreeList } from "@/api/app";
import { ProTable } from "work";
import { addOneDictType, editOneDictType, removeOneDictType, listDictTypeByApp } from "@/api/dictType";
// import { options } from "./formOptions";
import { type DialogForm, type FormOptionsProps } from "@work/components";
import DictData from "./dictData.vue";

const initRequestParam = reactive({
  appId: "",
});

const dictCode = ref("");
const drawer = ref(false);
const treeFilterRef = shallowRef();

const clickDictCode = (code: string) => {
  dictCode.value = code;
  drawer.value = true;
};

const handleTreeChange = (nodeId: number) => {
  initRequestParam.appId = nodeId + "";
};

const columns: TableColumnProps[] = [
  { type: "index", label: "#", width: 80 },
  {
    prop: "dictCode",
    label: "字典编码",
    search: { el: "el-input" },
    render: ({ row }) => {
      return (
        <>
          <el-link type="primary" underline={false} onClick={() => clickDictCode(row.dictCode)}>
            {row.dictCode}
          </el-link>
        </>
      );
    },
  },
  { prop: "dictName", label: "字典名称", search: { el: "el-input" } },
  { prop: "createTime", label: "创建时间" },
  { prop: "operation", label: "操作" },
];

const options: FormOptionsProps = {
  form: {
    inline: true,
    labelPosition: "top",
    labelWidth: "50px",
    size: "default",
    fixWidth: true,
  },
  columns: [
    {
      formItem: { label: "字典编码", prop: "dictCode", required: true },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 字典编码" } },
    },
    {
      formItem: { label: "字典名称", prop: "dictName" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 字典名称" } },
    },
    {
      formItem: { label: "所属应用", prop: "appId" },
      attrs: {
        el: "el-select",
        fieldNames: { value: "appId", label: "appName" },
        enum: computed(() => treeFilterRef.value?.treeData),
        defaultValue: computed(() => initRequestParam.appId),
        props: { clearable: true, placeholder: "请选择 所属应用" },
      },
    },
  ],
};

const detailForm: DialogForm = {
  options: options,
  addApi: addOneDictType,
  editApi: editOneDictType,
  deleteApi: removeOneDictType,
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "30%",
    top: "5vh",
    closeOnClickModal: false,
  },
};
</script>

<style lang="scss" scoped>
.user-container {
  display: flex;
  width: 100%;
  height: 100%;
  padding: 10px;

  .iconify {
    margin-right: 5px;
    vertical-align: -2px;
  }

  .user-table {
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
.user-container {
  .user-table .el-dialog__body {
    margin-left: 20px;
  }
}
</style>
