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
      style="width: 300px"
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
          <div>
            <el-select
              v-model="appId"
              placeholder="请选择 App 清单"
              size="small"
              style="width: 120px; margin-right: 10px"
            >
              <el-option v-for="item in appTreeList" :key="item.appId" :label="item.appName" :value="item.appId" />
            </el-select>
            <el-button size="small">日志</el-button>
          </div>
        </template>
      </Description>

      <el-row :gutter="10" class="card-body" v-if="userInfo">
        <el-col :span="6" style="height: 100%">
          <ListCard
            ref="userGroupListCardRef"
            title="用户组"
            :request-api="listUserGroupByUserId"
            :request-params="{ appId: appId, userId: userInfo?.userId }"
            value="groupId"
            label="groupName"
            @edit="userGroupEdit"
            @delete="userGroupDelete"
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
            :request-api="listRoleLinkByUserId"
            :request-params="{ appId: appId, userId: userInfo?.userId }"
            value="roleId"
            label="roleName"
            @edit="roleEdit"
            @delete="roleDelete"
          >
            <template #extra>
              <el-button link :icon="Plus" @click="handleAddRole">新增</el-button>
            </template>
          </ListCard>
        </el-col>
      </el-row>

      <DialogForm
        ref="userGroupDialogFormRef"
        id="groupId"
        :transfer-select-column="[{ prop: 'groupName', label: '用户组名' }]"
        :transfer-api="listUserGroupWithDisabledByUserId"
        :request-params="{ appId: appId, userId: userInfo?.userId }"
        transfer-placeholder="用户组"
        @confirm="userGroupConfirm"
      />

      <DialogForm
        ref="roleDialogFormRef"
        id="roleId"
        :transfer-select-column="[{ prop: 'roleName', label: '角色名' }]"
        :transfer-api="listRoleWithDisabledByUserId"
        :request-params="{ appId: appId, userId: userInfo?.userId }"
        transfer-placeholder="角色"
        @confirm="roleConfirm"
      />
    </div>
  </div>
</template>

<script setup lang="tsx" name="UserLink">
import { TreeFilter, message } from "work";
import { list, addUserGroupsToUser, addRolesToUser, type User as UserType } from "@/api/user/user";
import {
  listUserGroupByUserId,
  listWithDisabledByUserId as listUserGroupWithDisabledByUserId,
  removeUserFromUserGroup,
  editUserGroupLinkInfo,
  type UserGroup,
} from "@/api/user/userGroup";
import {
  editUserRoleLinkInfo,
  listRoleLinkByUserId,
  listWithDisabledByUserId as listRoleWithDisabledByUserId,
  removeUserFromRole,
  type Role,
} from "@/api/system/role";
import { Plus, User } from "@element-plus/icons-vue";
import { ElRow, ElCol } from "element-plus";
import Description, { type DescriptionProps } from "@/components/Description/index.vue";
import ListCard, { type ListCardInstance } from "@/components/ListCard/index.vue";
import DialogForm, { type DialogFormInstance } from "./components/dialogForm.vue";
import { getAppTreeList, type App } from "@/api/application/app";

const userGroupListCardRef = shallowRef<ListCardInstance>();
const roleListCardRef = shallowRef<ListCardInstance>();
const userGroupDialogFormRef = shallowRef<DialogFormInstance>();
const roleDialogFormRef = shallowRef<DialogFormInstance>();
const userInfo = ref<UserType.UserInfo>();

const descriptionData = ref<DescriptionProps>({
  title: "",
  data: [],
});
const appId = ref("");
const appTreeList = ref<App.AppTree[]>([]);

onBeforeMount(() => {
  getAppTreeList().then(res => {
    appId.value = res.data[0]?.appId;
    appTreeList.value = res.data;
  });
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
const handleAddUserGroup = () => userGroupDialogFormRef.value?.openAdd();

// 点击新增角色按钮的回调
const handleAddRole = () => roleDialogFormRef.value?.openAdd();

// 新增用户组的弹框确认回调
const userGroupConfirm = async (form: any, status: "add" | "edit", callback: () => void) => {
  if (status === "add") {
    addUserGroupsToUser({
      ...form,
      userGroupIds: form.transferIds,
      appId: appId.value,
      userId: userInfo.value?.userId || "",
    }).then((res: any) => {
      if (res.status === "success") {
        message.success("新增成功");
        // 刷新外面的用户组列表
        userGroupListCardRef.value?.getDataList();
        // 触发 dialog 的回调，关闭 dialog，清除 dialog 的数据
        callback();
      }
    });
  } else {
    editUserGroupLinkInfo(form).then((res: any) => {
      if (res.status === "success") {
        message.success("修改成功");
        // 刷新外面的用户组列表
        userGroupListCardRef.value?.getDataList();
        // 触发 dialog 的回调，关闭 dialog，清除 dialog 的数据
        callback();
      }
    });
  }
};

// 新增角色的弹框确认回调
const roleConfirm = async (form: any, status: "add" | "edit", callback: () => void) => {
  if (status === "add") {
    addRolesToUser({
      ...form,
      roleIds: form.transferIds,
      appId: appId.value,
      userId: userInfo.value?.userId || "",
    }).then((res: any) => {
      if (res.status === "success") {
        message.success("新增成功");
        // 刷新外面的用户组列表
        roleListCardRef.value?.getDataList();
        // 触发 dialog 的回调，关闭 dialog，清除 dialog 的数据
        callback();
      }
    });
  } else {
    editUserRoleLinkInfo(form).then((res: any) => {
      if (res.status === "success") {
        message.success("修改成功");
        // 刷新外面的用户组列表
        roleListCardRef.value?.getDataList();
        // 触发 dialog 的回调，关闭 dialog，清除 dialog 的数据
        callback();
      }
    });
  }
};

// 编辑用户组的回调
const userGroupEdit = (item: UserGroup.UserGroupLinkInfo) => {
  userGroupDialogFormRef.value?.openEdit({ id: item.linkId, validFrom: item.validFrom, expireOn: item.expireOn });
};

// 删除用户组的回调
const userGroupDelete = (item: UserGroup.UserGroupLinkInfo) => {
  removeUserFromUserGroup([item.linkId + ""]).then((res: any) => {
    if (res.status === "success") {
      message.success("删除成功");
      userGroupListCardRef.value?.getDataList();
    }
  });
};

// 编辑角色的回调
const roleEdit = (item: Role.RoleLinkInfo) => {
  roleDialogFormRef.value?.openEdit({ id: item.linkId, validFrom: item.validFrom, expireOn: item.expireOn });
};

// 删除角色的回调
const roleDelete = (item: Role.RoleLinkInfo) => {
  removeUserFromRole([item.linkId + ""]).then((res: any) => {
    if (res.status === "success") {
      message.success("删除成功");
      roleListCardRef.value?.getDataList();
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
@/api/system/role/role
