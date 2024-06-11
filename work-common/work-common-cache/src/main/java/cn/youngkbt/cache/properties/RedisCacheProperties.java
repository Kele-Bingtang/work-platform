package cn.youngkbt.cache.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Kele-Bingtang
 * @date 2024/6/12 01:56:50
 * @note
 */
@Data
@ConfigurationProperties("cache.redis")
public class RedisCacheProperties {
    /**
     * 缓存过期使劲按，单位为秒，默认 3600 秒
     */
    private long expire = 3600;
}
