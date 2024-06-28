import { Icon, useDialog, ProForm, type FormSchemaProps, message } from "work";
import { Plus } from "@element-plus/icons-vue";
import { unref, ref } from "vue";
import { addTeam } from "@/api/team";
import { useUserStore } from "@/stores";
import { useRoutes } from "@/hooks";

export const AddTeamMenu = () => {
  return (
    <div class="theme-color-hover" style="background: transparent;" onClick={openDialog}>
      <Icon icon={Plus} style="color: inherit;" />
      <span>新建团队</span>
    </div>
  );
};

interface TeamModel {
  name: string;
  description: string;
}

const openDialog = () => {
  const { open } = useDialog();

  const model = ref<Partial<TeamModel>>({});

  const schema: FormSchemaProps[] = [
    {
      prop: "teamName",
      label: "团队名称",
      el: "el-input",
      props: { placeholder: "请输入团队描述" },
      formItem: { rules: [{ required: true, message: "请输入 团队名称" }] },
    },
    {
      prop: "description",
      label: "团队描述",
      el: "el-input",
      props: { type: "textarea", placeholder: "请输入 团队描述" },
    },
  ];

  open({
    title: "新建团队",
    height: 170,
    onConfirm: () => handleConfirm(unref(model)),
    render: () => (
      <ProForm
        v-model={model.value}
        schema={schema}
        el-form-props={{ labelWidth: 80 }}
        row-props={{ col: { span: 24 } }}
      />
    ),
  });
};

const handleConfirm = async (model: Partial<TeamModel>) => {
  const res = await addTeam(model);
  if (res.code === 200) {
    useRoutes().initDynamicRouters(useUserStore().roles);
    message.success("团队新建成功");
  }
};
