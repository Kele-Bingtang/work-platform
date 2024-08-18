<template>
  <div :class="prefixClass">
    <TreeFilter
      ref="treeFilterRef"
      title="模块清单"
      :requestApi="() => listAppModule(route.params.appId as string)"
      @change="handleTreeChange"
      id="appModule"
      label="appModule"
      :enableTotal="false"
      defaultFirst
    >
      <template #default="{ node }">
        <Icon v-if="node.data.icon" :icon="node.data.icon"></Icon>
        <span>{{ node.label }}</span>
      </template>
    </TreeFilter>

    <div :class="`${prefixClass}__role-table`">
      <ProTable
        ref="proTableRef"
        :request-api="listFilePage"
        :columns="columns"
        :init-request-param="initRequestParam"
        :requestAuto="false"
        :search-cols="{ xs: 1, sm: 1, md: 2, lg: 4, xl: 4 }"
      >
        <template #tableHeader>
          <el-button type="danger" :icon="Delete" plain @click="handleDeleteBatch" :disabled="!proTableRef?.isSelected">
            批量删除
          </el-button>
        </template>
        <template #operation="{ row }">
          <el-button link type="primary" :icon="View" @click="handlePriView(row)">预览</el-button>
          <el-button link type="danger" :icon="Delete" @click="handleDelete(row)">删除</el-button>
        </template>
      </ProTable>
    </div>
  </div>
</template>

<script setup lang="tsx" name="File">
import { TreeFilter, ProTable, downloadByData, message } from "work";
import { listAppModule, listFilePage, removeFile, removeBatchFile, type File } from "@/api/file";
import { download } from "@/api/download";
import { type ProTableInstance, type TableColumnProps, type TreeFilterInstance } from "@work/components";
import { useDesign, useHandleData } from "@work/hooks";
import { View, Delete } from "@element-plus/icons-vue";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("app");

const treeFilterRef = shallowRef<TreeFilterInstance>();
const proTableRef = shallowRef<ProTableInstance>();

const route = useRoute();

const initRequestParam = reactive({
  appModule: "",
});

const handleDeleteBatch = () => {
  useHandleData("文件将会被删除，确定删除吗？", async () => {
    const selectedList = unref(proTableRef)?.selectedList || [];
    const fileKeyList = selectedList.map(item => item.fileKey);

    const res = await removeBatchFile(route.params.appId as string, fileKeyList);
    if (res.code === 200) {
      unref(proTableRef)?.clearSelection();
      unref(proTableRef)?.getTableList();
      message.success("删除成功");
    }
  });
};

const handlePriView = (row: File.FileInfo) => {
  window.open(`${import.meta.env.VITE_API_URL}/download/${row.appId}/${row.fileKey}`);
};

const handleDelete = (row: File.FileInfo) => {
  useHandleData("文件将会被删除，确定删除吗？", async () => {
    const res = await removeFile(row.appId, row.fileKey);
    if (res.code === 200) {
      unref(proTableRef)?.getTableList();
      message.success("删除成功");
    }
  });
};

const columns: TableColumnProps<File.FileInfo>[] = [
  { type: "selection", fixed: "left", width: 80 },
  {
    prop: "fileKey",
    label: "附件标识",
    search: { el: "el-input" },
    width: 280,
    link: true,
    linkProps: {
      onClick: ({ row }) => download(row.appId, row.fileKey).then(res => downloadByData(res, row.fileName)),
    },
  },
  { prop: "fileName", label: "源附件名称", search: { el: "el-input" }, width: 200 },
  { prop: "filePath", label: "附件存储路径", width: 280 },
  { prop: "fileType", label: "附件类型", width: 100 },
  { prop: "fileSize", label: "附件大小（KB）", width: 160 },
  {
    prop: "expireTime",
    label: "失效时间",
    width: 160,
    search: { el: "el-select", key: "expire" },
    isFilterEnum: false,
    enum: [
      { value: true, label: "是" },
      { value: false, label: "否" },
    ],
  },
  { prop: "createTime", label: "创建时间", width: 160 },
  { prop: "operation", label: "操作", width: 160, fixed: "right" },
];

const handleTreeChange = (nodeId: number) => {
  initRequestParam.appModule = nodeId + "";
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
