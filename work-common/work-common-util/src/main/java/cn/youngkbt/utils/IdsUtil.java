package cn.youngkbt.utils;

import java.util.UUID;

/**
 * @author Kele-Bingtang
 * @date 2023/9/24 3:02
 * @note
 */
public class IdsUtil {
    public static String simpleUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String UUID() {
        return UUID.randomUUID().toString();
    }
}
