package cn.youngkbt.uac.core.constant;

import cn.youngkbt.core.constants.GlobalConstant;

/**
 * @author Kele-Bingtang
 * @date 2024/6/12 22:49:24
 * @note
 */
public interface CacheNameConstant {
    /**
     * 数据字典
     */
    String SYS_DICT = GlobalConstant.GLOBAL_REDIS_KEY + "sys_dict";

    /**
     * 租户
     */
    String SYS_TENANT = GlobalConstant.GLOBAL_REDIS_KEY + "sys_tenant#30d";

}
