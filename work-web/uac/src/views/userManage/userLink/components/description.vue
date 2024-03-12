<template>
  <div class="descriptions flx-justify-start">
    <slot name="avatar">
      <el-avatar v-if="data?.length" class="head-icon" :icon="User" />
    </slot>
    <div class="descriptions-box">
      <div class="flx-justify-between">
        <span>{{ title }}</span>
        <slot name="extra"></slot>
      </div>
      <el-row class="descriptions-content flx-align-center">
        <el-col v-for="(item, index) in data" :key="index" :span="item.span || 3" class="descriptions-item">
          <slot>
            <div class="item-label">{{ item.label }}</div>
            <div class="item-value">{{ item.value }}</div>
          </slot>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup lang="ts" name="Description">
import { User } from "@element-plus/icons-vue";

interface DescriptionItem {
  value: string;
  label: string;
  span?: number;
}

export interface DescriptionProps {
  title?: string;
  data: DescriptionItem[];
}

// 接受父组件参数，配置默认值
withDefaults(defineProps<DescriptionProps>(), {
  title: "",
});
</script>

<style lang="scss" scoped>
.descriptions {
  margin: 12px 0;

  .head-icon {
    margin-right: 12px;
  }

  .descriptions-box {
    flex: 1;

    .descriptions-content {
      flex-wrap: wrap;
      min-height: 28px;
      padding: 0 8px;

      .descriptions-item {
        display: flex;

        .item-label {
          margin-right: 12px;
          font-size: 12px;
          color: #676767;
        }

        .item-value {
          font-size: 12px;
          color: #191919;
        }
      }
    }
  }
}
</style>
