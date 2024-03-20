<template>
  <div class="user-select-component">
    <el-select-v2
      v-model="selectValue"
      :options="userData"
      placeholder="Select User"
      :props="{ value: 'userId' }"
      v-bind="$attrs"
      :multiple="multiple"
      @change="handleSelectChange"
    >
      <template #default="{ item }">{{ item.nickname }} {{ item.username }}</template>
    </el-select-v2>

    <el-button :icon="User" link @click="dialogVisible = true"></el-button>

    <el-dialog v-model="dialogVisible" title="添加用户" width="700" class="select-dialog">
      <TransferSelect
        v-model="selectedUserList"
        :columns="transferSelectColumn"
        :data="userData"
        :list-icon="User"
        :multiple="multiple"
      >
        <template #name="item">{{ item.username }} {{ item.nickname }}</template>
      </TransferSelect>
      <template #footer>
        <div>
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSelectUser">确定</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts" name="UserSelect">
import { ref, onBeforeMount } from "vue";
import { User } from "@element-plus/icons-vue";
import { TransferSelect } from "work";
import type { TransferTableColumn } from "@work/components";

export interface UserSelectProps {
  modelValue: any;
  data?: Record<string, any>[];
  requestApi?: (data?: any) => Promise<any>; // 请求数据的 api ==> 非必传
  requestParams?: Record<string, any>;
  multiple?: boolean;
}
// 接受父组件参数，配置默认值
const props = withDefaults(defineProps<UserSelectProps>(), { multiple: true });

type EmitProps = { (e: "update:modelValue", value: any): void };

const emits = defineEmits<EmitProps>();

const selectValue = ref();
const userData = ref<Record<string, any>[]>([]);
const dialogVisible = ref(false);

const selectedUserList = ref<Record<string, any>[] | Record<string, any>>();

onBeforeMount(async () => {
  // 有数据就直接赋值，没有数据就执行请求函数
  if (props.data?.length) {
    userData.value = props.data;
    return;
  }

  getDataList();
});

const getDataList = async () => {
  if (props.requestApi) {
    const { data } = await props.requestApi({ ...props.requestParams });
    userData.value = data;
  }
};

const handleSelectChange = (val: string) => {
  emits("update:modelValue", val);
};

const handleSelectUser = () => {
  const { multiple } = props;
  let selectUserIds;

  if (multiple) selectUserIds = (selectedUserList.value as Record<string, any>[])?.map(item => item.userId);
  else selectUserIds = (selectedUserList.value as Record<string, any>).userId;

  selectValue.value = selectUserIds;

  emits("update:modelValue", selectUserIds);
  dialogVisible.value = false;
};

const transferSelectColumn: TransferTableColumn[] = [
  { prop: "username", label: "用户名称" },
  { prop: "nickname", label: "用户昵称" },
];
</script>

<style lang="scss" scoped>
.user-select-component {
  display: flex;
  width: 100%;
}
</style>

<style lang="scss">
.user-select-component {
  .select-dialog {
    .el-dialog__header {
      border-bottom: none !important;
    }
  }
}
</style>
