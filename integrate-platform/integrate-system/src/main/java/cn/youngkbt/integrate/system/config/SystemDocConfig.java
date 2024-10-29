package cn.youngkbt.integrate.system.config;

import cn.youngkbt.doc.config.SpringDocAutoConfiguration;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Kele-Bingtang
 * @date 2024/10/29 20:17:07
 * @note
 */
@Configuration(proxyBeanMethods = false)
public class SystemDocConfig {
    /**
     * system 模块的 API 分组
     */
    @Bean
    public GroupedOpenApi systemGroupedOpenApi() {
        return SpringDocAutoConfiguration.buildGroupedOpenApi("integrate");
    }
}