package cn.youngkbt.ag.config;

import cn.youngkbt.ag.system.security.UserDetailsServiceImpl;
import cn.youngkbt.ag.system.security.handler.LoginFailureHandler;
import cn.youngkbt.ag.system.security.handler.LoginSuccessHandler;
import cn.youngkbt.ag.system.security.interceptor.JwtAuthenticationFilter;
import cn.youngkbt.helper.SpringHelper;
import cn.youngkbt.security.handle.WorkAccessDeniedHandler;
import cn.youngkbt.security.handle.WorkAuthenticationEntryPoint;
import cn.youngkbt.security.handle.WorkLogoutSuccessHandler;
import cn.youngkbt.security.properties.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Kele-Bingtang
 * @date 2023/11/15 22:32
 * @note Web 安全配置
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(SecurityProperties.class)
@EnableMethodSecurity
public class WebSecurityConfig {

    public static final String LOGIN_URL = "/auth/login";

    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final SecurityProperties securityProperties;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(this::checkCsrfEnvironment)
                // 使用 JWT，所以禁用 session 机制，因此 SecurityContextHolder.getContext().getAuthentication() 始终为 null
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers(securityProperties.getExcludes()).permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .formLogin(form -> form.successHandler(loginSuccessHandler())
                        .failureHandler(loginFailureHandler()))
                .logout(logout -> logout.logoutSuccessHandler(workLogoutSuccessHandler()))
                .exceptionHandling(exception -> exception.accessDeniedHandler(workAccessDeniedHandler())
                        .authenticationEntryPoint(workAuthenticationEntryPoint()));

        http.addFilterBefore(new JwtAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
        http.userDetailsService(userDetailsService);
        return http.build();
    }
    
    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    @Bean
    public WorkAccessDeniedHandler workAccessDeniedHandler() {
        return new WorkAccessDeniedHandler();
    }

    @Bean
    public WorkAuthenticationEntryPoint workAuthenticationEntryPoint() {
        return new WorkAuthenticationEntryPoint();
    }

    @Bean
    public WorkLogoutSuccessHandler workLogoutSuccessHandler() {
        return new WorkLogoutSuccessHandler();
    }


    /**
     * 获取 AuthenticationManager（认证管理器），登录时认证使用
     */
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    public CsrfConfigurer<HttpSecurity> checkCsrfEnvironment(CsrfConfigurer<HttpSecurity> csrf) {
        String profiles = SpringHelper.getProperty("spring.profiles.active");
        if (profiles.contains("dev")) {
            csrf.disable();
        } else {
            csrf.ignoringRequestMatchers(LOGIN_URL);
        }
        return csrf;
    }
}
