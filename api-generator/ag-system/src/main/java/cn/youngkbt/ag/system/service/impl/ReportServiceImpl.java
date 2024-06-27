package cn.youngkbt.ag.system.service.impl;

import cn.youngkbt.ag.system.mapper.ReportMapper;
import cn.youngkbt.ag.system.model.dto.ReportDTO;
import cn.youngkbt.ag.system.model.po.Report;
import cn.youngkbt.ag.system.model.vo.ReportVO;
import cn.youngkbt.ag.system.service.ReportService;
import cn.youngkbt.utils.MapstructUtil;
import cn.youngkbt.utils.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2024-06-22 00:15:48
 * @note 针对表「t_report（报表）」的数据库操作 Service 实现
 */
@Service
@RequiredArgsConstructor
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements ReportService {

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
        // return baseMapper.delete(Wrappers.<Report>lambdaQuery()
        //         .eq(Report::getCategoryId, categoryId)) > 0;
        return false;
    }

    @Override
    public boolean removeReportByProjectIdId(String projectId) {
        return baseMapper.delete(Wrappers.<Report>lambdaQuery()
                .eq(Report::getProjectId, projectId)) > 0;
    }

    @Override
    public boolean removeReportByTeamId(String teamId) {
        // return baseMapper.delete(Wrappers.<Report>lambdaQuery()
        //         .eq(Report::getTeamId, teamId)) > 0;
        return false;
    }

    @Override
    public boolean checkReportTitleUnique(ReportDTO reportDTO) {
        return baseMapper.exists(Wrappers.<Report>lambdaQuery()
                .eq(Report::getReportTitle, reportDTO.getReportTitle())
                .eq(Report::getProjectId, reportDTO.getProjectId())
                .ne(Objects.nonNull(reportDTO.getId()), Report::getId, reportDTO.getId()));
    }
}




