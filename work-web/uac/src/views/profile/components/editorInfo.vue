<template>
  <ProForm
    ref="proFormRef"
    v-model="user"
    :schema="schema"
    :el-form-props="elFormProps"
    :row-props="{ col: { span: 24 } }"
  >
    <template #operation>
      <el-button type="primary" @click="submit()">保存</el-button>
      <el-button type="danger" @click="reset()">重置</el-button>
    </template>
  </ProForm>

  <!-- <el-form ref="formRef" :model="user" :rules="rules" label-width="80px">
    <el-form-item label="用户名称" prop="nickname">
      <el-input v-model="user.nickname" maxlength="30" />
    </el-form-item>
    <el-form-item label="联系方式" prop="phone">
      <el-input v-model="user.phone" maxlength="11" />
    </el-form-item>
    <el-form-item label="用户邮箱" prop="email">
      <el-input v-model="user.email" maxlength="50" />
    </el-form-item>
    <el-form-item label="用户性别">
      <el-radio-group v-model="user.sex">
        <el-radio :value="2">男</el-radio>
        <el-radio :value="1">女</el-radio>
        <el-radio :value="0">保密</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submit(formRef)">保存</el-button>
      <el-button type="danger" @click="reset(formRef)">重置</el-button>
    </el-form-item>
  </el-form> -->
</template>

<script setup lang="ts" name="EditorInfo">
import { ProForm, type FormSchemaProps, type ProFormInstance } from "work";
import { useFormRules } from "@/hooks/useFormRules";
import { useUserStore, type UserInfo, useLayoutStore } from "@/stores";
import { ElMessage } from "element-plus";
import { editOne } from "@/api/user/profile";

const props = defineProps<{ user: UserInfo }>();

const { user } = toRefs(props);
const emit = defineEmits(["reset-user"]);
const userStore = useUserStore();
const proFormRef = shallowRef<ProFormInstance>();

const { validatePhone } = useFormRules();
const rules = {
  username: [{ required: true, message: "用户名称不能为空", trigger: "blur" }],
  phone: [
    { required: true, message: "联系方式不能为空", trigger: "blur" },
    { validator: validatePhone, trigger: "blur" },
  ],
  email: [
    { required: true, message: "邮箱地址不能为空", trigger: "blur" },
    { type: "email", message: "请输入正确的邮箱地址", trigger: ["blur", "change"] },
  ],
} as any;

const submit = () => {
  proFormRef.value?.form?.validate(valid => {
    if (valid) {
      const { nickname, phone, email, sex } = user.value;

      editOne({ nickname, phone, email, sex }).then(res => {
        if (res.status === "success") {
          userStore.setUserInfo({ ...user.value });
          ElMessage({ message: "修改成功", type: "success" });
        } else reset();
      });

      proFormRef.value?.form?.clearValidate();
    }
  });
};

const reset = () => {
  proFormRef.value?.form?.clearValidate();
  emit("reset-user");
};

const elFormProps = {
  labelWidth: 80,
  rules: rules,
};

const schema: FormSchemaProps[] = [
  {
    prop: "nickname",
    label: "用户名称",
    el: "el-input",
    props: { clearable: true, placeholder: "请输入 用户名称", maxlength: "30" },
  },
  {
    prop: "phone",
    label: "联系方式",
    br: true,
    el: "el-input",
    props: { clearable: true, placeholder: "请输入 领导", maxlength: "11" },
  },
  {
    prop: "email",
    label: "用户邮箱",
    el: "el-input",
    props: { clearable: true, placeholder: "请输入 用户邮箱", maxlength: "50" },
  },
  {
    prop: "sex",
    label: "用户性别",
    el: "el-radio-group",
    fieldNames: { value: "dictValue", label: "dictLabel" },
    enum: () => useLayoutStore().getDictData("sys_user_sex"),
  },
];
</script>
