<template>
  <div :class="prefixClass">
    <ProTable
      ref="proTableRef"
      :request-api="listMembersPage"
      :initRequestParam
      :columns
      :search-cols="{ xs: 1, sm: 1, md: 2, lg: 5, xl: 5 }"
      :border="false"
    >
      <template #tableHeader>
        <el-button type="primary" :icon="Plus" @click="handleInvite" :disabled="teamRole === '普通成员'">
          邀请成员
        </el-button>
      </template>
    </ProTable>
  </div>
</template>

<script setup lang="tsx" name="Members">
import { useDesign } from "@work/hooks";
import { ProTable, ProForm, Point, type TableColumnProps, useDialog, type FormSchemaProps, message } from "work";
import { editTeamMember, listMembersPage, inviteMembers, listMembers, type TeamMember } from "@/api/teamMember";
import { useDictStore, useUserStore } from "@/stores";
import { Setting, Plus } from "@element-plus/icons-vue";
import { ElButton } from "element-plus";
import ProjectMemberRole, { type ProjectRole } from "./ProjectMemberRole.vue";
import { listByKeyword, type User } from "@/api/user";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("members");

const { open } = useDialog();

const { getDictData } = useDictStore();

const route = useRoute();

const initRequestParam = { teamId: route.meta.params?.teamId };
const proTableRef = ref<InstanceType<typeof ProTable>>();

const teamRole = computed(() => route.meta.params?.teamRole);

const statusColor = {
  0: "var(--el-color-info)",
  1: "var(--el-color-primary)",
};

const columns: TableColumnProps[] = [
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
    search: { el: "el-select" },
    render: ({ row }) => <Point color={statusColor[row.status]} text={row._enum.status} />,
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
          disabled={unref(teamRole) === "普通成员"}
        />
      );
    },
  },
];

const model = reactive<Record<string, any>>({});
const projectMemberList = ref<ProjectRole[]>([]);

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
    height: "auto",
    maxHeight: 700,
    onConfirm: settingConfirm,
    render: () => {
      return <ProForm v-model={model} schema={schema} colRow dynamicModel={false} />;
    },
  });
};

const handleProjectRoleChange = (projectRole: ProjectRole[]) => {
  projectMemberList.value = projectRole;
};

const settingConfirm = async () => {
  const form = { ...unref(model) };

  delete form.username;
  delete form.projectRoleDivider;
  const data = {
    teamMember: { ...form, teamId: route.meta.params?.teamId },
    projectMemberList: unref(projectMemberList),
  };
  const res = await editTeamMember(data as any);
  if (res.code === 200) {
    unref(proTableRef)?.getTableList();
    return message.success("修改成功");
  }
};

const memberInfo = ref<TeamMember.TeamMemberInfo[]>([]);
const loading = ref(false);
const optionList = ref<User.UserInfo[]>([]);
const memberInTeamInfo = ref<TeamMember.TeamMemberInfo[]>([]);
const handleInvite = async () => {
  if (!unref(memberInTeamInfo).length) {
    // 获取成员信息
    const res = await listMembers({ teamId: route.meta.params?.teamId });
    if (res.code === 200) memberInTeamInfo.value = res.data;
  }
  open({
    title: "邀请成员",
    height: 200,
    onConfirm: inviteMemberList,
    render: () => {
      return (
        <el-select
          v-model={memberInfo.value}
          placeholder="请选择成员"
          multiple
          clearable
          filterable
          remote
          remote-method={remoteMethod}
          loading={loading.value}
          collapse-tags
          collapse-tags-tooltip
          max-collapse-tags={4}
        >
          {unref(optionList).map(item => {
            const disabled = !!unref(memberInTeamInfo).find(member => member.userId === item.userId);
            const label = item.nickname || item.username;
            return (
              <el-option
                label={label}
                value={{
                  userId: item.userId,
                  username: item.username,
                  nickname: item.nickname,
                  teamId: route.meta.params?.teamId,
                }}
                disabled={disabled}
              >
                {item.username} {item.nickname} {disabled ? "（已在团队内）" : ""}
              </el-option>
            );
          })}
        </el-select>
      );
    },
  });
};

const remoteMethod = async (keyword: string) => {
  if (keyword) {
    loading.value = true;
    const res = await listByKeyword(keyword);
    if (res.code === 200) optionList.value = res.data;
    loading.value = false;
  } else optionList.value = [];
};

const inviteMemberList = async () => {
  if (!unref(memberInfo).length) {
    message.error("请选择成员");
    return false;
  }
  const res = await inviteMembers(unref(memberInfo), useUserStore().userInfo.userId);
  if (res.code === 200) {
    unref(proTableRef)?.getTableList();
    return message.success("邀请成功");
  }
  return message.error("邀请失败");
};
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-members;

.#{$prefix-class} {
  flex: 1;

  :deep(.#{$admin-namespace}-search-form) {
    padding: 0;
    margin: 0;
    border: none;
    box-shadow: none;
  }

  :deep(.#{$admin-namespace}-pro-table__main) {
    padding: 0;
    border: none;
    box-shadow: none;
  }
}
</style>
