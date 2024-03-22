<template>
  <IconifyIcon
    :icon="icon as IconifyIconInterface"
    :style="style ? Object.assign(style, { outline: 'none' }) : { outline: 'none' }"
    v-bind="$attrs"
  ></IconifyIcon>
</template>

<script setup lang="ts">
import { onMounted } from "vue";
import { Icon as IconifyIcon, addIcon, type IconifyIcon as IconifyIconInterface } from "@iconify/vue/dist/offline";
import type { CSSProperties } from "vue";

defineOptions({ name: "IconifyOffline" });

interface IconifyOfflineProps {
  icon: Object | string | IconifyIconInterface;
  style?: CSSProperties;
}

const props = defineProps<IconifyOfflineProps>();

onMounted(() => {
  if (typeof props.icon === "object") addIcon(props.icon + "", props.icon as IconifyIconInterface);
});
</script>
