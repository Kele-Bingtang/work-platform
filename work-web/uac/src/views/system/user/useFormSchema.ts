import { useLayoutStore } from "@/stores";
import type { DialogFormSchemaProps } from "@work/components";
import type { FormRules } from "element-plus";
import { type User } from "@/api/user/user";
import { userSelectPostList } from "@/api/system/post";
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

export const elFormProps = {
  labelPosition: "top",
  labelWidth: 80,
  rules: rules,
};

export const useFormSchema = (defaultValue: ComputedRef<string>) => {
  const schema: DialogFormSchemaProps<User.UserInfo>[] = [
    {
      prop: "username",
      label: "用户名称",
      el: "el-input",
      props: { clearable: true, placeholder: "请输入 用户名称" },
    },
    {
      prop: "status",
      label: "状态",
      defaultValue: "1",
      el: "el-select",
      fieldNames: { value: "dictValue", label: "dictLabel" },
      enum: () => useLayoutStore().getDictData("sys_normal_status"),
    },
    {
      prop: "nickname",
      label: "用户昵称",
      el: "el-input",
      hiddenIn: ["edit"],
      props: { clearable: true, placeholder: "请输入 用户昵称" },
    },
    {
      prop: "password",
      label: "密码",
      el: "el-input",
      hiddenIn: ["edit"],
      defaultValue: "123456",
      props: { clearable: true, placeholder: "请输入 密码", type: "password", showPassword: true },
    },
    {
      prop: "sex",
      label: "性别",
      el: "el-select",
      valueFormat: "string",
      enum: () => useLayoutStore().getDictData("sys_user_sex"),
      fieldNames: { value: "dictValue", label: "dictLabel" },
      props: { clearable: true, placeholder: "请选择 性别" },
    },
    {
      prop: "birthday",
      label: "生日",
      el: "el-date-picker",
      props: { clearable: true, placeholder: "请选择 生日" },
    },
    {
      prop: "email",
      label: "邮箱",
      el: "el-input",
      props: { clearable: true, placeholder: "请输入 邮箱" },
    },
    {
      prop: "phone",
      label: "电话号码",
      el: "el-input",
      props: { clearable: true, placeholder: "请输入 电话号码" },
    },
    {
      prop: "deptId",
      label: "部门",
      el: "el-tree-select",
      defaultValue: defaultValue,
      props: { clearable: true, defaultExpandAll: true, placeholder: "请选择 部门" },
      enum: () => listDeptTreeList(),
    },
    {
      prop: "postId",
      label: "岗位",
      el: "el-select",
      defaultValue: (_, enumMap) => enumMap.get("postId")?.postIds,
      enum: form => userSelectPostList(form.userId),
      useCacheEnum: false,
      enumKey: "postList",
      fieldNames: { value: "postId", label: "postName" },
      props: {
        clearable: true,
        multiple: true,
        collapseTags: true,
        collapseTagsTooltip: true,
        maxCollapseTags: 1,
        placeholder: "请选择 岗位",
      },
    },
  ];

  return { schema };
};
