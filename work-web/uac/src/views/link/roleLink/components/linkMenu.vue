<template>
  <el-button type="primary" @click="handleEdit" style="margin-bottom: 10px">编辑</el-button>
  <Tree :data="data" node-key="value" checkbox search :select="false" />

  <el-dialog v-model="dialogVisible" title="编辑菜单" width="500" @close="handleCancel">
    <ProForm v-if="dialogVisible" v-model="form" :options="options" />
    <template #footer>
      <el-button @click="handleCancel">取消</el-button>
      <el-button type="primary" @click="handleConfirm">确定</el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="ts" name="LinkMenu">
import { listMenuListByRoleId, listMenuIdsByRoleId, listMenuTreeSelectByApp, type Menu } from "@/api/system/menu";
import { ProForm, Tree } from "work";
import type { FormOptionsProps } from "@work/components";
import { editOne } from "@/api/system/role";

export interface LinkUserProps {
  appId: string;
  id: number;
  roleId: string;
}

const props = defineProps<LinkUserProps>();

const data = ref<Menu.MenuInfo[]>([]);
const dialogVisible = ref(false);
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

const handleEdit = () => {
  dialogVisible.value = true;
};

const handleCancel = () => {
  dialogVisible.value = false;
  form.value = { selectedMenuIds: undefined };
};

const handleConfirm = async () => {
  dialogVisible.value = false;
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
