<template>
  <el-button type="primary" @click="handleEdit" style="margin-bottom: 10px">编辑</el-button>
  <Tree :data="data" node-key="value" checkbox search :select="false" />
</template>

<script setup lang="tsx" name="LinkMenu">
import { listMenuListByRoleId, listMenuIdsByRoleId, listMenuTreeSelectByApp, type Menu } from "@/api/system/menu";
import { ProForm, Tree, useDialog } from "work";
import type { FormOptionsProps } from "@work/components";
import { editOne } from "@/api/system/role";

export interface LinkUserProps {
  appId: string;
  id: number;
  roleId: string;
}

const props = defineProps<LinkUserProps>();

const data = ref<Menu.MenuInfo[]>([]);
const form = ref<{ selectedMenuIds?: string[] }>({ selectedMenuIds: [] });
const selectedMenuIds = ref<string[]>([]);

const initTreeData = async (appId: string, roleId: string) => {
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
  open({
    title: "编辑菜单",
    onClose: handleCancel,
    onConfirm: handleConfirm,
    render: () => {
      return <ProForm v-model={form.value} options={options} />;
    },
  });
};

const handleCancel = () => {
  form.value = { selectedMenuIds: undefined };
};

const handleConfirm = async () => {
  await editOne({
    id: props.id,
    roleId: props.roleId,
    appId: props.appId,
    selectedMenuIds: form.value.selectedMenuIds,
  });
  initTreeData(props.appId, props.roleId);
};

const options: FormOptionsProps = {
  form: { inline: true, labelPosition: "right", labelWidth: 80, size: "default", fixWidth: true },
  columns: [
    {
      formItem: { label: "", prop: "selectedMenuIds", br: true },
      attrs: {
        el: "el-tree",
        defaultValue: async () => {
          if (!props.appId) return [];
          return selectedMenuIds.value;
        },
        enum: listMenuTreeSelectByApp,
        props: { nodeKey: "value", search: true, checkbox: true },
      },
    },
  ],
};
</script>

<style lang="scss" scoped></style>
