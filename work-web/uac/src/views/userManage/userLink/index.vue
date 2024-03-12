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
            title="用户组"
            :request-api="listUserGroupByUserId"
            :request-params="{ appId: uacAppSecret, userId: userInfo.userId }"
            value="groupId"
            label="groupName"
          >
            <template #extra>
              <el-button link :icon="Plus" @click="userGroupDialogVisible = true">新增</el-button>
            </template>
          </ListCard>
        </el-col>
        <el-col :span="6" style="height: 100%">
          <ListCard title="用户组" :data="[]">
            <template #extra>
              <el-button link :icon="Plus">新增</el-button>
            </template>
          </ListCard>
        </el-col>
      </el-row>

      <el-dialog v-model="userGroupDialogVisible" title="添加用户组" width="650">
        <el-transfer
          v-model="selectUserGroup"
          filterable
          filter-placeholder="用户组名称"
          :data="userGroupData"
          :titles="['选择', '选中']"
          :props="{ label: 'groupName', key: 'groupId' }"
        >
          <template #default="{ option }">
            <span>{{ option.groupName }}</span>
          </template>
        </el-transfer>
        <ProForm v-model="userGroupForm" :options="options" />
        <template #footer>
          <el-button @click="userGroupDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="userGroupConfirm">确定</el-button>
        </template>
      </el-dialog>
    </div>
  </div>
</template>

<script setup lang="tsx" name="UserLink">
import { TreeFilter, ProForm, uacAppSecret } from "work";
import { list, type User as UserType } from "@/api/user/base";
import { listUserGroupByUserId, listUserGroupWithDisabledByUserId, type UserGroup } from "@/api/user/userGroup";
import { Plus, User } from "@element-plus/icons-vue";
import type { FormOptionsProps } from "@work/components";
import type { FormRules } from "element-plus";
import { ElOption, ElSelect, ElDatePicker, ElRow, ElCol, dayjs } from "element-plus";
import Description, { type DescriptionProps } from "./components/description.vue";
import ListCard from "./components/listCard.vue";
import { useLayoutStore } from "@/stores/layout";
import type { DictData } from "@/api/system/dictData";

const userInfo = ref<UserType.UserInfo>();

const descriptionData = ref<DescriptionProps>({
  title: "",
  data: [],
});

const userGroupDialogVisible = ref(false);
const selectUserGroup = ref([]);
const userGroupForm = ref({});
const userGroupData = ref<UserGroup.UserGroupInfo[]>([]);
const selectOptions = ref<DictData.DictDataInfo[]>([]);

onMounted(() => {});

const rules = reactive<FormRules>({
  validFrom: [{ required: true, message: "请选择生效时间", trigger: "blur" }],
  expireOn: [{ required: true, message: "请选择过期时间", trigger: "blur" }],
});

watch(
  () => userGroupDialogVisible.value,
  () => {
    if (userGroupDialogVisible.value && !userGroupData.value.length) {
      listUserGroupWithDisabledByUserId({ appId: uacAppSecret, userId: userInfo.value?.userId || "" }).then(res => {
        userGroupData.value = res.data;
      });
    }

    if (userGroupDialogVisible.value && !selectOptions.value.length) {
      useLayoutStore()
        .getDictData("sys_expire_on")
        .then(res => {
          selectOptions.value = res.data;
        });
    }
  }
);

const userGroupConfirm = () => {};

const selectChange = (value: number) => {
  if (value === undefined) return;
  if (value === -1) userGroupForm.value.expireOn = dayjs().add(99, "year").format("YYYY-MM-DD");
  else userGroupForm.value.expireOn = dayjs().add(value, "year").format("YYYY-MM-DD");
};

const options: FormOptionsProps = {
  form: { inline: false, labelPosition: "top", labelWidth: 80, size: "default", rules: rules },
  columns: [
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
                  {selectOptions.value.map(item => (
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

const handleTreeChange = (_: number, data: UserType.UserInfo) => {
  userInfo.value = data;

  descriptionData.value.title = data.nickname;
  descriptionData.value.data = [
    {
      label: "用户名",
      value: data.username,
    },
    {
      label: "性别",
      value: data.sex === 1 ? "男" : data.sex === 2 ? "女" : "保密",
    },
    {
      label: "电话",
      value: data.phone,
    },
    {
      label: "邮箱",
      value: data.email,
    },
    {
      label: "注册时间",
      value: data.registerTime,
      span: 4,
    },
  ];
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
