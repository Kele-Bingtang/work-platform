package cn.youngkbt.excel.listen;

import cn.youngkbt.excel.dict.ExcelDictManager;
import cn.youngkbt.excel.model.ExcelResult;
import com.alibaba.excel.read.listener.ReadListener;

/**
 * @author Kele-Bingtang
 * @date 2024/6/10 18:13:02
 * @note Excel 导入监听
 */
public interface ExcelListener<T> extends ReadListener<T> {

    /**
     * 获取 Excel 解析结果
     */
    ExcelResult<T> getExcelResult();

    /**
     * 删除 Dict 缓存
     */
    default void removeExcelDictCache() {
        ExcelDictManager.remove();
    }
}
