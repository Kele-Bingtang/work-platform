package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysTenantDTO;
import cn.youngkbt.uac.sys.model.po.SysTenant;
import cn.youngkbt.uac.sys.model.vo.SysTenantVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_tenant(租户表)】的数据库操作Service
 */
public interface SysTenantService extends IService<SysTenant> {

    SysTenant queryByTenantId(String tenantId);

    SysTenantVO listById(Long id);

    List<SysTenantVO> queryListWithPage(SysTenantDTO sysTenantDto, PageQuery pageQuery);

    boolean insertOne(SysTenantDTO sysTenantDto);

    boolean updateOne(SysTenantDTO sysTenantDto);

    boolean removeBatch(List<Long> ids);

    boolean checkCompanyNameUnique(SysTenantDTO sysTenantDto);
}
