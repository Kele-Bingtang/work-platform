<template>
  <el-dialog v-model="dialogVisible" title="添加用户组" width="650" :close-on-click-modal="false">
    <ProForm ref="formElementRef" v-model="form" :options="options" />
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="handleConfirm">确定</el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="tsx" name="UserLinkDialogForm">
import { ProForm } from "work";
import type { FormOptionsProps, ProFormInstance } from "@work/components";
import { ElOption, ElSelect, ElDatePicker, ElRow, ElCol, ElTransfer, dayjs, type FormRules } from "element-plus";
import type { DictData } from "@/api/system/dictData";
import { useLayoutStore } from "@/stores/layout";
import DialogForm from "./dialogForm.vue";

export type DialogFormInstance = Omit<
  InstanceType<typeof DialogForm>,
  keyof ComponentPublicInstance | keyof DialogFormProps
>;

export interface DialogFormProps {
  transferApi: (data?: any) => Promise<any>;
  requestParams?: Record<string, any>;
  transferPlaceholder: string;
  value?: string;
  label?: string;
}

// 接受父组件参数，配置默认值
const props = withDefaults(defineProps<DialogFormProps>(), {
  value: "key",
  label: "label",
  status: "add",
});

type EmitProps = {
  (e: "confirm", form: any, callback: () => void): void;
};

const emits = defineEmits<EmitProps>();

const formElementRef = shallowRef<ProFormInstance>();
const dialogVisible = ref(false);
const form = ref<{ [key: string]: any }>({});
const transferData = ref([]);
const expireOnOptions = ref<DictData.DictDataInfo[]>([]);
const edit = ref(true);

watch(
  () => dialogVisible.value,
  () => {
    const { getDictData } = useLayoutStore();

    if (dialogVisible.value) {
      // 初始化穿梭框的数据
      if (!transferData.value.length) getTransferDataList();

      // 初始化过期时间的下拉框
      if (!expireOnOptions.value.length) getDictData("sys_expire_on").then(res => (expireOnOptions.value = res.data));
    } else form.value.transferIds = [];
  }
);

const getTransferDataList = () => {
  const { transferApi, requestParams } = props;
  transferApi({ ...requestParams }).then(res => (transferData.value = res.data));
};

// 监听页面 requestParam 改化，重新获取数据
watch(
  () => props.requestParams,
  () => {
    if (dialogVisible.value) getTransferDataList();
    else transferData.value = [];
  },
  { deep: true }
);

// 新增用户组的弹框确认回调
const handleConfirm = () => {
  formElementRef.value?.formRef?.validate(async valid => {
    if (valid) {
      emits("confirm", form.value, () => {
        dialogVisible.value = false;
        transferData.value = [];
      });
    }
  });
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

const options: FormOptionsProps = {
  form: { inline: false, labelPosition: "top", labelWidth: 80, size: "default", rules: rules },
  columns: [
    {
      formItem: { label: "用户组选择", prop: "transferIds", br: true },
      attrs: {
        isDestroy: () => edit.value,
        render: ({ scope }) => {
          return (
            <>
              <ElTransfer
                vModel={scope.form.transferIds}
                filterable
                filter-placeholder={props.transferPlaceholder}
                data={transferData.value}
                titles={["选择", "选中"]}
                props={{ label: props.label, key: props.value }}
              >
                {/* {{
                  default: ({ option }: any) => <span>{option[props.label]}</span>,
                }} */}
              </ElTransfer>
            </>
          );
        },
      },
    },
    {
      formItem: { label: "生效时间", prop: "validFrom", br: true },
      attrs: { el: "el-date-picker", props: { clearable: true, placeholder: "请选择生效时间" } },
    },
    {
      formItem: { label: "过期时间", prop: "expireOn", br: true },
      attrs: {
        render: ({ scope }) => {
          return (
            <ElRow gutter={10}>
              <ElCol span={12}>
                <ElSelect
                  vModel={scope.form.expireOnNum}
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
                  vModel={scope.form.expireOn}
                  type="date"
                  placeholder="请选择过期时间"
                  style={{ width: "100%" }}
                />
              </ElCol>
            </ElRow>
          );
        },
      },
    },
  ],
};

const openAdd = () => {
  edit.value = false;
  dialogVisible.value = true;
};
const openEdit = (data: any) => {
  edit.value = true;
  form.value = data;
  dialogVisible.value = true;
};
const close = () => (dialogVisible.value = false);
const toggle = () => (dialogVisible.value = !dialogVisible.value);

defineExpose({
  dialogVisible,
  transferData,
  openAdd,
  openEdit,
  close,
  toggle,
});
</script>

<style lang="scss" scoped></style>
