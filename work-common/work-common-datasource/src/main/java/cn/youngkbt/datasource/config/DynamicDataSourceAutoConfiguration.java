package cn.youngkbt.datasource.config;

import cn.youngkbt.datasource.processor.LastParamDsProcessor;
import cn.youngkbt.datasource.properties.DataSourceProperties;
import cn.youngkbt.datasource.support.DateSourceHelper;
import com.baomidou.dynamic.datasource.creator.DefaultDataSourceCreator;
import com.baomidou.dynamic.datasource.processor.DsProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * @author Kele-Bingtang
 * @date 2023/12/26 20:59
 * @note
 */
@AutoConfiguration
@EnableConfigurationProperties(DataSourceProperties.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@Import(DateSourceHelper.class)
public class DynamicDataSourceAutoConfiguration {
    @Bean
    public DynamicDataSourceProvider dynamicDataSourceProvider(DefaultDataSourceCreator defaultDataSourceCreator, DataSourceProperties dataSourceProperties) {
        return new DynamicDataSourceProvider(defaultDataSourceCreator, dataSourceProperties);
    }

    @Bean
    public DsProcessor dsProcessor() {
        return new LastParamDsProcessor();
    }
}