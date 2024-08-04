<template>
  <el-form ref="formRef" :model="password" :rules="rules" label-width="80px">
    <el-form-item label="旧密码" prop="oldPassword">
      <el-input v-model="password.oldPassword" placeholder="请输入旧密码" type="password" show-password />
    </el-form-item>
    <el-form-item label="新密码" prop="newPassword">
      <el-input v-model="password.newPassword" placeholder="请输入新密码" type="password" show-password />
    </el-form-item>
    <el-form-item label="确认密码" prop="confirmPassword">
      <el-input v-model="password.confirmPassword" placeholder="请确认新密码" type="password" show-password />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submit()">保存</el-button>
      <el-button type="danger" @click="reset()">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script setup lang="ts" name="Account">
import { updatePassword } from "@/api/user";
import { ElMessage, type FormInstance } from "element-plus";

const password = reactive({
  oldPassword: "",
  newPassword: "",
  confirmPassword: "",
});
const formRef = ref<FormInstance>();

const equalToPassword = (rule: any, value: string, callback: (info?: string) => void) => {
  if (password.newPassword !== value) {
    callback("两次输入的密码不一致");
  } else {
    callback();
  }
};

const rules = {
  oldPassword: [{ required: true, message: "旧密码不能为空", trigger: "blur" }],
  newPassword: [
    { required: true, message: "新密码不能为空", trigger: "blur" },
    { min: 4, max: 20, message: "长度在 4 到 20 个字符", trigger: "blur" },
  ],
  confirmPassword: [
    { required: true, message: "确认密码不能为空", trigger: "blur" },
    { required: true, validator: equalToPassword, trigger: "blur" },
  ],
};

const submit = async () => {
  await formRef.value?.validate(valid => {
    if (valid) {
      const { oldPassword, newPassword, confirmPassword } = password;
      updatePassword({ oldPassword, newPassword, confirmPassword }).then(res => {
        if (res.status === "success") {
          ElMessage({ message: "修改密码成功！", type: "success" });
        } else reset();
      });
    }
  });
};

const reset = () => {
  formRef.value?.resetFields();
  password.oldPassword = "";
  password.newPassword = "";
  password.confirmPassword = "";
};
</script>
