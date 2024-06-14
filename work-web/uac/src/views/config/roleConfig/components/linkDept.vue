<template>
  <div style="display: flex; align-items: center; margin-bottom: 10px">
    <el-button v-auth="['system:role:linkDept']" type="primary" @click="handleEdit">编辑</el-button>
    <el-button @click="initTreeData()">刷新</el-button>
    <el-alert title="蓝色代表已关联的部门，黑色代表未关联的部门" :closable="false" style="margin: 0 10px" />
  </div>
  <Tree :data="data" node-key="value" checkbox search :select="false">
    <template #default="{ data }">
      <span :class="data.class">{{ data.label }}</span>
    </template>
  </Tree>
</template>

<script setup lang="tsx" name="LinkMenu">
import { listDeptListByRoleId, listDeptIdsByRoleId, listDeptTreeList, type Dept } from "@/api/system/dept";
import { ProForm, Tree, useDialog } from "work";
import type { FormSchemaProps } from "@work/components";
import { addDeptsToRole } from "@/api/system/role";

export interface LinkDeptProps {
  appId: string;
  roleId: string;
}

const props = defineProps<LinkDeptProps>();

const data = ref<Dept.DeptTreeList[]>([]);
const form = ref<{ selectedDeptIds: string[] }>({ selectedDeptIds: [] });
const selectedDeptIds = ref<string[]>([]);

const initTreeData = async (appId = props.appId, roleId = props.roleId) => {
  const [treeData, deptIds] = await Promise.all([
    listDeptListByRoleId(appId, roleId),
    listDeptIdsByRoleId(props.appId, props.roleId),
  ]);
  data.value = treeData.data || [];
  form.value.selectedDeptIds = deptIds.data;
  selectedDeptIds.value = deptIds.data;
};

watchEffect(() => initTreeData(props.appId, props.roleId));

const { open } = useDialog();

const handleEdit = () => {
  form.value.selectedDeptIds = selectedDeptIds.value;
  open({
    title: "编辑部门",
    height: 500,
    onClose: handleCancel,
    onConfirm: handleConfirm,
    render: () => {
      return <ProForm v-model={form.value} elFormProps={{ labelWidth: 80 }} schema={schema} />;
    },
  });
};

const handleCancel = () => {
  form.value = { selectedDeptIds: [] };
};

const handleConfirm = async () => {
  await addDeptsToRole({
    roleId: props.roleId,
    appId: props.appId,
    selectedDeptIds: form.value.selectedDeptIds,
  });
  initTreeData(props.appId, props.roleId);
};

const schema: FormSchemaProps[] = [
  {
    prop: "selectedDeptIds",
    label: "",
    el: "el-tree",
    enum: () => listDeptTreeList(),
    props: { nodeKey: "value", search: true, checkbox: true },
    col: { span: 24 },
  },
];
</script>

<style lang="scss" scoped>
.selected {
  color: var(--el-color-primary);
}
</style>
