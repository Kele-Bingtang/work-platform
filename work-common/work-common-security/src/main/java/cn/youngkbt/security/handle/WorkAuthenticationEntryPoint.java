package cn.youngkbt.security.handle;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.ResponseStatusEnum;
import cn.youngkbt.security.enumeration.AuthErrorCodeEnum;
import cn.youngkbt.security.utils.JwtTokenUtils;
import cn.youngkbt.utils.ServletUtil;
import cn.youngkbt.utils.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * @author Kele-Bingtang
 * @date 2023/11/27 23:42
 * @note 匿名访问时，返回错误提示（捕获认证过程中出现的异常）
 */
public class WorkAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        String token = JwtTokenUtils.getToken(request);
        if (StringUtil.hasText(token)) {
            ServletUtil.writeJSON(response, HttpResult.fail(AuthErrorCodeEnum.AUTH_JWT_ERROR));
        } else {
            ServletUtil.writeJSON(response, HttpResult.fail(ResponseStatusEnum.UN_AUTHORIZED));
        }
    }
}
