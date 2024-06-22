package cn.youngkbt.ag.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Kele-Bingtang
 * @date 2024/6/19 22:23:02
 * @note
 */
@Data
@Component
@ConfigurationProperties(prefix = "ag")
public class AgConfig {
    /**
     * 项目名称
     */
    private String name;

    /**
     * 版本
     */
    private String version;

    /**
     * 版权年份
     */
    private String copyrightYear;
}
