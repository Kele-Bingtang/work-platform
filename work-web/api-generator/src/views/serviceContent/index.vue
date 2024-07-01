<template>
  <div :class="prefixClass">
    <el-tabs v-model="serviceTabActiveName">
      <el-tab-pane v-for="tab in serviceTabList" :key="tab.name" :label="tab.label" :name="tab.name" :lazy="tab.lazy">
        <component :is="tab.component" class="pt-2.5"></component>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup lang="ts" name="ServiceContent">
import { message, useDesign } from "work";
import { getServiceByServiceId, type Service } from "@/api/service";
import { ServiceKey } from "@/config/symbol";
import { CircuitBreaking, ResponseConfig, ResponseData, ServiceCol } from "./components";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("service-main");

const route = useRoute();

const serviceInfo = ref<Service.ServiceInfo>();

onMounted(async () => {
  // 初始化服务信息，给子组件使用
  const serviceId = route.params.serviceId as string;
  const res = await getServiceByServiceId(serviceId);
  if (!res.data) return message.error("服务不存在");

  serviceInfo.value = res.data;
});

provide(ServiceKey, serviceInfo);

const serviceTabActiveName = ref("数据响应");
const serviceTabList = [
  { label: "数据响应", name: "数据响应", lazy: false, component: ResponseData },
  { label: "数据列配置项", name: "数据列配置项", lazy: true, component: ServiceCol },
  { label: "降级响应", name: "降级响应", lazy: true, component: CircuitBreaking },
  { label: "响应配置", name: "项目设置", lazy: true, component: ResponseConfig },
];
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-service-main;

.#{$prefix-class} {
  flex: 1;

  .el-tabs {
    display: flex;
    flex-direction: column;
    height: 100%;

    :deep(.el-tabs__content, .el-tab-pane) {
      flex: 1;
    }

    :deep(.el-tab-pane) {
      height: 100%;
    }
  }

  :deep(.el-tabs__header) {
    padding: 0 15px;
    margin: 0;
    background: #ffffff;
  }
}
</style>
