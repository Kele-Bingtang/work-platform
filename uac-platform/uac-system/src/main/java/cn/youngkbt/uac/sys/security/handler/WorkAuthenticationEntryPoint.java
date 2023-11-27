package cn.youngkbt.uac.sys.security.handler;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.ResponseStatusEnum;
import cn.youngkbt.utils.ServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * @author Kele-Bingtang
 * @date 2023/11/27 23:42
 * @note 匿名访问时，返回 401
 */
public class WorkAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        ServletUtil.writeJSON(response, HttpResult.fail(ResponseStatusEnum.UN_AUTHORIZED));
    }
}
