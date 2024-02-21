package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.RoleGroupMenuLinkDto;
import cn.youngkbt.uac.sys.model.po.RoleGroupMenuLink;
import cn.youngkbt.uac.sys.model.vo.RoleGroupMenuLinkVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_role_group_menu_link(角色组关联菜单表)】的数据库操作Service
 */
public interface RoleGroupMenuLinkService extends IService<RoleGroupMenuLink> {
    List<RoleGroupMenuLinkVo> queryLinkByAppId(RoleGroupMenuLinkDto roleGroupMenuLinkDto, PageQuery pageQuery);

    boolean checkRoleGroupExistMenu(String roleGroupId);

    boolean checkMenuExistRoleGroup(String menuId);

    boolean addOneLink(RoleGroupMenuLinkDto roleGroupMenuLinkDto);

    boolean updateOneLink(RoleGroupMenuLinkDto roleGroupMenuLinkDto);

    boolean removeOneLink(Long id);
}
