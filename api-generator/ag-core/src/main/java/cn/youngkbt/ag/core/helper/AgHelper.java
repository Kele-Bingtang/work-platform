package cn.youngkbt.ag.core.helper;

import cn.youngkbt.security.utils.LoginHelper;

/**
 * @author Kele-Bingtang
 * @date 2024/6/22 16:36:56
 * @note API Generator 操作 Redis 工具类
 */
public class AgHelper extends LoginHelper {

    static {
        LoginHelper.prefixCacheKey = "ag";
    }

    /**
     * 初始化 static 的代码
     */
    public static void init() {
    }
}
