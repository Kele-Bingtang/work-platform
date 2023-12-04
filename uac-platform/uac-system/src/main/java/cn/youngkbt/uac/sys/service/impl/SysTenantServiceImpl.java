package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.core.error.Assert;
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
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

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
    public Boolean insertOne(SysTenantDto sysTenantDto) {
        SysTenant sysTenant = MapstructUtil.convert(sysTenantDto, SysTenant.class);
        return baseMapper.insert(sysTenant) > 0;
    }

    @Override
    public Boolean updateOne(SysTenantDto sysTenantDto) {
        SysTenant sysTenant = MapstructUtil.convert(sysTenantDto, SysTenant.class);
        return baseMapper.updateById(sysTenant) > 0;
    }

    @Override
    public Boolean removeOne(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}




