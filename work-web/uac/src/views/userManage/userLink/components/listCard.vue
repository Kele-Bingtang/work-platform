<template>
  <div class="list-card">
    <div class="list-header flx-justify-between">
      <span class="list-title">{{ title }}</span>
      <slot name="extra"></slot>
    </div>
    <el-scrollbar v-if="listData.length">
      <el-menu>
        <el-menu-item v-for="item in listData" :key="item[value]" :index="item[value]">{{ item[label] }}</el-menu-item>
      </el-menu>
    </el-scrollbar>
    <el-empty v-else />
  </div>
</template>

<script setup lang="ts" name="ListCard">
import ListCard from "./listCard.vue";

export type ListCardInstance = Omit<InstanceType<typeof ListCard>, keyof ComponentPublicInstance | keyof ListCardProps>;

interface ListCardItem {
  label: string;
  value: string | number;
}

export interface ListCardProps {
  title?: string;
  data?: ListCardItem[] | any;
  requestApi?: (data?: any) => Promise<any>; // 请求数据的 api ==> 非必传
  requestParams?: any;
  label?: string;
  value?: string;
}

const listData = ref<{ [key: string]: any }[]>([]);

// 接受父组件参数，配置默认值
const props = withDefaults(defineProps<ListCardProps>(), {
  title: "",
  label: "label",
  value: "value",
});

onBeforeMount(async () => {
  // 有数据就直接赋值，没有数据就执行请求函数
  if (props.data?.length) {
    listData.value = props.data;
    return;
  }

  getDataList();
});

const getDataList = async () => {
  if (props.requestApi) {
    const { data } = await props.requestApi({ ...props.requestParams });
    listData.value = data;
  }
};

defineExpose({ getDataList });
</script>

<style lang="scss" scoped>
.list-card {
  display: flex;
  flex-direction: column;
  height: 100%;
  background: #ffffff;
  border: 1px solid #dfdfdf;
  border-radius: 4px;

  .list-header {
    height: 29px;
    padding: 0 16px;
    background: #ebeff8;
    border-bottom: 1px solid #dfdfdf;

    .list-title {
      font-size: 14px;
      font-weight: 600;
      line-height: 28px;
      color: #1c1c1c;
    }
  }
}
</style>
