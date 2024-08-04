package cn.youngkbt.ag.system.service;

import cn.youngkbt.ag.system.model.vo.ProjectStatisticVO;

/**
 * @author Kele-Bingtang
 * @date 2024/8/4 15:39:25
 * @note
 */
public interface ProjectStatisticService {
    /**
     * 获取项目的统计数据
     *
     * @param projectId 项目 ID
     * @return 项目的统计数据
     */
    ProjectStatisticVO getBaseProjectStatistic(String projectId);
}
