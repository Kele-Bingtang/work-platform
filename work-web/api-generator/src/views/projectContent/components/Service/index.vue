<template>
  <div :class="prefixClass">
    <TreeFilter
      ref="treeFilterRef"
      title="目录"
      :requestApi="listCategory"
      @change="handleTreeChange"
      id="categoryId"
      label="categoryName"
      :enableTotal="false"
      defaultFirst
    >
      <template #default="{ node }">
        <div class="flx-center">
          <Icon :icon="FolderOpened" class="mr-1.5"></Icon>
          <span>{{ node.label }}</span>
        </div>
      </template>
    </TreeFilter>

    <Service v-if="categoryId" :categoryId />
    <el-empty v-else description="目录暂无服务" />
  </div>
</template>

<script setup lang="ts" name="ServiceMain">
import { TreeFilter, mittBus, useDesign, type TreeFilterInstance } from "work";
import { listCategory } from "@/api/category";
import { FolderOpened } from "@element-plus/icons-vue";
import Service from "./Service.vue";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("service-main");

const categoryId = ref("");

const treeFilterRef = shallowRef<TreeFilterInstance>();

const handleTreeChange = (nodeId: string) => {
  categoryId.value = nodeId;
};

mittBus.on("initTreeData", () => {
  unref(treeFilterRef)?.initTreeData();
});
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-service-main;

.#{$prefix-class} {
  display: flex;
  height: 100%;
}
</style>
