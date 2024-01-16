package cn.youngkbt.jwt.config;

import cn.youngkbt.jwt.properties.JwtProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author Kele-Bingtang
 * @date 2023/11/12 1:46
 * @note
 */
@AutoConfiguration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtAutoConfiguration {
}
