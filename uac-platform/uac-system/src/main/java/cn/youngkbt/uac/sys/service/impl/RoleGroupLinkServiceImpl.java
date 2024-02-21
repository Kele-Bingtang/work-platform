package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.mapper.RoleGroupLinkMapper;
import cn.youngkbt.uac.sys.model.dto.RoleGroupLinkDto;
import cn.youngkbt.uac.sys.model.po.RoleGroupLink;
import cn.youngkbt.uac.sys.model.vo.RoleGroupLinkVo;
import cn.youngkbt.uac.sys.service.RoleGroupLinkService;
import cn.youngkbt.utils.MapstructUtil;
import cn.youngkbt.utils.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_role_group_link(角色关联角色组表)】的数据库操作Service实现
 */
@Service
public class RoleGroupLinkServiceImpl extends ServiceImpl<RoleGroupLinkMapper, RoleGroupLink> implements RoleGroupLinkService {
    @Override
    public List<RoleGroupLinkVo> queryLinkByAppId(RoleGroupLinkDto roleGroupLinkDto, PageQuery pageQuery) {
        LambdaQueryWrapper<RoleGroupLink> wrapper = Wrappers.<RoleGroupLink>lambdaQuery()
                .eq(RoleGroupLink::getAppId, roleGroupLinkDto.getAppId())
                .eq(StringUtil.hasText(roleGroupLinkDto.getRoleId()), RoleGroupLink::getRoleId, roleGroupLinkDto.getRoleId())
                .eq(StringUtil.hasText(roleGroupLinkDto.getRoleGroupId()), RoleGroupLink::getRoleGroupId, roleGroupLinkDto.getRoleGroupId())
                .orderByAsc(RoleGroupLink::getId);

        List<RoleGroupLink> roleGroupLinkList;
        if (Objects.isNull(pageQuery)) {
            roleGroupLinkList = baseMapper.selectList(wrapper);
        } else {
            roleGroupLinkList = baseMapper.selectPage(pageQuery.buildPage(), wrapper).getRecords();
        }
        return MapstructUtil.convert(roleGroupLinkList, RoleGroupLinkVo.class);
    }

    @Override
    public boolean checkRoleExistRoleGroup(String roleId) {
        return baseMapper.exists(Wrappers.<RoleGroupLink>lambdaQuery()
                .eq(RoleGroupLink::getRoleId, roleId));
    }

    @Override
    public boolean checkRoleGroupExistRole(String roleGroupId) {
        return baseMapper.exists(Wrappers.<RoleGroupLink>lambdaQuery()
                .eq(RoleGroupLink::getRoleGroupId, roleGroupId));
    }

    @Override
    public boolean addOneLink(RoleGroupLinkDto roleGroupLinkDto) {
        RoleGroupLink roleGroupLink = MapstructUtil.convert(roleGroupLinkDto, RoleGroupLink.class);
        return baseMapper.insert(roleGroupLink) > 0;
    }

    @Override
    public boolean updateOneLink(RoleGroupLinkDto roleGroupLinkDto) {
        RoleGroupLink roleGroupLink = MapstructUtil.convert(roleGroupLinkDto, RoleGroupLink.class);
        return baseMapper.updateById(roleGroupLink) > 0;
    }

    @Override
    public boolean removeOneLink(Long id) {
        return baseMapper.deleteById(id) > 0;
    }
}




