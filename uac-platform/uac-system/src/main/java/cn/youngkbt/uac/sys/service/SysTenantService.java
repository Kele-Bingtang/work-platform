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

    /**
     * 根据租户 ID 查询租户信息
     *
     * @param tenantId 租户 ID
     * @return 租户信息
     */
    SysTenant queryByTenantId(String tenantId);

    /**
     * 根据主键查询租户信息
     *
     * @param id 主键
     * @return 租户信息
     */
    SysTenantVO listById(Long id);

    /**
     * 通过条件查询租户列表
     *
     * @param sysTenantDto 查询条件
     * @param pageQuery    分页参数
     * @return 租户列表
     */
    List<SysTenantVO> listWithPage(SysTenantDTO sysTenantDto, PageQuery pageQuery);

    /**
     * 新增租户
     *
     * @param sysTenantDto 租户信息
     * @return 是否新增成功
     */
    boolean insertOne(SysTenantDTO sysTenantDto);

    /**
     * 更新租户
     *
     * @param sysTenantDto 租户信息
     * @return 是否更新成功
     */
    boolean updateOne(SysTenantDTO sysTenantDto);

    /**
     * 删除租户
     *
     * @param ids 主键列表
     * @return 是否删除成功
     */
    boolean removeBatch(List<Long> ids);

    /**
     * 校验租户名称是否唯一
     *
     * @param sysTenantDto 租户信息
     * @return 是否唯一
     */
    boolean checkCompanyNameUnique(SysTenantDTO sysTenantDto);
}
