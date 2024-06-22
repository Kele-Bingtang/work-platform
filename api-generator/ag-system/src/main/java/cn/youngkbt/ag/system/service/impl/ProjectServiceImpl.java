package cn.youngkbt.ag.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.youngkbt.ag.system.model.po.Project;
import cn.youngkbt.ag.system.service.ProjectService;
import cn.youngkbt.ag.system.mapper.ProjectMapper;
import org.springframework.stereotype.Service;

/**
 * @author Kele-Bingtang
 * @date 2024-06-22 00:15:48
 * @note 针对表「t_project（项目表）」的数据库操作 Service 实现
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

}




