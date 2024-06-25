package cn.youngkbt.ag.system.service;

import cn.youngkbt.ag.system.model.dto.ReportDTO;
import cn.youngkbt.ag.system.model.po.Report;
import cn.youngkbt.ag.system.model.vo.ReportVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Kele-Bingtang
 * @date 2024-06-22 00:15:48
 * @note 针对表「t_report（报表表）」的数据库操作 Service
 */
public interface ReportService extends IService<Report> {
    /**
     * 根据条件查询报表信息
     *
     * @param reportDTO 报表查询条件
     * @return 报表信息
     */
    ReportVO listOne(ReportDTO reportDTO);

    /**
     * 添加报表
     *
     * @param reportDTO 报表信息
     * @return 是否添加成功
     */
    boolean addReport(ReportDTO reportDTO);

    /**
     * 编辑报表
     *
     * @param reportDTO 报表信息
     * @return 是否编辑成功
     */
    boolean editReport(ReportDTO reportDTO);

    /**
     * 根据服务 ID 删除报表
     *
     * @param serviceId 服务 ID
     * @return 是否删除成功
     */
    boolean removeReport(String serviceId);

    /**
     * 检查报表标题是否唯一
     *
     * @param reportDTO 报表信息
     * @return 报表标题是否唯一
     */
    boolean checkReportTitleUnique(ReportDTO reportDTO);
    
    /**
     * 检查报表是否允许操作
     *
     * @param projectId 项目 ID
     * @param userId    用户 ID
     */
    void checkReportAllowed(String projectId, String userId);
}
