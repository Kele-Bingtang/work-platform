<template>
  <el-space class="card w-full" fill>
    <div>
      <el-button :disabled="serviceInfo?.projectRole === '只读成员'" @click="handleFormatterJson">格式化</el-button>
      <el-button type="primary" @click="handleSave" :disabled="serviceInfo?.projectRole === '只读成员'">
        保 存
      </el-button>
    </div>
    <div>
      <CodeMirror
        v-model="jsonCode"
        :localTheme="oneDark"
        :lang="json()"
        :linter="jsonParseLinter()"
        :height="600"
        fullScreen
      />
    </div>
  </el-space>
</template>

<script setup lang="ts" name="ResponseConfig">
import { CodeMirror, message } from "work";
import { oneDark } from "@codemirror/theme-one-dark";
import { json, jsonParseLinter } from "@codemirror/lang-json";
import { ServiceKey } from "@/config/symbol";
import { listByServiceId } from "@/api/serviceCol";

const serviceInfo = inject(ServiceKey);
const jsonCode = ref("");

onMounted(async () => {
  const res = await listByServiceId(unref(serviceInfo)?.serviceId || "");
  jsonCode.value = `{"_root": [${res.data.map(item => `"${item.jsonCol}"`).join(`,`)}]}`;
  handleFormatterJson();
});

const handleFormatterJson = () => {
  jsonCode.value = unref(jsonCode) ? JSON.stringify(JSON.parse(jsonCode.value), null, "\t") : "";
};
const handleSave = async () => {};
</script>

<style lang="scss" scoped></style>
