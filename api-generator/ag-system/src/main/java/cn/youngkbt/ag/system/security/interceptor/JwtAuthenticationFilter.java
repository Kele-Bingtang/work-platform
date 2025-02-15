package cn.youngkbt.ag.system.security.interceptor;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.security.enumeration.AuthErrorCodeEnum;
import cn.youngkbt.security.utils.SecurityUtils;
import cn.youngkbt.web.utils.ServletUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

/**
 * @author Kele-Bingtang
 * @date 2022/12/10 21:43
 * @note 用户访问项目的资源前，先进行验证 token
 */
@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            // 访问项目前，获取 request 的 token, 并检查登录状态
            SecurityUtils.checkAuthentication(request);
        } catch (Exception e) {
            log.error("认证检查异常：{}", e.getMessage());
            ServletUtil.writeJSON(response, HttpResult.fail(AuthErrorCodeEnum.UN_AUTHORIZED));
            return;
        }
        // 释放目标资源到其他拦截器
        chain.doFilter(request, response);
    }
}