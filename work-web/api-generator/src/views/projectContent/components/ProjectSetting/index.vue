<template>
  <el-space fill :class="`${prefixClass} w-full`">
    <el-descriptions title="基础信息" :column="1">
      <template #extra>
        <el-button plain auto-insert-space :disabled="!isProjectOwner" @click="handleEdit">编辑</el-button>
      </template>
      <el-descriptions-item
        v-for="item in baseInfo"
        :key="item.label"
        :label="item.label"
        label-class-name="w-125 "
        class-name="flex-1 flx-justify-between"
      >
        <span>{{ item.value }}</span>
      </el-descriptions-item>
    </el-descriptions>

    <!-- <el-descriptions title="基础信息" :column="1">
      <el-descriptions-item label="克隆项目" label-class-name="w-125 " class-name="flex-1 flx-justify-between">
        <span class="text-gray-400">克隆项目到当前团队或其他团队</span>
        <el-button plain auto-insert-space>克隆</el-button>
      </el-descriptions-item>
    </el-descriptions> -->

    <el-descriptions :column="1">
      <template #title>
        <el-space :size="5" class="text-danger">
          <el-icon><WarnTriangleFilled /></el-icon>
          <span>危险操作区域</span>
        </el-space>
      </template>

      <template v-if="isProjectOwner">
        <el-descriptions-item label="移动项目" label-class-name="w-125 fz-15" class-name="flex-1 flx-justify-between">
          <span class="text-gray-400">将项目移动到其他团队</span>
          <el-button plain auto-insert-space @click="handleTransferProject">移动</el-button>
        </el-descriptions-item>

        <el-descriptions-item label="删除项目" label-class-name="w-125 fz-15" class-name="flex-1 flx-justify-between">
          <span class="text-gray-400">务必谨慎，删除后项目不可以找回</span>
          <el-button plain auto-insert-space @click="handleRemoveProject">删除</el-button>
        </el-descriptions-item>
      </template>
    </el-descriptions>
  </el-space>
</template>

<script setup lang="tsx" name="ProjectSetting">
import { useDialog, ProForm, message, HOME_NAME } from "work";
import { useDesign, useHandleData } from "@work/hooks";
import { WarnTriangleFilled } from "@element-plus/icons-vue";
import { ProjectKey, ProjectOnGetKey } from "@/config/symbol";
import { rules, schema } from "@/views/team/components/Project/formSchema";
import { editProject, removeProject, type Project } from "@/api/project";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("project-setting");

const { open } = useDialog();

const projectInfo = inject(ProjectKey);
const projectOnGetFunction = inject(ProjectOnGetKey);
const formModel = ref<Record<string, any>>({});
const isProjectOwner = ref(true);

const baseInfo = computed(() => [
  { label: "项目 ID", value: unref(projectInfo)?.projectId },
  { label: "项目名称", value: unref(projectInfo)?.projectName },
  { label: "项目 URL", value: unref(projectInfo)?.baseUrl },
  { label: "项目简介", value: unref(projectInfo)?.description },
]);

const handleEdit = () => {
  formModel.value = { ...unref(projectInfo) };
  open({
    title: "编辑项目信息",
    height: 300,
    onConfirm: async () => {
      const res = await editProject({ ...unref(formModel) } as Project.ProjectInsert);
      if (res.code === 200) {
        projectOnGetFunction();
        return message.success("编辑成功");
      }
    },
    render: () => (
      <ProForm
        v-model={formModel.value}
        el-form-props={{ rules }}
        schema={schema}
        col-row
        includeModelKeys={["id", "projectId"]}
      />
    ),
  });
};

const handleTransferProject = () => {};

const router = useRouter();
const handleRemoveProject = (item: Project.ProjectInfo) => {
  useHandleData("此操作将永久删除该项目，是否继续？", async () => {
    const res = await removeProject({ projectId: item.projectId });
    if (res.code === 200) {
      router.push({ name: HOME_NAME });
      return message.success("删除成功");
    }
  });
};
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-project-setting;

.#{$prefix-class} {
  padding: 20px;
  background-color: #ffffff;

  :deep(.el-descriptions__title) {
    font-size: 14px;
  }

  :deep(.el-descriptions__cell) {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 45px;
  }
}
</style>
