package cn.youngkbt.security.handle;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.ResponseStatusEnum;
import cn.youngkbt.web.utils.ServletUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * @author Kele-Bingtang
 * @date 2023/11/27 23:43
 * @note 登录后，访问缺失权限的资源会调用（捕获授权过程中出现的异常）
 */
@Slf4j
public class WorkAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        ServletUtil.writeJSON(response, HttpResult.fail(ResponseStatusEnum.REQ_REJECT, "没有权限访问资源"));
    }
}
