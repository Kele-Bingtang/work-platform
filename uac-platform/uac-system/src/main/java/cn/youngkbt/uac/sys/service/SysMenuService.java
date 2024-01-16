package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysMenuDto;
import cn.youngkbt.uac.sys.model.po.SysMenu;
import cn.youngkbt.uac.sys.model.vo.SysMenuVo;
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
    
    List<SysMenuVo> buildMenuTree(List<SysMenu> sysMenuList);

    Boolean checkMenuNameUnique(SysMenuDto sysMenuDto);

    Boolean hasChild(Long menuId);

    Boolean checkMenuExistRole(Long menuId);

    Boolean insertOne(SysMenuDto sysMenuDto);

    Boolean updateOne(SysMenuDto sysMenuDto);

    Boolean removeOne(Long menuId);
    
    Boolean removeBatch(List<Long> ids);
    
}
