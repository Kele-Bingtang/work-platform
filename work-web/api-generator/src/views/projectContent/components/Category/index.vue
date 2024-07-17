<template>
  <ProTable
    :request-api="listCategoryPage"
    :init-request-param
    :columns
    :dialogForm
    :search-cols="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
  ></ProTable>
</template>

<script setup lang="ts" name="Category">
import { listCategoryPage, addCategory, editCategory, removeCategory } from "@/api/category";
import { ProjectKey } from "@/config/symbol";
import { ProTable, type DialogForm, type TableColumnProps, type FormSchemaProps, mittBus } from "work";

const projectInfo = inject(ProjectKey);

const teamId = computed(() => unref(projectInfo)?.teamId);
const projectId = computed(() => unref(projectInfo)?.projectId);

const initRequestParam = reactive({ teamId: unref(teamId), projectId: unref(projectId) });

const columns: TableColumnProps[] = [
  { type: "index", label: "#", width: 80 },
  { prop: "categoryName", label: "目录名称", search: { el: "el-input" } },
  { prop: "categoryCode", label: "目录编码", search: { el: "el-input" } },
  {
    prop: "isMain",
    label: "主目录",
    tag: true,
    enum: [
      { value: 0, label: "否", tagEl: "el-check-tag", tagType: "primary" },
      { value: 1, label: "是", tagEl: "el-check-tag", tagType: "success" },
    ],
  },
  { prop: "orderNum", label: "排序" },
  { prop: "createTime", label: "创建时间" },
  { prop: "operation", label: "操作", width: 160, fixed: "right" },
];

const schema: FormSchemaProps[] = [
  { prop: "categoryCode", label: "目录编码", el: "el-input" },
  { prop: "categoryName", label: "目录名称", el: "el-input" },
  { prop: "orderNum", label: "排序", el: "el-input-number", defaultValue: 0 },
];

const elFormProps = {
  labelWidth: 80,
  rules: {
    categoryCode: [{ required: true, message: "请输入目录编码", trigger: "blur" }],
    categoryName: [{ required: true, message: "请输入目录名称", trigger: "blur" }],
  },
};

const dialogForm: DialogForm = {
  formProps: { elFormProps, schema: schema, colRow: true },
  id: ["id", "categoryId"],
  addApi: data => addCategory({ ...data, ...initRequestParam }),
  editApi: data => editCategory({ ...data, ...initRequestParam }),
  removeApi: removeCategory,
  disableAdd: unref(projectInfo)?.projectRole === "只读成员",
  disableEdit: unref(projectInfo)?.projectRole === "只读成员",
  disableRemove: unref(projectInfo)?.projectRole === "只读成员",
  afterConfirm: () => {
    mittBus.emit("initTreeData");
  },
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "30%",
    height: 200,
    top: "5vh",
    closeOnClickModal: false,
  },
};
</script>
