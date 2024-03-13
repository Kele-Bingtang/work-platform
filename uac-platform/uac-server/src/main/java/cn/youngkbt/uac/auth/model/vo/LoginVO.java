package cn.youngkbt.uac.auth.model.vo;

import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2023/11/12 14:37
 * @note 返回的登录信息
 */
@Data
public class LoginVO {
    /**
     * 授权令牌
     */
    private String accessToken;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * 授权令牌 access_token 的有效期
     */
    private Long expireIn;

    /**
     * 刷新令牌 refresh_token 的有效期
     */
    private Long refreshExpireIn;
}
