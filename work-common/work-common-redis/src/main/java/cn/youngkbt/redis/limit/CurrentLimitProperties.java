package cn.youngkbt.redis.limit;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Kele-Bingtang
 * @date 2023/6/30 23:01
 * @note
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "b2b.current-limit")
public class CurrentLimitProperties {

    /**
     * 访问次数
     */
    private Long limit;

    /**
     * 有效期,单位秒
     */
    private Long expire;

    /**
     * 限流urls
     */
    private String[] urls;

}
