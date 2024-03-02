import { ElMessageBox } from "element-plus";
import { exportJsonToExcel, formatJsonToArray } from "@work/utils";

// 导出
export const downloadFile = async (columns: any, data: any, fileName: string, msg: string, useProp = true) => {
  ElMessageBox.confirm(msg, "温馨提示", { type: "warning" }).then(() => {
    const tHeader = [] as string[];
    const propName = [] as string[];
    columns.forEach((item: any) => {
      if (!item.type && item.prop !== "operation") {
        propName.push(item.prop!);
        if (useProp) tHeader.push(item.prop!);
        else tHeader.push(item.label!);
      }
    });
    const filterVal = propName;
    const d = formatJsonToArray(data, filterVal);
    exportJsonToExcel(tHeader, d, fileName, undefined, undefined, true, "xlsx");
  });
};
