package cn.youngkbt.ag.system.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kele-Bingtang
 * @date 2024/7/7 17:24:38
 * @note
 */
public interface ApiService {
    LinkedHashMap<String, Object> getOne(String apiUri, String secretKey, Map<String, Object> requestParamsMap);

    List<LinkedHashMap<String, Object>> list(String apiUri, String secretKey, Map<String, Object> requestParamsMap, Integer limit);

    TablePage<LinkedHashMap<String, Object>> page(String apiUri, String secretKey, Map<String, Object> requestParamsMap, PageQuery pageQuery);

    Integer operate(String operateType, String apiUri, String secretKey, Map<String, Object> dataMap, List<Map<String, Object>> jsonList);
}
