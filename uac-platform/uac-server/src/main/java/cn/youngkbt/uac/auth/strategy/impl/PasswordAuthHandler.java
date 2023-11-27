package cn.youngkbt.uac.auth.strategy.impl;

import cn.youngkbt.core.validate.AuthGroup;
import cn.youngkbt.security.JwtAuthenticationToken;
import cn.youngkbt.security.enumeration.AuthGrantTypeEnum;
import cn.youngkbt.security.utils.SecurityUtils;
import cn.youngkbt.tenant.helper.TenantHelper;
import cn.youngkbt.uac.auth.strategy.AuthHandler;
import cn.youngkbt.uac.core.bo.LoginSuccessBO;
import cn.youngkbt.uac.core.bo.LoginUserBO;
import cn.youngkbt.uac.sys.listen.LoginEventListen;
import cn.youngkbt.uac.sys.model.po.SysClient;
import cn.youngkbt.utils.ServletUtil;
import cn.youngkbt.utils.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

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

        JwtAuthenticationToken token = SecurityUtils.login(ServletUtil.getRequest(),
                TenantHelper.isEnable() ? loginUserBO.getTenantId() + ":" + loginUserBO.getUsername() : loginUserBO.getUsername(),
                loginUserBO.getPassword(),
                authenticationManager,
                sysClient.getTimeout());
        // 生成 Token 并返回
        LoginSuccessBO loginSuccessBO = new LoginSuccessBO();
        loginSuccessBO.setAccessToken(token.getAccessToken());
        loginSuccessBO.setExpireIn(sysClient.getTimeout());
        return loginSuccessBO;
    }
}
