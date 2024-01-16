package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.mapper.RoleMenuLinkMapper;
import cn.youngkbt.uac.sys.model.dto.RoleMenuLinkDto;
import cn.youngkbt.uac.sys.model.po.RoleMenuLink;
import cn.youngkbt.uac.sys.model.vo.RoleMenuLinkVO;
import cn.youngkbt.uac.sys.service.RoleMenuLinkService;
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
 * @note 针对表【t_role_menu_link(角色关联菜单表)】的数据库操作Service实现
 */
@Service
public class RoleMenuLinkServiceImpl extends ServiceImpl<RoleMenuLinkMapper, RoleMenuLink> implements RoleMenuLinkService {

    @Override
    public List<RoleMenuLinkVO> queryLinkByAppId(RoleMenuLinkDto roleMenuLinkDto, PageQuery pageQuery) {
        LambdaQueryWrapper<RoleMenuLink> wrapper = Wrappers.<RoleMenuLink>lambdaQuery()
                .eq(RoleMenuLink::getAppId, roleMenuLinkDto.getAppId())
                .eq(StringUtil.hasText(roleMenuLinkDto.getRoleId()), RoleMenuLink::getRoleId, roleMenuLinkDto.getRoleId())
                .eq(StringUtil.hasText(roleMenuLinkDto.getMenuId()), RoleMenuLink::getMenuId, roleMenuLinkDto.getMenuId())
                .orderByAsc(RoleMenuLink::getId);

        List<RoleMenuLink> roleMenuLinkList;
        if (Objects.isNull(pageQuery)) {
            roleMenuLinkList = baseMapper.selectList(wrapper);
        } else {
            roleMenuLinkList = baseMapper.selectPage(pageQuery.buildPage(), wrapper).getRecords();
        }
        return MapstructUtil.convert(roleMenuLinkList, RoleMenuLinkVO.class);
    }

    @Override
    public Boolean checkMenuExistRole(String menuId) {
        return baseMapper.exists(Wrappers.<RoleMenuLink>lambdaQuery()
                .eq(RoleMenuLink::getMenuId, menuId));
    }

    @Override
    public Boolean checkRoleExistMenu(String roleId) {
        return baseMapper.exists(Wrappers.<RoleMenuLink>lambdaQuery()
                .eq(RoleMenuLink::getRoleId, roleId));
    }

    @Override
    public Boolean addOneLink(RoleMenuLinkDto roleMenuLinkDto) {
        RoleMenuLink menuLink = MapstructUtil.convert(roleMenuLinkDto, RoleMenuLink.class);
        return baseMapper.insert(menuLink) > 0;
    }

    @Override
    public Boolean updateOneLink(RoleMenuLinkDto roleMenuLinkDto) {
        RoleMenuLink menuLink = MapstructUtil.convert(roleMenuLinkDto, RoleMenuLink.class);
        return baseMapper.updateById(menuLink) > 0;
    }

    @Override
    public Boolean removeOneLink(Long id) {
        return baseMapper.deleteById(id) > 0;
    }
}




