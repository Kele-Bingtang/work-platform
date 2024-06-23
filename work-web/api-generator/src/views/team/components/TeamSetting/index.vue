<template>
  <el-space fill :class="`${prefixClass} w-full`">
    <el-descriptions title="基础信息" :column="1">
      <el-descriptions-item label="团队名称" label-class-name="w-125 " class-name="flex-1 flx-justify-between">
        <span>{{ teamName }}</span>
        <el-button v-if="isTeamOwner || isTeamAdmin" plain auto-insert-space @click="handleEdit">编辑</el-button>
      </el-descriptions-item>
    </el-descriptions>

    <el-descriptions :column="1">
      <template #title>
        <el-space :size="5" class="text-danger">
          <el-icon><WarnTriangleFilled /></el-icon>
          <span>危险操作区域</span>
        </el-space>
      </template>

      <template v-if="isTeamOwner">
        <el-descriptions-item label="移交团队" label-class-name="w-125 fz-15" class-name="flex-1 flx-justify-between">
          <span class="text-gray-400">将团队的所有者权限移交给其他成员</span>
          <el-button plain auto-insert-space @click="handleTransfer">移交</el-button>
        </el-descriptions-item>

        <el-descriptions-item label="解散团队" label-class-name="w-125 fz-15" class-name="flex-1 flx-justify-between">
          <span class="text-gray-400">务必谨慎，解散后无法找回</span>
          <el-button plain auto-insert-space @click="handleRemoveTeam">解散</el-button>
        </el-descriptions-item>
      </template>

      <template v-else>
        <el-descriptions-item label="退出团队" label-class-name="w-125 fz-15" class-name="flex-1 flx-justify-between">
          <span class="text-gray-400">退出当前所在团队</span>
          <el-button plain auto-insert-space @click="handleExitTeam">退出</el-button>
        </el-descriptions-item>
      </template>
    </el-descriptions>
  </el-space>
</template>

<script setup lang="tsx" name="TeamSetting">
import { WarnTriangleFilled } from "@element-plus/icons-vue";
import { useDesign } from "@work/hooks";
import { useDialog, ProForm, message, type FormSchemaProps, HOME_NAME } from "work";
import { editTeam, removeTeam, transferOwner } from "@/api/team";
import { useRoutes } from "@/hooks";
import { useUserStore } from "@/stores";
import { listMembers, leaveTeam } from "@/api/teamMember";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("team-setting");

const { open } = useDialog();

const route = useRoute();
const router = useRouter();
// 编辑团队的表单
const teamModel = reactive<{ teamName: string }>({ teamName: route.meta.params?.teamName });
// 团队名称
const teamName = ref(route.meta.params?.teamName || "--");
// 缓存请求的成员下拉信息
const members = ref<{ value: string; label: string; disabled: boolean }[]>([]);

// 是否是团队所有者
const isTeamOwner = computed(() => route.meta.params?.teamRole === "所有者");
// 是否是团队管理者
const isTeamAdmin = computed(() => route.meta.params?.teamRole === "管理者");

/**
 * 修改按钮事件
 */
const handleEdit = () => {
  open({
    title: "修改团队",
    width: 400,
    height: 80,
    render: () => (
      <ProForm
        v-model={teamModel}
        schema={[{ prop: "teamName", label: "团队名称", el: "el-input" }]}
        formItem={{ rules: [{ required: true, message: "请输入 团队名称" }] }}
        colRow
      />
    ),
    onConfirm: editConfirm,
  });
};

/**
 * 执行修改功能
 */
const editConfirm = async () => {
  const id = route.meta.params?.id;
  if (!id) return message.warning("编辑失败，项目不存在");
  const res = await editTeam({ ...unref(teamModel), id });

  if (res.code === 200) {
    useRoutes().initDynamicRouters(useUserStore().roles);
    teamName.value = teamModel.teamName;
    message.success("编辑成功");
    return true;
  }
};

/**
 * 转移团队表单配置项
 */
const transferSchema: FormSchemaProps[] = [
  {
    prop: "ownerName",
    label: "接收人",
    el: "el-select",
    formItem: { rules: [{ required: true, message: "请选择 接收人" }] },
    enum: async () => {
      if (unref(members).length) return { data: unref(members) };

      const res = await listMembers({ teamId: route.meta.params?.teamId });
      res.data?.forEach(item => {
        members.value.push({
          value: `${item.userId}-${item.username}`,
          label: item.nickname ? `${item.nickname}(@${item.username})` : `${item.username}(@${item.username})`,
          disabled: useUserStore().userInfo.userId === item.userId,
        });
      });
      return { data: unref(members) };
    },
  },
];

/**
 * 转移团队表表单
 */
const transferModel = reactive<{ ownerName?: string }>({});

/**
 * 转移按钮事件
 */
const handleTransfer = async () => {
  if (!route.meta.params?.teamId) return message.warning("项目不存在");

  open({
    title: "移交团队",
    width: 400,
    height: 80,
    render: () => <ProForm v-model={transferModel} schema={transferSchema} colRow />,
    onConfirm: transferConfirm,
  });
};

/**
 * 执行转移功能
 */
const transferConfirm = async () => {
  const teamId = route.meta.params?.teamId;
  if (!teamId) return message.warning("项目不存在");

  const userInfo = transferModel.ownerName?.split("-");
  if (userInfo?.length !== 2) return message.warning("请选择接收人");

  const res = await transferOwner({ teamId, userId: userInfo[0], username: userInfo[1] });
  if (res.code === 200) {
    useRoutes().initDynamicRouters(useUserStore().roles);
    message.success("移交成功");
    return true;
  }
};

const confirmInput = ref("");

/**
 * 退出按钮事件
 */
const handleExitTeam = () => {
  if (!route.meta.params?.teamId) return message.warning("项目不存在");

  open({
    title: "危险提醒",
    width: 600,
    height: 150,
    confirmLabel: "我已了解后果，确定退出",
    render: () => (
      <el-space fill>
        <div>退出「{route.meta.params?.teamName}」团队后，您将失去该团队内所有项目的访问权限。</div>
        <div class="flx-center">
          <span>请输入团队名确定操作：</span>
          <el-input v-model={confirmInput.value} class="flex-1" />
        </div>
      </el-space>
    ),
    onConfirm: exitTeamConfirm,
  });
};

/**
 * 执行退出项目功能
 */
const exitTeamConfirm = async () => {
  const teamId = route.meta.params?.teamId;
  if (!teamId) return message.warning("项目不存在");

  if (unref(confirmInput) !== route.meta.params?.teamName) return message.warning("团队名称不正确");

  const res = await leaveTeam(teamId);
  if (res.code === 200) {
    router.push({ name: HOME_NAME });
    useRoutes().initDynamicRouters(useUserStore().roles);
    message.success("退出成功");
    return true;
  }
};

/**
 * 解散按钮事件
 */
const handleRemoveTeam = () => {
  if (!route.meta.params?.teamId) return message.warning("项目不存在");

  open({
    title: "危险提醒",
    width: 600,
    height: 150,
    confirmLabel: "我已了解后果，确定解散",
    render: () => (
      <el-space fill>
        <div>解散「{route.meta.params?.teamName}」团队后，该团队下的所有项目都将被同步删除，且不可恢复！</div>
        <div class="flx-center">
          <span>请输入团队名确定操作：</span>
          <el-input v-model={confirmInput.value} class="flex-1" />
        </div>
      </el-space>
    ),
    onConfirm: removeTeamConfirm,
  });
};

/**
 * 执行解散项目功能
 */
const removeTeamConfirm = async () => {
  const teamId = route.meta.params?.teamId;
  if (!teamId) return message.warning("项目不存在");

  if (unref(confirmInput) !== route.meta.params?.teamName) return message.warning("团队名称不正确");

  const res = await removeTeam(teamId);
  if (res.code === 200) {
    router.push({ name: HOME_NAME });
    useRoutes().initDynamicRouters(useUserStore().roles);
    message.success("解散成功");
    return true;
  }
};
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-team-setting;

.#{$prefix-class} {
  :deep(.el-descriptions__title) {
    font-size: 14px;
  }

  :deep(.el-descriptions__cell) {
    display: flex;
    align-items: center;
    justify-content: center;
  }
}
</style>
