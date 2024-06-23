<template>
  <el-table :data="tableData">
    <el-table-column v-if="false" prop="id" label="项目" />
    <el-table-column prop="projectName" label="项目" />
    <el-table-column prop="projectRole" label="角色">
      <template #default="{ row }">
        <el-select v-model="row.projectRole" @change="handleChange(row)">
          <el-option
            v-for="item in projectRoleEnum"
            :key="item.dictValue"
            :value="item.dictValue"
            :label="item.dictLabel"
          />
        </el-select>
      </template>
    </el-table-column>
  </el-table>
</template>

<script setup lang="ts" name="ProjectMemberRole">
import type { DictData } from "@/api/dictData";
import { useDictStore } from "@/stores";
import { listProjectRole, type ProjectMember } from "@/api/projectMember";

export interface ProjectRole {
  id: number;
  projectRole: string;
}

const props = defineProps<{ userId: string }>();
const emits = defineEmits<{ change: [projectRole: ProjectRole[]] }>();

const { getDictData } = useDictStore();

const route = useRoute();

const projectRoleEnum = ref<DictData.DictDataInfo[]>();
const tableData = ref<ProjectMember.ProjectMemberInfo[]>();

const changeModel = ref<Map<number, ProjectRole>>(new Map());

onMounted(async () => {
  const res = await getDictData("project_role");
  projectRoleEnum.value = res.data;

  // 初始化表格数据
  initTableData();
});

const handleChange = (row: { id: number; projectName: string; projectRole: string }) => {
  const { id, projectRole } = row;
  if (unref(changeModel).has(id)) return;

  unref(changeModel).set(id, { id, projectRole });
  emits("change", [...unref(changeModel).values()]);
};

/**
 * 初始化表格数据
 */
const initTableData = async () => {
  const teamId = route.meta.params?.teamId;
  const res = await listProjectRole(teamId, props.userId);
  tableData.value = res.data;

  tableData.value = [{ id: 1, projectName: "1", projectRole: "1" }];
};
</script>

<style lang="scss" scoped></style>
