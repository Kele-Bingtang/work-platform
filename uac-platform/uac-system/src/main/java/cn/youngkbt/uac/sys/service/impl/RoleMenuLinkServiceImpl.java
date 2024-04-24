package cn.youngkbt.uac.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.youngkbt.core.constants.ColumnConstant;
import cn.youngkbt.uac.sys.mapper.RoleMenuLinkMapper;
import cn.youngkbt.uac.sys.model.dto.SysRoleDTO;
import cn.youngkbt.uac.sys.model.po.RoleMenuLink;
import cn.youngkbt.uac.sys.model.po.SysMenu;
import cn.youngkbt.uac.sys.service.RoleMenuLinkService;
import cn.youngkbt.uac.sys.utils.TreeBuildUtil;
import cn.youngkbt.utils.ListUtil;
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
        return buildMenuTree(sysMenu);
    }

    /**
     * 构建前端所需要下拉树结构
     */
    private List<Tree<String>> buildMenuTree(List<SysMenu> sysMenuList) {
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
    public boolean addMenusToRole(SysRoleDTO sysRoleDTO, boolean removeLink) {
        if (removeLink) {
            // 删除角色与菜单关联
            baseMapper.delete(Wrappers.<RoleMenuLink>lambdaQuery()
                    .eq(RoleMenuLink::getRoleId, sysRoleDTO.getRoleId()));
        }
        
        List<String> selectedMenuIds = sysRoleDTO.getSelectedMenuIds();

        List<RoleMenuLink> roleMenuLinkList = ListUtil.newArrayList(selectedMenuIds, menuId ->
                        new RoleMenuLink().setMenuId(menuId)
                                .setRoleId(sysRoleDTO.getRoleId())
                                .setAppId(sysRoleDTO.getAppId())
                , RoleMenuLink.class);

        return Db.saveBatch(roleMenuLinkList);
    }
}




