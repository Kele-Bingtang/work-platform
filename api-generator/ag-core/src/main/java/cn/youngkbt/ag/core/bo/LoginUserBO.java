package cn.youngkbt.ag.core.bo;

import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2024/6/22 16:40:32
 * @note
 */
@Data
public class LoginUserBO {
    /**
     * 用户名
     */
    private String username;
    /**
     * "用户密码
     */
    private String password;
}
