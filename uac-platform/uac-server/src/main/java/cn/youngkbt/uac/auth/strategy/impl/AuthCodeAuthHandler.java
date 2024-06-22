package cn.youngkbt.uac.auth.strategy.impl;

import cn.youngkbt.security.enumeration.AuthGrantTypeEnum;
import cn.youngkbt.uac.auth.strategy.AuthHandler;
import cn.youngkbt.uac.core.bo.LoginSuccessBO;
import cn.youngkbt.uac.core.bo.LoginUserBO;
import cn.youngkbt.uac.system.model.po.SysClient;

/**
 * @author Kele-Bingtang
 * @date 2023/11/14 23:13
 * @note 授权码模式处理器
 */
public class AuthCodeAuthHandler implements AuthHandler {
    @Override
    public boolean support(AuthGrantTypeEnum grantType) {
        return false;
    }

    @Override
    public void validate(LoginUserBO loginUserBO) {

    }

    @Override
    public LoginSuccessBO login(LoginUserBO loginUserBO, SysClient sysClient) {
        return null;
    }
}
