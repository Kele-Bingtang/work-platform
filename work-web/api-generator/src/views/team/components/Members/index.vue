<template>
  <div :class="prefixClass">
    <ProTable
      :request-api="listMembersPage"
      :initRequestParam
      :columns
      :search-cols="{ xs: 1, sm: 1, md: 2, lg: 5, xl: 5 }"
      :border="false"
    >
      <template #tableHeader>
        <el-button type="primary" :icon="Plus" @click="handleInvite">邀请成员</el-button>
      </template>
    </ProTable>
  </div>
</template>

<script setup lang="tsx" name="Members">
import { useDesign } from "@work/hooks";
import { ProTable, ProForm, type TableColumnProps, useDialog, type FormSchemaProps } from "work";
import { listMembersPage, type TeamMember } from "@/api/teamMember";
import { useDictStore } from "@/stores";
import { Setting, Plus } from "@element-plus/icons-vue";
import { ElButton } from "element-plus";
import ProjectMemberRole, { type ProjectRole } from "./ProjectMemberRole.vue";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("members");

const { open } = useDialog();

const { getDictData } = useDictStore();

const route = useRoute();

const initRequestParam = {
  teamId: route.meta.params?.teamId,
};

const columns: TableColumnProps<TeamMember.TeamMemberInfo>[] = [
  {
    prop: "userId",
    label: "成员 ID",
    isShow: false,
  },
  {
    prop: "username",
    label: "成员",
    search: {
      el: "el-input",
    },
  },
  {
    prop: "nickname",
    label: "团队内昵称",
    search: {
      el: "el-input",
    },
  },
  {
    prop: "status",
    label: "状态",
    enum: () => getDictData("sys_normal_status"),
    fieldNames: { value: "dictValue", label: "dictLabel" },
    search: {
      el: "el-select",
    },
  },
  {
    prop: "teamRole",
    label: "成员权限",
    tag: true,
    enum: () => getDictData("team_role"),
    fieldNames: { value: "dictValue", label: "dictLabel" },
    search: {
      el: "el-select",
    },
  },
  {
    prop: "operation",
    label: "设置",
    render: ({ row }) => {
      return (
        <ElButton
          icon={Setting}
          link
          type="primary"
          onClick={() => handleSetting(row)}
          disabled={row.teamRole !== 1 && row.teamRole !== 2}
        />
      );
    },
  },
];

const model = reactive<Record<string, any>>({});
const projectMemberRole = ref<ProjectRole[]>([]);

const schema: FormSchemaProps[] = [
  {
    prop: "username",
    label: computed(() => model.username),
    el: "el-divider",
  },
  {
    prop: "teamRole",
    label: "团队权限",
    el: "el-select",
    props: { disabled: computed(() => model.teamRole === "所有者") },
    enum: () => getDictData("team_role"),
    fieldNames: { value: "dictValue", label: "dictLabel" },
    valueFormat: "string",
    formItem: { rules: [{ required: true, message: "请选择 团队权限" }] },
  },
  {
    prop: "nickname",
    label: "团队内昵称",
    el: "el-input",
  },
  {
    prop: "projectRoleDivider",
    label: "项目权限",
    el: "el-divider",
    props: {
      contentPosition: "left",
    },
  },
  {
    prop: "projectRole",
    label: "",
    render: () => <ProjectMemberRole userId={model.userId} onChange={handleProjectRoleChange} />,
  },
];

const handleSetting = (row: TeamMember.TeamMemberInfo) => {
  model.userId = row.userId;
  model.username = row.username;
  model.nickname = row.nickname;
  model.teamRole = row.teamRole;
  open({
    title: "设置成员角色",
    onConfirm: settingConfirm,
    render: () => {
      return <ProForm v-model={model} schema={schema} colRow dynamicModel={false} />;
    },
  });
};

const handleProjectRoleChange = (projectRole: ProjectRole[]) => {
  projectMemberRole.value = projectRole;
};

const settingConfirm = () => {
  const data = {
    projectInfo: unref(model),
    projectMemberRole: unref(projectMemberRole),
  };
  console.log(data);
};

const handleInvite = () => {
  console.log("TODO 邀请成员");
};
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-members;

.#{$prefix-class} {
  flex: 1;

  :deep(.k-search-form) {
    padding: 10px 0;
    margin: 0;
    border: none;
    box-shadow: none;
  }

  :deep(.k-pro-table__main) {
    padding: 0;
    border: none;
    box-shadow: none;
  }
}
</style>
