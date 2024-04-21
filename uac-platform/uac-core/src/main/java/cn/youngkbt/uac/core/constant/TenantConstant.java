package cn.youngkbt.uac.core.constant;

/**
 * @author Kele-Bingtang
 * @date 2024/4/20 22:59:09
 * @note
 */
public interface TenantConstant {

    /**
     * 超级管理员角色 roleKey
     */
    String SUPER_ADMIN_ROLE_KEY = "superadmin";

    /**
     * 租户管理员角色 roleKey
     */
    String TENANT_ADMIN_ROLE_KEY = "admin";

    /**
     * 租户管理员角色名称
     */
    String TENANT_ADMIN_ROLE_NAME = "管理员";

    /**
     * 默认租户ID
     */
    String DEFAULT_TENANT_ID = "000000";
    
    String DEFAULT_UAC_APP_ID = "1000000000000000000";
    
    String DEFAULT_ROLE_ID = "1000000000000000000";
}
