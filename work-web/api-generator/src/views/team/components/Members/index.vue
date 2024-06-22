<template>
  <div :class="prefixClass">
    <ProTable
      ref="proTableRef"
      :request-api="listMembers"
      :initRequestParam
      :columns
      :search-cols="{ xs: 1, sm: 1, md: 2, lg: 5, xl: 5 }"
      :border="false"
    ></ProTable>
  </div>
</template>

<script setup lang="tsx" name="Members">
import { useDesign } from "@work/hooks";
import { ProTable, type TableColumnProps } from "work";
import { listMembers } from "@/api/teamMember";
import { useDictStore } from "@/stores";
import { Setting } from "@element-plus/icons-vue";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("members");

const { getDictData } = useDictStore();

const route = useRoute();

const initRequestParam = {
  teamId: route.meta.params?.teamId,
};

const columns: TableColumnProps[] = [
  {
    prop: "username",
    label: "成员",
    search: {
      el: "el-input",
    },
  },
  {
    prop: "nickname",
    label: "昵称",
    search: {
      el: "el-input",
    },
  },
  {
    prop: "status",
    label: "状态",
    enum: () => getDictData("sys_normal_status"),
    fieldNames: { value: "dictValue", label: "dictLabel" },
    search: {
      el: "el-select",
    },
  },
  {
    prop: "teamRole",
    label: "成员权限",
    tag: true,
    enum: () => getDictData("team_role"),
    fieldNames: { value: "dictValue", label: "dictLabel" },
    search: {
      el: "el-select",
    },
  },
  {
    prop: "operation",
    label: "设置",
    render: () => {
      return <ElButton icon={Setting} link type="primary" />;
    },
  },
];
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-members;

.#{$prefix-class} {
  flex: 1;
}
</style>
