package cn.youngkbt.ag.system.service.impl;

import cn.youngkbt.ag.system.mapper.ServiceMapper;
import cn.youngkbt.ag.system.model.po.ServiceInfo;
import cn.youngkbt.ag.system.service.ServiceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Kele-Bingtang
 * @date 2024-06-22 00:15:48
 * @note 针对表「t_service（服务表）」的数据库操作 Service 实现
 */
@Service
public class ServiceServiceImpl extends ServiceImpl<ServiceMapper, ServiceInfo> implements ServiceService {

}




