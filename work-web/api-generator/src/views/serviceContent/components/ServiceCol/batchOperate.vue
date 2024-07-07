<template>
  <ProForm ref="proFormRef" v-model="model" :schema col-row :el-form-props />
</template>

<script setup lang="ts" name="ServiceColBatchProps">
import { type FormSchemaProps, ProForm, message } from "work";
import { type ServiceCol } from "@/api/serviceCol";

interface ServiceColBatchProps {
  data: ServiceCol.ServiceColInfo[];
}

const props = defineProps<ServiceColBatchProps>();
const proFormRef = shallowRef<typeof ProForm>();

const operateType = ["添加", "更新", "请求"];

const model = reactive<ServiceCol.ServiceColUpdateBatch>({
  operateType: [],
  operateValue: "",
  jsonColList: [],
});

const columnList = ref<ServiceCol.ServiceColInfo[]>();

const handleFocus = () => {
  const { operateType, operateValue } = model;
  if (operateType.length && operateValue) {
    const ov = Number(operateValue);
    let data = props.data;
    operateType.forEach(item => {
      if (item === "添加") data = data.filter(d => d.allowInsert !== ov);
      else if (item === "更新") data = data.filter(d => d.allowUpdate !== ov);
      else if (item === "请求") data = data.filter(d => d.allowRequest !== ov);
    });
    columnList.value = data;
  }
};

const elFormProps = {
  labelWidth: 100,
  rules: {
    operateType: [{ required: true, message: "请选择操作类型", trigger: "blur" }],
    operateValue: [{ required: true, message: "请选择操作内容", trigger: "blur" }],
    jsonColList: [{ required: true, message: "请选择请求名称", trigger: "blur" }],
  },
};

const schema: FormSchemaProps[] = [
  {
    prop: "operateType",
    label: "操作类型",
    el: "el-select",
    props: { multiple: true },
    enum: operateType.map(item => ({ label: item, value: item })),
  },
  {
    prop: "operateValue",
    label: "操作内容",
    el: "el-select",
    enum: [
      { label: "不允许", value: "0" },
      { label: "允许", value: "1" },
    ],
  },
  {
    prop: "jsonColList",
    label: "请求名称",
    el: "el-select",
    props: { multiple: true, onFocus: handleFocus },
    enum: columnList,
    fieldNames: { label: "jsonCol", value: "jsonCol" },
  },
];

const getData = async () => {
  const res = await unref(proFormRef)?.form?.validate(valid => valid);

  if (res) {
    const { operateType: op, operateValue: ov, jsonColList } = model;

    return {
      allowInsert: op.includes("添加") ? Number(ov) : null,
      allowUpdate: op.includes("更新") ? Number(ov) : null,
      allowRequest: op.includes("请求") ? Number(ov) : null,
      jsonColList,
    };
  }
};

defineExpose({
  getData,
});
</script>
