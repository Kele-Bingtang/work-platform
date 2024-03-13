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
            :request-params="{ appId: uacAppSecret, userId: userInfo.userId }"
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
            :request-params="{ appId: uacAppSecret, userId: userInfo.userId }"
            value="roleId"
            label="roleName"
          >
            <template #extra>
              <el-button link :icon="Plus" @click="handleAddRole">新增</el-button>
            </template>
          </ListCard>
        </el-col>
      </el-row>

      <el-dialog v-model="userGroupDialogVisible" title="添加用户组" width="650" :close-on-click-modal="false">
        <ProForm ref="userGroupFormElementRef" v-model="userGroupForm" :options="userGroupOptions" />
        <template #footer>
          <el-button @click="userGroupDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="userGroupConfirm">确定</el-button>
        </template>
      </el-dialog>

      <el-dialog v-model="roleDialogVisible" title="添加角色" width="650" :close-on-click-modal="false">
        <ProForm ref="roleFormElementRef" v-model="roleForm" :options="roleOptions" />
        <template #footer>
          <el-button @click="roleDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="roleConfirm">确定</el-button>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script setup lang="tsx" name="UserLink">
import { TreeFilter, ProForm, uacAppSecret, message } from "work";
import { list, type User as UserType } from "@/api/user/base";
import {
  addUserToGroups,
  listUserGroupByUserId,
  listUserGroupWithDisabledByUserId,
  type UserGroup,
} from "@/api/user/userGroup";
import { listRoleListByUserId, listRoleListWithDisabledByUserId, type Role } from "@/api/system/role";
import { Plus, User } from "@element-plus/icons-vue";
import type { FormOptionsProps, ProFormInstance } from "@work/components";
import type { FormRules } from "element-plus";
import { ElOption, ElSelect, ElDatePicker, ElRow, ElCol, dayjs, ElTransfer } from "element-plus";
import Description, { type DescriptionProps } from "./components/description.vue";
import ListCard, { type ListCardInstance } from "./components/listCard.vue";
import { useLayoutStore } from "@/stores/layout";
import type { DictData } from "@/api/system/dictData";

const userGroupFormElementRef = shallowRef<ProFormInstance>();
const userGroupListCardRef = shallowRef<ListCardInstance>();
const roleListCardRef = shallowRef<ListCardInstance>();
const userInfo = ref<UserType.UserInfo>();

const descriptionData = ref<DescriptionProps>({
  title: "",
  data: [],
});

const userGroupInfo: UserGroup.UserLinkUserGroup = {
  userId: "",
  userGroupIds: [],
  validFrom: "",
  expireOn: "",
  appId: "",
};

const roleInfo: Role.UserLinkRole = {
  userId: "",
  roleIds: [],
  validFrom: "",
  expireOn: "",
  appId: "",
};

const userGroupDialogVisible = ref(false);
const roleDialogVisible = ref(false);
const userGroupForm = ref<UserGroup.UserLinkUserGroup>({ ...userGroupInfo });
const roleForm = ref<Role.UserLinkRole>({ ...roleInfo });
const userGroupData = ref<UserGroup.UserGroupInfo[]>([]);
const roleData = ref<Role.RoleInfo[]>([]);
const expireOnOptions = ref<DictData.DictDataInfo[]>([]);

watch(
  () => userGroupDialogVisible.value,
  () => {
    // 点击用户组的新增按钮后，初始化穿梭框的数据
    if (userGroupDialogVisible.value && !userGroupData.value.length) {
      listUserGroupWithDisabledByUserId({ appId: uacAppSecret, userId: userInfo.value?.userId || "" }).then(res => {
        userGroupData.value = res.data;
      });
    }

    // 点击用户组的新增按钮后，初始化过期时间的下拉框
    if (userGroupDialogVisible.value && !expireOnOptions.value.length) {
      useLayoutStore()
        .getDictData("sys_expire_on")
        .then(res => {
          expireOnOptions.value = res.data;
        });
    }
  }
);
watch(
  () => roleDialogVisible.value,
  () => {
    // 点击角色的新增按钮后，初始化穿梭框的数据
    if (roleDialogVisible.value && !roleData.value.length) {
      listRoleListWithDisabledByUserId({ appId: uacAppSecret, userId: userInfo.value?.userId || "" }).then(res => {
        roleData.value = res.data;
      });
    }

    // 点击角色的新增按钮后，初始化过期时间的下拉框
    if (roleDialogVisible.value && !expireOnOptions.value.length) {
      useLayoutStore()
        .getDictData("sys_expire_on")
        .then(res => {
          expireOnOptions.value = res.data;
        });
    }
  }
);

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
const handleAddUserGroup = () => {
  userGroupForm.value = { ...userGroupInfo };
  userGroupDialogVisible.value = true;
};

// 点击新增角色按钮的回调
const handleAddRole = () => {
  roleForm.value = { ...roleInfo };
  roleDialogVisible.value = true;
};

// 新增用户组的弹框确认回调
const userGroupConfirm = () => {
  userGroupFormElementRef.value?.formRef.validate(valid => {
    if (valid) {
      addUserToGroups({
        ...userGroupForm.value,
        appId: uacAppSecret,
        userId: userInfo.value?.userId || "",
      }).then(res => {
        if (res.status === "success") {
          message.success("修改成功");
          userGroupDialogVisible.value = false;
          // 刷新外面的用户组列表
          userGroupListCardRef.value?.getDataList();
          // 清空穿梭框的数据，下次进来重新获取
          userGroupData.value = [];
        }
      });
    }
  });
};

// 新增角色的弹框确认回调
const roleConfirm = () => {};

// 选择时长后，计算出过期时间
const selectChange = (value: number) => {
  if (value === undefined) return;
  if (value === -1) userGroupForm.value.expireOn = dayjs().add(99, "year").format("YYYY-MM-DD");
  else userGroupForm.value.expireOn = dayjs().add(value, "year").format("YYYY-MM-DD");
};

// 表单规则
const rules = reactive<FormRules>({
  userGroupIds: [{ required: true, message: "请选择用户组", trigger: "blur" }],
  validFrom: [{ required: true, message: "请选择生效时间", trigger: "blur" }],
  expireOn: [{ required: true, message: "请选择过期时间", trigger: "blur" }],
});

const userGroupOptions: FormOptionsProps = {
  form: { inline: false, labelPosition: "top", labelWidth: 80, size: "default", rules: rules },
  columns: [
    {
      formItem: { label: "用户组选择", prop: "userGroupIds", br: true },
      attrs: {
        render: ({ scope }) => {
          return (
            <>
              <ElTransfer
                vModel={scope.form.userGroupIds}
                filterable
                filter-placeholder="用户组名称"
                data={userGroupData.value}
                titles={["选择", "选中"]}
                props={{ label: "groupName", key: "groupId" }}
              >
                {{
                  default: ({ option }) => <span>{option.groupName}</span>,
                }}
              </ElTransfer>
            </>
          );
        },
      },
    },
    {
      formItem: { label: "生效时间", prop: "validFrom", br: true },
      attrs: { el: "el-date-picker", props: { clearable: true, placeholder: "请选择生效时间" } },
    },
    {
      formItem: { label: "过期时间", prop: "expireOn", br: true },
      attrs: {
        render: ({ scope }) => {
          return (
            <ElRow gutter={10}>
              <ElCol span={12}>
                <ElSelect
                  vModel={scope.form.expireOnNum}
                  placeholder="请选择时长"
                  style={{ width: "100%" }}
                  onChange={(val: string) => selectChange(Number(val))}
                  clearable
                >
                  {expireOnOptions.value.map(item => (
                    <ElOption key={item.dictValue} label={item.dictLabel} value={item.dictValue} />
                  ))}
                </ElSelect>
              </ElCol>
              <ElCol span={12}>
                <ElDatePicker
                  vModel={scope.form.expireOn}
                  type="date"
                  placeholder="请选择过期时间"
                  style={{ width: "100%" }}
                />
              </ElCol>
            </ElRow>
          );
        },
      },
    },
  ],
};

const roleOptions: FormOptionsProps = {
  form: { inline: false, labelPosition: "top", labelWidth: 80, size: "default", rules: rules },
  columns: [
    {
      formItem: { label: "角色选择", prop: "roleIds", br: true },
      attrs: {
        render: ({ scope }) => {
          return (
            <>
              <ElTransfer
                vModel={scope.form.roleIds}
                filterable
                filter-placeholder="角色名称"
                data={roleData.value}
                titles={["选择", "选中"]}
                props={{ label: "roleName", key: "roleId" }}
              >
                {{
                  default: ({ option }) => <span>{option.roleName}</span>,
                }}
              </ElTransfer>
            </>
          );
        },
      },
    },
    {
      formItem: { label: "生效时间", prop: "validFrom", br: true },
      attrs: { el: "el-date-picker", props: { clearable: true, placeholder: "请选择生效时间" } },
    },
    {
      formItem: { label: "过期时间", prop: "expireOn", br: true },
      attrs: {
        render: ({ scope }) => {
          return (
            <ElRow gutter={10}>
              <ElCol span={12}>
                <ElSelect
                  vModel={scope.form.expireOnNum}
                  placeholder="请选择时长"
                  style={{ width: "100%" }}
                  onChange={(val: string) => selectChange(Number(val))}
                  clearable
                >
                  {expireOnOptions.value.map(item => (
                    <ElOption key={item.dictValue} label={item.dictLabel} value={item.dictValue} />
                  ))}
                </ElSelect>
              </ElCol>
              <ElCol span={12}>
                <ElDatePicker
                  vModel={scope.form.expireOn}
                  type="date"
                  placeholder="请选择过期时间"
                  style={{ width: "100%" }}
                />
              </ElCol>
            </ElRow>
          );
        },
      },
    },
  ],
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
