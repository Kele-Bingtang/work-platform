<template>
  <el-dialog v-model="dialogVisible" top="5vh" :title="title()" width="650" :close-on-click-modal="false">
    <ProForm
      ref="formElementRef"
      v-model="form"
      :el-form-props="elFormProps"
      :row-props="{ col: { span: 24 } }"
      :schema="schema"
      :include-model-keys="['id']"
    />
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="handleConfirm">确定</el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="tsx" name="UserLinkDialogForm">
import { ProForm, TransferSelect } from "work";
import type { FormSchemaProps, ProFormInstance, TransferTableColumn } from "@work/components";
import { ElOption, ElSelect, ElDatePicker, ElRow, ElCol, dayjs, type FormRules } from "element-plus";
import type { DictData } from "@/api/system/dictData";
import { useLayoutStore } from "@/stores";
import DialogForm from "./dialogForm.vue";
import { User } from "@element-plus/icons-vue";

export type DialogFormInstance = Omit<
  InstanceType<typeof DialogForm>,
  keyof ComponentPublicInstance | keyof DialogFormProps
>;

export interface DialogFormProps {
  transferSelectColumn: TransferTableColumn[];
  transferApi: (data?: any) => Promise<any>;
  requestParams?: Record<string, any>;
  transferPlaceholder: string;
  id: string;
}

// 接受父组件参数，配置默认值
const props = defineProps<DialogFormProps>();

type EmitProps = {
  (e: "confirm", form: any, status: "add" | "edit", callback: () => void): void;
};

const emits = defineEmits<EmitProps>();

const formElementRef = shallowRef<ProFormInstance>();
const dialogVisible = ref(false);
const form = ref<{ [key: string]: any }>({});
const expireOnOptions = ref<DictData.DictDataInfo[]>([]);
const status = ref<"add" | "edit">("add");
const transferSelectRef = shallowRef();

watch(
  () => dialogVisible.value,
  () => {
    const { getDictData } = useLayoutStore();

    if (dialogVisible.value) {
      // 初始化过期时间的下拉框
      if (!expireOnOptions.value.length) getDictData("sys_expire_on").then(res => (expireOnOptions.value = res.data));
    } else form.value.transferIds = [];
  }
);

// 新增用户组的弹框确认回调
const handleConfirm = () => {
  formElementRef.value?.form?.validate(async valid => {
    if (valid) {
      emits("confirm", form.value, status.value, () => {
        dialogVisible.value = false;
        // 更新用户组选择，把选过的禁用
        transferSelectRef.value?.getDataList();
      });
    }
  });
};

const title = () => {
  if (status.value === "add") return `新增${props.transferPlaceholder}`;
  else if (status.value === "edit") return `编辑${props.transferPlaceholder}`;
};

// 选择时长后，计算出过期时间
const selectChange = (value: number) => {
  if (!form.value || value === undefined) return;
  if (value === -1) form.value.expireOn = dayjs().add(99, "year").format("YYYY-MM-DD");
  else form.value.expireOn = dayjs().add(value, "year").format("YYYY-MM-DD");
};

// 表单规则
const rules = reactive<FormRules>({
  transferIds: [{ required: true, message: `请选择${props.transferPlaceholder}`, trigger: "blur" }],
  validFrom: [{ required: true, message: "请选择生效时间", trigger: "blur" }],
  expireOn: [{ required: true, message: "请选择过期时间", trigger: "blur" }],
});

const elFormProps = {
  labelPosition: "top",
  labelWidth: 80,
  rules: rules,
};

const schema: FormSchemaProps[] = [
  {
    prop: "transferIds",
    label: "用户组选择",
    destroy: () => status.value === "edit",
    render: ({ model }) => {
      return (
        <>
          <TransferSelect
            ref={transferSelectRef}
            v-model={model.transferIds}
            columns={props.transferSelectColumn}
            request-api={props.transferApi}
            request-params={props.requestParams}
            multiple
            list-icon={User}
            id={props.id}
          ></TransferSelect>
        </>
      );
    },
  },
  {
    prop: "validFrom",
    label: "生效时间",
    el: "el-date-picker",
    props: { clearable: true, placeholder: "请选择生效时间" },
  },
  {
    prop: "expireOn",
    label: "过期时间",
    render: ({ model }) => {
      return (
        <ElRow gutter={10} class="flex1-1">
          <ElCol span={12}>
            <ElSelect
              vModel={model.expireOnNum}
              placeholder="请选择时长"
              style={{ width: "100%" }}
              onChange={(val: string) => selectChange(Number(val))}
              clearable
            >
              {expireOnOptions.value.map(item => (
                <ElOption key={item.dictValue} label={item.dictLabel} value={item.dictValue} />
              ))}
            </ElSelect>
          </ElCol>
          <ElCol span={12}>
            <ElDatePicker
              vModel={model.expireOn}
              type="date"
              placeholder="请选择过期时间"
              style={{ width: "100%" }}
              value-format="YYYY-MM-DD"
            />
          </ElCol>
        </ElRow>
      );
    },
  },
];

const openAdd = () => {
  status.value = "add";
  dialogVisible.value = true;
  form.value = {};
};
const openEdit = (data: any) => {
  status.value = "edit";
  form.value = data;
  dialogVisible.value = true;
};
const close = () => (dialogVisible.value = false);
const toggle = () => (dialogVisible.value = !dialogVisible.value);

defineExpose({
  dialogVisible,
  openAdd,
  openEdit,
  close,
  toggle,
});
</script>
