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
        :search-cols="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
      ></ProTable>
    </div>
  </div>
</template>

<script setup lang="tsx" name="File">
import { TreeFilter, ProTable } from "work";
import { listAppModule, listFilePage, type File } from "@/api/file";
import { type ProTableInstance, type TableColumnProps, type TreeFilterInstance } from "@work/components";
import { useDesign } from "@work/hooks";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("app");

const treeFilterRef = shallowRef<TreeFilterInstance>();
const proTableRef = shallowRef<ProTableInstance>();

const route = useRoute();

const initRequestParam = reactive({
  appModule: "",
});

const columns: TableColumnProps<File.FileInfo>[] = [
  { type: "selection", fixed: "left", width: 80 },
  { prop: "appId", label: "App ID", search: { el: "el-input" } },
  { prop: "fileKey", label: "附件标识", search: { el: "el-input" } },
  { prop: "fileName", label: "源附件名称", search: { el: "el-input" } },
  { prop: "filePath", label: "附件存储路径" },
  { prop: "fileType", label: "附件类型" },
  { prop: "fileSize", label: "附件大小（KB）" },
  { prop: "expireTime", label: "失效时间" },
  { prop: "createTime", label: "创建时间" },
  // { prop: "operation", label: "操作", width: 160, fixed: "right" },
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
