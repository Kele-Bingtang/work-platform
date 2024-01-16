package cn.youngkbt.uac.sys.security.handler;

import cn.youngkbt.core.base.BaseCommonEnum;
import cn.youngkbt.core.event.LoginInfoEvent;
import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.helper.SpringHelper;
import cn.youngkbt.security.enumeration.AuthErrorCodeEnum;
import cn.youngkbt.uac.core.constant.AuthConstant;
import cn.youngkbt.utils.JacksonUtil;
import cn.youngkbt.utils.ServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Kele-Bingtang
 * @date 2022/12/10 23:51
 * @note 用户认证失败处理类
 */
@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        // 判断异常类型
        BaseCommonEnum authErrorCodeEnum = AuthErrorCodeEnum.LOGIN_FAIL;
        if (exception instanceof AccountExpiredException) {
            authErrorCodeEnum = AuthErrorCodeEnum.USER_ACCOUNT_EXPIRED;
        } else if (exception instanceof BadCredentialsException) {
            authErrorCodeEnum = AuthErrorCodeEnum.USERNAME_PASSWORD_ERROR;
        } else if (exception instanceof CredentialsExpiredException) {
            authErrorCodeEnum = AuthErrorCodeEnum.USER_PASSWORD_EXPIRED;
        } else if (exception instanceof DisabledException) {
            authErrorCodeEnum = AuthErrorCodeEnum.USER_ACCOUNT_DISABLE;
        } else if (exception instanceof LockedException) {
            authErrorCodeEnum = AuthErrorCodeEnum.USER_ACCOUNT_LOCKED;
        } else if (exception instanceof InternalAuthenticationServiceException) {
            authErrorCodeEnum = AuthErrorCodeEnum.USER_ACCOUNT_NOT_EXIST;
        }
        log.error("Exception：{}", authErrorCodeEnum.getMessage());
        // 该异常是在 UserDetailsService.loadUserByUsername 里抛出的，不需要记录
        if (!(exception instanceof InternalAuthenticationServiceException)) {
            String username = request.getParameter(AuthConstant.USERNAME);
            String tenantId = request.getParameter(AuthConstant.TENANT_ID);
            LoginInfoEvent loginInfoEvent = LoginInfoEvent.builder()
                    .tenantId(tenantId)
                    .username(username)
                    .status(AuthConstant.LOGIN_FAIL)
                    .request(ServletUtil.getRequest())
                    .build();
            loginInfoEvent.setMessage(authErrorCodeEnum.getMessage());
            // 发布登录失败事件
            SpringHelper.publishEvent(loginInfoEvent);
        }

        // 设置客户端响应编码格式
        response.setContentType("application/json;charset=UTF-8");
        // 获取输出流
        PrintWriter writer = response.getWriter();
        // 将错误信息转换成 JSON
        writer.println(JacksonUtil.toJsonStr(HttpResult.response(null, authErrorCodeEnum)));
        writer.flush();
        writer.close();
    }
}