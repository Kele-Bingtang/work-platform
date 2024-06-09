<template>
  <component :is="LayoutComponents[layoutMode]" />
  <ThemeDrawer />
</template>

<script setup lang="ts" name="Layout">
import LayoutVertical from "./LayoutVertical/index.vue";
import LayoutClassic from "./LayoutClassic/index.vue";
import LayoutTransverse from "./LayoutTransverse/index.vue";
import LayoutColumns from "./LayoutColumns/index.vue";
import LayoutMixins from "./LayoutMixins/index.vue";
import LayoutSubsystem from "./LayoutSubsystem/index.vue";
import ThemeDrawer from "@/layout/components/ThemeDrawer/index.vue";
import { useSettingsStore, useUserStore, useWebSocketStore } from "@/stores";
import { useLayout } from "@/hooks";
import { getPx, setStyleVar } from "@work/utils";
import { type Component, computed, watch, watchEffect, provide } from "vue";
import { useRoute } from "vue-router";
import { WebSocketKey } from "@work/constants";

const LayoutComponents: Record<string, Component> = {
  vertical: LayoutVertical,
  classic: LayoutClassic,
  transverse: LayoutTransverse,
  columns: LayoutColumns,
  mixins: LayoutMixins,
  subsystem: LayoutSubsystem,
};

const settingsStore = useSettingsStore();
const layoutMode = computed(() => settingsStore.layoutMode);

const route = useRoute();
const { setBrowserTitle } = useLayout();

watch(
  () => route.fullPath,
  () => setBrowserTitle(route), // 修改页面的 title
  {
    immediate: true,
  }
);

watchEffect(() => setStyleVar("--aside-width", getPx(settingsStore.menuWidth)));

watchEffect(() => setStyleVar("--header-height", getPx(settingsStore.headerHeight)));

// 初始化 WebSocket
if (import.meta.env.VITE_WEBSOCKET === "true") {
  const webSocket = useWebSocketStore();
  const url = import.meta.env.VITE_WEBSOCKET_URL || "";

  url && webSocket.connect(url + "?token=" + useUserStore().token);

  provide(WebSocketKey, webSocket);
}
</script>
