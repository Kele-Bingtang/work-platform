package cn.youngkbt.uac.sys.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.youngkbt.core.error.Assert;
import cn.youngkbt.core.exception.ServiceException;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.uac.sys.mapper.SysTenantMapper;
import cn.youngkbt.uac.sys.model.dto.SysTenantDTO;
import cn.youngkbt.uac.sys.model.dto.SysUserDTO;
import cn.youngkbt.uac.sys.model.po.SysTenant;
import cn.youngkbt.uac.sys.model.vo.SysTenantVO;
import cn.youngkbt.uac.sys.service.SysTenantService;
import cn.youngkbt.uac.sys.service.SysUserService;
import cn.youngkbt.utils.MapstructUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_tenant(租户表)】的数据库操作Service实现
 */
@Service
@RequiredArgsConstructor
public class SysTenantServiceImpl extends ServiceImpl<SysTenantMapper, SysTenant> implements SysTenantService {

    private final ReentrantLock reentrantLock = new ReentrantLock();
    private final SysUserService sysUserService;

    @Override
    public SysTenant queryByTenantId(String tenantId) {
        return baseMapper.selectOne(Wrappers.<SysTenant>lambdaQuery().eq(SysTenant::getTenantId, tenantId));
    }

    @Override
    public SysTenantVO listById(Long id) {
        SysTenant sysTenant = baseMapper.selectById(id);
        Assert.nonNull(sysTenant, "租户不存在");
        return MapstructUtil.convert(sysTenant, SysTenantVO.class);
    }

    @Override
    public List<SysTenantVO> queryList(SysTenantDTO sysTenantDTO) {
        LambdaQueryWrapper<SysTenant> wrapper = buildQueryWrapper(sysTenantDTO);
        List<SysTenant> sysTenantList = baseMapper.selectList(wrapper);
        
        return MapstructUtil.convert(sysTenantList, SysTenantVO.class);
    }

    @Override
    public TablePage<SysTenantVO> listPage(SysTenantDTO sysTenantDTO, PageQuery pageQuery) {
        LambdaQueryWrapper<SysTenant> wrapper = buildQueryWrapper(sysTenantDTO);
        Page<SysTenant> sysTenantPage = baseMapper.selectPage(pageQuery.buildPage(), wrapper);
        
        return TablePage.build(sysTenantPage, SysTenantVO.class);
    }

    private LambdaQueryWrapper<SysTenant> buildQueryWrapper(SysTenantDTO sysTenantDTO) {
        return Wrappers.<SysTenant>lambdaQuery()
                .eq(StringUtils.hasText(sysTenantDTO.getTenantId()), SysTenant::getTenantId, sysTenantDTO.getTenantId())
                .eq(Objects.nonNull(sysTenantDTO.getUserCount()), SysTenant::getUserCount, sysTenantDTO.getUserCount())
                .eq(Objects.nonNull(sysTenantDTO.getStatus()), SysTenant::getStatus, sysTenantDTO.getStatus())
                .orderByAsc(SysTenant::getId);
    }

    @Override
    public boolean checkCompanyNameUnique(SysTenantDTO sysTenantDTO) {
        return baseMapper.exists(new LambdaQueryWrapper<SysTenant>()
                .eq(SysTenant::getTenantName, sysTenantDTO.getTenantName())
                .ne(Objects.nonNull(sysTenantDTO.getTenantId()), SysTenant::getTenantId, sysTenantDTO.getTenantId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertOne(SysTenantDTO sysTenantDTO) {
        SysTenant sysTenant = MapstructUtil.convert(sysTenantDTO, SysTenant.class);
        // 获取数据库所有的租户 ID，然后根据最后一个生成新的 ID
        List<SysTenant> sysTenantList = baseMapper.selectList(Wrappers.<SysTenant>lambdaQuery().select(SysTenant::getTenantId));

        reentrantLock.lock();
        boolean result;
        try {
            List<String> tenantIds = sysTenantList.stream().filter(Objects::nonNull).map(t -> String.valueOf(t.getTenantId())).toList();
            // 生成新的租户 ID
            String tenantId = generateTenantId(tenantIds);
            sysTenant.setTenantId(tenantId);
            result = baseMapper.insert(sysTenant) > 0;
        } finally {
            reentrantLock.unlock();
        }
        if (!result) {
            throw new ServiceException("创建租户失败");
        }

        // 创建系统用户
        SysUserDTO sysUserDTO = new SysUserDTO().setUsername(sysTenant.getTenantName())
                .setPassword(sysTenantDTO.getTenantName());
        sysUserService.insertOne(sysUserDTO);

        // 创建角色
        

        // 创建菜单

        // 创建部门: 企业名称是部门名称

        // 创建字典数据

        return true;
    }

    private String generateTenantId(List<String> tenantIds) {
        // 随机生成6位
        String numbers = RandomUtil.randomNumbers(6);
        // 判断是否存在，如果存在则重新生成
        if (tenantIds.contains(numbers)) {
            generateTenantId(tenantIds);
        }
        return numbers;
    }
    
    // private String createSysRoleAndMenuThenGet(String tenantId) {
        // 创建角色
        // SysRole role = new SysRole();
        // role.setTenantId(tenantId);
        // role.setRoleName(TenantConstants.TENANT_ADMIN_ROLE_NAME);
        // role.setRoleKey(TenantConstants.TENANT_ADMIN_ROLE_KEY);
        // role.setRoleSort(1);
        // role.setStatus(TenantConstants.NORMAL);
    // }

    @Override
    public boolean updateOne(SysTenantDTO sysTenantDTO) {
        SysTenant sysTenant = MapstructUtil.convert(sysTenantDTO, SysTenant.class);
        return baseMapper.updateById(sysTenant) > 0;
    }

    @Override
    public boolean removeBatch(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}




