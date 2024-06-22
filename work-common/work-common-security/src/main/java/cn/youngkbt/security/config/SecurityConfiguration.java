package cn.youngkbt.security.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Kele-Bingtang
 * @date 2024/6/22 19:04:34
 * @note
 */
@AutoConfiguration
@ConditionalOnProperty(value = "security.enabled", havingValue = "true")
@ComponentScan("cn.youngkbt.security.domain")
public class SecurityConfiguration {
}
