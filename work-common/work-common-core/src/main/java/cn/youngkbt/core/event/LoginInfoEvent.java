package cn.youngkbt.core.event;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Kele-Bingtang
 * @date 2023/11/17 0:28
 * @note 登录信息事件类
 */
@Data
@Builder
public class LoginInfoEvent implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 租户 ID
     */
    private String tenantId;

    /**
     * 用户 ID
     */
    private String userId;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 应用 ID
     */
    private String appId;

    /**
     * 登录状态 0成功 1失败
     */
    private String status;

    /**
     * 提示消息
     */
    private String message;

    /**
     * 请求体
     */
    private HttpServletRequest request;

    /**
     * 其他参数
     */
    private Object[] args;
}