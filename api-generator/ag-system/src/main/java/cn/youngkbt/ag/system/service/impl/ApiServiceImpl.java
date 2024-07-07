package cn.youngkbt.ag.system.service.impl;

import cn.youngkbt.ag.core.enums.QueryFilterType;
import cn.youngkbt.ag.core.helper.SqlHelper;
import cn.youngkbt.ag.system.mapper.base.SQLExecuteMapper;
import cn.youngkbt.ag.system.model.po.DataSource;
import cn.youngkbt.ag.system.model.po.Project;
import cn.youngkbt.ag.system.model.po.ServiceCol;
import cn.youngkbt.ag.system.model.po.ServiceInfo;
import cn.youngkbt.ag.system.service.ApiService;
import cn.youngkbt.ag.system.service.DataSourceService;
import cn.youngkbt.ag.system.service.ProjectService;
import cn.youngkbt.ag.system.service.ServiceInfoService;
import cn.youngkbt.core.constants.ColumnConstant;
import cn.youngkbt.core.error.Assert;
import cn.youngkbt.datasource.helper.DataSourceHelper;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.utils.JacksonUtil;
import cn.youngkbt.utils.ListUtil;
import cn.youngkbt.utils.StringUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cn.youngkbt.ag.core.constant.ColumnConstant.*;

/**
 * @author Kele-Bingtang
 * @date 2024/7/7 17:25:49
 * @note
 */
@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {
    private final ProjectService projectService;
    private final ServiceInfoService serviceInfoService;
    private final ServiceColServiceImpl serviceColService;
    private final DataSourceService dataSourceService;

    private final SQLExecuteMapper sqlExecuteMapper;

    @Override
    public LinkedHashMap<String, Object> getOne(String apiUri, String secretKey, Map<String, Object> requestParamsMap) {
        List<LinkedHashMap<String, Object>> linkedHashMapList = list(apiUri, secretKey, requestParamsMap, 1);

        return linkedHashMapList.get(0);
    }

    @Override
    public List<LinkedHashMap<String, Object>> list(String apiUri, String secretKey, Map<String, Object> requestParamsMap, Integer limit) {
        // 验证项目，获取服务信息
        ServiceInfo serviceInfo = checkThenGetService(apiUri, secretKey);
        DataSourceHelper.use(serviceInfo.getDataSourceId());

        // 获取服务列配置项信息
        List<ServiceCol> serviceColList = serviceColService.list(Wrappers.<ServiceCol>lambdaQuery()
                .eq(ServiceCol::getServiceId, serviceInfo.getServiceId()));
        Assert.isTrue(!ListUtil.isEmpty(serviceColList), "不存在列配置项");

        String selectSql = serviceInfo.getSelectSql();
        Assert.notBlank(selectSql, "SQL 为空，因此不存在数据返回");

        String fullSelectSql = getFullSelectSql(selectSql, requestParamsMap, serviceColList);

        if (Objects.nonNull(limit)) {
            // 查询数据源信息
            DataSource dataSource = dataSourceService.getOne(Wrappers.<DataSource>lambdaQuery()
                    .eq(DataSource::getDataSourceId, serviceInfo.getDataSourceId()));
            fullSelectSql = SqlHelper.addLimitToSql(fullSelectSql, limit, dataSource.getDataSourceType());
        }

        List<LinkedHashMap<String, Object>> linkedHashMap = sqlExecuteMapper.executeSelectList(fullSelectSql, requestParamsMap);

        if (ListUtil.isEmpty(linkedHashMap)) {
            return new ArrayList<>();
        }

        DataSourceHelper.close();

        return processMapping(linkedHashMap, serviceColList);
    }

    @Override
    public TablePage<LinkedHashMap<String, Object>> page(String apiUri, String secretKey, Map<String, Object> requestParamsMap, PageQuery pageQuery) {
        // 验证项目，获取服务信息
        ServiceInfo serviceInfo = checkThenGetService(apiUri, secretKey);
        DataSourceHelper.use(serviceInfo.getDataSourceId());

        // 获取服务列配置项信息
        List<ServiceCol> serviceColList = serviceColService.list(Wrappers.<ServiceCol>lambdaQuery()
                .eq(ServiceCol::getServiceId, serviceInfo.getServiceId()));
        Assert.isTrue(!ListUtil.isEmpty(serviceColList), "不存在列配置项");

        String selectSql = serviceInfo.getSelectSql();
        Assert.notBlank(selectSql, "SQL 为空，因此不存在数据返回");

        String fullSelectSql = getFullSelectSql(selectSql, requestParamsMap, serviceColList);

        Page<LinkedHashMap<String, Object>> linkedHashMapPage = sqlExecuteMapper.executeSelectPage(pageQuery.buildPage(), fullSelectSql, requestParamsMap);
        List<LinkedHashMap<String, Object>> linkedHashMapList = linkedHashMapPage.getRecords();

        List<LinkedHashMap<String, Object>> processMappingList = processMapping(linkedHashMapList, serviceColList);
        linkedHashMapPage.setRecords(processMappingList);

        DataSourceHelper.close();
        return TablePage.build(linkedHashMapPage);
    }

    /**
     * 验证项目，获取服务信息
     *
     * @param requestUri 请求地址
     * @param secretKey  密钥
     * @return 服务信息
     */
    private ServiceInfo checkThenGetService(String requestUri, String secretKey) {
        Project project = projectService.getOne(Wrappers.<Project>lambdaQuery()
                .eq(Project::getSecretKey, secretKey));
        Assert.isTrue(Objects.equals(project.getStatus(), ColumnConstant.STATUS_NORMAL), "该项目已被禁用");

        String baseUrl = project.getBaseUrl();
        String projectUrl = requestUri.substring(0, baseUrl.length());
        String serviceUrl = requestUri.substring(baseUrl.length());

        Assert.isTrue(projectUrl.equals(baseUrl), "接口链接和项目密钥不匹配");

        ServiceInfo serviceInfo = serviceInfoService.getOne(Wrappers.<ServiceInfo>lambdaQuery()
                .eq(ServiceInfo::getProjectId, project.getProjectId())
                .eq(ServiceInfo::getServiceUrl, serviceUrl)
        );

        Assert.isTrue(Objects.equals(serviceInfo.getStatus(), ColumnConstant.STATUS_NORMAL), "该接口已被禁用");
        return serviceInfo;
    }

    /**
     * 获取完整的 select sql
     *
     * @param selectSql        select sql
     * @param requestParamsMap 查询参数
     * @param serviceColList   列配置项
     * @return 完整的 select sql
     */
    private String getFullSelectSql(String selectSql, Map<String, Object> requestParamsMap, List<ServiceCol> serviceColList) {
        // 获取查询条件
        String querySql = getQuerySql(requestParamsMap, serviceColList);
        // 获取 orderBy 排序
        String orderBySql = getOrderBySql(serviceColList);

        // 查询和分页处理，直接将 selectSql 包起来进行查询和分页
        return "SELECT a.* FROM (" + selectSql + ") a" + querySql + orderBySql;
    }

    /**
     * 获取 where 后的查询 sql
     *
     * @param requestParamsMap 字段数据
     * @param serviceColList   字段集合
     * @return where 后的查询 sql
     */
    private String getQuerySql(Map<String, Object> requestParamsMap, List<ServiceCol> serviceColList) {
        StringBuilder querySql = new StringBuilder();
        for (Map.Entry<String, Object> entry : requestParamsMap.entrySet()) {
            for (ServiceCol serviceCol : serviceColList) {
                if (!serviceCol.getJsonCol().equals(entry.getKey()) || (Objects.isNull(serviceCol.getQueryFilter()) || serviceCol.getQueryFilter() == 0)) {
                    continue;
                }

                Object value = entry.getValue();
                String name = QueryFilterType.getName(serviceCol.getQueryFilter());
                Assert.notBlank(name, "查询条件暂不支持");

                querySql.append(serviceCol.getTableCol());
                if (value instanceof String val) {
                    querySql.append(name.replace("{val}", val));
                } else if (value instanceof String[] val) {
                    // BETWEEN 范围查询
                    if (serviceCol.getQueryFilter() == 10) {
                        querySql.append(name.replace("{val0}", val[0]).replace("{val1}", val[1]));
                    }
                    // IN 查询
                    if (serviceCol.getQueryFilter() == 11) {
                        String inCondition = String.join(",", val);
                        querySql.append(name.replace("{val}", inCondition));
                    }
                }
                querySql.append(AND);
                break;
            }
        }
        if (!querySql.isEmpty()) {
            // 删除尾部的 AND
            querySql.delete(querySql.length() - AND.length(), querySql.length());
            return WHERE + querySql;
        }

        return "";
    }

    /**
     * 获取 order by sql
     *
     * @param serviceColList 字段结婚
     * @return order by sql
     */
    private String getOrderBySql(List<ServiceCol> serviceColList) {
        StringBuilder orderBySql = new StringBuilder();
        Stream<ServiceCol> serviceColStream = serviceColList.stream().sorted(Comparator.comparing(x -> Math.abs(x.getOrderBy())));
        serviceColStream.forEach(serviceColVO -> {
            Integer orderBy = serviceColVO.getOrderBy();
            // Order by 顺序：0 - 99
            if (orderBy > -99 && orderBy < 0) {
                orderBySql.append("'").append(serviceColVO.getTableCol()).append("'").append(" desc").append(", ");
            } else if (orderBy > 0 && orderBy < 99) {
                orderBySql.append("'").append(serviceColVO.getTableCol()).append("'").append(" asc").append(", ");
            }
        });
        // 去除结尾的 , 
        if (!orderBySql.isEmpty()) {
            orderBySql.delete(orderBySql.length() - 2, orderBySql.length());
            return ORDER_BY + orderBySql;
        }
        return "";
    }

    private List<LinkedHashMap<String, Object>> processMapping(List<LinkedHashMap<String, Object>> linkedHashMapList, List<ServiceCol> serviceColList) {
        List<LinkedHashMap<String, Object>> newListMap = new ArrayList<>(linkedHashMapList.size());
        Map<String, String> cacheMapping = serviceColList.stream()
                .filter(serviceCol -> serviceCol.getAllowRequest() != 0)
                .collect(Collectors.toMap(ServiceCol::getTableCol, ServiceCol::getJsonCol, (k1, k2) -> k1));

        for (LinkedHashMap<String, Object> linkedHashMap : linkedHashMapList) {
            LinkedHashMap<String, Object> newMap = new LinkedHashMap<>();
            for (Map.Entry<String, Object> entry : linkedHashMap.entrySet()) {
                if (cacheMapping.containsKey(entry.getKey())) {
                    newMap.put(cacheMapping.get(entry.getKey()), entry.getValue());
                } else if (Objects.nonNull(cacheMapping.get(entry.getKey()))) {
                    // cacheMapping 存在的，数据里不存在的也要添加进去
                    newMap.put(cacheMapping.get(entry.getKey()), null);
                }
            }
            newListMap.add(newMap);
        }

        return newListMap;
    }

    @Override
    public String operate(String operateType, String apiUri, String secretKey, Map<String, Object> dataMap, List<Map<String, Object>> jsonList) {
        // 验证项目，获取服务信息
        ServiceInfo serviceInfo = checkThenGetService(apiUri, secretKey);
        DataSourceHelper.use(serviceInfo.getDataSourceId());

        // 获取服务列配置项信息
        List<ServiceCol> serviceColList = serviceColService.list(Wrappers.<ServiceCol>lambdaQuery()
                .eq(ServiceCol::getServiceId, serviceInfo.getServiceId()));
        Assert.isTrue(!ListUtil.isEmpty(serviceColList), "不存在列配置项");

        String selectSql = serviceInfo.getSelectSql();
        Assert.notBlank(selectSql, "SQL 为空，因此不存在数据返回");

        // x-www-form-urlencoded 模式

        // application/json 模式

        return "";
    }

    /**
     * 处理增删改的映射关系
     */
    private String processWwwForm(String operateType, List<ServiceCol> serviceColList, List<Map<String, Object>> dataListMap) {
        // 过滤不需要操作的列配置项，并将需要过滤的列配置项转为 Map
        Map<String, ServiceCol> cacheNeedMapping = serviceColList.stream().filter(serviceCol -> ("insert".equalsIgnoreCase(operateType) && serviceCol.getAllowInsert() != 0) ||
                        ("update".equalsIgnoreCase(operateType) && serviceCol.getAllowUpdate() != 0 && serviceCol.getIsWhereKey() != 0))
                .collect(Collectors.toMap(ServiceCol::getJsonCol, serviceCol -> serviceCol, (k1, k2) -> k1));

        // 数据
        List<HashMap<String, Object>> newDataListMap = new ArrayList<>(dataListMap.size());
        // 主键数据
        HashMap<String, Object> keyMap = new HashMap<>(16);
        // 类型数据
        HashMap<String, Object> typeMap = new HashMap<>(16);
        // 默认值数据
        HashMap<String, Object> defaultValueMap = new HashMap<>(16);
        // 记录没有完成匹配的 cacheNeedMapping
        HashMap<String, ServiceCol> cacheHasNeedMapping = new HashMap<>(newDataListMap.size());
        // 是否完成第一轮循环
        boolean isLoadOneFor = false;
        for (Map<String, Object> dataMap : dataListMap) {
            HashMap<String, Object> newDataMap = new HashMap<>(16);
            for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                if (!cacheNeedMapping.containsKey(entry.getKey())) {
                    continue;
                }
                ServiceCol serviceCol = cacheNeedMapping.get(entry.getKey());
                // 因为每次循环都是重复的 Key，所以执行一次循环的缓存即可
                if (!isLoadOneFor) {
                    cacheHasNeedMapping.put(entry.getKey(), serviceCol);
                }
                Object value = entry.getValue();
                // 最终的 value
                String finalValue = "";
                if (value instanceof String) {
                    finalValue = (String) value;
                } else if (value instanceof String[] s) {
                    if (s.length > 0) {
                        // 转 JSON 字符串
                        finalValue = JacksonUtil.toJsonStr(s);
                    }
                }
                // 值不能为空
                if (StringUtil.hasText(finalValue)) {
                    // 1 代表一定添加到 keyMap，2 代表如果值为空，则不添加到 keyMap，0 代表不添加
                    if (serviceCol.getIsWhereKey() == 1) {
                        keyMap.put(serviceCol.getTableCol(), finalValue);
                    } else if (serviceCol.getIsWhereKey() == 2 && !finalValue.isEmpty()) {
                        keyMap.put(serviceCol.getTableCol(), finalValue);
                    } else if (serviceCol.getIsWhereKey() == 0) {
                        // 不是 where 条件，那么就是需要操作的数据
                        newDataMap.put(serviceCol.getTableCol(), finalValue);
                    }
                }
                typeMap.put(serviceCol.getTableCol(), serviceCol.getColType());
            }
            isLoadOneFor = true;
            newDataListMap.add(newDataMap);
        }

        // 获取 cacheHasNeedMapping 和 cacheNeedMapping 的差异 Key
        Set<String> cacheMappingKey = cacheNeedMapping.keySet();
        Set<String> cacheHasMappingKey = cacheHasNeedMapping.keySet();
        Set<String> differenceKey = cacheMappingKey.stream().filter(s -> !cacheHasMappingKey.contains(s)).collect(Collectors.toSet());

        for (String key : differenceKey) {
            ServiceCol serviceCol = cacheNeedMapping.get(key);
            if (StringUtil.hasText(serviceCol.getDefaultValue()) && serviceCol.getIsWhereKey() == 0) {
                defaultValueMap.put(serviceCol.getTableCol(), serviceCol.getDefaultValue());
            }
        }

        return "";
    }


}
