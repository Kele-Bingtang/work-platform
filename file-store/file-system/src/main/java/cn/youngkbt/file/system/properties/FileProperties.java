package cn.youngkbt.file.system.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Kele-Bingtang
 * @date 2024/8/5 21:57:44
 * @note
 */
@ConfigurationProperties(prefix = "file")
@Data
@Component
public class FileProperties {
    private String fileStorePath;
    private Integer expireTime;
}
