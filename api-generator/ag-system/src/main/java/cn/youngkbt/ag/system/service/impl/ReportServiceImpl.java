package cn.youngkbt.ag.system.service.impl;

import cn.youngkbt.ag.core.constant.ColumnConstant;
import cn.youngkbt.ag.core.enums.QueryFilterType;
import cn.youngkbt.ag.system.mapper.ReportMapper;
import cn.youngkbt.ag.system.mapper.ServiceColMapper;
import cn.youngkbt.ag.system.mapper.ServiceInfoMapper;
import cn.youngkbt.ag.system.mapper.base.SQLExecuteMapper;
import cn.youngkbt.ag.system.model.dto.ReportDTO;
import cn.youngkbt.ag.system.model.po.Report;
import cn.youngkbt.ag.system.model.po.ServiceCol;
import cn.youngkbt.ag.system.model.po.ServiceInfo;
import cn.youngkbt.ag.system.model.vo.component.ProFormSchemaVO;
import cn.youngkbt.ag.system.model.vo.component.ProTableColumnsVO;
import cn.youngkbt.ag.system.model.vo.ReportDataVO;
import cn.youngkbt.ag.system.model.vo.ReportVO;
import cn.youngkbt.ag.system.service.ApiService;
import cn.youngkbt.ag.system.service.ReportService;
import cn.youngkbt.datasource.helper.DataSourceHelper;
import cn.youngkbt.utils.JacksonUtil;
import cn.youngkbt.utils.MapstructUtil;
import cn.youngkbt.utils.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Kele-Bingtang
 * @date 2024-06-22 00:15:48
 * @note 针对表「t_report（报表）」的数据库操作 Service 实现
 */
@Service
@RequiredArgsConstructor
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements ReportService {

    private final ApiService apiService;
    
    private final ServiceColMapper serviceColMapper;
    private final ServiceInfoMapper serviceInfoMapper;
    private final SQLExecuteMapper sqlExecuteMapper;

    @Override
    public ReportVO listOne(ReportDTO reportDTO) {
        LambdaQueryWrapper<Report> wrapper = buildQueryWrapper(reportDTO);
        Report report = baseMapper.selectOne(wrapper);
        return MapstructUtil.convert(report, ReportVO.class);
    }

    private LambdaQueryWrapper<Report> buildQueryWrapper(ReportDTO reportDTO) {
        return Wrappers.<Report>lambdaQuery()
                .eq(StringUtil.hasText(reportDTO.getServiceId()), Report::getServiceId, reportDTO.getServiceId())
                .eq(StringUtil.hasText(reportDTO.getReportTitle()), Report::getReportTitle, reportDTO.getReportTitle())
                .orderByDesc(Report::getCreateTime);
    }

    @Override
    public boolean addReport(ReportDTO reportDTO) {
        Report report = MapstructUtil.convert(reportDTO, Report.class);
        return baseMapper.insert(report) > 0;
    }

    @Override
    public boolean editReport(ReportDTO reportDTO) {
        Report report = MapstructUtil.convert(reportDTO, Report.class);
        return baseMapper.updateById(report) > 0;
    }

    @Override
    public boolean removeReport(String serviceId) {
        return baseMapper.delete(Wrappers.<Report>lambdaQuery()
                .eq(Report::getServiceId, serviceId)) > 0;
    }

    @Override
    public boolean removeReportByCategoryId(String categoryId) {
        return baseMapper.delete(Wrappers.<Report>lambdaQuery()
                .eq(Report::getCategoryId, categoryId)) > 0;
    }

    @Override
    public boolean removeReportByProjectIdId(String projectId) {
        return baseMapper.delete(Wrappers.<Report>lambdaQuery()
                .eq(Report::getProjectId, projectId)) > 0;
    }

    @Override
    public boolean removeReportByTeamId(String teamId) {
        return baseMapper.delete(Wrappers.<Report>lambdaQuery()
                .eq(Report::getTeamId, teamId)) > 0;
    }

    @Override
    public boolean checkReportTitleUnique(ReportDTO reportDTO) {
        return baseMapper.exists(Wrappers.<Report>lambdaQuery()
                .eq(Report::getReportTitle, reportDTO.getReportTitle())
                .eq(Report::getProjectId, reportDTO.getProjectId())
                .ne(Objects.nonNull(reportDTO.getId()), Report::getId, reportDTO.getId()));
    }

    @Override
    public ReportDataVO listReportConfig(String serviceId) {
        ServiceInfo serviceInfo = serviceInfoMapper.selectOne(Wrappers.<ServiceInfo>lambdaQuery()
                .eq(ServiceInfo::getServiceId, serviceId)
        );

        List<ServiceCol> serviceColList = serviceColMapper.selectList(Wrappers.<ServiceCol>lambdaQuery()
                .eq(ServiceCol::getServiceId, serviceId)
        );

        List<ProTableColumnsVO> proTableColumnsVOList = new ArrayList<>();
        List<ProFormSchemaVO> proFormSchemaVOList = new ArrayList<>();
        // 负数降序，正数升序，绝对值越小，优先级越高
        List<ServiceCol> sortServiceColList = serviceColList.stream().sorted(Comparator.comparingInt(ServiceCol::getDisplaySeq)
                .thenComparingInt(serviceCol -> Math.abs(serviceCol.getDisplaySeq()))
                .reversed()).toList();

        ReportVO report = listOne(new ReportDTO().setServiceId(serviceId));

        // 是否显示行数
        if (report.getAllowRow() == 1) {
            proTableColumnsVOList.add(new ProTableColumnsVO().setType("index").setProp("_index").setLabel("#").setWidth(70));
        }

        for (ServiceCol serviceCol : sortServiceColList) {
            if (serviceCol.getAllowShowInReport() == 1) {
                proTableColumnsVOList.addAll(buildProTableColumns(serviceCol, serviceInfo, report.getAllowFilter()));
            }

            if (serviceCol.getAllowShowInDetail() == 1) {
                proFormSchemaVOList.addAll(buildProFormSchema(serviceCol, serviceInfo));
            }
        }

        return new ReportDataVO()
                .setProTableColumnsList(proTableColumnsVOList)
                .setProFormSchemaList(proFormSchemaVOList)
                .setReport(report);
    }

    /**
     * 构建表格列配置项
     *
     * @param serviceCol  服务列配置项信息
     * @param serviceInfo 服务信息
     * @param allowFilter 是否开启查询功能
     * @return 表格列配置项
     */
    private List<ProTableColumnsVO> buildProTableColumns(ServiceCol serviceCol, ServiceInfo serviceInfo, Integer allowFilter) {
        List<ProTableColumnsVO> proTableColumnsVOList = new ArrayList<>();
        ProTableColumnsVO proTableColumnsVO = new ProTableColumnsVO();
        proTableColumnsVO.setProp(serviceCol.getJsonCol());
        proTableColumnsVO.setLabel(serviceCol.getReportCol());
        proTableColumnsVO.setWidth(serviceCol.getReportColWidth() == -1 ? null : serviceCol.getReportColWidth());
        proTableColumnsVO.setAlign(serviceCol.getColAlign() == 0 ? "left" : serviceCol.getColAlign() == 1 ? "center" : "right");
        if (StringUtil.hasAnyText(serviceCol.getDropdownValue(), serviceCol.getDropdownService(), serviceCol.getDropdownSql())) {
            // 下拉值枚举配置（表格需要根据枚举类格式化，因此不需要开启搜索才获取枚举配置）
            proTableColumnsVO.setEnumMap(getSelectOptions(serviceCol, serviceInfo));
        }

        if (allowFilter == 1 && !Objects.equals(serviceCol.getQueryFilter(), QueryFilterType.NO_FILTER.getIndex())) {
            // 设置搜索的 el 类型
            ProTableColumnsVO.Search search = new ProTableColumnsVO.Search();
            if (StringUtil.hasAnyText(serviceCol.getDropdownValue(), serviceCol.getDropdownService(), serviceCol.getDropdownSql())) {
                // 下拉值枚举 EL 配置
                proTableColumnsVO.setSearch(search.setEl("ElSelect"));
            } else if (serviceCol.getQueryFilter().equals(QueryFilterType.BETWEEN.getIndex())) {
                // 判断字段类型，觉得是日期范围还是日期时间范围
                if (serviceCol.getColType().equals("DateTime") || serviceCol.getColType().equals("Timestamp")) {
                    search.setProps(Map.of("type", "datetimerange"));
                } else if (serviceCol.getColType().equals("Date")) {
                    search.setProps(Map.of("type", "daterange"));
                }
                proTableColumnsVO.setSearch(search.setEl("ElDatePicker"));
            } else {
                // 默认为 input 框
                proTableColumnsVO.setSearch(search.setEl("ElInput"));
            }
        }
        proTableColumnsVOList.add(proTableColumnsVO);
        return proTableColumnsVOList;
    }

    /**
     * 构建表单配置项
     *
     * @param serviceCol  服务列配置项信息
     * @param serviceInfo 服务信息
     * @return 表单配置项
     */
    private List<ProFormSchemaVO> buildProFormSchema(ServiceCol serviceCol, ServiceInfo serviceInfo) {
        List<ProFormSchemaVO> proFormSchemaVOList = new ArrayList<>();

        ProFormSchemaVO proFormSchemaVO = new ProFormSchemaVO();
        proFormSchemaVO.setProp(serviceCol.getJsonCol());
        proFormSchemaVO.setLabel(serviceCol.getReportCol());
        proFormSchemaVO.setCol(serviceCol.getDetailColSpan() == -1 ? null : Map.of("span", serviceCol.getDetailColSpan()));
        proFormSchemaVO.setDefaultValue(serviceCol.getDefaultValue());

        if (StringUtil.hasAnyText(serviceCol.getDropdownValue(), serviceCol.getDropdownService(), serviceCol.getDropdownSql())) {
            // 下拉值枚举配置
            proFormSchemaVO.setEl("ElSelect");
            proFormSchemaVO.setEnumMap(getSelectOptions(serviceCol, serviceInfo));
        } else {
            String colType = serviceCol.getColType();
            // 根据字段类型设置 el
            switch (colType) {
                case "Integer", "Double", "Long" -> proFormSchemaVO.setEl("ElInputNumber");
                case "String" -> proFormSchemaVO.setEl("ElInput");
                case "Date" -> {
                    proFormSchemaVO.setEl("ElDatePicker");
                    proFormSchemaVO.setProps(Map.of("type", "date"));
                }
                case "DateTime", "Timestamp" -> {
                    proFormSchemaVO.setEl("ElDatePicker");
                    proFormSchemaVO.setProps(Map.of("type", "datetime"));
                }
                case "Blob" -> proFormSchemaVO.setEl("ElUpload");
                case "Text" -> {
                    proFormSchemaVO.setEl("ElInput");
                    proFormSchemaVO.setProps(Map.of("type", "textarea", "minRows", 2));
                }
                default -> proFormSchemaVO.setEl("ElInput");
            }
        }
        proFormSchemaVOList.add(proFormSchemaVO);

        return proFormSchemaVOList;
    }

    /**
     * 获取下拉枚举值
     *
     * @param serviceCol  服务列配置项信息
     * @param serviceInfo 服务信息
     * @return 下拉枚举值
     */
    private List<Map<String, Object>> getSelectOptions(ServiceCol serviceCol, ServiceInfo serviceInfo) {
        if (StringUtil.hasText(serviceCol.getDropdownValue())) {
            // 从配置解析获取，必须是 value、label
            return JacksonUtil.toListMap(serviceCol.getDropdownValue());
        }
        if (StringUtil.hasText(serviceCol.getDropdownService())) {
            // 从其他服务获取下拉值，返回值必须是 value、label
            List<LinkedHashMap<String, Object>> linkedHashMapList = apiService.listByServiceId(serviceCol.getDropdownService(), new HashMap<>());

            return linkedHashMapList.stream()
                    .map(originalMap -> Map.of(ColumnConstant.VALUE, originalMap.get(ColumnConstant.VALUE), ColumnConstant.LABEL, originalMap.get(ColumnConstant.LABEL)))
                    .toList();
        }
        if (StringUtil.hasText(serviceCol.getDropdownSql())) {
            // 执行 SQL 获取下拉值
            DataSourceHelper.use(serviceInfo.getDataSourceId());
            List<LinkedHashMap<String, Object>> linkedHashMapList = sqlExecuteMapper.executeSelectList(serviceCol.getDropdownSql(), null);
            DataSourceHelper.close();

            return linkedHashMapList.stream()
                    .map(originalMap -> Map.of(ColumnConstant.VALUE, originalMap.get(ColumnConstant.VALUE), ColumnConstant.LABEL, originalMap.get(ColumnConstant.LABEL)))
                    .toList();
        }
        return null;
    }
}




