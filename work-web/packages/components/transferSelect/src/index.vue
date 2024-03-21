<template>
  <div class="select-container">
    <div class="left-box select-box">
      <el-table
        ref="elTableRef"
        :data="tableData"
        border
        show-overflow-tooltip
        @select="handleSelect"
        @row-click="handleRowClick"
      >
        <el-table-column v-if="multiple" type="selection" width="40" :selectable="(row: any) => !row.disabled" />
        <template v-for="item in columns" :key="item">
          <el-table-column :prop="item.prop" :label="item.label" v-bind="$attrs" />
        </template>
      </el-table>
    </div>

    <div class="right-box">
      <div class="flx-justify-between">
        <div class="select-num">
          选择数：
          <span class="num">{{ selectedList?.length || "" }}</span>
        </div>
        <el-button link @click="handleClearSelect">清空</el-button>
      </div>

      <div class="selected-list">
        <template v-if="selectedList?.length">
          <template v-for="item in selectedList" :key="item">
            <slot name="list" v-bind="item">
              <div class="selected-item">
                <slot>
                  <el-icon><component :is="listIcon" /></el-icon>
                </slot>
                <span class="item-name">
                  <slot name="name" v-bind="item">{{ item[columns[0].prop!] }}</slot>
                </span>
                <el-button :icon="closeIcon" link @click="handleCancelSelect(item)"></el-button>
              </div>
            </slot>
          </template>
        </template>

        <el-empty v-else />
      </div>
    </div>
  </div>
</template>
<script setup lang="ts" name="TransferSelect">
import { Close } from "@element-plus/icons-vue";
import type { TableColumnCtx } from "element-plus/es/components/table/src/table-column/defaults";
import { type TableInstance, ElMessage } from "element-plus";
import { ref, shallowRef, onBeforeMount, type Component } from "vue";
import TransferSelect from "./index.vue";

export type TransferSelectInstance = Omit<
  InstanceType<typeof TransferSelect>,
  keyof ComponentPublicInstance | keyof TransferSelectProps
>;
type CommonObjType = Record<string, any>;

export type TransferTableColumn<T = CommonObjType> = Partial<TableColumnCtx<T>>;

export interface TransferSelectProps<T = CommonObjType> {
  modelValue: CommonObjType | CommonObjType[] | any; // v-model
  columns: TransferTableColumn<T>[]; // 表格列配置项
  data?: CommonObjType[] | any; // 表格数据
  requestApi?: (data?: any) => Promise<any>; // 请求数据的 api ==> 非必传
  requestParams?: CommonObjType; // 请求携带的参数
  multiple?: boolean; // 是否多选
  listIcon?: Component; // 选择内容的前缀
  closeIcon?: Component; // 移除内容的 Icon
  id?: string; // 一行的唯一标识，添加和移出选择项时用到
}

// 接受父组件参数，配置默认值
const props = withDefaults(defineProps<TransferSelectProps>(), { multiple: true, closeIcon: Close });

type EmitProps = {
  (e: "update:modelValue", value: CommonObjType | CommonObjType[]): void; // 选择的行数据
  (e: "update:ids", value: string | string[]): void; // 选择的 ids
};

const emits = defineEmits<EmitProps>();
const tableData = ref([]);
const elTableRef = shallowRef<TableInstance>();

onBeforeMount(async () => {
  // 有数据就直接赋值，没有数据就执行请求函数
  if (props.data?.length) {
    tableData.value = props.data;
    return;
  }

  getDataList();
});

/**
 * @description 初始化选中项（可传入符合条件的数据，会自动激活选中）
 * 计算出来的 selectedList 一定是一个数组，如果 props 传入一个对象，也会变成 [对象]（转成数组）
 */
const selectedList = computed({
  get() {
    const { multiple, modelValue } = props;

    if (multiple && Array.isArray(modelValue)) {
      modelValue?.forEach(row => elTableRef.value?.toggleRowSelection(row, true));
      return modelValue || [];
    }

    if (!multiple && Array.isArray(modelValue)) return modelValue[0] ? [modelValue[0]] : [];
    else return modelValue ? [modelValue] : [];
  },
  set(value) {
    emits("update:modelValue", props.multiple ? value : value[0] || []);

    if (props.multiple) {
      const selectedIds: string[] = [];

      value?.forEach(item => selectedIds.push(item[props.id ?? props.columns[0]?.prop!]));
      emits("update:ids", selectedIds);
    } else {
      emits("update:ids", value[0][props.id ?? props.columns[0].prop!]);
    }
  },
});

/**
 * @description 初始化数据
 */
const getDataList = async () => {
  if (props.requestApi) {
    const { data } = await props.requestApi({ ...props.requestParams });
    tableData.value = data;
  }
};

/**
 * @description 表格行点击事件
 */
const handleRowClick = (row: CommonObjType) => {
  if (row.disabled) return ElMessage.warning("该数据已被选中过，不允许再次选择");
  !props.multiple && (selectedList.value = [row]);
};

/**
 * @description 多选模式下，手动选中项发生变化时触发
 */
const handleSelect = (selected: CommonObjType[]) => {
  selectedList.value = selected;
};

/**
 * @description 取消选中回调
 */
const handleCancelSelect = (row: CommonObjType) => {
  selectedList.value = selectedList.value?.filter(
    (item: any) => item[props.id ?? props.columns[0].prop!] !== row[props.id ?? props.columns[0]?.prop!]
  );
  props.multiple && elTableRef.value?.toggleRowSelection(row, false);
};

/**
 * @description 清除按钮回调
 */
const handleClearSelect = () => {
  selectedList.value = [];
  elTableRef.value?.clearSelection();
};

defineExpose({ tableElement: elTableRef.value });
</script>

<style lang="scss" scoped>
.select-container {
  display: flex;
  flex: 1;
  align-items: center;
  width: 100%;

  .left-box {
    display: flex;
    flex-direction: column;
    width: 50%;
    height: 450px;
    padding-right: 16px;
    border-right: 1px solid #dfdfdf;
  }

  .right-box {
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    width: 50%;
    height: 450px;
    padding-left: 16px;

    .select-num {
      margin-bottom: 4px;
      line-height: 24px;
      color: #676767;

      .num {
        font-weight: 700;
      }
    }

    .selected-list {
      overflow: auto;

      .selected-item {
        display: flex;
        align-items: center;
        height: 28px;

        .item-name {
          display: inline-block;
          flex: 1;
          margin-left: 8px;
          overflow: hidden;
          font-size: 12px;
          line-height: 22px;
          text-overflow: ellipsis;
          white-space: nowrap;
        }
      }
    }
  }
}
</style>
