<template>
  <div :class="prefixClass">
    <ProSearch v-model="searchModel" :schema="searchSchema" @search="handleSearch" @reset="handleReset" />
    <el-tabs type="border-card" v-model="activeName" @tab-change="switchTab">
      <el-tab-pane v-for="item in tabs" :key="item.name" :label="item.label" :name="item.name" :lazy="true">
        <el-row :gutter="10">
          <el-col :xs="12" :sm="12" :md="6" :lg="6" :xl="4" v-for="item in projectList" :key="item.id">
            <ProjectCard @header-click="handleHeaderClick(item)">
              <template #header>
                <span>{{ item.projectName }}</span>
              </template>
              <div class="mt-0 mb-2.5 font-medium sle">{{ item.baseUrl }}</div>
              <div class="line-clamp-3 text-neutral-400">
                {{ item.description }}
              </div>
              <template #footer>
                <el-button link type="primary" :icon="View" @click="handleHeaderClick(item)"></el-button>
                <el-button link type="danger" :icon="Delete" @click="handleRemoveProject(item)"></el-button>
                <el-button link type="warning" :icon="Setting" @click="handleEditProject(item)"></el-button>
              </template>
            </ProjectCard>
          </el-col>
          <el-col :xs="12" :sm="12" :md="6" :lg="6" :xl="4">
            <project-card
              :only-body="true"
              class="text-neutral-400 cursor-pointer text-center hover:!bg-zinc-200 leading-[220px]"
              @click="handleAddProject"
            >
              <el-icon class="inline-block !text-[60px]"><Plus /></el-icon>
            </project-card>
          </el-col>
        </el-row>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup lang="tsx" name="Project">
import { ProjectCard } from "@/components";
import { listProject, addProject, editProject, removeProject, type Project } from "@/api/project";
import { View, Delete, Setting, Plus } from "@element-plus/icons-vue";
import { useDesign, useHandleData } from "@work/hooks";
import { useDialog, ProForm, ProSearch, message } from "work";
import { rules, schema } from "./formSchema";
import { searchSchema } from "./searchSchema";
import { listSelectDataSource, type DataSource } from "@/api/dataSource";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("project");

const { open } = useDialog();

const route = useRoute();

export interface CommonTab {
  id?: number;
  label: string;
  name: string;
}

// ElTabs 组件配置项
const tabs: CommonTab[] = [
  {
    label: "我的项目",
    name: "all",
  },
  {
    label: "我创建的",
    name: "creator",
  },
  {
    label: "我加入的",
    name: "joiner",
  },
];

// 项目分类
enum belongType {
  all = 0,
  creator = 1,
  joiner = 2,
}

const activeName = ref("all");
const projectList = ref<Project.ProjectInfo[]>([]);
const formModel = ref<Record<string, any>>({});
const searchModel = reactive<Record<string, any>>({});

const dialogTitle: { [propName: string]: string } = {
  add: "添加项目",
  edit: "编辑项目",
};

const teamId = computed(() => route.meta.params?.teamId);

onMounted(() => {
  initProject();
});

// 初始化项目
const initProject = (params?: Project.ProjectSearch) => {
  listProject({ ...params, belongType: belongType[unref(activeName)], teamId: unref(teamId) }).then(res => {
    if (res.code === 200) projectList.value = res.data;
  });
};

// 查询事件
const handleSearch = () => {
  initProject(searchModel);
};

// 重置事件
const handleReset = () => {
  initProject();
};

// 切换标签事件
const switchTab = () => {
  initProject();
};

// 缓存查询的数据源列表
const selectDataSource = ref<DataSource.DataSourceInfo[]>([]);

if (schema[schema.length - 1].prop !== "dataSourceId") {
  schema.push({
    prop: "dataSourceId",
    label: "数据源",
    el: "el-select",
    enum: async () => {
      if (unref(selectDataSource).length) return { data: selectDataSource };
      const res = await listSelectDataSource(unref(teamId));
      selectDataSource.value = res.data;
      return res;
    },
    fieldNames: { value: "dataSourceId", label: "dataSourceName" },
    props: { placeholder: "请选择 数据库" },
  });
}

const dialogForm = (api: (data: any) => Promise<http.Response<string>>, status: string, successMessage: string) => {
  open({
    title: dialogTitle[status],
    height: 300,
    onConfirm: async () => {
      const res = await api({ ...unref(formModel), teamId: unref(teamId) } as Project.ProjectInsert);
      if (res.code === 200) {
        initProject();
        return message.success(successMessage);
      }
    },
    render: () => (
      <ProForm
        v-model={formModel.value}
        el-form-props={{ rules }}
        schema={schema}
        row-props={{ col: { span: 24 } }}
        includeModelKeys={["id", "projectId"]}
      />
    ),
  });
};

const handleAddProject = () => {
  formModel.value = {};
  dialogForm(addProject, "add", "新增成功");
};

const handleEditProject = (item: Project.ProjectInfo) => {
  formModel.value = { ...item };
  dialogForm(editProject, "edit", "编辑成功");
};

const handleRemoveProject = (item: Project.ProjectInfo) => {
  useHandleData("此操作将永久删除该项目，是否继续？", async () => {
    const res = await removeProject({ projectId: item.projectId });
    if (res.code === 200) {
      initProject();
      return message.success("删除成功");
    }
  });
};

const router = useRouter();
const handleHeaderClick = (item: Project.ProjectInfo) => {
  const { projectId, projectName } = item;

  router.push(`/project/${projectId}/${projectName}`);
};
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-project;

.#{$prefix-class} {
  :deep(.k-search-form) {
    padding: 10px 0;
    margin: 0;
    border: none;
    box-shadow: none;
  }

  :deep(.el-dialog .item-base-url label) {
    padding-right: 5px;
  }
}
</style>
