package cn.youngkbt.utils;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

/**
 * @author Kele-Bingtang
 * @date 2023/7/3 22:21
 * @note
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WebUtil {
    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string   待渲染的字符串
     */
    public static void renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 获取 HttpServletRequest
     *
     * @return {HttpServletRequest}
     */
    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return (requestAttributes == null) ? null : ((ServletRequestAttributes) requestAttributes).getRequest();
    }

    /**
     * 获取 request 请求体
     *
     * @param servletInputStream servletInputStream
     * @return body
     */
    public static String getRequestBody(ServletInputStream servletInputStream) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(servletInputStream, StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (servletInputStream != null) {
                try {
                    servletInputStream.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
        return sb.toString();
    }

    /**
     * 获取 request 请求内容
     *
     * @param request request
     * @return {String}
     */
    public static String getRequestContent(HttpServletRequest request) {
        try {
            String queryString = request.getQueryString();
            if (StringUtil.hasText(queryString)) {
                return new String(queryString.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8).replaceAll("&amp;", "&").replaceAll("%22", "\"");
            }
            String charEncoding = request.getCharacterEncoding();
            if (charEncoding == null) {
                charEncoding = "UTF-8";
            }
            byte[] buffer = getRequestBody(request.getInputStream()).getBytes();
            String str = new String(buffer, charEncoding).trim();
            if (!StringUtil.hasText(str)) {
                StringBuilder sb = new StringBuilder();
                Enumeration<String> parameterNames = request.getParameterNames();
                while (parameterNames.hasMoreElements()) {
                    String key = parameterNames.nextElement();
                    String value = request.getParameter(key);
                    sb.append(key).append("=").append(value).append("&");
                }
                str = StrUtil.removeSuffix(sb.toString(), "&");
            }
            return str.replaceAll("&amp;", "&");
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }
}
