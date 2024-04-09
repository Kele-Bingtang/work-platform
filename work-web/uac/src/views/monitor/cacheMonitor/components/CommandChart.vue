<template>
  <div ref="echartsRef" :style="{ width: width, height: height }"></div>
</template>

<script setup lang="ts" name="CommandChart">
import * as echarts from "echarts";
import { useEcharts } from "@work/hooks";

interface Data {
  name: string;
  value: number;
}

interface ChartProps {
  data: Data[];
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
      text: "命令统计",
      left: "center",
    },
    tooltip: {
      trigger: "item",
      formatter: "{a} <br/>{b} : {c} ({d}%)",
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
    series: [
      {
        name: "命令",
        type: "pie",
        radius: [20, 140],
        center: ["50%", "50%"],
        roseType: "area",
        itemStyle: {
          borderRadius: 5,
        },
        data: props.data,
      },
    ],
  };
  useEcharts(chart, option);
};
</script>

<style lang="scss" scoped></style>
