<template>
  <div class="descriptions flx-justify-start">
    <slot name="avatar">
      <el-avatar class="head-icon" :icon="User" />
    </slot>
    <div class="descriptions-box flex1">
      <div class="flx-justify-between">
        <span class="descriptions-title">{{ title }}</span>
        <slot name="extra"></slot>
      </div>
      <el-row v-if="data?.length" class="descriptions-content flx-align-center">
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
  title: string;
  data?: DescriptionItem[];
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
    .descriptions-title {
      font-size: 16px;
      font-weight: 600;
      color: #191919;
    }

    .descriptions-content {
      flex-wrap: wrap;
      min-height: 28px;

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
