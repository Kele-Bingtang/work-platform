package cn.youngkbt.security.utils;

import cn.hutool.core.convert.Convert;
import cn.youngkbt.helper.SpringHelper;
import cn.youngkbt.security.JwtAuthenticationToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import java.util.Objects;


/**
 * @author Kele-Bingtang
 * @date 2022/12/10 21:42
 * @note Spring Security 认证工具
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtils {

    /**
     * 系统登录认证
     *
     * @param request               客户端请求
     * @param username              当前用户名
     * @param password              当前用户密码
     * @param authenticationManager 认证对象
     * @return 认证后的用户信息，内容包括 token
     */
    public static JwtAuthenticationToken login(HttpServletRequest request, String username, String password, AuthenticationManager authenticationManager) {
        return login(request, username, password, authenticationManager, null);
    }

    /**
     * 系统登录认证
     *
     * @param request               客户端请求
     * @param username              当前用户名
     * @param password              当前用户密码
     * @param authenticationManager 认证对象
     * @param expireTime            Token 有效期
     * @return 认证后的用户信息，内容包括 token
     */
    public static JwtAuthenticationToken login(HttpServletRequest request, String username, String password, AuthenticationManager authenticationManager, Long expireTime) {
        JwtAuthenticationToken token = new JwtAuthenticationToken(username, password);
        // token 存入 request 的相关信息
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // 执行登录认证过程，获取调用 UserDetailsServiceImpl 的 loadUserByUsername 方法
        Authentication authentication = authenticationManager.authenticate(token);
        // 认证成功存储认证信息到上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成令牌并返回给客户端 
        token.setAccessToken(Objects.nonNull(expireTime) ? JwtTokenUtils.generateToken(authentication, expireTime) : JwtTokenUtils.generateToken(authentication));
        return token;
    }

    /**
     * 获取令牌进行认证
     *
     * @param request 客户端请求
     */
    public static void checkAuthentication(HttpServletRequest request) {
        // 获取令牌并根据令牌获取登录认证信息
        Authentication authentication = JwtTokenUtils.getAuthenticationFromRequest(request);
        // 设置登录认证信息到上下文
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * 从上下文获取当前用户名
     *
     * @return 当前用户名
     */
    public static String getUsername() {
        return getUsername(getAuthentication());
    }

    /**
     * 从上下文获取当前用户名：检查是否开启租户模式
     *
     * @return 当前用户名
     */
    public static String getUsername(boolean checkTenant) {
        String username = getUsername(getAuthentication());
        if (Objects.nonNull(username) && checkTenant) {
            Boolean isStartTenant = Convert.toBool(SpringHelper.getProperty("tenant.enable"), false);
            if (Boolean.TRUE.equals(isStartTenant)) {
                String[] split = username.split(":");
                return split.length == 2 ? split[1] : null;
            }
        }
        return username;
    }

    /**
     * 从传入的认证信息中获取用户名
     *
     * @return 用户名
     */
    public static String getUsername(Authentication authentication) {
        String username = null;
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails userdetails) {
                username = userdetails.getUsername();
            } else {
                return String.valueOf(authentication);
            }
        }
        return username;
    }

    /**
     * 从上下文获取当前登录信息
     *
     * @return 当前登录信息
     */
    public static Authentication getAuthentication() {
        if (SecurityContextHolder.getContext() == null) {
            return null;
        }
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getTenantId() {
        // username 实际由 TenantID + username 组成
        String username = SecurityUtils.getUsername();
        if (Objects.nonNull(username)) {
            String[] split = username.split(":");
            if (split.length == 2) {
                return split[0];
            }
            return null;
        }
        return null;
    }
}
