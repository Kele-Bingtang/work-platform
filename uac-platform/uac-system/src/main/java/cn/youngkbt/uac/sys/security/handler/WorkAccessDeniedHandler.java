package cn.youngkbt.uac.sys.security.handler;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.ResponseStatusEnum;
import cn.youngkbt.utils.ServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * @author Kele-Bingtang
 * @date 2023/11/27 23:43
 * @note 登录后，访问缺失权限的资源会调用
 */
public class WorkAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        ServletUtil.writeJSON(response, HttpResult.fail(ResponseStatusEnum.REQ_REJECT));
    }
}
