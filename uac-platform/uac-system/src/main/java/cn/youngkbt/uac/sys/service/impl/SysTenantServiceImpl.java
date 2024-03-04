package cn.youngkbt.uac.sys.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.youngkbt.core.error.Assert;
import cn.youngkbt.core.exception.ServiceException;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.mapper.SysTenantMapper;
import cn.youngkbt.uac.sys.model.dto.SysTenantDto;
import cn.youngkbt.uac.sys.model.po.SysTenant;
import cn.youngkbt.uac.sys.model.vo.SysTenantVo;
import cn.youngkbt.uac.sys.service.SysTenantService;
import cn.youngkbt.utils.MapstructUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class SysTenantServiceImpl extends ServiceImpl<SysTenantMapper, SysTenant> implements SysTenantService {
    
    private final ReentrantLock reentrantLock = new ReentrantLock();

    @Override
    public SysTenant queryByTenantId(String tenantId) {
        return baseMapper.selectOne(Wrappers.<SysTenant>lambdaQuery().eq(SysTenant::getTenantId, tenantId));
    }

    @Override
    public SysTenantVo queryById(Long id) {
        SysTenant sysTenant = baseMapper.selectById(id);
        Assert.nonNull(sysTenant, "租户不存在");
        return MapstructUtil.convert(sysTenant, SysTenantVo.class);
    }

    @Override
    public List<SysTenantVo> queryListWithPage(SysTenantDto sysTenantDto, PageQuery pageQuery) {
        LambdaQueryWrapper<SysTenant> wrapper = Wrappers.<SysTenant>lambdaQuery()
                .eq(StringUtils.hasText(sysTenantDto.getTenantId()), SysTenant::getTenantId, sysTenantDto.getTenantId())
                .eq(Objects.nonNull(sysTenantDto.getUserCount()), SysTenant::getUserCount, sysTenantDto.getUserCount())
                .eq(Objects.nonNull(sysTenantDto.getStatus()), SysTenant::getStatus, sysTenantDto.getStatus())
                .orderByAsc(SysTenant::getId);

        List<SysTenant> sysTenantList;
        if (Objects.isNull(pageQuery)) {
            sysTenantList = baseMapper.selectList(wrapper);
        } else {
            sysTenantList = baseMapper.selectPage(pageQuery.buildPage(), wrapper).getRecords();
        }
        return MapstructUtil.convert(sysTenantList, SysTenantVo.class);
    }

    @Override
    public boolean checkCompanyNameUnique(SysTenantDto sysTenantDto) {
        return baseMapper.exists(new LambdaQueryWrapper<SysTenant>()
                .eq(SysTenant::getTenantName, sysTenantDto.getTenantName())
                .ne(Objects.nonNull(sysTenantDto.getTenantId()), SysTenant::getTenantId, sysTenantDto.getTenantId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertOne(SysTenantDto sysTenantDto) {
        SysTenant sysTenant = MapstructUtil.convert(sysTenantDto, SysTenant.class);
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
        
        // TODO
        // 创建角色

        // 创建菜单

        // 创建部门: 企业名称是部门名称
        
        // 创建系统用户
        
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

    @Override
    public boolean updateOne(SysTenantDto sysTenantDto) {
        SysTenant sysTenant = MapstructUtil.convert(sysTenantDto, SysTenant.class);
        return baseMapper.updateById(sysTenant) > 0;
    }

    @Override
    public boolean removeBatch(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}




