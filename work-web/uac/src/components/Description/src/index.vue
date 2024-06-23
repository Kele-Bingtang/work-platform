<template>
  <div :class="`${prefixClass} flx-justify-start`">
    <slot name="avatar">
      <el-avatar :class="`${prefixClass}__head-icon`" :icon="User" />
    </slot>
    <div :class="`${prefixClass}__box flex1-1`">
      <div class="flx-justify-between">
        <span :class="`${prefixClass}__box--title`">{{ title }}</span>
        <slot name="extra"></slot>
      </div>
      <el-row v-if="data?.length" :class="`${prefixClass}__box--content flx-align-center`">
        <el-col
          v-for="(item, index) in data"
          :key="index"
          :span="item.span || 3"
          :class="`${prefixClass}__box--content__item`"
        >
          <slot>
            <div :class="`${prefixClass}__box--content__item--label`">{{ item.label }}</div>
            <div :class="`${prefixClass}__box--content__item--value`">{{ item.value }}</div>
          </slot>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup lang="ts" name="Descriptions">
import { User } from "@element-plus/icons-vue";
import { useDesign } from "@work/hooks";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("descriptions");

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
$prefix-class: #{$admin-namespace}-descriptions;

.#{$prefix-class} {
  margin: 12px 0;

  &__head-icon {
    margin-right: 12px;
  }

  &__box {
    &--title {
      font-size: 16px;
      font-weight: 600;
      color: #191919;
    }

    &--content {
      flex-wrap: wrap;
      min-height: 28px;

      &__item {
        display: flex;

        &--label {
          margin-right: 12px;
          font-size: 12px;
          color: #676767;
        }

        &--value {
          font-size: 12px;
          color: #191919;
        }
      }
    }
  }
}
</style>
