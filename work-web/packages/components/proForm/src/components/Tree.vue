<template>
  <div v-if="checkbox">
    <el-checkbox v-model="defaultExpandAll" label="展开/折叠" />
    <el-checkbox v-if="select" v-model="isSelectAll" :indeterminate="indeterminate" label="全选/全不选" />
    <el-checkbox v-if="select" v-model="checkStrictly" label="父子联动" />
  </div>
  <el-input
    v-if="search"
    v-model="filterText"
    :style="{ width: $attrs.searchWidth || '98.5%' }"
    :placeholder="$attrs.searchPlaceholder || '请输入关键词进行筛选'"
  />
  <el-tree
    ref="treeRef"
    :show-checkbox="select"
    @check="handleCheck"
    :filter-node-method="filterNode"
    :check-strictly="!checkStrictly"
    :default-expanded-keys="defaultExpandedKeys"
    v-bind="$attrs"
    :data="data"
    :nodeKey="nodeKey"
  >
    <template #default="{ node, data }">
      <slot :node="node" :data="data"></slot>
    </template>
  </el-tree>
</template>
<script setup lang="ts">
import { ElTree } from "element-plus";
import { nextTick, ref, watch } from "vue";

defineOptions({ name: "Tree" });

export interface TreeProps {
  modelValue?: any[];
  data: any[]; // 树数据
  nodeKey?: string; // 每一个树节点 id
  checkValueType?: "keys" | "nodes"; // v-model 返回的格式，keys 返回选中的节点 nodeKey，nodes 为返回选中的节点
  expandSelected?: boolean; // 初始化就有默认选中的节点时，是否展开选中节点的所有父节点
  checkbox?: boolean;
  search?: boolean;
  select?: boolean;
}

const props = withDefaults(defineProps<TreeProps>(), {
  modelValue: () => [],
  nodeKey: " id",
  checkValueType: "keys",
  expandSelected: true,
  checkbox: false,
  search: false,
  select: true,
});

type EmitProps = { (e: "update:modelValue", value: string[]): void };
const emits = defineEmits<EmitProps>();
const defaultExpandAll = ref(false); // 展开/折叠状态
const isSelectAll = ref(false); // 全选/全不选状态
const indeterminate = ref(false); // 处于全选和全不选期间的状态
const checkStrictly = ref(true); // 父子联动
const defaultExpandedKeys = ref<string[]>([]); // 默认展开的节点 nodeKey
const filterText = ref(""); // 搜索的文本
const treeRef = ref<InstanceType<typeof ElTree>>();

watch(defaultExpandAll, val => {
  const nodes = treeRef.value?.store._getAllNodes();
  console.log(treeRef.value);
  // true 全展开，false 全折叠
  if (val) nodes?.forEach(item => (item.expanded = true));
  else nodes?.forEach(item => (item.expanded = false));
});

watch(isSelectAll, val => {
  // true 全选，false 全不选
  if (val) treeRef.value?.setCheckedNodes(props.data);
  else treeRef.value?.setCheckedNodes([]);
  // 关闭处于全选和全不选期间的状态
  indeterminate.value = false;
});

watch(filterText, val => {
  treeRef.value!.filter(val);
});

// 过滤搜索条件
const filterNode = (value: string, data: Record<string, any>) => {
  if (!value) return true;
  return data.label.includes(value);
};

const handleCheck = (_: any, selected: any) => {
  if (props.checkValueType === "nodes") emits("update:modelValue", selected.checkedNodes);
  else emits("update:modelValue", selected.checkedKeys);

  // 如果都没选择任何节点，则状态关闭
  if (!selected.checkedKeys?.length) {
    isSelectAll.value = false;
    indeterminate.value = false;
  } else if (selected.checkedKeys?.length === treeRef.value?.store._getAllNodes().length) {
    // 如果选择的节点等于节点数量，则代表全选
    isSelectAll.value = true;
    indeterminate.value = false;
  } else indeterminate.value = true;
};

const setChecked = (val: any[]) => {
  // 不用 nextTick 导致获取不到 treeRef 实例
  nextTick(() => {
    const { checkValueType, expandSelected, nodeKey } = props;
    if (checkValueType === "nodes") {
      treeRef.value?.setCheckedNodes(val);
      if (expandSelected) defaultExpandedKeys.value = val?.map(item => item[nodeKey]);
    } else {
      treeRef.value?.setCheckedKeys(val, false);
      if (expandSelected) defaultExpandedKeys.value = val;
    }
  });
};

watch(
  () => props.modelValue,
  val => val && setChecked(val),
  { immediate: true }
);
</script>