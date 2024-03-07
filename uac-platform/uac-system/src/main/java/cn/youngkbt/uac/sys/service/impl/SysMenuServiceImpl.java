package cn.youngkbt.uac.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.youngkbt.core.constants.ColumnConstant;
import cn.youngkbt.core.error.Assert;
import cn.youngkbt.core.exception.ServiceException;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.mapper.RoleMenuLinkMapper;
import cn.youngkbt.uac.sys.mapper.SysMenuMapper;
import cn.youngkbt.uac.sys.model.dto.SysMenuDto;
import cn.youngkbt.uac.sys.model.po.RoleMenuLink;
import cn.youngkbt.uac.sys.model.po.SysMenu;
import cn.youngkbt.uac.sys.model.vo.SysMenuVo;
import cn.youngkbt.uac.sys.model.vo.extra.MenuTree;
import cn.youngkbt.uac.sys.service.SysMenuService;
import cn.youngkbt.uac.sys.utils.MenuTreeUtil;
import cn.youngkbt.utils.MapstructUtil;
import cn.youngkbt.utils.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_menu(菜单表)】的数据库操作Service实现
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final RoleMenuLinkMapper roleMenuLinkMapper;

    @Override
    public SysMenuVo listById(Long id) {
        SysMenu sysMenu = baseMapper.selectById(id);
        Assert.nonNull(sysMenu, "菜单不存在");
        return MapstructUtil.convert(sysMenu, SysMenuVo.class);
    }

    @Override
    public List<SysMenuVo> queryListWithPage(SysMenuDto sysMenuDto, PageQuery pageQuery) {
        LambdaQueryWrapper<SysMenu> wrapper = buildQueryWrapper(sysMenuDto);

        List<SysMenu> sysMenuList;
        if (Objects.isNull(pageQuery)) {
            sysMenuList = baseMapper.selectList(wrapper);
        } else {
            sysMenuList = baseMapper.selectPage(pageQuery.buildPage(), wrapper).getRecords();
        }
        return MapstructUtil.convert(sysMenuList, SysMenuVo.class);
    }

    /**
     * 构建前端需要的路由菜单
     */
    @Override
    public List<Tree<String>> listMenuTreeSelect(SysMenuDto sysMenuDto) {
        // 查询正常状态的部门
        sysMenuDto.setStatus(ColumnConstant.STATUS_NORMAL);
        LambdaQueryWrapper<SysMenu> wrapper = buildQueryWrapper(sysMenuDto);
        List<SysMenu> sysMenuList = baseMapper.selectList(wrapper);
        return buildDeptTree(sysMenuList);
    }

    @Override
    public List<MenuTree> listMenuTreeTable(SysMenuDto sysMenuDto) {
        LambdaQueryWrapper<SysMenu> wrapper = buildQueryWrapper(sysMenuDto);
        List<SysMenu> sysMenuList = baseMapper.selectList(wrapper);
        List<MenuTree> menuTreeList = MapstructUtil.convert(sysMenuList, MenuTree.class);
        return MenuTreeUtil.build(menuTreeList);
    }

    private LambdaQueryWrapper<SysMenu> buildQueryWrapper(SysMenuDto sysMenuDto) {
        return Wrappers.<SysMenu>lambdaQuery()
                .eq(StringUtil.hasText(sysMenuDto.getMenuCode()), SysMenu::getMenuCode, sysMenuDto.getMenuCode())
                .eq(StringUtil.hasText(sysMenuDto.getMenuName()), SysMenu::getMenuName, sysMenuDto.getMenuName())
                .eq(StringUtil.hasText(sysMenuDto.getAppId()), SysMenu::getAppId, sysMenuDto.getAppId())
                .eq(Objects.nonNull(sysMenuDto.getStatus()), SysMenu::getStatus, sysMenuDto.getStatus())
                .orderByAsc(SysMenu::getParentId)
                .orderByAsc(SysMenu::getOrderNum);
    }

    /**
     * 构建前端所需要下拉树结构
     */
    private List<Tree<String>> buildDeptTree(List<SysMenu> sysMenuList) {
        if (CollUtil.isEmpty(sysMenuList)) {
            return Collections.emptyList();
        }

        return TreeUtil.build(sysMenuList, "0", TreeNodeConfig.DEFAULT_CONFIG.setNameKey("label"), (treeNode, tree) ->
                tree.setId(treeNode.getMenuId())
                        .setParentId(treeNode.getParentId())
                        .setName(treeNode.getMenuName())
                        .setWeight(treeNode.getOrderNum())
                        .putExtra("icon", treeNode.getIcon()));
    }

    @Override
    public boolean checkMenuNameUnique(SysMenuDto sysMenuDto) {
        boolean exist = baseMapper.exists(Wrappers.<SysMenu>lambdaQuery()
                .eq(SysMenu::getMenuName, sysMenuDto.getMenuName())
                .eq(SysMenu::getParentId, sysMenuDto.getParentId())
                .ne(Objects.nonNull(sysMenuDto.getMenuId()), SysMenu::getMenuId, sysMenuDto.getMenuId()));

        return !exist;
    }

    @Override
    public boolean hasChild(String menuId) {
        return baseMapper.exists(Wrappers.<SysMenu>lambdaQuery()
                .eq(SysMenu::getParentId, menuId));
    }

    @Override
    public boolean checkMenuExistRole(String menuId) {
        return roleMenuLinkMapper.exists(Wrappers.<RoleMenuLink>lambdaQuery()
                .eq(RoleMenuLink::getMenuId, menuId));
    }

    @Override
    public boolean insertOne(SysMenuDto sysMenuDto) {
        SysMenu sysMenu = MapstructUtil.convert(sysMenuDto, SysMenu.class);

        if (StringUtil.hasText(sysMenu.getParentId())) {
            SysMenu menu = baseMapper.selectOne(Wrappers.<SysMenu>lambdaQuery()
                    .eq(SysMenu::getMenuId, sysMenu.getParentId()));

            // 如果父节点不为正常状态,则不允许新增子节点
            if (!ColumnConstant.STATUS_NORMAL.equals(menu.getStatus())) {
                throw new ServiceException("菜单停用，不允许新增");
            }

            return baseMapper.insert(sysMenu) > 0;
        }

        sysMenu.setParentId("0");
        return baseMapper.insert(sysMenu) > 0;
    }

    @Override
    public boolean updateOne(SysMenuDto sysMenuDto) {
        SysMenu sysMenu = MapstructUtil.convert(sysMenuDto, SysMenu.class);
        return baseMapper.updateById(sysMenu) > 0;
    }

    @Override
    public boolean removeOne(Long id) {
        return baseMapper.deleteById(id) > 0;
    }

}




