<template>
  <div v-if="modelValue" class="create-drawer-modal" @click="clickModal" />
  <el-drawer v-model="value" v-bind="$attrs" :modal="false" modal-class="blank-drawer-modal">
    <template v-for="(_, name) in $slots" #[name]="slotData" :key="name">
      <slot :name="name" v-bind="slotData"></slot>
    </template>
  </el-drawer>
</template>

<script setup lang="ts">
import { computed } from "vue";

defineOptions({ name: "BlankDrawer" });

interface BlankDrawerProps {
  modelValue: any;
}

const props = defineProps<BlankDrawerProps>();

type EmitProps = {
  (e: "update:modelValue", value: string): void;
};

const emits = defineEmits<EmitProps>();

const value = computed({
  get() {
    return props.modelValue;
  },
  set(value) {
    emits("update:modelValue", value);
  },
});

const clickModal = () => {
  value.value = false;
};
</script>

<style lang="scss">
.create-drawer-modal {
  position: fixed;
  inset: 0;
  z-index: 0;
  width: 100%;
  height: 100%;
  overflow: auto;

  // pointer-events: auto;
  // background-color: var(--el-overlay-color-lighter);
}

.blank-drawer-modal {
  position: unset !important;
}
</style>
