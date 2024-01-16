package cn.youngkbt.datasource.annotation;

import cn.youngkbt.datasource.config.DynamicDataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Kele-Bingtang
 * @date 2023/12/26 20:58
 * @note 启动多数据源
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(DynamicDataSourceAutoConfiguration.class)
public @interface EnableDynamicDataSource {
}