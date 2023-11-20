package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.uac.sys.mapper.SysTenantMapper;
import cn.youngkbt.uac.sys.model.po.SysTenant;
import cn.youngkbt.uac.sys.service.SysTenantService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_tenant(租户表)】的数据库操作Service实现
 */
@Service
public class SysTenantServiceImpl extends ServiceImpl<SysTenantMapper, SysTenant> implements SysTenantService {

    @Override
    public SysTenant queryByTenantId(String tenantId) {
        return baseMapper.selectOne(Wrappers.<SysTenant>lambdaQuery().eq(SysTenant::getTenantId, tenantId));
    }
}




