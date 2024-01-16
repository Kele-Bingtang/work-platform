package cn.youngkbt.datasource.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Kele-Bingtang
 * @date 2023/12/26 21:06
 * @note
 */
@Data
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceProperties {
    private String username;
    private String password;
    private String url;
    private String driverClassName;
    private String dbName;
    private String queryDsSql = "select * from rpt_db.datasource_conf where is_delete = 1";
}