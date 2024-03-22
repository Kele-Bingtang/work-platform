import { useLayoutStore } from "@/stores";
import { findItemNested } from "@work/components";
import { message } from "@work/utils";
import { ElMessageBox } from "element-plus";

export const useChange = (
  name: string,
  desc: string,
  editApi: (params: any, status: number) => Promise<http.Response<string>>,
  listApi: () => Promise<any> | undefined
) => {
  const statusChange = async (value: number, row: any) => {
    const statusEnum = await useLayoutStore().getDictData("sys_normal_status");
    const filterData = findItemNested(statusEnum.data, value + "", "dictValue", "");

    if (!filterData?.dictLabel) return (row.status = 1) && message.warning("不存在状态");

    ElMessageBox.confirm(
      `确认要 <span style="color: teal">${filterData?.dictLabel}</span> 【${row[name]}】${desc}吗`,
      "系统提示",
      {
        dangerouslyUseHTMLString: true,
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }
    )
      .then(() => {
        editApi(row, value)
          .then(res => {
            if (res.status === "success") message.success("修改成功") && nextTick(() => listApi && listApi());
            else {
              statusRecover(value, row);
            }
          })
          .catch(() => statusRecover(value, row));
      })
      .catch(() => statusRecover(value, row));
  };

  const statusRecover = (value: number, row: any) => {
    if (value === 0) return (row.status = 1);
    if (value === 1) return (row.status = 0);
  };

  return {
    statusChange,
  };
};
