<template>
  <ProTable :columns :data></ProTable>
</template>

<script setup lang="ts" name="DataTable">
import { ProTable, type TableColumnProps } from "work";

const props = defineProps<{ data: Record<string, any>[] }>();

const columns = computed<TableColumnProps[]>(() => {
  if (!props.data?.length) return [];
  const keys = Object.keys(props.data[0]);

  const columns: TableColumnProps[] = [];
  keys.forEach(item => {
    columns.push({
      prop: item,
      label: item,
      width: 200,
      sortable: true,
      filterConfig: {
        enabled: true,
      },
    });
  });
  return columns;
});
</script>
