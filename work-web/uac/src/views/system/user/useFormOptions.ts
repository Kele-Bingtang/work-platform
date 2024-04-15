import { useLayoutStore } from "@/stores";
import type { FormOptionsProps } from "@work/components";
import type { FormRules } from "element-plus";
import { getRolePostList, type User } from "@/api/user/user";
import { listDeptTreeList } from "@/api/system/dept";
import { useFormRules } from "@/hooks/useFormRules";

const { validatePassword, validatePhone } = useFormRules();

const rules = reactive<FormRules>({
  username: [{ required: true, message: "请输入用户名称", trigger: "blur" }],
  password: [{ required: true, validator: validatePassword, trigger: "blur" }],
  deptId: [{ required: true, message: "请选择部门", trigger: "blur" }],
  phone: [{ validator: validatePhone, trigger: "blur" }],
  email: [{ type: "email", message: "请输入正确的邮箱", trigger: ["blur", "change"] }],
});

export const useFormOptions = (defaultValue: ComputedRef<string>) => {
  const options: FormOptionsProps<User.UserInfo> = {
    form: {
      inline: true,
      labelPosition: "top",
      labelWidth: 80,
      size: "default",
      fixWidth: true,
      rules: rules,
    },
    columns: [
      {
        formItem: { label: "用户名称", prop: "username" },
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
          hidden: ["edit"],
          defaultValue: "123456",
          props: { clearable: true, placeholder: "请输入 邮箱", type: "password", showPassword: true },
        },
      },
      {
        formItem: { label: "性别", prop: "sex" },
        attrs: {
          el: "el-select",
          enum: () => useLayoutStore().getDictData("sys_user_sex"),
          fieldNames: { value: "dictValue", label: "dictLabel" },
          props: { clearable: true, placeholder: "请选择 性别" },
        },
      },
      {
        formItem: { label: "生日", prop: "birthday" },
        attrs: { el: "el-date-picker", props: { clearable: true, placeholder: "请选择 生日" } },
      },
      {
        formItem: { label: "邮箱", prop: "email" },
        attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 邮箱" } },
      },
      {
        formItem: { label: "电话号码", prop: "phone" },
        attrs: { el: "el-input", props: { clearable: true, placeholder: "请输入 电话号码" } },
      },
      {
        formItem: { label: "部门", prop: "deptId" },
        attrs: {
          el: "el-tree-select",
          defaultValue: defaultValue,
          props: { clearable: true, defaultExpandAll: true, placeholder: "请选择 部门" },
          enum: listDeptTreeList,
        },
      },
      {
        formItem: { label: "岗位", prop: "postId" },
        attrs: {
          el: "el-select",
          enum: () => getRolePostList(),
          enumKey: "postList",
          fieldNames: { value: "postId", label: "postName" },
          props: { clearable: true, placeholder: "请选择 岗位" },
        },
      },
      {
        formItem: { label: "角色", prop: "roleId" },
        attrs: {
          el: "el-select",
          useEnumMap: enumMap => enumMap.get("postId")?.roleList,
          fieldNames: { value: "roleId", label: "roleName" },
          props: { clearable: true, placeholder: "请选择 角色" },
        },
      },
    ],
  };

  return { options };
};
