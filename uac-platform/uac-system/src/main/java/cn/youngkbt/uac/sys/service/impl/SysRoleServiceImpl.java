package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.core.error.Assert;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.mapper.SysRoleMapper;
import cn.youngkbt.uac.sys.model.dto.SysRoleDto;
import cn.youngkbt.uac.sys.model.po.SysRole;
import cn.youngkbt.uac.sys.model.vo.SysRoleVo;
import cn.youngkbt.uac.sys.service.SysRoleService;
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
 * @note 针对表【t_sys_role(应用角色信息表)】的数据库操作Service实现
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public SysRoleVo queryById(Long id) {
        SysRole sysRole = baseMapper.selectById(id);
        Assert.nonNull(sysRole, "角色不存在");
        return MapstructUtil.convert(sysRole, SysRoleVo.class);
    }

    @Override
    public List<SysRoleVo> queryListWithPage(SysRoleDto sysRoleDto, PageQuery pageQuery) {
        LambdaQueryWrapper<SysRole> wrapper = Wrappers.<SysRole>lambdaQuery()
                .eq(StringUtils.hasText(sysRoleDto.getRoleId()), SysRole::getRoleId, sysRoleDto.getRoleId())
                .eq(StringUtils.hasText(sysRoleDto.getRoleCode()), SysRole::getRoleCode, sysRoleDto.getRoleCode())
                .eq(StringUtils.hasText(sysRoleDto.getAppId()), SysRole::getAppId, sysRoleDto.getAppId())
                .eq(Objects.nonNull(sysRoleDto.getStatus()), SysRole::getStatus, sysRoleDto.getStatus())
                .orderByAsc(SysRole::getOrderNum);

        List<SysRole> sysRoleList;
        if (Objects.isNull(pageQuery)) {
            sysRoleList = baseMapper.selectList(wrapper);
        } else {
            sysRoleList = baseMapper.selectPage(pageQuery.buildPage(), wrapper).getRecords();
        }
        return MapstructUtil.convert(sysRoleList, SysRoleVo.class);
    }

    @Override
    public Boolean insertOne(SysRoleDto sysRoleDto) {
        SysRole sysRole = MapstructUtil.convert(sysRoleDto, SysRole.class);
        return baseMapper.insert(sysRole) > 0;
    }

    @Override
    public Boolean updateOne(SysRoleDto sysRoleDto) {
        SysRole sysRole = MapstructUtil.convert(sysRoleDto, SysRole.class);
        return baseMapper.updateById(sysRole) > 0;
    }

    @Override
    public Boolean removeOne(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}




