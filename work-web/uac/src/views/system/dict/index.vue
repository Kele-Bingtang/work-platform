<template>
  <div :class="prefixClass">
    <TreeFilter
      ref="treeFilterRef"
      title="App 清单"
      :requestApi="getAppTreeList"
      @change="handleTreeChange"
      id="appId"
      label="appName"
    >
      <template #default="{ node }">
        <Icon v-if="node.data.icon" :icon="node.data.icon"></Icon>
        <span>{{ node.label }}</span>
      </template>
    </TreeFilter>

    <div :class="`${prefixClass}__table`">
      <ProTable
        ref="proTableRef"
        :request-api="listPage"
        :columns="columns"
        :init-request-param="initRequestParam"
        :search-cols="{ xs: 1, sm: 1, md: 2, lg: 3, xl: 3 }"
        :dialogForm="dialogForm"
        :exportFile
        :disabled-button="!hasAuth('system:dict:export') ? ['export'] : []"
      ></ProTable>
    </div>

    <BasicDrawer v-model="drawer" size="55%" title="字典数据配置">
      <div :class="`${prefixClass}__drawer-content`">
        <DictData
          :dict-code="dictInfo?.dictCode || ''"
          :app-id="dictInfo?.appId || ''"
          :is-cascade="dictInfo?.isCascade || 0"
        />
      </div>
    </BasicDrawer>
  </div>
</template>

<script setup lang="tsx" name="DictType">
import { ProTable, TreeFilter, BasicDrawer, Point, downloadByData } from "work";
import { getAppTreeList } from "@/api/application/app";
import { listPage, addOne, editOne, deleteOne, type DictType, exportExcel } from "@/api/system/dictType";
import { type DialogForm, type TableColumnProps, type TreeFilterInstance } from "@work/components";
import DictData from "./dictData.vue";
import { ElLink, ElMessageBox } from "element-plus";
import { dictTypeElFormProps, useFormSchema } from "./useFormSchema";
import { baseEnum } from "@work/constants";
import { useDesign } from "@work/hooks";
import { usePermission } from "@/hooks";

const { getPrefixClass } = useDesign();
const prefixClass = getPrefixClass("dict-type");

const initRequestParam = reactive({
  appId: "",
});

const dictInfo = ref<DictType.DictTypeInfo>();
const drawer = ref(false);
const treeFilterRef = shallowRef<TreeFilterInstance>();

const clickDictCode = (row: DictType.DictTypeInfo) => {
  dictInfo.value = row;
  drawer.value = true;
};

const handleTreeChange = (nodeId: number) => {
  initRequestParam.appId = nodeId + "";
};

const isCascadeColorMap: Record<string, any> = {
  0: "#909399",
  1: "#395ae3",
};

const columns: TableColumnProps<DictType.DictTypeInfo>[] = [
  { type: "index", label: "#", width: 80 },
  {
    prop: "dictCode",
    label: "字典编码",
    search: { el: "el-input" },
    render: ({ row }) => {
      return (
        <>
          <ElLink type="primary" underline={false} onClick={() => clickDictCode(row)}>
            {row.dictCode}
          </ElLink>
        </>
      );
    },
  },
  { prop: "dictName", label: "字典名称", search: { el: "el-input" } },
  {
    prop: "isCascade",
    label: "是否级联",
    width: 110,
    enum: baseEnum,
    search: { el: "el-input" },
    render: ({ row }) => {
      return <Point color={isCascadeColorMap[row.isCascade]}>{row._enum.isCascade}</Point>;
    },
  },
  { prop: "intro", label: "描述" },
  { prop: "createTime", label: "创建时间", sortable: true },
  { prop: "operation", label: "操作", width: 160, fixed: "right" },
];

const { hasAuth } = usePermission();

const dialogForm: DialogForm = {
  formProps: {
    elFormProps: dictTypeElFormProps,
    schema: useFormSchema(
      computed(() => treeFilterRef.value?.treeData),
      computed(() => initRequestParam.appId)
    ).dictTypeSchema,
  },
  id: ["id", "dictTypeId"],
  addApi: addOne,
  editApi: editOne,
  removeApi: deleteOne,
  disableAdd: !hasAuth("system:dict:add"),
  disableEdit: !hasAuth("system:dict:edit"),
  disableRemove: !hasAuth("system:dict:remove"),
  dialog: {
    title: (_, status) => (status === "add" ? "新增" : "编辑"),
    width: "45%",
    height: 300,
    top: "5vh",
    closeOnClickModal: false,
  },
};

const exportFile = (_: Record<string, any>[], searchParam: Record<string, any>) => {
  ElMessageBox.confirm("确认导出吗？", "温馨提示", { type: "warning" }).then(() => {
    exportExcel(searchParam).then(res => {
      downloadByData(res, `dictType_${new Date().getTime()}.xlsx`);
    });
  });
};
</script>

<style lang="scss" scoped>
$prefix-class: #{$admin-namespace}-dict-type;

.#{$prefix-class} {
  display: flex;
  flex: 1;

  &__table {
    width: calc(100% - 230px);
    height: 100%;

    :deep(.#{$el-namespace}-dialog__body) {
      margin-left: 20px;
    }
  }

  &__drawer-content {
    width: 100%;
    height: 100%;
    background-color: #f0f2f5;
  }
}
</style>
