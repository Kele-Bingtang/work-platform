<template>
  <div>
    <Tinymce v-model="value" />
  </div>
</template>

<script setup lang="ts" name="Home">
import { Tinymce } from "work";

const value = ref("");
</script>
