<template>
  <div class="login-log-container">
    <ProTable
      ref="proTableRef"
      :request-api="listPage"
      :init-request-param="initRequestParam"
      :columns="columns"
      :search-cols="{ xs: 1, sm: 1, md: 3, lg: 5, xl: 5 }"
      :border="false"
      @sort-change="sortChange"
      :default-sort="{ prop: 'loginTime', order: 'descending' }"
    ></ProTable>
  </div>
</template>

<script setup lang="tsx" name="LoginLog">
import { ProTable } from "work";
import { listPage, type LoginLog } from "@/api/monitor/loginLog";
import { type ProTableInstance, type TableColumnProps } from "@work/components";

const proTableRef = shallowRef<ProTableInstance>();

const initRequestParam = reactive({
  orderRuleList: [{}],
});

const sortChange = (data: { column: any; prop: string; order: any }) => {
  initRequestParam.orderRuleList = [{ column: data.prop, type: data.order === "descending" ? "desc" : "asc" }];
};

const columns: TableColumnProps<LoginLog.LoginLogInfo>[] = [
  { prop: "username", label: "用户账号", search: { el: "el-input" } },
  { prop: "clientName", label: "客户端名称", width: 100, search: { el: "el-input" } },
  { prop: "loginIp", label: "登录 IP 地址", search: { el: "el-input" } },
  { prop: "loginLocation", label: "登录地点", search: { el: "el-input" } },
  { prop: "browser", label: "浏览器类型", search: { el: "el-input" } },
  { prop: "os", label: "操作系统" },
  { prop: "msg", label: "提示消息" },
  {
    prop: "status",
    label: "状态",
    enum: [
      { value: "0", label: "失败" },
      { value: "1", label: "成功" },
    ],
    search: { el: "el-select", order: 3 },
  },
  {
    prop: "loginTime",
    label: "登录时间",
    sortable: "custom",
    search: {
      el: "el-date-picker",
      order: 3,
      props: { type: "daterange" },
    },
  },
];
</script>

<style lang="scss" scoped>
.login-log-container {
  width: 100%;
}
</style>
