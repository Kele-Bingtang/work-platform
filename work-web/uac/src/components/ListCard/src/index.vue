<template>
  <div :class="prefixClass">
    <div :class="`${prefixClass}__header flx-justify-between`">
      <span :class="`${prefixClass}__header--title`">{{ title }}</span>
      <slot name="extra"></slot>
    </div>
    <el-scrollbar v-if="listData.length">
      <List :data="listData" :label="label" :value="value">
        <template #default="item">
          <span>{{ item[label] }}</span>
          <template v-if="inCurrentWeek(item.expireOn)">
            （
            <span style="color: red">{{ inCurrentWeek(item.expireOn) }}</span>
            ）
          </template>
        </template>

        <template #extra="item">
          <el-button v-if="useEdit" :icon="Edit" link @click="emits('edit', item)"></el-button>
          <el-popconfirm
            v-if="useRemove"
            :title="`你确定移出该 ${title} 吗?`"
            @confirm="emits('remove', item)"
            :width="200"
          >
            <template #reference>
              <el-button :icon="Delete" link></el-button>
            </template>
          </el-popconfirm>
        </template>
      </List>
    </el-scrollbar>
    <el-empty v-else />
  </div>
</template>

<script setup lang="ts" name="ListCard">
import ListCard from "./index.vue";
import { List } from "work";
import { Edit, Delete } from "@element-plus/icons-vue";
import { dayjs } from "element-plus";
import { type ComponentPublicInstance, ref, onBeforeMount, watch } from "vue";
import { useDesign } from "@work/hooks";

export type ListCardInstance = Omit<InstanceType<typeof ListCard>, keyof ComponentPublicInstance | keyof ListCardProps>;

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("list-card");

interface ListCardItem {
  label: string;
  value: string | number;
}

export interface ListCardProps {
  title?: string;
  data?: ListCardItem[] | any;
  requestApi?: (data?: any) => Promise<any>; // 请求数据的 api ==> 非必传
  requestParams?: Record<string, any>;
  label?: string;
  value?: string;
  useEdit?: boolean;
  useRemove?: boolean;
}

type EmitProps = {
  (e: "edit", item: any): void;
  (e: "remove", item: any): void;
};

const emits = defineEmits<EmitProps>();

// 接受父组件参数，配置默认值
const props = withDefaults(defineProps<ListCardProps>(), {
  title: "",
  label: "label",
  value: "value",
});

const listData = ref<Record<string, any>[]>([]);

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

// expireOn 是否在这七天内，在则返回 expireOn
const inCurrentWeek = (expireOn: string) => {
  const expireOnDate = dayjs(expireOn);
  const dateAgo = dayjs().subtract(7, "day");

  if (expireOnDate.isAfter(dateAgo) && expireOnDate.isBefore(dayjs())) return expireOn;
};

// 监听页面 requestParam 改化，重新获取数据
watch(() => props.requestParams, getDataList, { deep: true });

defineExpose({ getDataList });
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-list-card;

.#{$prefix-class} {
  display: flex;
  flex-direction: column;
  height: 100%;
  background: #ffffff;
  border: 1px solid #dfdfdf;
  border-radius: 4px;

  &__header {
    height: 29px;
    padding: 0 16px;
    background: #ebeff8;
    border-bottom: 1px solid #dfdfdf;

    &--title {
      font-size: 14px;
      font-weight: 600;
      line-height: 28px;
      color: #1c1c1c;
    }
  }
}
</style>
