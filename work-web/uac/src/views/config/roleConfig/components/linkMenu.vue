<template>
  <div style="display: flex; align-items: center; margin-bottom: 10px">
    <el-button v-auth="['system:role:linkMenu']" type="primary" @click="handleEdit">编辑</el-button>
    <el-button @click="initTreeData()">刷新</el-button>
    <el-alert title="蓝色代表已关联的菜单，黑色代表未关联的菜单" :closable="false" style="margin: 0 10px" />
  </div>
  <Tree :data="data" node-key="value" checkbox search :select="false">
    <template #default="{ data }">
      <span :class="data.class">{{ data.label }}</span>
    </template>
  </Tree>
</template>

<script setup lang="tsx" name="LinkMenu">
import { listMenuListByRoleId, listMenuIdsByRoleId, listMenuTreeSelectByApp, type Menu } from "@/api/system/menu";
import { ProForm, Tree, useDialog } from "work";
import type { FormSchemaProps } from "@work/components";
import { addMenusToRole } from "@/api/system/role";

export interface LinkMenuProps {
  appId: string;
  id: number;
  roleId: string;
}

const props = defineProps<LinkMenuProps>();

const data = ref<Menu.MenuTreeList[]>([]);
const form = ref<{ selectedMenuIds: string[] }>({ selectedMenuIds: [] });
const selectedMenuIds = ref<string[]>([]);

const initTreeData = async (appId = props.appId, roleId = props.roleId) => {
  const [treeData, menuIds] = await Promise.all([
    listMenuListByRoleId(appId, roleId),
    listMenuIdsByRoleId(props.appId, props.roleId),
  ]);
  data.value = treeData.data || [];
  form.value.selectedMenuIds = menuIds.data;
  selectedMenuIds.value = menuIds.data;
};

watchEffect(() => initTreeData(props.appId, props.roleId));

const { open } = useDialog();

const handleEdit = () => {
  form.value.selectedMenuIds = selectedMenuIds.value;
  open({
    title: "编辑菜单",
    height: 500,
    onClose: handleCancel,
    onConfirm: handleConfirm,
    render: () => {
      return <ProForm v-model={form.value} elFormProps={{ labelWidth: 80 }} schema={schema} />;
    },
  });
};

const handleCancel = () => {
  form.value = { selectedMenuIds: [] };
};

const handleConfirm = async () => {
  await addMenusToRole({
    roleId: props.roleId,
    appId: props.appId,
    selectedMenuIds: form.value.selectedMenuIds,
  });
  initTreeData();
};

const schema: FormSchemaProps[] = [
  {
    prop: "selectedMenuIds",
    label: "",
    el: "el-tree",
    enum: () => listMenuTreeSelectByApp({ appId: props.appId }),
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
