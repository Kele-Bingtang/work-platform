<template>
  <div :class="prefixClass">
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

<script setup lang="tsx" name="OnlineUsers">
import { ProTable } from "work";
import { forceLogout, listPage, type OnlineUser } from "@/api/monitor/onlineUser";
import { type ProTableInstance, type TableColumnProps } from "@work/components";
import { listDeptTreeList } from "@/api/system/dept";
import { useDesign } from "@work/hooks";
import { useUserStore } from "@/stores";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("online-users");

const proTableRef = shallowRef<ProTableInstance>();

const initRequestParam = reactive({
  orderRuleList: [{}],
});

const sortChange = (data: { column: any; prop: string; order: any }) => {
  initRequestParam.orderRuleList = [{ column: data.prop, type: data.order === "descending" ? "desc" : "asc" }];
};

const { userInfo } = useUserStore();

const columns: TableColumnProps<OnlineUser.OnlineUserInfo>[] = [
  { prop: "username", label: "用户账号", search: { el: "el-input" } },
  { prop: "nickname", label: "用户昵称" },
  { prop: "deptId", label: "所属部门", enum: () => listDeptTreeList() },
  { prop: "clientName", label: "客户端名称" },
  { prop: "loginIp", label: "登录 IP 地址" },
  { prop: "loginLocation", label: "登录地点" },
  { prop: "browser", label: "浏览器类型" },
  { prop: "os", label: "操作系统", width: 280 },
  { prop: "loginTime", label: "登录时间", sortable: "custom" },
  {
    prop: "operation",
    label: "操作",
    width: 100,
    fixed: "right",
    render: ({ row }) => (
      <el-popconfirm title={`你确定强制 ${row.nickname} 用户下线吗?`} onConfirm={() => forceLogout(row.username)}>
        {{
          reference: () => (
            <el-button link type="danger" disabled={row.username === userInfo.username}>
              强退{" "}
            </el-button>
          ),
        }}
      </el-popconfirm>
    ),
  },
];
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-online-users;

.#{$prefix-class} {
  flex: 1;
}
</style>
