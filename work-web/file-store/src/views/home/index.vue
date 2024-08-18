<template>
  <div>
    <!-- <img src="http://localhost:8099/api/download/1/afc15b33cb604a3f8850dcb34f989e9f?isBrowse=true" /> -->
    <Tinymce v-model="value" />
  </div>
</template>

<script setup lang="ts" name="Home">
import { Tinymce } from "work";

const value = ref("");
</script>
