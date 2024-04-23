package cn.youngkbt.uac.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.youngkbt.core.constants.ColumnConstant;
import cn.youngkbt.core.exception.ServiceException;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.uac.sys.mapper.RoleMenuLinkMapper;
import cn.youngkbt.uac.sys.mapper.SysMenuMapper;
import cn.youngkbt.uac.sys.model.dto.SysMenuDTO;
import cn.youngkbt.uac.sys.model.po.RoleMenuLink;
import cn.youngkbt.uac.sys.model.po.SysMenu;
import cn.youngkbt.uac.sys.model.vo.SysMenuVO;
import cn.youngkbt.uac.sys.service.SysMenuService;
import cn.youngkbt.uac.sys.utils.TreeBuildUtil;
import cn.youngkbt.utils.MapstructUtil;
import cn.youngkbt.utils.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    public List<SysMenuVO> queryList(SysMenuDTO sysMenuDTO) {
        LambdaQueryWrapper<SysMenu> wrapper = buildQueryWrapper(sysMenuDTO);
        List<SysMenu> sysMenuList = baseMapper.selectList(wrapper);

        return MapstructUtil.convert(sysMenuList, SysMenuVO.class);
    }

    @Override
    public TablePage<SysMenuVO> listPage(SysMenuDTO sysMenuDTO, PageQuery pageQuery) {
        LambdaQueryWrapper<SysMenu> wrapper = buildQueryWrapper(sysMenuDTO);
        Page<SysMenu> sysMenuPage = baseMapper.selectPage(pageQuery.buildPage(), wrapper);

        return TablePage.build(sysMenuPage, SysMenuVO.class);
    }

    /**
     * 构建前端需要的菜单树形结构
     */
    @Override
    public List<Tree<String>> listMenuTreeSelect(SysMenuDTO sysMenuDTO) {
        // 查询正常状态的部门
        sysMenuDTO.setStatus(ColumnConstant.STATUS_NORMAL);
        LambdaQueryWrapper<SysMenu> wrapper = buildQueryWrapper(sysMenuDTO);
        List<SysMenu> sysMenuList = baseMapper.selectList(wrapper);
        return buildDeptTree(sysMenuList);
    }

    @Override
    public List<SysMenuVO> listMenuTreeTable(SysMenuDTO sysMenuDTO) {
        LambdaQueryWrapper<SysMenu> wrapper = buildQueryWrapper(sysMenuDTO);
        List<SysMenu> sysMenuList = baseMapper.selectList(wrapper);
        List<SysMenuVO> menuTreeList = MapstructUtil.convert(sysMenuList, SysMenuVO.class);

        return TreeBuildUtil.build(menuTreeList, SysMenuVO::getMenuId);
    }

    private LambdaQueryWrapper<SysMenu> buildQueryWrapper(SysMenuDTO sysMenuDTO) {
        return Wrappers.<SysMenu>lambdaQuery()
                .eq(StringUtil.hasText(sysMenuDTO.getMenuCode()), SysMenu::getMenuCode, sysMenuDTO.getMenuCode())
                .eq(StringUtil.hasText(sysMenuDTO.getMenuName()), SysMenu::getMenuName, sysMenuDTO.getMenuName())
                .eq(StringUtil.hasText(sysMenuDTO.getAppId()), SysMenu::getAppId, sysMenuDTO.getAppId())
                .eq(Objects.nonNull(sysMenuDTO.getStatus()), SysMenu::getStatus, sysMenuDTO.getStatus())
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

        return TreeBuildUtil.build(sysMenuList, ColumnConstant.PARENT_ID, TreeNodeConfig.DEFAULT_CONFIG.setIdKey("value").setNameKey("label"), (treeNode, tree) ->
                tree.setId(treeNode.getMenuId())
                        .setParentId(treeNode.getParentId())
                        .setName(treeNode.getMenuName())
                        .setWeight(treeNode.getOrderNum())
                        .putExtra("icon", treeNode.getIcon()));
    }

    @Override
    public List<SysMenuVO> listMenuListByUserId(String appId,  String userId) {
        return baseMapper.listMenuListByUserId(appId, userId);
    }

    @Override
    public boolean checkMenuCodeUnique(SysMenuDTO sysMenuDTO) {
        return baseMapper.exists(Wrappers.<SysMenu>lambdaQuery()
                .eq(SysMenu::getMenuCode, sysMenuDTO.getMenuCode())
                .eq(SysMenu::getParentId, sysMenuDTO.getParentId())
                .ne(Objects.nonNull(sysMenuDTO.getMenuId()), SysMenu::getMenuId, sysMenuDTO.getMenuId()));
    }
    
    @Override
    public boolean checkMenuNameUnique(SysMenuDTO sysMenuDTO) {
        return baseMapper.exists(Wrappers.<SysMenu>lambdaQuery()
                .eq(SysMenu::getMenuName, sysMenuDTO.getMenuName())
                .eq(SysMenu::getParentId, sysMenuDTO.getParentId())
                .ne(Objects.nonNull(sysMenuDTO.getMenuId()), SysMenu::getMenuId, sysMenuDTO.getMenuId()));
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
    public boolean insertOne(SysMenuDTO sysMenuDTO) {
        SysMenu sysMenu = MapstructUtil.convert(sysMenuDTO, SysMenu.class);

        if (StringUtil.hasText(sysMenu.getParentId())) {
            SysMenu menu = baseMapper.selectOne(Wrappers.<SysMenu>lambdaQuery()
                    .eq(SysMenu::getMenuId, sysMenu.getParentId()));

            // 如果父节点不为正常状态,则不允许新增子节点
            if (!ColumnConstant.STATUS_NORMAL.equals(menu.getStatus())) {
                throw new ServiceException("菜单停用，不允许新增");
            }

            return baseMapper.insert(sysMenu) > 0;
        }

        sysMenu.setParentId(ColumnConstant.PARENT_ID);
        return baseMapper.insert(sysMenu) > 0;
    }

    @Override
    public boolean updateOne(SysMenuDTO sysMenuDTO) {
        SysMenu sysMenu = MapstructUtil.convert(sysMenuDTO, SysMenu.class);
        return baseMapper.updateById(sysMenu) > 0;
    }

    @Override
    public boolean removeOne(Long id) {
        return baseMapper.deleteById(id) > 0;
    }

    @Override
    public boolean checkAppExitMenu(List<String> appIds) {
        return baseMapper.exists(Wrappers.<SysMenu>lambdaQuery()
                .in(SysMenu::getAppId, appIds));
    }
}




