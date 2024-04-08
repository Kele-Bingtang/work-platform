package cn.youngkbt.uac.core.constant;

/**
 * @author Kele-Bingtang
 * @date 2023/11/13 22:31
 * @note
 */
public interface AuthConstant {
    
    String LOGIN_SUCCESS = "Success";
    String LOGIN_FAIL = "Error";
    String LOGOUT = "Logout";
    String REGISTER = "Register";
    String TENANT_ID = "tenantId";
    
    String USERNAME = "username";
    String PASSWORD = "password";
    
    String CLIENT_NAME = "clientName";
    
    /**
     * 用户名长度限制
     */
    int USERNAME_MIN_LENGTH = 2;
    int USERNAME_MAX_LENGTH = 20;

    /**
     * 密码长度限制
     */
    int PASSWORD_MIN_LENGTH = 5;
    int PASSWORD_MAX_LENGTH = 20;
}
