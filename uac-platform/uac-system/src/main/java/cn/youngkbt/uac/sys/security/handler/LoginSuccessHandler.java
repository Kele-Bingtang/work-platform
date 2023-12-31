package cn.youngkbt.uac.sys.security.handler;

import cn.youngkbt.helper.SpringHelper;
import cn.youngkbt.uac.core.constant.AuthConstant;
import cn.youngkbt.mp.base.SysUserBO;
import cn.youngkbt.utils.ServletUtil;
import cn.youngkbt.core.event.LoginInfoEvent;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

/**
 * @author Kele-Bingtang
 * @date 2022/12/10 23:45
 * @note 登录认证成功处理器类
 */
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // 获取当登录用户信息
        SysUserBO user = (SysUserBO) authentication.getPrincipal();

        LoginInfoEvent loginInfoEvent = LoginInfoEvent.builder()
                .tenantId(user.getTenantId())
                .userId(user.getUserId())
                .username(user.getUsername())
                .status(AuthConstant.LOGIN_SUCCESS)
                .request(ServletUtil.getRequest())
                .message("登录成功")
                .build();
        // 发布登录成功事件
        SpringHelper.publishEvent(loginInfoEvent);
    }
}
