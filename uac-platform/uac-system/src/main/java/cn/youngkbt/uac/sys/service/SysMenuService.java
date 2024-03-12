package cn.youngkbt.uac.sys.service;

import cn.hutool.core.lang.tree.Tree;
import cn.youngkbt.mp.base.PageQuery;
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

    SysMenuVO listById(Long id);

    List<SysMenuVO> queryListWithPage(SysMenuDTO sysMenuDto, PageQuery pageQuery);
    
    List<Tree<String>> listMenuTreeSelect(SysMenuDTO sysMenuDto);

    List<MenuTree> listMenuTreeTable(SysMenuDTO sysMenuDto);

    boolean checkMenuNameUnique(SysMenuDTO sysMenuDto);

    boolean hasChild(String menuId);

    boolean checkMenuExistRole(String menuId);

    boolean insertOne(SysMenuDTO sysMenuDto);

    boolean updateOne(SysMenuDTO sysMenuDto);

    boolean removeOne(Long id);

}
