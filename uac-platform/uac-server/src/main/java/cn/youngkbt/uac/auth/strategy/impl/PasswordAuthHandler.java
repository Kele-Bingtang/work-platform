package cn.youngkbt.uac.auth.strategy.impl;

import cn.youngkbt.core.validate.AuthGroup;
import cn.youngkbt.security.JwtAuthenticationToken;
import cn.youngkbt.security.domain.LoginUser;
import cn.youngkbt.security.enumeration.AuthGrantTypeEnum;
import cn.youngkbt.security.utils.JwtTokenUtils;
import cn.youngkbt.security.utils.SecurityUtils;
import cn.youngkbt.security.utils.UacHelper;
import cn.youngkbt.tenant.helper.TenantHelper;
import cn.youngkbt.uac.auth.strategy.AuthHandler;
import cn.youngkbt.uac.core.bo.LoginSuccessBO;
import cn.youngkbt.uac.core.bo.LoginUserBO;
import cn.youngkbt.uac.sys.listen.LoginEventListen;
import cn.youngkbt.uac.sys.model.po.SysClient;
import cn.youngkbt.uac.sys.security.handler.LoginFailureHandler;
import cn.youngkbt.uac.sys.security.handler.LoginSuccessHandler;
import cn.youngkbt.utils.MapstructUtil;
import cn.youngkbt.utils.ServletUtil;
import cn.youngkbt.utils.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2023/11/14 0:14
 * @note 密码处理器
 */
@Slf4j
@RequiredArgsConstructor
@Service("password" + AuthHandler.BASE_NAME)
public class PasswordAuthHandler implements AuthHandler {

    private final AuthenticationManager authenticationManager;
    private final LoginEventListen loginEventListen;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;

    @Override
    public boolean support(AuthGrantTypeEnum grantType) {
        return grantType.equals(AuthGrantTypeEnum.PASSWORD);
    }

    @Override
    public void validate(LoginUserBO loginUserBO) {
        ValidatorUtil.validate(loginUserBO, AuthGroup.PasswordGroup.class);
    }

    @Override
    public LoginSuccessBO login(LoginUserBO loginUserBO, SysClient sysClient) {
        // 检查用户是否被锁定登录
        loginEventListen.checkLogin(loginUserBO);

        JwtAuthenticationToken token = new JwtAuthenticationToken(null, null);
        try {
            token = SecurityUtils.login(ServletUtil.getRequest(),
                    TenantHelper.isEnable() ? loginUserBO.getTenantId() + ":" + loginUserBO.getUsername() : loginUserBO.getUsername(),
                    loginUserBO.getPassword(),
                    authenticationManager,
                    sysClient.getTimeout());

            // 走了自定义认证，则 Spring Security 不会调用自定义的成功处理器，这里需要手动调用
            // 参考 org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter.doFilter()
            loginSuccessHandler.onAuthenticationSuccess(null, null, token.getAuthentication());
        } catch (InternalAuthenticationServiceException exception) {
            // 走了自定义认证，则 Spring Security 不会调用自定义的失败处理器，这里需要手动调用
            log.error("An internal error occurred while trying to authenticate the user.", exception);
            loginFailureHandler.onAuthenticationFailure(ServletUtil.getRequest(), ServletUtil.getResponse(), exception, loginUserBO);
        } catch (AuthenticationException exception) {
            loginFailureHandler.onAuthenticationFailure(ServletUtil.getRequest(), ServletUtil.getResponse(), exception, loginUserBO);
        }

        Long timeout = sysClient.getTimeout();

        if (Objects.isNull(timeout)) {
            timeout = JwtTokenUtils.EXPIRE_TIME;
        }

        LoginSuccessBO loginSuccessBO = new LoginSuccessBO();
        loginSuccessBO.setAccessToken(token.getAccessToken());
        loginSuccessBO.setExpireIn(timeout);

        // 用户基本信息存入 Redis
        LoginUser loginUser = MapstructUtil.convert(token.getAuthentication().getPrincipal(), LoginUser.class);
        UacHelper.cacheUserInfo(loginUser, timeout);
        return loginSuccessBO;
    }
}
