package cn.youngkbt.jwt.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Kele-Bingtang
 * @date 2023/11/6 23:01
 * @note
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private String id;
    /**
     * 有效期 12 小时：12 * 60 * 60 * 1000
     */
    private long expireTime;
}
