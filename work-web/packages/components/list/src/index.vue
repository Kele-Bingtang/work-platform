<template>
  <ul :class="prefixClass">
    <li
      v-for="item in data"
      :key="item[value]"
      :class="[`${prefixClass}__item`, { 'is-active': isActive === item[value] }]"
      @click="changeClass(item[value])"
    >
      <div class="flx-justify-between">
        <div>
          <slot name="default" v-bind="item">
            <span>{{ item[label] }}</span>
          </slot>
        </div>
        <div v-if="$slots.extra" class="extra-button">
          <slot name="extra" v-bind="item"></slot>
        </div>
      </div>
    </li>
  </ul>
</template>

<script setup lang="ts">
import { type ComponentPublicInstance, ref } from "vue";
import List from "./index.vue";
import { useDesign } from "@work/hooks";

defineOptions({ name: "ListBox" });

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("list-box");

export type ListInstance = Omit<InstanceType<typeof List>, keyof ComponentPublicInstance | keyof ListProps>;

export interface ListProps {
  data: any;
  label?: string;
  value?: string;
}

withDefaults(defineProps<ListProps>(), {
  label: "label",
  value: "value",
});

const isActive = ref("");

const changeClass = (value: string) => {
  isActive.value = value;
};
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-list-box;

.#{$prefix-class} {
  box-sizing: border-box;
  padding-left: 0;
  margin: 0;
  list-style: none;
  border-right: solid 1px #ffffff;

  &__item {
    box-sizing: border-box;
    height: 32px;
    padding: 0 20px;
    padding-left: 400;
    font-size: 14px;
    line-height: 32px;
    color: #303133;
    white-space: nowrap;
    list-style: none;
    cursor: pointer;
    transition:
      background-color 0.3s,
      color 0.3s;

    &:hover {
      background-color: #e8f3fe;

      .extra-button {
        display: block;
      }
    }

    .extra-button {
      display: none;
      margin-right: -15px;
    }

    &.is-active {
      color: #168bf7;
    }
  }
}
</style>
