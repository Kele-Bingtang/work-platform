<template>
  <el-space class="card w-full" fill>
    <div>
      <el-button>格式化</el-button>
      <el-button type="primary" @click="handleSave">保 存</el-button>
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
  jsonCode.value = unref(serviceInfo)?.breakingRespond || "";
});

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

  const res = await editService({ id, breakingRespond, projectId: unref(serviceInfo).projectId });
  if (res.code === 200) message.success("保存成功");
};
</script>

<style lang="scss" scoped></style>
