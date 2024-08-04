<template>
  <el-card style="margin-bottom: 20px" class="user-card-container">
    <template #header>
      <span>个人中心</span>
    </template>

    <div class="user-card-header">
      <div class="header-content">
        <slot>
          <el-image :src="user.avatar" class="user-avatar" alt="头像">
            <template #error>
              <el-image :src="defaultAvatar" class="user-avatar" alt="头像" />
            </template>
          </el-image>
        </slot>
      </div>
      <div class="header-content">
        <div class="user-name">{{ user.username }} {{ user.nickname }}</div>
        <div class="user-role">
          {{ getUserRoles() }}
        </div>
      </div>
    </div>

    <div class="user-card-info">
      <div class="user-card-info-section">
        <div class="user-card-info-section-header">
          <span>基本信息</span>
        </div>
        <div class="user-card-info-section-body">
          <div class="basic-info">
            <div class="basic-info-item" v-for="item in basicInfo" :key="item.name">
              <span>{{ item.name }}</span>
              <div class="basic-info-content">{{ item.content }}</div>
            </div>
          </div>
        </div>
      </div>

      <div class="user-skills user-card-info-section">
        <div class="user-card-info-section-header">
          <span>核心技术栈</span>
        </div>
        <div class="user-card-info-section-body">
          <div class="progress-item">
            <span>Java（希望）</span>
            <el-progress :percentage="100" class="progress-content" />
          </div>
          <div class="progress-item">
            <span>Spring</span>
            <el-progress :percentage="55" class="progress-content" />
          </div>
          <div class="progress-item">
            <span>Docker</span>
            <el-progress :percentage="44" class="progress-content" />
          </div>
          <div class="progress-item">
            <span>Web（前端）</span>
            <el-progress :percentage="67" class="progress-content" />
          </div>
          <div class="progress-item">
            <span>Vue</span>
            <el-progress :percentage="57" class="progress-content" />
          </div>
          <div class="progress-item">
            <span>React</span>
            <el-progress :percentage="36" class="progress-content" />
          </div>
          <div class="progress-item">
            <span>SQL</span>
            <el-progress :percentage="39" class="progress-content" />
          </div>
          <div class="progress-item">
            <span>Git</span>
            <el-progress :percentage="76" class="progress-content" />
          </div>
        </div>
      </div>
    </div>
  </el-card>
</template>

<script setup lang="ts" name="UserCard">
import { useDictStore, type UserInfo } from "@/stores";
import defaultAvatar from "@work/static/images/default.png";

const props = defineProps<{ user: UserInfo; roles: string[] }>();
const { user } = toRefs(props);

const getUserRoles = () => {
  let userRole = "";
  const separator = " | ";
  props.roles?.forEach(role => {
    userRole = userRole + separator + role;
  });
  return userRole.slice(separator.length);
};

const sexLabel = ref("");

onMounted(async () => {
  const res = await useDictStore().getDictData("sys_user_sex");
  if (Array.isArray(res.data)) {
    const label = res.data.filter(item => item.dictValue === user.value.sex + "");
    if (label.length) sexLabel.value = label[0].dictLabel;
  }
});

const basicInfo = computed(() => [
  { name: "用户名称", content: user.value.username },
  { name: "用户昵称", content: user.value.nickname },
  { name: "用户性别", content: sexLabel.value },
  { name: "联系方式", content: user.value.phone },
  { name: "用户邮箱", content: user.value.email },
  { name: "用户角色", content: getUserRoles() },
  { name: "注册时间", content: user.value.registerTime },
]);
</script>

<style lang="scss" scoped>
.user-card-container {
  .user-card-header {
    text-align: center;

    .header-content {
      .user-avatar {
        width: 100px;
        height: 100px;
        border-radius: 50%;
      }

      .user-name {
        font-weight: bold;
      }

      .user-role {
        padding-top: 10px;
        font-size: 14px;
        font-weight: 400;
        color: #666666;
      }
    }
  }

  .user-card-info {
    margin-top: 20px;
    color: #000000;

    span {
      padding-left: 4px;
    }

    .user-card-info-section:first-child {
      padding-top: 0;
    }

    .user-card-info-section {
      padding: 15px 0;
      font-size: 14px;

      .user-card-info-section-header {
        padding-bottom: 10px;
        margin-bottom: 10px;
        font-weight: bold;
        border-bottom: 1px solid #dcdfe6;
      }

      .user-card-info-section-body {
        .basic-info {
          padding-right: 0;
          padding-left: 0;
          border-right: 0;
          border-left: 0;
          border-radius: 0;
        }

        .basic-info-item {
          padding: 11px 0;
          margin-bottom: -1px;
          font-size: 13px;
          border-top: 1px solid #e7eaec;
          border-bottom: 1px solid #e7eaec;

          .basic-info-content {
            float: right;
          }
        }

        .basic-info-item:first-child {
          padding-top: 5px;
          border-top: none;
        }

        .progress-item {
          padding-bottom: 11px;

          .progress-content {
            float: right;
            width: 65%;
          }
        }
      }
    }
  }
}
</style>
