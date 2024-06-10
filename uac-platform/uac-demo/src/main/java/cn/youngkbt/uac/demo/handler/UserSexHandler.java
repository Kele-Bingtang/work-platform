package cn.youngkbt.uac.demo.handler;

import cn.youngkbt.excel.dict.ExcelDictHandler;

/**
 * @author Kele-Bingtang
 * @date 2024/6/10 20:33:00
 * @note
 */
public class UserSexHandler implements ExcelDictHandler {
    @Override
    public String getLabel(String value) {
        if (value.equals("1")) {
            return "男";
        } else if (value.equals("2")) {
            return "女";
        }
        return "保密";
    }

    @Override
    public String getValue(String label) {
        if (label.equals("男")) {
            return "1";
        } else if (label.equals("女")) {
            return "2";
        }
        return "3";
    }
}
