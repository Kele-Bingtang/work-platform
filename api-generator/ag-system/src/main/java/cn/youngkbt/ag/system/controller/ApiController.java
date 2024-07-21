package cn.youngkbt.ag.system.controller;

import cn.youngkbt.ag.core.constant.ColumnConstant;
import cn.youngkbt.ag.system.service.ApiService;
import cn.youngkbt.core.exception.ServiceException;
import cn.youngkbt.core.http.HttpResult;
import cn.youngkbt.core.http.Response;
import cn.youngkbt.core.http.annotation.RequestURI;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.utils.JacksonUtil;
import cn.youngkbt.utils.StringUtil;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "查询一笔数据", description = "查询一笔数据")
    public Response<LinkedHashMap<String, Object>> getOne(@RequestURI String requestUri, @RequestHeader(value = "Secret-Key", required = false) String secretKey, HttpServletRequest request) {
        Map<String, Object> requestParamsMap = getParameterMap(request);
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
    @Operation(summary = "查询多笔数据", description = "查询多笔数据")
    public Response<List<LinkedHashMap<String, Object>>> list(@RequestURI String requestUri, @RequestHeader(value = "Secret-Key", required = false) String secretKey, HttpServletRequest request) {
        Map<String, Object> requestParamsMap = getParameterMap(request);
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
    @Operation(summary = "查询分页数据", description = "查询分页数据")
    public Response<TablePage<LinkedHashMap<String, Object>>> page(@RequestURI String requestUri, @RequestHeader(value = "Secret-Key", required = false) String secretKey, 
                                                                   @RequestParam Map<String, Object> requestParamsMap, PageQuery pageQuery) {
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

    @GetMapping(value = "/listByServiceId/{serviceId}")
    @Operation(summary = "通过服务 ID 查询多笔数据", description = "通过服务 ID 查询多笔数据")
    public Response<List<LinkedHashMap<String, Object>>> listByServiceId(@PathVariable String serviceId, HttpServletRequest request) {
        Map<String, Object> dataMap = getParameterMap(request);
        List<LinkedHashMap<String, Object>> dataList = apiService.listByServiceId(serviceId, dataMap);
        return HttpResult.ok(dataList);
    }

    @GetMapping(value = "/pageByServiceId/{serviceId}")
    @Operation(summary = "通过服务 ID 查询多笔数据（分页）", description = "通过服务 ID 查询多笔数据（分页）")
    public Response<TablePage<LinkedHashMap<String, Object>>> pageByServiceId(@PathVariable String serviceId, PageQuery pageQuery, HttpServletRequest request) {
        Map<String, Object> dataMap = getParameterMap(request);
        TablePage<LinkedHashMap<String, Object>> dataList = apiService.pageByServiceId(serviceId, pageQuery, dataMap);
        return HttpResult.ok(dataList);
    }

    @PostMapping("/{operateType}/**")
    @Operation(summary = "操作数据", description = "操作数据")
    public Response<Integer> operate(@PathVariable String operateType, @RequestURI String requestUri, @RequestHeader(value = "Secret-Key", required = false) String secretKey, HttpServletRequest request) {
        Map<String, Object> dataMap = getParameterMap(request);
        String secretKeyParam = (String) dataMap.get("secretKey");

        if (StringUtil.hasEmpty(secretKey, secretKeyParam)) {
            return HttpResult.failMessage("您没有权限访问");
        } else if (StringUtil.hasText(secretKeyParam)) {
            secretKey = secretKeyParam;
        }
        List<Map<String, Object>> jsonList = getJson(request);
        String apiUri = requestUri.substring("/generic-api/".length() + operateType.length());
        dataMap.remove("secretKey");
        Integer result = apiService.operate(operateType, apiUri, secretKey, dataMap, jsonList);

        // 新增返回主键
        if (ColumnConstant.INSERT.equalsIgnoreCase(operateType)) {
            return HttpResult.ok(result);
        }
        return HttpResult.okMessage(operateType + " 了 " + result + " 笔数据");
    }

    @PostMapping(value = "/operate/{operateType}/{serviceId}")
    @Operation(summary = "通过服务 ID 操作数据", description = "通过服务 ID 操作数据")
    public Response<Integer> operateByServiceId(@PathVariable String operateType, @PathVariable String serviceId, HttpServletRequest request) {
        Integer result = apiService.operateByServiceId(operateType, serviceId, getJson(request).get(0));
        return HttpResult.ok(result);
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
        String json = sb.toString();
        return json.startsWith("[") ? JacksonUtil.toListMap(json) : Collections.singletonList(JacksonUtil.toJson(json, Map.class));
    }
}
