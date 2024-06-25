package cn.youngkbt.core.config;

import cn.youngkbt.core.date.DatePatternPlus;
import cn.youngkbt.core.http.annotation.resolver.RequestUriArgumentResolver;
import cn.youngkbt.core.http.annotation.resolver.RequestUrlArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023/6/30 23:04
 * @note
 */
@Configuration
public class WebMvcConfiguration  implements WebMvcConfigurer {

    /**
     * 解决跨域问题
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true)
                .allowedOriginPatterns("*")
                .maxAge(3600);
    }

    /**
     * 增加 GET 请求参数中时间类型转换
     * <ul>
     * <li> HH:mm:ss -> LocalTime </li>
     * <li> yyyy-MM-dd -> LocalDate </li>
     * <li> yyyy-MM-dd HH:mm:ss -> LocalDateTime </li>
     * </ul>
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setTimeFormatter(DatePatternPlus.NORM_TIME_FORMATTER);
        registrar.setDateFormatter(DatePatternPlus.NORM_DATE_FORMATTER);
        registrar.setDateTimeFormatter(DatePatternPlus.NORM_DATETIME_FORMATTER);
        registrar.registerFormatters(registry);
    }

    /**
     * 添加自定义注解
     *
     * @param resolvers initially an empty list
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new RequestUrlArgumentResolver());
        resolvers.add(new RequestUriArgumentResolver());
    }
}