package cn.youngkbt.websocket.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Kele-Bingtang
 * @date 2024/6/9 19:25:45
 * @note WebSocket 配置项
 */
@Data
@ConfigurationProperties("websocket")
public class WebSocketProperties {

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 路径
     */
    private String path = "/websocket";

    /**
     *  设置访问源地址
     */
    private String allowedOrigins = "*";
}
