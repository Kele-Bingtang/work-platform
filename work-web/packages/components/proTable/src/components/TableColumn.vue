<template>
  <RenderTableColumn v-bind="column" />
</template>

<script setup lang="tsx" name="TableColumn">
import { inject, ref, useSlots } from "vue";
import type { TableColumnProps, RenderScope, HeaderRenderScope } from "../interface";
import { filterEnum, formatValue, lastProp, handleRowAccordingToProp } from "../utils";

defineProps<{ column: TableColumnProps }>();

const slots = useSlots();

const enumMap = inject("enumMap", ref(new Map()));

// 渲染表格数据
const renderCellData = (item: TableColumnProps, scope: RenderScope<any>) => {
  return enumMap.value.get(item.prop) && item.isFilterEnum
    ? filterEnum(handleRowAccordingToProp(scope.row, item.prop!), enumMap.value.get(item.prop)!, item.fieldNames)
    : formatValue(handleRowAccordingToProp(scope.row, item.prop!));
};

// 获取 tag 类型
const getTagType = (item: TableColumnProps, scope: RenderScope<any>) => {
  return filterEnum(
    handleRowAccordingToProp(scope.row, item.prop!),
    enumMap.value.get(item.prop),
    item.fieldNames,
    "tag"
  );
};

const RenderTableColumn = (item: TableColumnProps) => {
  return (
    <>
      {item.isShow && (
        <el-table-column
          {...item}
          align={item.align ?? "center"}
          showOverflowTooltip={item.showOverflowTooltip ?? item.prop !== "operation"}
        >
          {{
            default: (scope: RenderScope<any>) => {
              if (item._children) return item._children.map(child => RenderTableColumn(child));
              if (item.render) return item.render(scope);
              if (slots[lastProp(item.prop!)]) return slots[lastProp(item.prop!)]!(scope);
              const data = renderCellData(item, scope);
              if (item.tag && data) {
                if (Array.isArray(data)) {
                  return data.map((d, index) => (
                    <el-tag key={index} type={getTagType(item, scope)}>
                      {d}
                    </el-tag>
                  ));
                }
                return <el-tag type={getTagType(item, scope)}>{data}</el-tag>;
              }
              return Array.isArray(data) ? data.join(",") : data;
            },
            header: (scope: HeaderRenderScope<any>) => {
              if (item.headerRender) return item.headerRender(scope);
              if (slots[`${lastProp(item.prop!)}Header`]) return slots[`${lastProp(item.prop!)}Header`]!(scope);
              return item.label;
            },
          }}
        </el-table-column>
      )}
    </>
  );
};
</script>
