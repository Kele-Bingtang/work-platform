<template>
  <div ref="echartsRef" :style="{ width: width, height: height }"></div>
</template>

<script setup lang="ts" name="MemoryChart">
import * as echarts from "echarts";
import { useEcharts } from "@work/hooks";

interface ChartProps {
  value: string;
  width?: string;
  height?: string;
}

const props = withDefaults(defineProps<ChartProps>(), {
  width: "100%",
  height: "520px",
});

const echartsRef = shallowRef();

onMounted(() => {
  initChart();
});

const initChart = () => {
  const chart = echarts.init(echartsRef.value as HTMLElement, "shine");
  const option: echarts.EChartsOption = {
    title: {
      text: "内存信息",
      left: "center",
    },
    toolbox: {
      show: true,
      feature: {
        mark: { show: true },
        dataView: { show: true, readOnly: false },
        restore: { show: true },
        saveAsImage: { show: true },
      },
    },
    tooltip: {
      formatter: `{a} <br/>{b} : {c}${props.value.charAt(props.value.length - 1)}`,
    },
    series: [
      {
        name: "内存消耗",
        type: "gauge",
        min: 0,
        max: 1000,
        detail: {
          formatter: () => {
            return props.value;
          },
        },
        data: [
          {
            value: parseFloat(props.value),
            name: "峰值",
          },
        ],
      },
    ],
  };
  useEcharts(chart, option);
};
</script>

<style lang="scss" scoped></style>
