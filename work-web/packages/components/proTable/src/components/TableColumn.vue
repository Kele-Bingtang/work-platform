<template>
  <RenderTableColumn v-bind="column" />
</template>

<script setup lang="tsx" name="TableColumn">
import { inject, ref, useSlots } from "vue";
import type { TableColumnProps, RenderScope, HeaderRenderScope } from "../interface";
import { filterEnum, filterEnumLabel, formatValue, lastProp, handleRowAccordingToProp } from "../utils";

defineProps<{ column: TableColumnProps }>();

const slots = useSlots();

const enumMap = inject("enumMap", ref(new Map()));

const getEnumData = (item: TableColumnProps, scope: RenderScope<any>) => {
  return enumMap.value.get(item.prop) && item.isFilterEnum
    ? filterEnum(handleRowAccordingToProp(scope.row, item.prop!), enumMap.value.get(item.prop)!, item.fieldNames)
    : "";
};

const renderCellData = (item: TableColumnProps, scope: RenderScope<any>, enumData: any) => {
  return enumMap.value.get(item.prop) && item.isFilterEnum
    ? filterEnumLabel(enumData, item.fieldNames)
    : formatValue(handleRowAccordingToProp(scope.row, item.prop!));
};

// 获取 tag 标签
const renderTag = (item: any, data: any, last: boolean, index?: number) => {
  const { tagType, tagEffect } = item;

  if (item.tagEl === "el-check-tag") {
    // 直接 index ? : 是不行的，因为这样 index = 0 是 false
    return index !== undefined ? (
      <>
        <el-check-tag key={index} checked type={tagType || "primary"}>
          {data}
        </el-check-tag>
        {last ? "" : " "}
      </>
    ) : (
      <el-check-tag checked type={tagType || "primary"}>
        {data}
      </el-check-tag>
    );
  }
  return index !== undefined ? (
    <>
      <el-tag key={index} type={tagType || "primary"} effect={tagEffect || "light"}>
        {data}
      </el-tag>
      {last ? "" : " "}
    </>
  ) : (
    <el-tag type={tagType || "primary"} effect={tagEffect || "light"}>
      {data}
    </el-tag>
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
              const enumData = getEnumData(item, scope);

              const data = renderCellData(item, scope, enumData);

              if (item.tag && enumData) {
                // data 是从 enumData 取出来的，如果 enumData 是数组，那么 data 必然是数组
                if (Array.isArray(enumData)) {
                  return enumData.map((e, index) => renderTag(e, data[index], enumData.length - 1 === index, index));
                }
                return renderTag(getEnumData, data);
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
