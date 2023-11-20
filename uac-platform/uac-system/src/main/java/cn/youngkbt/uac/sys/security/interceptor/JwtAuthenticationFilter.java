package cn.youngkbt.uac.sys.security.interceptor;

import cn.youngkbt.security.utils.SecurityUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * @author Kele-Bingtang
 * @date 2022/12/10 21:43
 * @note 用户访问项目的资源前，先进行验证 token
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 访问项目前，获取 request 的 token, 并检查登录状态
        SecurityUtils.checkAuthentication(request);
        // 释放目标资源到其他拦截器
        chain.doFilter(request, response);
    }
}