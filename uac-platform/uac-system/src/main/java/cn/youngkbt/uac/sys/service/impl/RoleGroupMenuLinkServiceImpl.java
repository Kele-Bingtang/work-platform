package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.mapper.RoleGroupMenuLinkMapper;
import cn.youngkbt.uac.sys.model.dto.RoleGroupMenuLinkDto;
import cn.youngkbt.uac.sys.model.po.RoleGroupMenuLink;
import cn.youngkbt.uac.sys.model.vo.RoleGroupMenuLinkVo;
import cn.youngkbt.uac.sys.service.RoleGroupMenuLinkService;
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
 * @note 针对表【t_role_group_menu_link(角色组关联菜单表)】的数据库操作Service实现
 */
@Service
public class RoleGroupMenuLinkServiceImpl extends ServiceImpl<RoleGroupMenuLinkMapper, RoleGroupMenuLink> implements RoleGroupMenuLinkService {

    @Override
    public List<RoleGroupMenuLinkVo> queryLinkByAppId(RoleGroupMenuLinkDto roleGroupMenuLinkDto, PageQuery pageQuery) {
        LambdaQueryWrapper<RoleGroupMenuLink> wrapper = Wrappers.<RoleGroupMenuLink>lambdaQuery()
                .eq(RoleGroupMenuLink::getAppId, roleGroupMenuLinkDto.getAppId())
                .eq(StringUtil.hasText(roleGroupMenuLinkDto.getRoleGroupId()), RoleGroupMenuLink::getRoleGroupId, roleGroupMenuLinkDto.getRoleGroupId())
                .eq(StringUtil.hasText(roleGroupMenuLinkDto.getMenuId()), RoleGroupMenuLink::getMenuId, roleGroupMenuLinkDto.getMenuId())
                .orderByAsc(RoleGroupMenuLink::getId);

        List<RoleGroupMenuLink> roleGroupMenuLinkList;
        if (Objects.isNull(pageQuery)) {
            roleGroupMenuLinkList = baseMapper.selectList(wrapper);
        } else {
            roleGroupMenuLinkList = baseMapper.selectPage(pageQuery.buildPage(), wrapper).getRecords();
        }
        return MapstructUtil.convert(roleGroupMenuLinkList, RoleGroupMenuLinkVo.class);
    }

    @Override
    public boolean checkRoleGroupExistMenu(String roleGroupId) {
        return baseMapper.exists(Wrappers.<RoleGroupMenuLink>lambdaQuery()
                .eq(RoleGroupMenuLink::getRoleGroupId, roleGroupId));
    }

    @Override
    public boolean checkMenuExistRoleGroup(String menuId) {
        return baseMapper.exists(Wrappers.<RoleGroupMenuLink>lambdaQuery()
                .eq(RoleGroupMenuLink::getMenuId, menuId));
    }

    @Override
    public boolean addOneLink(RoleGroupMenuLinkDto roleGroupMenuLinkDto) {
        RoleGroupMenuLink roleGroupMenuLink = MapstructUtil.convert(roleGroupMenuLinkDto, RoleGroupMenuLink.class);
        return baseMapper.insert(roleGroupMenuLink) > 0;
    }

    @Override
    public boolean updateOneLink(RoleGroupMenuLinkDto roleGroupMenuLinkDto) {
        RoleGroupMenuLink roleGroupMenuLink = MapstructUtil.convert(roleGroupMenuLinkDto, RoleGroupMenuLink.class);
        return baseMapper.updateById(roleGroupMenuLink) > 0;
    }

    @Override
    public boolean removeOneLink(Long id) {
        return baseMapper.deleteById(id) > 0;
    }
}




