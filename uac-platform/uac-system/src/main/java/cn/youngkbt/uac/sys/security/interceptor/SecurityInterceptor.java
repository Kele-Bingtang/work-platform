package cn.youngkbt.uac.sys.security.interceptor;

import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.ResponseStatusEnum;
import cn.youngkbt.uac.sys.service.SysUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.PrintWriter;

/**
 * @author Kele-Bingtang
 * @date 2023/4/18 22:33
 * @note
 */
public class SecurityInterceptor implements HandlerInterceptor {

    @Resource
    private SysUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String secretKey = request.getHeader("Secret-Key");
        if (!StringUtils.hasText(secretKey)) {
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.println(new ObjectMapper().writeValueAsString(HttpResult.response(null, ResponseStatusEnum.CLIENT_UN_AUTHORIZED)));
            writer.flush();
            writer.close();
            return false;
        } else {
            // userService.queryUserRole(secretKey);
            return true;
        }
    }
}
