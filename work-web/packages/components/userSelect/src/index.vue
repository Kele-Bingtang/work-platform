<template>
  <div class="user-select-component">
    <el-select-v2
      v-model="selectValue"
      :options="userData"
      placeholder="请选择用户"
      :props="{ value: id, label: 'nickname' }"
      :multiple="multiple"
      clearable
      filterable
      v-bind="$attrs"
      @change="handleSelectChange"
    >
      <template #default="{ item }">{{ item.nickname }} {{ item.username }}</template>
    </el-select-v2>

    <el-button :icon="User" link @click="handleOpenUseTransferSelect"></el-button>

    <el-dialog v-model="dialogVisible" title="添加用户" width="700" class="select-dialog">
      <TransferSelect
        v-model="selectedIds"
        v-model:rows="selectedUserList"
        :columns="transferSelectColumns"
        :data="userData"
        :list-icon="User"
        :multiple="multiple"
        :id="id"
      >
        <template #name="item">{{ item.username }} {{ item.nickname }}</template>
      </TransferSelect>
      <template #footer>
        <div>
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleConfirmSelect">确定</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onBeforeMount, computed, unref } from "vue";
import { User } from "@element-plus/icons-vue";
import { TransferSelect } from "work";
import type { TransferTableColumn } from "@work/components";

defineOptions({ name: "UserSelect" });

export interface UserSelectProps {
  modelValue: string | string[] | any;
  data?: any;
  requestApi?: (data?: any) => Promise<any>; // 请求数据的 api ==> 非必传
  requestParams?: Record<string, any>;
  multiple?: boolean;
  id?: string;
}

// 接受父组件参数，配置默认值
const props = withDefaults(defineProps<UserSelectProps>(), {
  data: () => [],
  multiple: false,
  id: "userId",
});

type EmitProps = {
  (e: "update:modelValue", value: any): void; // 用户 ID
  (e: "update:user", value: any): void; // 完整的用户信息
};

const emits = defineEmits<EmitProps>();

const userData = ref<Record<string, any>[]>([]);
const dialogVisible = ref(false);
const selectedIds = ref<string | string[]>([]);
const selectedUserList = ref<Record<string, any> | Record<string, any>[]>([]);

/**
 * @description 初始化选中项
 */
const selectValue = computed({
  get() {
    if (props.multiple) return props.modelValue || [];
    return props.modelValue || "";
  },
  set(val) {
    emits("update:modelValue", val);

    updateUser(val);
  },
});

onBeforeMount(async () => {
  if (userData.value.length) return;

  // 有数据就直接赋值，没有数据就执行请求函数
  if (props.data?.length) {
    userData.value = props.data;
    props.modelValue && updateUser(props.modelValue);
    return;
  }
  await getDataList();

  props.modelValue && updateUser(props.modelValue);
});

const updateUser = (val: any) => {
  const r = userData.value.filter(item => (props.multiple ? val?.includes(item[props.id]) : val === item[props.id]));
  emits("update:user", props.multiple ? r : r[0]);
};

const getDataList = async () => {
  if (props.requestApi) {
    const { data } = await props.requestApi({ ...props.requestParams });
    userData.value = data;
  }
};

watch(
  () => props.data,
  val => {
    if (props.requestApi) return;
    userData.value = unref(val) || [];
  },
  { immediate: true }
);

/**
 * @description 下拉框选择某个元素回调
 */
const handleSelectChange = (val: string | string[]) => {
  selectValue.value = val;
};

/**
 * @description 打开用户选择弹窗回调
 */
const handleOpenUseTransferSelect = () => {
  selectedIds.value = selectValue.value;
  dialogVisible.value = true;
};

/**
 * @description 确认选择用户回调
 */
const handleConfirmSelect = () => {
  selectValue.value = selectedIds.value;
  emits("update:modelValue", selectedIds.value);
  dialogVisible.value = false;
};

/**
 * @description 用户弹框表格列配置项
 */
const transferSelectColumns: TransferTableColumn[] = [
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
