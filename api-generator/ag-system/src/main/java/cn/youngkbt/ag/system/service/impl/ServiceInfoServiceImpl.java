package cn.youngkbt.ag.system.service.impl;

import cn.youngkbt.ag.core.helper.AgHelper;
import cn.youngkbt.ag.system.mapper.ProjectMapper;
import cn.youngkbt.ag.system.mapper.ServiceInfoMapper;
import cn.youngkbt.ag.system.model.dto.ReportDTO;
import cn.youngkbt.ag.system.model.dto.ServiceInfoDTO;
import cn.youngkbt.ag.system.model.po.Project;
import cn.youngkbt.ag.system.model.po.ServiceInfo;
import cn.youngkbt.ag.system.model.vo.ServiceInfoVO;
import cn.youngkbt.ag.system.permission.PermissionHelper;
import cn.youngkbt.ag.system.service.ReportService;
import cn.youngkbt.ag.system.service.ServiceColService;
import cn.youngkbt.ag.system.service.ServiceInfoService;
import cn.youngkbt.core.error.Assert;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.utils.JacksonUtil;
import cn.youngkbt.utils.MapstructUtil;
import cn.youngkbt.utils.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2024-06-22 00:15:48
 * @note 针对表「t_service（服务表）」的数据库操作 Service 实现
 */
@Service
@RequiredArgsConstructor
public class ServiceInfoServiceImpl extends ServiceImpl<ServiceInfoMapper, ServiceInfo> implements ServiceInfoService {

    private final ReportService reportService;
    private final ServiceColService serviceColService;
    private final ProjectMapper projectMapper;

    @Override
    public ServiceInfoVO getByServiceId(String serviceId) {
        ServiceInfo serviceInfo = baseMapper.selectOne(Wrappers.<ServiceInfo>lambdaQuery()
                .eq(ServiceInfo::getServiceId, serviceId));
        Assert.nonNull(serviceInfo, "服务不存在");
        PermissionHelper.checkProjectOperator(AgHelper.getUserId(), serviceInfo.getProjectId(), "1h");

        ServiceInfoVO serviceInfoVO = MapstructUtil.convert(serviceInfo, ServiceInfoVO.class);
        boolean checkExitCol = serviceColService.checkExitCol(serviceId);
        serviceInfoVO.setExitCol(checkExitCol);
        return serviceInfoVO;
    }

    @Override
    public TablePage<ServiceInfoVO> listPage(ServiceInfoDTO serviceInfoDTO, PageQuery pageQuery) {
        LambdaQueryWrapper<ServiceInfo> wrapper = buildQueryWrapper(serviceInfoDTO);
        Page<ServiceInfo> serviceInfoPage = baseMapper.selectPage(pageQuery.buildPage(), wrapper);

        return TablePage.build(serviceInfoPage, ServiceInfoVO.class);
    }

    private LambdaQueryWrapper<ServiceInfo> buildQueryWrapper(ServiceInfoDTO serviceInfoDTO) {
        return Wrappers.<ServiceInfo>lambdaQuery()
                .eq(StringUtil.hasText(serviceInfoDTO.getServiceName()), ServiceInfo::getServiceName, serviceInfoDTO.getServiceName())
                .eq(StringUtil.hasText(serviceInfoDTO.getServiceUrl()), ServiceInfo::getServiceUrl, serviceInfoDTO.getServiceUrl())
                .eq(StringUtil.hasText(serviceInfoDTO.getProjectId()), ServiceInfo::getProjectId, serviceInfoDTO.getProjectId())
                .eq(StringUtil.hasText(serviceInfoDTO.getCategoryId()), ServiceInfo::getCategoryId, serviceInfoDTO.getCategoryId())
                .orderByDesc(ServiceInfo::getCreateTime);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addService(ServiceInfoDTO serviceInfoDTO) {
        ServiceInfo serviceInfo = MapstructUtil.convert(serviceInfoDTO, ServiceInfo.class);
        
        Project project = projectMapper.selectOne(Wrappers.<Project>lambdaQuery()
                .eq(Project::getProjectId, serviceInfo.getProjectId()));
        
        serviceInfo.setFullUrl(project.getBaseUrl() + serviceInfo.getServiceUrl());

        // 如果没有设置报表标题，则默认为服务名称
        if (StringUtil.hasEmpty(serviceInfo.getReportTitle())) {
            serviceInfo.setReportTitle(serviceInfo.getServiceName());
        }

        int result = baseMapper.insert(serviceInfo);

        if (result != 0) {
            // 新增报表
            ReportDTO reportDTO = new ReportDTO();
            reportDTO.setReportTitle(serviceInfo.getServiceName());
            reportDTO.setDescription(serviceInfo.getServiceName());
            reportDTO.setServiceId(serviceInfo.getServiceId());

            reportService.addReport(reportDTO);
        }

        return result > 0;
    }

    @Override
    public boolean editService(ServiceInfoDTO serviceInfoDTO) {
        ServiceInfo serviceInfo = MapstructUtil.convert(serviceInfoDTO, ServiceInfo.class);
        // 降级响应数据转为字符串存入数据库
        if (Objects.nonNull(serviceInfoDTO.getBreakingRespond())) {
            serviceInfo.setBreakingRespond(JacksonUtil.toJsonStr(serviceInfoDTO.getBreakingRespond()));
        }

        return baseMapper.updateById(serviceInfo) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeService(String serviceId) {
        ServiceInfo serviceInfo = baseMapper.selectOne(Wrappers.<ServiceInfo>lambdaQuery()
                .eq(ServiceInfo::getServiceId, serviceId));
        PermissionHelper.checkProjectOperator(AgHelper.getUserId(), serviceInfo.getProjectId(), "1h");

        // 删除报表
        reportService.removeReport(serviceId);
        // 删除服务的所有列配置项
        serviceColService.removeAllServiceColByServiceId(serviceId);

        return baseMapper.delete(Wrappers.<ServiceInfo>lambdaQuery()
                .eq(ServiceInfo::getServiceId, serviceId)) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeAllServiceInfo(String categoryId) {
        // 删除目录下所有报表
        reportService.removeReportByCategoryId(categoryId);
        // 删除目录下所有所有列配置项
        serviceColService.removeAllServiceColByServiceIdByCategoryId(categoryId);

        return baseMapper.delete(Wrappers.<ServiceInfo>lambdaQuery()
                .eq(ServiceInfo::getCategoryId, categoryId)) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeAllServiceInfoByProjectId(String projectId) {
        // 删除项目下所有报表
        reportService.removeReportByProjectIdId(projectId);
        // 删除项目下所有所有列配置项
        serviceColService.removeAllServiceColByServiceIdByProjectIdId(projectId);

        return baseMapper.delete(Wrappers.<ServiceInfo>lambdaQuery()
                .eq(ServiceInfo::getProjectId, projectId)) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeAllServiceInfoByTeamId(String teamId) {
        // 删除目录下所有报表
        reportService.removeReportByTeamId(teamId);
        // 删除目录下所有所有列配置项
        serviceColService.removeAllServiceColByServiceIdByTeamId(teamId);

        return baseMapper.delete(Wrappers.<ServiceInfo>lambdaQuery()
                .eq(ServiceInfo::getTeamId, teamId)) > 0;
    }

    @Override
    public boolean checkServiceNameUnique(ServiceInfoDTO serviceInfoDTO) {
        return baseMapper.exists(Wrappers.<ServiceInfo>lambdaQuery()
                .eq(ServiceInfo::getServiceName, serviceInfoDTO.getServiceName())
                .eq(ServiceInfo::getProjectId, serviceInfoDTO.getProjectId())
                .ne(Objects.nonNull(serviceInfoDTO.getId()), ServiceInfo::getId, serviceInfoDTO.getId()));
    }

    @Override
    public boolean checkServiceUrlUnique(ServiceInfoDTO serviceInfoDTO) {
        return baseMapper.exists(Wrappers.<ServiceInfo>lambdaQuery()
                .eq(ServiceInfo::getServiceUrl, serviceInfoDTO.getServiceUrl())
                .eq(ServiceInfo::getProjectId, serviceInfoDTO.getProjectId())
                .ne(Objects.nonNull(serviceInfoDTO.getId()), ServiceInfo::getId, serviceInfoDTO.getId()));
    }

    @Override
    public Integer generateCol(ServiceInfoDTO serviceInfoDTO) {
        // 保存服务信息
        editService(serviceInfoDTO);
        // 生成列配置项
        return serviceColService.listColumnThenInsert(serviceInfoDTO);
    }

}




