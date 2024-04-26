package cn.youngkbt.uac.sys.security.interceptor;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.security.domain.LoginUser;
import cn.youngkbt.security.enumeration.AuthErrorCodeEnum;
import cn.youngkbt.security.properties.SecurityProperties;
import cn.youngkbt.security.utils.JwtTokenUtils;
import cn.youngkbt.security.utils.UacHelper;
import cn.youngkbt.uac.core.constant.AuthConstant;
import cn.youngkbt.utils.ServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Date;
import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2023/4/18 22:33
 * @note
 */
@RequiredArgsConstructor
public class SecurityInterceptor implements HandlerInterceptor {

    @Value("${jwt.refresh-time}")
    private long refreshTime;

    private final SecurityProperties securityProperties;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 排除不需要认证的地址
        String requestURI = request.getRequestURI();
        for (String excludePath : securityProperties.getExcludes()) {
            if (pathMatcher.match(excludePath, requestURI)) {
                return true;
            }
        }

        String token = JwtTokenUtils.getToken(request);
        LoginUser loginUser = UacHelper.getLoginUser();
        // 检查缓存中的用户信息是否存在，Token 是否等于前端传来的 Token
        if (Objects.isNull(loginUser) || !loginUser.getAccessToken().equals(token)) {
            ServletUtil.writeJSON(response, HttpResult.fail(AuthErrorCodeEnum.UN_AUTHORIZED));
            return false;
        }

        // 检查 Token 是否需要自动续期
        Date expired = JwtTokenUtils.getTokenExpired(token);
        Long tokenExpireTime = JwtTokenUtils.getTokenExpireTime(token);
        // 当 Token 在 refreshTime 时间内，且用户还在活跃，则自动续期
        if (Objects.nonNull(expired) && expired.getTime() - new Date().getTime() < refreshTime) {
            String newToken = JwtTokenUtils.refreshToken(token, tokenExpireTime);
            response.setHeader(AuthConstant.JWT_TOKEN, newToken);

            // 缓存也更新 Token
            loginUser.setAccessToken(newToken);
            UacHelper.cacheUserInfo(loginUser, tokenExpireTime);
        }
        return true;
    }
}
