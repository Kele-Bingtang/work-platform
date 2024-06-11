package cn.youngkbt.cache.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Kele-Bingtang
 * @date 2024/6/12 01:50:06
 * @note Caffeine 配置类
 */
@Data
@ConfigurationProperties("cache.caffeine")
public class CaffeineCacheProperties {
    /**
     * 缓存过期时间，单位秒
     */
    private Long expire = 30L;
    
    /**
     * 缓存初始化容量
     */
    private int capacity = 100;
    
    /**
     * 缓存最大容量
     */
    private Long maximumSize = 1000L;
}
