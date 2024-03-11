<template>
  <div class="userLink-container">
    <TreeFilter
      ref="treeFilterRef"
      title="App 清单"
      :requestApi="list"
      @change="handleTreeChange"
      id="userId"
      label="username"
      :enable-total="false"
    >
      <template #default="{ node }">
        <el-icon style="margin-right: 10px"><User /></el-icon>
        <span>{{ node.label }}{{ node.data.nickName ? `(${node.data.nickName})` : "" }}</span>
      </template>
    </TreeFilter>

    <div class="right-card">
      <div class="card-header">
        <el-descriptions :column="8" title="刘世鹏">
          <el-descriptions-item label="Username">kooriookami</el-descriptions-item>
          <el-descriptions-item label="Sex">18100000000</el-descriptions-item>
          <el-descriptions-item label="Phone">Suzhou</el-descriptions-item>
          <el-descriptions-item label="Email">Suzhou</el-descriptions-item>
          <el-descriptions-item label="Status">Suzhou</el-descriptions-item>
          <el-descriptions-item label="Expire On">Suzhou</el-descriptions-item>
        </el-descriptions>
      </div>

      <div class="card-body">
        <div class="list-box">
          <div class="list-header">User Group</div>
          <div class="list-body"></div>
        </div>
        <div>xxx</div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts" name="UserLink">
import { TreeFilter } from "work";
import { list } from "@/api/user/base";
import { User } from "@element-plus/icons-vue";

const handleTreeChange = (nodeId: number) => {
  console.log(nodeId);
};
</script>

<style lang="scss" scoped>
.userLink-container {
  display: flex;
  width: 100%;

  .right-card {
    width: 100%;
    padding: 10px;
    background-color: #ffffff;

    .card-body {
      display: flex;

      & > div {
        width: 50%;
      }
    }
  }
}
</style>
