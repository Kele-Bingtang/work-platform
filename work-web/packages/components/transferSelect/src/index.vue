<template>
  <div class="select-container">
    <div class="left-box select-box">
      <el-table
        ref="elTableRef"
        :data="tableData"
        border
        show-overflow-tooltip
        highlight-current-row
        @selection-change="handleSelectionChange"
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
          <span class="num">{{ selectedList.length || "" }}</span>
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
import { ElMessage } from "element-plus";
import { ref, shallowRef, watch, onBeforeMount, type Component } from "vue";

type CommonObjType = Record<string, any>;

export type TransferTableColumn<T = CommonObjType> = Partial<TableColumnCtx<T>>;

export interface TransferSelectProps<T = CommonObjType> {
  modelValue: any;
  columns: TransferTableColumn<T>[]; // 表格列配置项
  data?: CommonObjType[] | any; // 表格数据
  requestApi?: (data?: any) => Promise<any>; // 请求数据的 api ==> 非必传
  requestParams?: CommonObjType; // 请求携带的参数
  multiple?: boolean; // 是否多选
  listIcon?: Component; // 选择内容的前缀
  closeIcon?: Component; // 移除内容的 Icon
}

// 接受父组件参数，配置默认值
const props = withDefaults(defineProps<TransferSelectProps>(), { multiple: true, closeIcon: Close });
type EmitProps = { (e: "update:modelValue", value: any): void };
const emits = defineEmits<EmitProps>();
const tableData = ref([]);
const selectedList = ref<CommonObjType[]>([]);
const elTableRef = shallowRef();

onBeforeMount(async () => {
  // 有数据就直接赋值，没有数据就执行请求函数
  if (props.data?.length) {
    tableData.value = props.data;
    return;
  }

  getDataList();
});

watch(
  () => selectedList.value,
  () => emits("update:modelValue", props.multiple ? selectedList.value : selectedList.value[0])
);

const getDataList = async () => {
  if (props.requestApi) {
    const { data } = await props.requestApi({ ...props.requestParams });
    tableData.value = data;
  }
};

const handleRowClick = (row: CommonObjType) => {
  if (row.disabled) return ElMessage.warning("该数据已被选中过，不允许再次选择");
  !props.multiple && (selectedList.value = [row]);
};

const handleSelectionChange = (selected: CommonObjType[]) => {
  selectedList.value = selected;
};

const handleCancelSelect = (row: CommonObjType) => {
  selectedList.value = selectedList.value?.filter(item => item.username !== row.username);
  elTableRef.value?.toggleRowSelection(row);
};

const handleClearSelect = () => {
  selectedList.value = [];
  elTableRef.value?.clearSelection();
};

// const rowStyle = ({ row }: { row: CommonObjType }) => {
//   if (row.disabled) return { backgroundColor: "#ebebeb" };
// };
</script>

<style lang="scss" scoped>
.select-container {
  display: flex;
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
