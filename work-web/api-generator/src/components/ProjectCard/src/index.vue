<template>
  <div :class="prefixClass">
    <div :class="`${prefixClass}__header`" v-if="!onlyBody" @click="handleClick">
      <slot name="header">projectName</slot>
      <el-icon class="action">
        <Right />
      </el-icon>
    </div>
    <div :class="[`${prefixClass}__body`, { 'is-only': onlyBody }]">
      <slot></slot>
    </div>
    <div :class="`${prefixClass}__footer`" v-if="!onlyBody">
      <slot name="footer"></slot>
    </div>
  </div>
</template>

<script setup lang="ts" name="ProjectCard">
import { Right } from "@element-plus/icons-vue";
import { useDesign } from "@work/hooks";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("project-card");

interface ProjectCardProps {
  onlyBody?: boolean;
  projectName?: string;
  footer?: string;
}
interface ProjectCardEmits {
  (e: "header-click", projectName?: string): void;
}

const props = withDefaults(defineProps<ProjectCardProps>(), {
  onlyBody: false,
  projectName: "",
  footer: "",
});

const emits = defineEmits<ProjectCardEmits>();

const handleClick = () => {
  if (props.projectName) {
    emits("header-click", props.projectName);
  } else {
    emits("header-click");
  }
};
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-project-card;

.#{$prefix-class} {
  width: 100%;
  height: 220px;
  background: #ffffff;
  border: 1px solid #fafafa;
  border-radius: 4px;
  box-shadow: 0 0 10px 0 hsl(0deg 0% 82% / 43%);

  &__header {
    position: relative;
    height: 40px;
    padding: 0 30px 0 20px;
    margin: 0;
    overflow: hidden;
    font-size: 16px;
    font-weight: 500;
    line-height: 40px;
    color: #333333;
    text-overflow: ellipsis;
    white-space: nowrap;
    cursor: pointer;

    &:hover {
      background-color: #f2f2f2;
    }

    .action {
      position: absolute;
      top: 12px;
      right: 15px;
      font-size: 20px;
      transform: scale(0.8);
    }
  }

  &__body {
    height: 140px;
    padding: 15px 30px;
    font-size: 14px;
    color: #666666;

    &.is-only {
      width: 100%;
      height: 100%;
    }
  }

  &__footer {
    display: flex;
    height: 40px;
    line-height: 40px;

    & > :deep(*) {
      display: inline-block;
      flex: 1 1;
      height: 40px;
      text-align: center;
      cursor: pointer;

      &:hover {
        background-color: #f2f2f2;
      }
    }
  }
}
</style>
