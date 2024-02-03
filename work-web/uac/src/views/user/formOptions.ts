import { useLayoutStore } from "@/stores/layout";
import type { FormOptionsProps } from "@work/components";

export const options: FormOptionsProps = {
  form: {
    inline: true,
    labelPosition: "top",
    labelWidth: "50px",
    size: "default",
    fixWidth: true,
  },
  columns: [
    {
      formItem: { label: "用户名称", prop: "username", required: true },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 用户名称" } },
    },
    {
      formItem: { label: "用户昵称", prop: "nickname" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 用户昵称" } },
    },
    {
      formItem: { label: "密码", prop: "password" },
      attrs: {
        el: "el-input",
        show: ["add"],
        defaultValue: "123456",
        props: { clearable: true, placeholder: "请输入 邮箱", type: "password", showPassword: true },
      },
    },
    {
      formItem: { label: "邮箱", prop: "email" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 邮箱" } },
    },
    {
      formItem: { label: "性别", prop: "sex" },
      attrs: {
        el: "el-select",
        // enum: [
        //   { value: 0, label: "保密" },
        //   { value: 1, label: "男" },
        //   { value: 2, label: "女" },
        // ],
        enum: () => useLayoutStore().getDictData("sys_user_sex"),
        fieldNames: { value: "dictValue", label: "dictLabel" },
        props: { clearable: true, placeholder: "请输入 性别" },
      },
    },
    {
      formItem: { label: "生日", prop: "birthday" },
      attrs: { el: "el-date-picker", props: { clearable: true, placeholder: "请选择 生日" } },
    },
    {
      formItem: { label: "电话号码", prop: "phone", required: true },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 电话号码" } },
    },
    {
      formItem: { label: "部门", prop: "deptId" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 部门" } },
    },
    {
      formItem: { label: "岗位", prop: "postId" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 岗位" } },
    },
    {
      formItem: { label: "头像上传", prop: "avatar" },
      attrs: { el: "el-input", props: { clearable: true, placeholder: "头像上传" } },
    },
  ],
};
