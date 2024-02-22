package cn.youngkbt.uac.sys.service;

import cn.hutool.core.lang.tree.Tree;
import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysMenuDto;
import cn.youngkbt.uac.sys.model.po.SysMenu;
import cn.youngkbt.uac.sys.model.vo.SysMenuVo;
import cn.youngkbt.uac.sys.model.vo.extra.MenuTree;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_menu(菜单表)】的数据库操作Service
 */
public interface SysMenuService extends IService<SysMenu> {

    SysMenuVo queryById(Long id);

    List<SysMenuVo> queryListWithPage(SysMenuDto sysMenuDto, PageQuery pageQuery);
    
    List<Tree<String>> selectMenuTreeList(SysMenuDto sysMenuDto);

    List<MenuTree> buildDeptTreeTable(SysMenuDto sysMenuDto);

    boolean checkMenuNameUnique(SysMenuDto sysMenuDto);

    boolean hasChild(Long menuId);

    boolean checkMenuExistRole(Long menuId);

    boolean insertOne(SysMenuDto sysMenuDto);

    boolean updateOne(SysMenuDto sysMenuDto);

    boolean removeOne(Long menuId);
    
    boolean removeBatch(List<Long> ids);

}
