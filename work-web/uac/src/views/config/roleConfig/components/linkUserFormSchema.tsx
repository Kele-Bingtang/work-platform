import type { Role } from "@/api/system/role";
import { listWithDisabledByRoleId } from "@/api/user/user";
import { useLayoutStore } from "@/stores";
import type { DialogFormSchemaProps } from "@work/components";
import { ElOption, ElSelect, ElDatePicker, ElRow, ElCol, type FormRules, dayjs } from "element-plus";

const rules = reactive<FormRules>({
  userIds: [{ required: true, message: "请选择用户", trigger: "blur" }],
  validFrom: [{ required: true, message: "请选择生效时间", trigger: "blur" }],
  expireOn: [{ required: true, message: "请选择过期时间", trigger: "blur" }],
});

export const elFormProps = {
  labelWidth: 80,
  rules: rules,
};

export const useFormSchema = (requestParams: { roleId: string }) => {
  const { getDictData } = useLayoutStore();

  // 选择时长后，计算出过期时间
  const selectChange = (form: any, value: number) => {
    if (!form || value === undefined) return;
    if (value === -1) form.expireOn = dayjs().add(99, "year").format("YYYY-MM-DD");
    else form.expireOn = dayjs().add(value, "year").format("YYYY-MM-DD");
  };

  const schema: DialogFormSchemaProps<Role.RoleLinkUser>[] = [
    {
      prop: "userIds",
      label: "用户选择",
      el: "user-select",
      props: { requestApi: listWithDisabledByRoleId, requestParams: requestParams, multiple: true },
      destroyIn: ["edit"],
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
      enum: () => getDictData("sys_expire_on"),
      render: ({ model, enumData }) => {
        return (
          <ElRow gutter={10} class="flex-1">
            <ElCol span={12}>
              <ElSelect
                v-model={model.expireOnNum}
                placeholder="请选择时长"
                style={{ width: "100%" }}
                onChange={(val: string) => selectChange(model, Number(val))}
                clearable
              >
                {enumData?.map((item: any) => (
                  <ElOption key={item.dictValue} label={item.dictLabel} value={item.dictValue} />
                ))}
              </ElSelect>
            </ElCol>
            <ElCol span={12}>
              <ElDatePicker
                v-model={model.expireOn}
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

  return { schema };
};
