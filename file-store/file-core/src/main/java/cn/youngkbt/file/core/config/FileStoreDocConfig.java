package cn.youngkbt.file.core.config;

import cn.youngkbt.doc.config.SpringDocAutoConfiguration;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Kele-Bingtang
 * @date 2024/8/5 00:51:12
 * @note
 */
@Configuration(proxyBeanMethods = false)
public class FileStoreDocConfig {
    /**
     * system 模块的 API 分组
     */
    @Bean
    public GroupedOpenApi agGroupedOpenApi() {
        return SpringDocAutoConfiguration.buildGroupedOpenApi("File Store");
    }
}
