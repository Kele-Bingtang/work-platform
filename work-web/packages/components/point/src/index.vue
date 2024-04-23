<template>
  <span class="point-warper"><i class="point" :style="{ backgroundColor: color }"></i></span>
  <span>
    <slot>{{ text }}</slot>
  </span>
</template>

<script setup lang="ts">
defineOptions({ name: "Point" });

interface PointProps {
  color?: string;
  text?: string;
}

defineProps<PointProps>();
</script>

<style lang="scss" scoped>
.point-warper {
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
