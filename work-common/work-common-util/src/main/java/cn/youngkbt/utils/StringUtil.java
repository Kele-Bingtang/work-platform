package cn.youngkbt.utils;

import org.springframework.util.StringUtils;

/**
 * @author Kele-Bingtang
 * @date 2023/11/26 23:19
 * @note
 */
public class StringUtil extends StringUtils {

    public static boolean hasText(String... content) {
        for (String s : content) {
            if (!hasText(s)) {
                return false;
            }
        }
        return true;
    }
    
}
