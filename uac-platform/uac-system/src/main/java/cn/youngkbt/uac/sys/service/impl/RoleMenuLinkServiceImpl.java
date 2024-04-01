package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.mapper.RoleMenuLinkMapper;
import cn.youngkbt.uac.sys.model.dto.RoleMenuLinkDTO;
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
    public List<RoleMenuLinkVO> queryLinkByAppId(RoleMenuLinkDTO roleMenuLinkDTO, PageQuery pageQuery) {
        LambdaQueryWrapper<RoleMenuLink> wrapper = Wrappers.<RoleMenuLink>lambdaQuery()
                .eq(RoleMenuLink::getAppId, roleMenuLinkDTO.getAppId())
                .eq(StringUtil.hasText(roleMenuLinkDTO.getRoleId()), RoleMenuLink::getRoleId, roleMenuLinkDTO.getRoleId())
                .eq(StringUtil.hasText(roleMenuLinkDTO.getMenuId()), RoleMenuLink::getMenuId, roleMenuLinkDTO.getMenuId())
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
    public boolean checkMenuExistRole(String menuId) {
        return baseMapper.exists(Wrappers.<RoleMenuLink>lambdaQuery()
                .eq(RoleMenuLink::getMenuId, menuId));
    }

    @Override
    public boolean checkRoleExistMenu(String roleId) {
        return baseMapper.exists(Wrappers.<RoleMenuLink>lambdaQuery()
                .eq(RoleMenuLink::getRoleId, roleId));
    }

    @Override
    public boolean addOneLink(RoleMenuLinkDTO roleMenuLinkDTO) {
        RoleMenuLink menuLink = MapstructUtil.convert(roleMenuLinkDTO, RoleMenuLink.class);
        return baseMapper.insert(menuLink) > 0;
    }

    @Override
    public boolean updateOneLink(RoleMenuLinkDTO roleMenuLinkDTO) {
        RoleMenuLink menuLink = MapstructUtil.convert(roleMenuLinkDTO, RoleMenuLink.class);
        return baseMapper.updateById(menuLink) > 0;
    }

    @Override
    public boolean removeOneLink(Long id) {
        return baseMapper.deleteById(id) > 0;
    }
}




