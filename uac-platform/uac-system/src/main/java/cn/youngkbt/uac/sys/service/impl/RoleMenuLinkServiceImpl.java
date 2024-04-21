package cn.youngkbt.uac.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.youngkbt.core.constants.ColumnConstant;
import cn.youngkbt.uac.sys.mapper.RoleMenuLinkMapper;
import cn.youngkbt.uac.sys.model.dto.RoleMenuLinkDTO;
import cn.youngkbt.uac.sys.model.dto.SysRoleDTO;
import cn.youngkbt.uac.sys.model.po.RoleMenuLink;
import cn.youngkbt.uac.sys.model.po.SysMenu;
import cn.youngkbt.uac.sys.service.RoleMenuLinkService;
import cn.youngkbt.uac.sys.utils.TreeBuildUtil;
import cn.youngkbt.utils.ListUtil;
import cn.youngkbt.utils.MapstructUtil;
import cn.youngkbt.utils.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_role_menu_link(角色关联菜单表)】的数据库操作Service实现
 */
@Service
public class RoleMenuLinkServiceImpl extends ServiceImpl<RoleMenuLinkMapper, RoleMenuLink> implements RoleMenuLinkService {


    @Override
    public List<String> listMenuIdsByRoleId(String roleId, String appId, String tenantId) {

        List<RoleMenuLink> roleMenuLinks = baseMapper.selectList(Wrappers.<RoleMenuLink>lambdaQuery()
                .eq(RoleMenuLink::getRoleId, roleId)
                .eq(StringUtil.hasText(appId), RoleMenuLink::getAppId, appId)
                .eq(StringUtil.hasText(tenantId), RoleMenuLink::getTenantId, tenantId)
        );

        if (ListUtil.isNotEmpty(roleMenuLinks)) {
            return roleMenuLinks.stream().map(RoleMenuLink::getMenuId).toList();
        }

        return List.of();
    }

    @Override
    public List<Tree<String>> listMenuListByRoleId(String roleId, String appId) {
        QueryWrapper<RoleMenuLink> wrapper = Wrappers.query();
        wrapper.eq("trml.is_deleted", 0)
                .eq("trml.role_id", roleId)
                .eq(StringUtil.hasText(appId), "trml.app_id", appId);

        List<SysMenu> sysMenu = baseMapper.listMenuListByRoleId(wrapper);
        return buildDeptTree(sysMenu);
    }

    /**
     * 构建前端所需要下拉树结构
     */
    private List<Tree<String>> buildDeptTree(List<SysMenu> sysMenuList) {
        if (CollUtil.isEmpty(sysMenuList)) {
            return Collections.emptyList();
        }

        return TreeBuildUtil.build(sysMenuList, ColumnConstant.PARENT_ID, TreeNodeConfig.DEFAULT_CONFIG.setIdKey("value").setNameKey("label"), (treeNode, tree) ->
                tree.setId(treeNode.getMenuId())
                        .setParentId(treeNode.getParentId())
                        .setName(treeNode.getMenuName())
                        .setWeight(treeNode.getOrderNum())
                        .putExtra("icon", treeNode.getIcon()));
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

    @Override
    public boolean addMenusToRole(SysRoleDTO sysRoleDTO) {
        List<String> selectedMenuIds = sysRoleDTO.getSelectedMenuIds();

        List<RoleMenuLink> userGroupLinkList = ListUtil.newArrayList(selectedMenuIds, menuId ->
                        new RoleMenuLink().setMenuId(menuId)
                                .setRoleId(sysRoleDTO.getRoleId())
                                .setAppId(sysRoleDTO.getAppId())
                , RoleMenuLink.class);

        return Db.saveBatch(userGroupLinkList);
    }
}




