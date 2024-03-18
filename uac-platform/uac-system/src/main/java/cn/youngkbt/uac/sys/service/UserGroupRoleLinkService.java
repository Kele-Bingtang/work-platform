package cn.youngkbt.uac.sys.service;

import cn.youngkbt.uac.sys.model.dto.link.RoleLinkUserGroupDTO;
import cn.youngkbt.uac.sys.model.dto.link.UserGroupLinkRoleDTO;
import cn.youngkbt.uac.sys.model.po.UserGroupRoleLink;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_user_group_role_link(用户组关联角色表)】的数据库操作Service
 */
public interface UserGroupRoleLinkService extends IService<UserGroupRoleLink> {
    /**
     * 检查角色是否已存在用户组
     *
     * @param roleIds     角色 id 集合
     * @param userGroupId 用户组 id
     * @return 是否存在
     */
    boolean checkRolesExistUserGroup(List<String> roleIds, String userGroupId);


    /**
     * 检查用户组是否已存在角色
     *
     * @param roleId       角色 id
     * @param userGroupIds 用户组 id 集合
     * @return 是否存在
     */
    boolean checkRoleExistUserGroups(String roleId, List<String> userGroupIds);

    /**
     * 添加角色到用户组（多个角色）
     *
     * @param userGroupLinkRoleDTO 用户组角色关联实体
     * @return 是否成功
     */
    boolean addUserGroupToRoles(UserGroupLinkRoleDTO userGroupLinkRoleDTO);

    /**
     * 添加用户到用户组（多个用户组）
     *
     * @param roleLinkUserGroupDTO 角色用户组关联实体
     * @return 是否成功
     */
    boolean addUserGroupsToRole(RoleLinkUserGroupDTO roleLinkUserGroupDTO);
}
