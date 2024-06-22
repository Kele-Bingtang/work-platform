package cn.youngkbt.uac.system.service;

import cn.youngkbt.uac.system.model.dto.SysRoleDTO;
import cn.youngkbt.uac.system.model.po.RoleMenuLink;
import cn.youngkbt.uac.system.model.po.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_role_menu_link(角色关联菜单表)】的数据库操作Service
 */
public interface RoleMenuLinkService extends IService<RoleMenuLink> {

    List<String>  listMenuIdsByRoleId(String roleId, String appId, String tenantId);

    List<SysMenu> listMenuListByRoleId(String roleId, String appId);

    boolean addMenusToRole(SysRoleDTO sysRoleDTO, boolean removeLink);

}
