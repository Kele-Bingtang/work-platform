package cn.youngkbt.ag.auth.model.vo;

import lombok.Data;

/**
 * @author Kele-Bingtang
 * @date 2024/6/22 16:14:43
 * @note
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
