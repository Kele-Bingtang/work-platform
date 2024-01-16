package cn.youngkbt.datasource.processor;

import com.baomidou.dynamic.datasource.processor.DsProcessor;
import jakarta.servlet.http.HttpServletRequest;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author Kele-Bingtang
 * @date 2023/12/26 21:03
 * @note 解决 com.baomidou.dynamic.datasource.processor.DsHeaderProcessor 的 HttpServletRequest 类不兼容 JDK 17 问题
 */
public class DsHeaderProcessor extends DsProcessor {
    private static final String HEADER_PREFIX = "#header";

    @Override
    public boolean matches(String key) {
        return key.startsWith(HEADER_PREFIX);
    }

    @Override
    public String doDetermineDatasource(MethodInvocation methodInvocation, String key) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getHeader(key.substring(HEADER_PREFIX.length()) + 1);
    }
}