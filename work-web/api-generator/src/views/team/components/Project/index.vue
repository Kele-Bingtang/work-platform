<template>
  <div :class="prefixClass">
    <el-tabs type="border-card" v-model="activeName" :class="`${prefixClass}__tabs`" :before-leave="switchTab">
      <el-tab-pane v-for="item in tabs" :key="item.name" :label="item.label" :name="item.name" :lazy="true">
        <el-row :gutter="10">
          <el-col
            :xs="12"
            :sm="12"
            :md="6"
            :lg="6"
            :xl="4"
            v-for="item in projectList"
            :key="item.id"
            style="margin-bottom: 10px"
          >
            <ProjectCard @header-click="handleHeaderClick(item)">
              <template #header>
                <span>{{ item.projectName }}</span>
              </template>
              <div :class="`${prefixClass}__url`">{{ item.baseUrl }}</div>
              <div :class="`${prefixClass}__description`">{{ item.description }}</div>
              <template #footer>
                <el-button link type="primary" :icon="View" @click="handleHeaderClick(item)"></el-button>
                <el-button link type="danger" :icon="Delete" @click="removeProject(item)"></el-button>
                <el-button link type="warning" :icon="Setting" @click="editProject(item)"></el-button>
              </template>
            </ProjectCard>
          </el-col>
          <el-col :xs="12" :sm="12" :md="6" :lg="6" :xl="4">
            <project-card :only-body="true" :class="`${prefixClass}__plus`" @click="addProject">
              <el-icon class="action"><Plus /></el-icon>
            </project-card>
          </el-col>
        </el-row>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup lang="tsx" name="Project">
import { ProjectCard } from "@/components";
import { queryProjectListOwner, type ProjectModule } from "@/api/project";
import { type TabPaneName } from "element-plus";
import { View, Delete, Setting, Plus } from "@element-plus/icons-vue";
import { useDesign } from "@work/hooks";
import { useDialog, ProForm } from "work";
import { schema } from "./formSchema";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("project");

const { open } = useDialog();

export interface CommonTab {
  id?: number;
  label: string;
  name: string;
}

type Project = ProjectModule.Project;

type UserProjectSearch = ProjectModule.UserProjectSearch;

const tabs: CommonTab[] = [
  {
    label: "我的项目",
    name: "all",
  },
  {
    label: "我创建的",
    name: "create",
  },
  {
    label: "我加入的",
    name: "join",
  },
];

const activeName = ref("all");
const projectList = ref<Project[]>([]);
const model = ref<Record<string, any>>();

const dialogTitle: { [propName: string]: string } = {
  add: "添加项目",
  edit: "编辑项目",
};

onMounted(() => {
  // initProject();
});

const initProject = (condition?: UserProjectSearch) => {
  queryProjectListOwner(condition).then(res => {
    if (res.status === "success") {
      projectList.value = res.data;
    }
  });
};

const switchTab = (activeName: TabPaneName, oldActiveName: TabPaneName) => {
  if (activeName === oldActiveName) {
    return;
  }
  if (activeName === "create") {
    initProject({ enterType: 0 });
  } else if (activeName === "join") {
    initProject({ enterType: 1 });
  } else {
    initProject();
  }
};

const addProject = () => {
  open({
    title: dialogTitle["add"],
    height: 300,
    render: () => <ProForm vModel={model.value} schema={schema} row-props={{ col: { span: 24 } }} />,
  });
};

const handleHeaderClick = (item: Project) => {};
const removeProject = (item: Project) => {};
const editProject = (item: Project) => {};
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-project;

.#{$prefix-class} {
  &__tabs {
    width: 100%;
    height: 100%;

    .#{$prefix-class}__plus {
      line-height: 220px;
      color: #999999;
      text-align: center;
      cursor: pointer;

      &:hover {
        background-color: #f2f2f2;
      }

      .action {
        display: inline-block;
        font-size: 60px;
        font-style: normal;
        color: inherit;
        text-align: center;
        text-transform: none;
        text-rendering: optimizelegibility;
        -webkit-font-smoothing: antialiased;
      }
    }

    .#{$prefix-class}__url {
      margin-top: 0;
      margin-bottom: 10px;
      overflow: hidden;
      font-size: 16px;
      font-weight: 500;
      color: rgb(0 0 0 / 85%);
      text-overflow: ellipsis;
      white-space: nowrap;
    }

    .#{$prefix-class}__description {
      display: -webkit-box;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 3;
      overflow: hidden;
      color: #999999;
    }
  }

  :deep(.el-dialog .item-base-url label) {
    padding-right: 5px;
  }
}
</style>
