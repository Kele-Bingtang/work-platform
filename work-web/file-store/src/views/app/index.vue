<template>
  <ProTable
    :request-api="listAppPage"
    :columns
    :dialogForm
    :search-cols="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
  ></ProTable>
</template>

<script setup lang="tsx" name="App">
import { listAppPage, registerApp, editApp, removeApp } from "@/api/app";
import { ElMessageBox } from "element-plus";
import { ProTable, type DialogForm, type TableColumnProps, type FormSchemaProps, message } from "work";

const validatePhone = (rule: any, value: any, callback: any) => {
  if (!value) {
    return callback();
  }

  // 示例：检查电话号码是否符合中国大陆手机号码格式
  if (!/^1[3-9]\d{9}$/.test(value)) {
    return callback(new Error("请输入正确的中国大陆手机号码"));
  }

  return callback();
};

const statusChange = (value: number, row: any) => {
  ElMessageBox.confirm(
    `确认要 <span style="color: teal">${value === 0 ? "禁用" : "启用"}</span> 【${row.appName ? row.appName : ""}】App 吗`,
    "系统提示",
    {
      dangerouslyUseHTMLString: true,
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
    }
  )
    .then(() => {
      editApp({ appId: row.appId, status: value })
        .then(res => {
          if (res.code === 200) message.success("修改成功") && nextTick(() => listAppPage && listAppPage());
          else {
            statusRecover(value, row);
          }
        })
        .catch(() => statusRecover(value, row));
    })
    .catch(() => statusRecover(value, row));
};

const statusRecover = (value: number, row: any) => {
  if (value === 0) return (row.status = 1);
  if (value === 1) return (row.status = 0);
};

const router = useRouter();

const columns: TableColumnProps[] = [
  { type: "index", label: "#", width: 80 },
  {
    prop: "appId",
    label: "App ID",
    search: { el: "el-input" },
    link: true,
    linkProps: {
      onClick: ({ row }) => router.push(`/file/${row.appId}`),
    },
  },
  { prop: "appName", label: "App 名称", search: { el: "el-input" } },
  { prop: "owner", label: "负责人", search: { el: "el-input" } },
  { prop: "email", label: "邮箱" },
  { prop: "phone", label: "电话" },
  {
    prop: "status",
    label: "状态",
    enum: [
      { value: 0, label: "禁用" },
      { value: 1, label: "启用" },
    ],
    search: { el: "el-select" },
    render: ({ row }) => {
      return (
        <>
          {row.status !== undefined && (
            <ElSwitch
              vModel={row.status}
              activeValue={1}
              inactiveValue={0}
              activeText="启用"
              inactiveText="停用"
              inlinePrompt
              onChange={(value: number) => statusChange(value, row)}
            />
          )}
        </>
      );
    },
  },
  { prop: "remark", label: "备注" },
  { prop: "createTime", label: "创建时间" },
  { prop: "operation", label: "操作", width: 160, fixed: "right" },
];

const schema: FormSchemaProps[] = [
  { prop: "appName", label: "App 名称", el: "el-input" },
  { prop: "owner", label: "负责人", el: "el-input" },
  { prop: "email", label: "邮箱", el: "el-input" },
  { prop: "phone", label: "电话", el: "el-input" },

  {
    prop: "remark",
    label: "备注",
    el: "el-input",
    props: { type: "textarea", clearable: true },
  },
];

const elFormProps = {
  labelWidth: 80,
  rules: {
    appName: [{ required: true, message: "请输入App 名称", trigger: "blur" }],
    phone: [{ required: true, validator: validatePhone, trigger: "blur" }],
    email: [{ type: "email", message: "请输入正确的邮箱", trigger: ["blur", "change"] }],
  },
};

const dialogForm: DialogForm = {
  formProps: { elFormProps, schema: schema, colRow: true },
  id: ["id", "appId"],
  addApi: registerApp,
  editApi: editApp,
  removeApi: removeApp,
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "45%",
    height: 300,
    top: "5vh",
    closeOnClickModal: false,
  },
};
</script>
