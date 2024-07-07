package cn.youngkbt.ag.system.controller;

import cn.youngkbt.ag.system.service.ApiService;
import cn.youngkbt.core.exception.ServiceException;
import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.http.annotation.RequestURI;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.utils.JacksonUtil;
import cn.youngkbt.utils.StringUtil;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author Kele-Bingtang
 * @date 2024/7/7 16:50:24
 * @note
 */
@RestController
@RequestMapping("/generic-api")
@RequiredArgsConstructor
public class ApiController {

    private final ApiService apiService;

    @GetMapping(value = "/one/**")
    public Response<LinkedHashMap<String, Object>> getOne(@RequestURI String requestUri, @RequestHeader(value = "Secret-Key", required = false) String secretKey,
                                                          HttpServletRequest request) {
        Map<String, Object> requestParamsMap = this.getParameterMap(request);
        String secretKeyParam = (String) requestParamsMap.get("secretKey");

        if (StringUtil.hasEmpty(secretKey, secretKeyParam)) {
            return HttpResult.failMessage("您没有权限访问");
        } else if (StringUtil.hasText(secretKeyParam)) {
            secretKey = secretKeyParam;
        }
        String apiUri = requestUri.substring("/generic-api/one".length());
        LinkedHashMap<String, Object> one = apiService.getOne(apiUri, secretKey, requestParamsMap);
        return HttpResult.ok(one);
    }

    @GetMapping(value = "/list/**")
    public Response<List<LinkedHashMap<String, Object>>> list(@RequestURI String requestUri, @RequestHeader(value = "Secret-Key", required = false) String secretKey,
                                                              HttpServletRequest request) {
        Map<String, Object> requestParamsMap = this.getParameterMap(request);
        String secretKeyParam = (String) requestParamsMap.get("secretKey");

        if (StringUtil.hasEmpty(secretKey, secretKeyParam)) {
            return HttpResult.failMessage("您没有权限访问");
        } else if (StringUtil.hasText(secretKeyParam)) {
            secretKey = secretKeyParam;
        }
        String apiUri = requestUri.substring("/generic-api/list".length());
        List<LinkedHashMap<String, Object>> list = apiService.list(apiUri, secretKey, requestParamsMap, null);
        return HttpResult.ok(list);
    }

    @GetMapping(value = "/page/**")
    public Response<TablePage<LinkedHashMap<String, Object>>> page(@RequestURI String requestUri, @RequestHeader(value = "Secret-Key", required = false) String secretKey,
                                                                   @RequestParam Map<String, Object> requestParamsMap,
                                                                   PageQuery pageQuery) {
        String secretKeyParam = (String) requestParamsMap.get("secretKey");

        if (StringUtil.hasEmpty(secretKey, secretKeyParam)) {
            return HttpResult.failMessage("您没有权限访问");
        } else if (StringUtil.hasText(secretKeyParam)) {
            secretKey = secretKeyParam;
        }
        String apiUri = requestUri.substring("/generic-api/page".length());
        TablePage<LinkedHashMap<String, Object>> list = apiService.page(apiUri, secretKey, requestParamsMap, pageQuery);

        return HttpResult.ok(list);
    }

    @PostMapping("/{operateType}/**")
    public Response<String> operate(@PathVariable String operateType, @RequestURI String requestUri, @RequestHeader(value = "Secret-Key", required = false) String secretKey,
                                    HttpServletRequest request) {
        Map<String, Object> dataMap = this.getParameterMap(request);
        String secretKeyParam = (String) dataMap.get("secretKey");

        if (StringUtil.hasEmpty(secretKey, secretKeyParam)) {
            return HttpResult.failMessage("您没有权限访问");
        } else if (StringUtil.hasText(secretKeyParam)) {
            secretKey = secretKeyParam;
        }
        List<Map<String, Object>> jsonList = getJson(request);
        String apiUri = requestUri.substring("/generic-api/".length() + operateType.length());
        String result = apiService.operate(operateType, apiUri, secretKey, dataMap, jsonList);
        return HttpResult.okMessage(result);
    }

    /**
     * 从请求里读取参数，转成 Map
     *
     * @param request 请求对象
     * @return 参数 Map
     */
    private Map<String, Object> getParameterMap(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>(16);
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (!paramValue.isEmpty()) {
                    map.put(paramName, paramValue);
                }
            } else if (paramValues.length > 0) {
                // 数组
                map.put(paramName, paramValues);
            }
        }
        return map;
    }

    /**
     * 获取 JSON 数据
     *
     * @param request 请求对象
     * @return JSON 数据
     */
    public List<Map<String, Object>> getJson(HttpServletRequest request) {
        StringBuilder sb;
        try {
            // 从前端获取输入字节流
            ServletInputStream requestInputStream = request.getInputStream();
            // 将字节流转换为字符流,并设置字符编码为 utf-8
            InputStreamReader ir = new InputStreamReader(requestInputStream, StandardCharsets.UTF_8);
            // 使用字符缓冲流进行读取
            BufferedReader br = new BufferedReader(ir);
            // 开始拼装 JSON 字符串
            String line;
            sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw new ServiceException("解析 JSON 失败");
        }
        return JacksonUtil.toListMap(sb.toString());
    }
}
