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
        :height="500"
        fullScreen
      />
    </div>
  </el-space>
</template>

<script setup lang="ts" name="CircuitBreaking">
import { CodeMirror, message } from "work";
import { oneDark } from "@codemirror/theme-one-dark";
import { json, jsonParseLinter } from "@codemirror/lang-json";
import { ServiceKey } from "@/config/symbol";
import { editService } from "@/api/service";

const serviceInfo = inject(ServiceKey);

const jsonCode = ref("");

onMounted(() => {
  jsonCode.value = unref(serviceInfo)?.breakingRespond
    ? JSON.stringify(JSON.parse(unref(serviceInfo)?.breakingRespond!), null, "\t")
    : "";
});

const handleFormatterJson = () => {
  jsonCode.value = unref(jsonCode) ? JSON.stringify(JSON.parse(jsonCode.value), null, "\t") : "";
};

const handleSave = async () => {
  const service = unref(serviceInfo);
  if (!service) return message.warning("服务不存在");

  const { id } = service;
  let breakingRespond;
  try {
    breakingRespond = JSON.parse(unref(jsonCode));
  } catch (error) {
    return message.error("JSON 格式错误");
  }

  const res = await editService({ id, breakingRespond, projectId: service.projectId });
  if (res.code === 200) message.success("保存成功");
};
</script>

<style lang="scss" scoped></style>
