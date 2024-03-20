import { listDisabledGroupId } from "@/api/user/base";
import type { UserGroup } from "@/api/user/userGroup";
import { useLayoutStore } from "@/stores/layout";
import type { FormOptionsProps } from "@work/components";
import { ElOption, ElSelect, ElDatePicker, ElRow, ElCol, type FormRules, dayjs } from "element-plus";
import { UserSelect } from "work";

const rules = reactive<FormRules>({
  userIds: [{ required: true, message: "请选择用户", trigger: "blur" }],
  validFrom: [{ required: true, message: "请选择生效时间", trigger: "blur" }],
  expireOn: [{ required: true, message: "请选择过期时间", trigger: "blur" }],
});

export const useFormOptions = (appId: string, userGroupId: string) => {
  const { getDictData } = useLayoutStore();

  // 选择时长后，计算出过期时间
  const selectChange = (form: any, value: number) => {
    if (!form || value === undefined) return;
    if (value === -1) form.expireOn = dayjs().add(99, "year").format("YYYY-MM-DD");
    else form.expireOn = dayjs().add(value, "year").format("YYYY-MM-DD");
  };

  const options: FormOptionsProps<UserGroup.UserLinkInfo> = {
    form: { inline: true, labelPosition: "right", labelWidth: 80, size: "default", fixWidth: true, rules: rules },
    columns: [
      {
        formItem: { label: "用户名", prop: "userIds", br: true },
        attrs: {
          destroy: ["edit"],
          render: ({ scope }) => {
            return (
              <>
                <UserSelect
                  vModel={scope.form.userIds}
                  requestApi={listDisabledGroupId}
                  requestParams={{ appId, userGroupId }}
                ></UserSelect>
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
          enum: () => getDictData("sys_expire_on"),
          render: ({ scope, enumData }) => {
            return (
              <ElRow gutter={10}>
                <ElCol span={12}>
                  <ElSelect
                    vModel={scope.form.expireOnNum}
                    placeholder="请选择时长"
                    style={{ width: "100%" }}
                    onChange={(val: string) => selectChange(scope.form, Number(val))}
                    clearable
                  >
                    {enumData?.map(item => (
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
                    value-format="YYYY-MM-DD"
                  />
                </ElCol>
              </ElRow>
            );
          },
        },
      },
    ],
  };

  return { options };
};
