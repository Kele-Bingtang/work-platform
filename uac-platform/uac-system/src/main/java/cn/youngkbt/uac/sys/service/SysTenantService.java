package cn.youngkbt.uac.sys.service;

import cn.youngkbt.uac.sys.model.po.SysTenant;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_tenant(租户表)】的数据库操作Service
 */
public interface SysTenantService extends IService<SysTenant> {

    SysTenant queryByTenantId(String tenantId);
}
