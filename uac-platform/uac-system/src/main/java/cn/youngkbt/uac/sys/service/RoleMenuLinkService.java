package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.RoleMenuLinkDTO;
import cn.youngkbt.uac.sys.model.po.RoleMenuLink;
import cn.youngkbt.uac.sys.model.vo.RoleMenuLinkVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_role_menu_link(角色关联菜单表)】的数据库操作Service
 */
public interface RoleMenuLinkService extends IService<RoleMenuLink> {

    List<RoleMenuLinkVO> queryLinkByAppId(RoleMenuLinkDTO roleMenuLinkDTO, PageQuery pageQuery);

    boolean checkMenuExistRole(String menuId);

    boolean checkRoleExistMenu(String roleId);
    
    boolean addOneLink(RoleMenuLinkDTO roleMenuLinkDTO);
    
    boolean updateOneLink(RoleMenuLinkDTO roleMenuLinkDTO);
    
    boolean removeOneLink(Long id);
    
}
