package cn.youngkbt.security.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Kele-Bingtang
 * @date 2024/6/22 18:54:07
 * @note
 */
@AutoConfiguration
@ConditionalOnProperty(value = "security.enabled", havingValue = "false", matchIfMissing = true)
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@ComponentScan({"cn.youngkbt.security.domain"})
public class SecurityDisabledConfiguration {
}
