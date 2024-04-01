package cn.youngkbt.uac.sys.service;

import cn.hutool.core.lang.tree.Tree;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.uac.sys.model.dto.SysMenuDTO;
import cn.youngkbt.uac.sys.model.po.SysMenu;
import cn.youngkbt.uac.sys.model.vo.SysMenuVO;
import cn.youngkbt.uac.sys.model.vo.extra.MenuTree;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_menu(菜单表)】的数据库操作Service
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 根据主键查询菜单信息
     *
     * @param id 主键
     * @return 菜单信息
     */
    SysMenuVO listById(Long id);

    /**
     * 通过条件查询菜单列表
     *
     * @param sysMenuDTO 查询条件
     * @return 菜单列表
     */
    List<SysMenuVO> queryList(SysMenuDTO sysMenuDTO);

    /**
     * 通过条件查询菜单列表（支持分页）
     *
     * @param sysMenuDTO 查询条件
     * @param pageQuery  分页参数
     * @return 菜单列表
     */
    TablePage<SysMenuVO> listPage(SysMenuDTO sysMenuDTO, PageQuery pageQuery);

    /**
     * 查询菜单下拉框列表
     *
     * @param sysMenuDTO 查询条件
     * @return 菜单下拉框列表
     */
    List<Tree<String>> listMenuTreeSelect(SysMenuDTO sysMenuDTO);

    /**
     * 查询菜单树形表格列表
     *
     * @param sysMenuDTO 查询条件
     * @return 菜单树形表格列表
     */
    List<MenuTree> listMenuTreeTable(SysMenuDTO sysMenuDTO);

    /**
     * 检查菜单名称是否唯一
     *
     * @param sysMenuDTO 查询条件
     * @return 是否唯一
     */
    boolean checkMenuNameUnique(SysMenuDTO sysMenuDTO);

    /**
     * 检查菜单是否存在子菜单
     *
     * @param menuId 菜单id
     * @return 是否存在子菜单
     */
    boolean hasChild(String menuId);

    /**
     * 检查菜单是否存在角色关联
     *
     * @param menuId 菜单 ID
     * @return 是否存在角色关联
     */
    boolean checkMenuExistRole(String menuId);

    /**
     * 新增菜单
     *
     * @param sysMenuDTO 新增对象
     * @return 是否成功
     */
    boolean insertOne(SysMenuDTO sysMenuDTO);

    /**
     * 修改菜单
     *
     * @param sysMenuDTO 修改对象
     * @return 是否成功
     */
    boolean updateOne(SysMenuDTO sysMenuDTO);

    /**
     * 删除菜单
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean removeOne(Long id);

}
