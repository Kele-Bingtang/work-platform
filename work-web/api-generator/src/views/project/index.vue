<template>
  <div class="projects-container">
    <el-tabs type="border-card" v-model="activeName" class="projects-tabs" :before-leave="switchTab">
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
            <project-card @header-click="handleHeaderClick(item)">
              <template #header>
                <span>{{ item.projectName }}</span>
              </template>
              <div class="base-url">{{ item.baseUrl }}</div>
              <div class="description">{{ item.description }}</div>
              <template #footer>
                <el-button link type="primary" icon="View" @click="handleHeaderClick(item)"></el-button>
                <el-button link type="danger" icon="Delete" @click="removeProject(item)"></el-button>
                <el-button link type="warning" icon="Setting" @click="editProject(item)"></el-button>
              </template>
            </project-card>
          </el-col>
          <el-col :xs="12" :sm="12" :md="6" :lg="6" :xl="4">
            <project-card :only-body="true" class="plus-project" @click="addProject">
              <el-icon class="action"><Plus /></el-icon>
            </project-card>
          </el-col>
        </el-row>
      </el-tab-pane>
    </el-tabs>

    <el-dialog draggable :title="dialogTitle[operateStatus]" v-model="dialogVisible" width="30%">
      <el-form ref="projectFormRef" :model="projectForm" :rules="projectRules" label-width="110px">
        <el-form-item label="项目名称：" prop="projectName">
          <el-input v-model.trim="projectForm.projectName" placeholder="请输入项目名称"></el-input>
        </el-form-item>
        <el-form-item label="接口基础路径" prop="baseUrl" class="item-base-url">
          <el-tooltip effect="dark" content="" placement="right">
            <template #content>
              <div style="line-height: 1.5715">
                接口基础路径为 '/' 开头有字母或 数
                <br />
                字 组成的字符串如：'/shop01' 不能
                <br />
                有多个 '/'
              </div>
            </template>
            <el-icon><QuestionFilled /></el-icon>
          </el-tooltip>
          ：
          <el-input v-model.trim="projectForm.baseUrl" placeholder="请输入接口基础路径" style="width: 384px"></el-input>
        </el-form-item>
        <el-form-item label="项目描述：" prop="description">
          <el-input
            type="textarea"
            :autosize="{ minRows: 4 }"
            v-model="projectForm.description"
            placeholder="请输入项目描述"
          ></el-input>
        </el-form-item>
        <el-form-item label="数据库：" prop="databaseName">
          <el-select v-model="projectForm.databaseName" filterable clearable placeholder="请选择接表名">
            <el-option v-for="item in databaseNameList" :key="item" :label="item" :value="item"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button v-waves @click="dialogVisible = false">取 消</el-button>
          <el-button v-waves type="primary" @click="operateProjectConfirm(operateStatus)">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script lang="ts">
export interface CommonTab {
  id?: number;
  label: string;
  name: string;
}
</script>

<script setup lang="ts" name="Project">
import { ProjectCard } from "@/components";
import {
  defaultProjectData,
  deleteProject,
  insertProject,
  queryDatabaseName,
  queryProjectListOwner,
  updateProject,
  type ProjectModule,
} from "@/api/project";
import { useUserStore, useLayoutStore, useDataStore } from "@/stores";
import { ElMessageBox, ElNotification, type FormInstance, type TabPaneName } from "element-plus";

type Project = ProjectModule.Project;

type UserProjectSearch = ProjectModule.UserProjectSearch;

const dialogTitle: { [propName: string]: string } = {
  add: "添加项目",
  edit: "编辑项目",
};
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

const projectRules = {
  projectName: [{ required: true, message: "请输入项目名称", trigger: "change" }],
  baseUrl: [{ required: true, message: "请输入接口基础路径", trigger: "change" }],
  description: [{ required: true, message: "请输入项目描述", trigger: "change" }],
};

const router = useRouter();
const userStore = useUserStore();
const layoutStore = useLayoutStore();
const dataStore = useDataStore();

const activeName = ref("all");
const dialogVisible = ref(false);
const operateStatus = ref<"add" | "edit" | "">("");
const projectList = ref<Project[]>([]);
const projectForm = ref({ ...defaultProjectData });
const databaseNameList = ref<string[]>([]);
const projectFormRef = shallowRef<FormInstance>();

onMounted(() => {
  initProject();
});

const initProject = (condition?: UserProjectSearch) => {
  queryProjectListOwner(condition).then(res => {
    if (res.status === "success") {
      projectList.value = res.data;
    }
  });
};

const initDatabaseName = () => {
  if (!databaseNameList.value.length) {
    queryDatabaseName("my").then(res => {
      databaseNameList.value = res.data;
    });
  }
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
  dialogVisible.value = true;
  operateStatus.value = "add";
  initDatabaseName();
  nextTick(() => {
    projectFormRef.value?.clearValidate();
  });
};

const operateProjectConfirm = (status: "add" | "edit" | "") => {
  projectFormRef.value?.validate(valid => {
    if (valid) {
      const project = { ...projectForm.value };
      const secretKey = project.secretKey || "";
      delete project.secretKey;
      project.modifyUser = userStore.userInfo.username;
      if (status === "add") {
        project.createUser = userStore.userInfo.username;
        insertProject(project as Project, secretKey).then(res => {
          if (res.status === "success") {
            ElNotification.success("新增成功！");
            initProject();
          }
          dialogVisible.value = false;
        });
      } else {
        updateProject(project as Project, secretKey).then(res => {
          if (res.status === "success") {
            ElNotification.success("更新成功！");
            initProject();
          }
          dialogVisible.value = false;
        });
      }
    } else {
      return false;
    }
  });
};
const editProject = (project: Project) => {
  dialogVisible.value = true;
  operateStatus.value = "edit";
  const { id, projectName, baseUrl, description, databaseName, secretKey } = project;
  projectForm.value = { id, projectName, baseUrl, description, databaseName, secretKey };
  initDatabaseName();
  nextTick(() => {
    projectFormRef.value?.clearValidate();
  });
};

const handleHeaderClick = (project: Project) => {
  const { id, projectName, secretKey, baseUrl } = project;
  if (id) {
    dataStore.updateProject(project);
    router.push({
      path: `/project/details/${projectName}/${secretKey}`,
      query: {
        baseUrl: baseUrl,
      },
    });
  }
};

const removeProject = (project: Project) => {
  ElMessageBox.confirm("此操作将永久删除该项目, 是否继续?", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(() => {
      const { projectName, secretKey } = project;
      deleteProject(project, secretKey).then(async res => {
        if (res.data) {
          ElNotification.success("删除成功！");
          await layoutStore.removeBatchTab([`/project/details/${projectName}/${secretKey}`]);
          initProject();
        }
      });
    })
    .catch();
};
</script>

<style lang="scss" scoped>
.projects-container {
  .projects-tabs {
    width: 100%;
    height: 100%;

    .plus-project {
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

    .base-url {
      margin-top: 0;
      margin-bottom: 10px;
      overflow: hidden;
      font-size: 16px;
      font-weight: 500;
      color: rgb(0 0 0 / 85%);
      text-overflow: ellipsis;
      white-space: nowrap;
    }

    .description {
      display: -webkit-box;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 3;
      overflow: hidden;
      color: #999999;
    }
  }
}
</style>

<style lang="scss">
.projects-container {
  .el-dialog {
    .item-base-url label {
      padding-right: 5px;
    }
  }
}
</style>
