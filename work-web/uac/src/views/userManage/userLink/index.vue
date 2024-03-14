<template>
  <div class="userLink-container">
    <TreeFilter
      ref="treeFilterRef"
      title="用户列表"
      :requestApi="list"
      @change="handleTreeChange"
      id="userId"
      label="username"
      :enable-total="false"
      style="width: 280px"
      default-first
    >
      <template #default="{ node }">
        <el-icon style="margin-right: 10px"><User /></el-icon>
        <span>{{ node.label }}{{ node.data.nickname ? `（${node.data.nickname}）` : "" }}</span>
      </template>
    </TreeFilter>

    <div class="right-card">
      <Description :title="descriptionData.title" :data="descriptionData.data">
        <template #extra>
          <el-button size="small">日志</el-button>
        </template>
      </Description>

      <el-row :gutter="10" class="card-body" v-if="userInfo">
        <el-col :span="6" style="height: 100%">
          <ListCard
            ref="userGroupListCardRef"
            title="用户组"
            :request-api="listUserGroupByUserId"
            :request-params="{ appId: uacAppSecret, userId: userInfo?.userId }"
            value="groupId"
            label="groupName"
          >
            <template #extra>
              <el-button link :icon="Plus" @click="handleAddUserGroup">新增</el-button>
            </template>
          </ListCard>
        </el-col>
        <el-col :span="6" style="height: 100%">
          <ListCard
            ref="roleListCardRef"
            title="角色"
            :data="[]"
            :request-api="listRoleListByUserId"
            :request-params="{ appId: uacAppSecret, userId: userInfo?.userId }"
            value="roleId"
            label="roleName"
          >
            <template #extra>
              <el-button link :icon="Plus" @click="handleAddRole">新增</el-button>
            </template>
          </ListCard>
        </el-col>
      </el-row>

      <DialogForm
        ref="userGroupDialogFormRef"
        :transfer-api="listUserGroupWithDisabledByUserId"
        :request-params="{ appId: uacAppSecret, userId: userInfo?.userId }"
        transfer-placeholder="用户组"
        value="groupId"
        label="groupName"
        @confirm="userGroupConfirm"
      />

      <DialogForm
        ref="roleDialogFormRef"
        :transfer-api="listRoleListWithDisabledByUserId"
        :request-params="{ appId: uacAppSecret, userId: userInfo?.userId }"
        transfer-placeholder="角色"
        value="roleId"
        label="roleName"
        @confirm="roleConfirm"
      />
    </div>
  </div>
</template>

<script setup lang="tsx" name="UserLink">
import { TreeFilter, uacAppSecret, message } from "work";
import { list, type User as UserType } from "@/api/user/base";
import { addUserToGroups, listUserGroupByUserId, listUserGroupWithDisabledByUserId } from "@/api/user/userGroup";
import { addUserToRoles, listRoleListByUserId, listRoleListWithDisabledByUserId, type Role } from "@/api/system/role";
import { Plus, User } from "@element-plus/icons-vue";
import { ElRow, ElCol } from "element-plus";
import Description, { type DescriptionProps } from "./components/description.vue";
import ListCard, { type ListCardInstance } from "./components/listCard.vue";
import DialogForm, { type DialogFormInstance } from "./components/dialogForm.vue";

const userGroupListCardRef = shallowRef<ListCardInstance>();
const roleListCardRef = shallowRef<ListCardInstance>();
const userGroupDialogFormRef = shallowRef<DialogFormInstance>();
const roleDialogFormRef = shallowRef<DialogFormInstance>();
const userInfo = ref<UserType.UserInfo>();

const descriptionData = ref<DescriptionProps>({
  title: "",
  data: [],
});

// 点击用户列表的回调
const handleTreeChange = (_: number, data: UserType.UserInfo) => {
  userInfo.value = data;
  descriptionData.value.title = data.nickname;
  descriptionData.value.data = [
    { label: "用户名", value: data.username },
    { label: "性别", value: data.sex === 1 ? "男" : data.sex === 2 ? "女" : "保密" },
    { label: "电话", value: data.phone },
    { label: "邮箱", value: data.email },
    { label: "注册时间", value: data.registerTime, span: 4 },
  ];
};

// 点击新增用户组按钮的回调
const handleAddUserGroup = () => userGroupDialogFormRef.value?.open();

// 点击新增角色按钮的回调
const handleAddRole = () => roleDialogFormRef.value?.open();

// 新增用户组的弹框确认回调
const userGroupConfirm = async (form: any, callback: () => void) => {
  addUserToGroups({
    ...form,
    userGroupIds: form.transferIds,
    appId: uacAppSecret,
    userId: userInfo.value?.userId || "",
  }).then((res: any) => {
    if (res.status === "success") {
      message.success("修改成功");
      // 刷新外面的用户组列表
      userGroupListCardRef.value?.getDataList();
      // 触发 dialog 的回调，关闭 dialog，清除 dialog 的数据
      callback();
    }
  });
};

// 新增角色的弹框确认回调
const roleConfirm = async (form: any, callback: () => void) => {
  addUserToRoles({
    ...form,
    roleIds: form.transferIds,
    appId: uacAppSecret,
    userId: userInfo.value?.userId || "",
  }).then((res: any) => {
    if (res.status === "success") {
      message.success("修改成功");
      // 刷新外面的用户组列表
      roleListCardRef.value?.getDataList();
      // 触发 dialog 的回调，关闭 dialog，清除 dialog 的数据
      callback();
    }
  });
};
</script>

<style lang="scss" scoped>
.userLink-container {
  display: flex;
  width: 100%;

  .right-card {
    width: 100%;
    padding: 10px;
    background-color: #ffffff;

    .card-body {
      display: flex;
      height: calc(100vh - 215px);
    }
  }
}
</style>

<style lang="scss">
.userLink-container {
  .card-body {
    .el-menu-item {
      height: 32px;
    }
  }
}
</style>
