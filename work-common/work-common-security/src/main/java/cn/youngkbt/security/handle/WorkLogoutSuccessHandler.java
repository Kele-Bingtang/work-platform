package cn.youngkbt.security.handle;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.utils.ServletUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

/**
 * @author Kele-Bingtang
 * @date 2023/11/27 23:42
 * @note 退出登录成功处理器
 */
public class WorkLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ServletUtil.writeJSON(response, HttpResult.ok(200, "退出登录成功"));
    }
}
