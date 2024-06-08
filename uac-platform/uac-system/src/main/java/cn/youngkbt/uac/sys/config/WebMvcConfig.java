package cn.youngkbt.uac.sys.config;

import cn.youngkbt.security.properties.SecurityProperties;
import cn.youngkbt.uac.sys.security.interceptor.SecurityInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Kele-Bingtang
 * @date 2024/4/27 00:01:34
 * @note 排除不需要认证的 URL
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final SecurityProperties securityProperties;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SecurityInterceptor(securityProperties));
    }
}
