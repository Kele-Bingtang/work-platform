<template>
  <span :class="prefixClass"><i class="point" :style="{ backgroundColor: color }"></i></span>
  <span>
    <slot>{{ text }}</slot>
  </span>
</template>

<script setup lang="ts">
import { useDesign } from "@work/hooks";

defineOptions({ name: "Point" });

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("point-warper");

interface PointProps {
  color?: string;
  text?: string;
}

defineProps<PointProps>();
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-point-warper;

.#{$prefix-class} {
  margin-right: 4px;
  text-align: left;

  .point {
    display: inline-block;
    width: 8px;
    height: 8px;
    border-radius: 50%;
  }
}
</style>
