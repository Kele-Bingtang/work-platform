<template>
  <component :is="'el-form'" v-bind="options.form" ref="formRef" :model="form">
    <template v-for="item in options.columns">
      <component
        :key="item.formItem.prop"
        v-if="!isHidden(item)"
        :is="'el-form-item'"
        v-bind="item.formItem"
        :style="{ display: item.formItem.br ? 'flex' : false }"
      >
        <ProFormItem :column="item" :form="form" :style="formWidth(item)" />
      </component>
    </template>
    <el-form-item class="form-footer"><slot name="footer"></slot></el-form-item>
    <el-form-item class="form-operation"><slot name="operation"></slot></el-form-item>
  </component>
</template>

<script setup lang="ts" name="ProForm">
import { computed, shallowRef, ref, provide, watch, isRef, type ComputedRef } from "vue";
import type { FormColumnProps, FormEnumProps, FormOptionsProps } from "./interface";
import ProFormItem from "./components/ProFormItem.vue";
import { getPx } from "@work/utils";

export interface ProFormProps {
  options: FormOptionsProps;
  modelValue: { [key: string]: any };
  disabled?: boolean;
}

const props = withDefaults(defineProps<ProFormProps>(), { disabled: false, modelValue: () => ({}) });

const formRef = shallowRef();
const form = computed(() => props.modelValue); // 定义 enumMap 存储 enum 值（避免异步请求无法格式化单元格内容 || 无法填充下拉选择）

const enumMap = ref(new Map<string, { [key: string]: any }[]>());
provide("enumMap", enumMap);
const setEnumMap = async (column: FormColumnProps) => {
  const attrs = column.attrs;
  const formItem = column.formItem;
  if (!attrs.enum) return;
  // 如果当前 enum 为后台数据需要请求数据，则调用该请求接口，并存储到 enumMap
  if (isRef(attrs.enum)) return enumMap.value.set(formItem.prop!, (attrs.enum as ComputedRef).value!);
  if (typeof attrs.enum !== "function") return enumMap.value.set(formItem.prop!, (attrs.enum as FormEnumProps[])!);
  const { data } = await attrs.enum(enumMap.value);
  enumMap.value.set(formItem.prop!, data);
};

// 初始化默认值
const initDefaultValue = (column: FormColumnProps, index: number) => {
  const { attrs, formItem } = column;
  // 设置表单排序默认值 && 设置表单项的默认值
  attrs!.order = attrs!.order ?? index + 2;
  if (attrs?.defaultValue !== undefined && attrs?.defaultValue !== null) {
    // 如果存在值，则不需要赋默认值
    if (form.value[formItem.prop]) return;
    if (isProxy(attrs.enum)) return (form.value[formItem.prop] = (attrs?.defaultValue as ComputedRef).value);
    if (typeof attrs?.defaultValue === "function") return (form.value[formItem.prop] = attrs?.defaultValue());
    else return (form.value[formItem.prop] = attrs?.defaultValue);
  }
};

const cascadeEnum = (column: FormColumnProps) => {
  const { formItem, attrs } = column;
  const formEl = attrs?.el;
  if (formEl === "el-select") {
    if (attrs && attrs.subProp && typeof attrs.subProp === "string") {
      watch(
        () => form.value[formItem.prop],
        async (newVal: string) => {
          // 然后执行内置的级联 change 事件
          const { subEnum } = attrs;
          if (subEnum && !enumMap.value.get(attrs.subProp!)) {
            if (typeof subEnum === "function") enumMap.value.set(attrs.subProp!, await subEnum(newVal));
            else if (typeof subEnum === "object") enumMap.value.set(attrs.subProp!, subEnum);
          }
          const formEnum = enumMap.value.get(formItem.prop) || [];
          const e = formEnum.filter(item => item.value === newVal);
          if (e[0]?.subValue) form.value[attrs.subProp!] = e[0].subValue;
        },
        { immediate: true }
      );
    }
  }
};

/**
 * 是否隐藏表单项
 */
const isHidden = (column: FormColumnProps) => {
  if (typeof column.attrs.isHidden === "function") {
    return column.attrs.isHidden(form.value);
  }
  return column.attrs.isHidden;
};

props.options.columns.forEach((item, index) => {
  // 设置枚举
  setEnumMap(item);
  // 级联下拉监听
  cascadeEnum(item);
  // 设置默认值
  initDefaultValue(item, index);
});

// 排序表单项
props.options.columns.sort((a, b) => a.attrs!.order! - b.attrs!.order!);
const formWidth = (column: FormColumnProps) => {
  const { form } = props.options;
  const { attrs } = column;
  const style = attrs.style || {};
  if (attrs.width) return { ...style, width: getPx(attrs.width) };
  if (form?.fixWidth && !column.formItem.br) return { ...style, width: getPx(attrs.width || "220px") };
  return style;
};

defineExpose({ formRef });
</script>

<style scoped lang="scss">
@import "./index";
</style>
