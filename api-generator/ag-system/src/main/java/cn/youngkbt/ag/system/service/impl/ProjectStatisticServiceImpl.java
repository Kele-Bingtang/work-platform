package cn.youngkbt.ag.system.service.impl;

import cn.youngkbt.ag.system.mapper.CategoryMapper;
import cn.youngkbt.ag.system.mapper.ServiceInfoMapper;
import cn.youngkbt.ag.system.model.po.Category;
import cn.youngkbt.ag.system.model.po.ServiceInfo;
import cn.youngkbt.ag.system.model.vo.ProjectStatisticVO;
import cn.youngkbt.ag.system.service.ProjectStatisticService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Kele-Bingtang
 * @date 2024/8/4 15:39:48
 * @note
 */
@Service
@RequiredArgsConstructor
public class ProjectStatisticServiceImpl implements ProjectStatisticService {
    
    private final ServiceInfoMapper serviceInfoMapper;
    private final CategoryMapper categoryMapper;
    
    @Override
    public ProjectStatisticVO getBaseProjectStatistic(String projectId) {
        Long serviceCount = serviceInfoMapper.selectCount(Wrappers.<ServiceInfo>lambdaQuery()
                .eq(ServiceInfo::getProjectId, projectId));

        Long categoryCount = categoryMapper.selectCount(Wrappers.<Category>lambdaQuery()
                .eq(Category::getProjectId, projectId));

        return ProjectStatisticVO.builder()
                .serviceCount(serviceCount)
                .categoryCount(categoryCount)
                .build();
    }
}
