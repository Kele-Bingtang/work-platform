package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.uac.sys.mapper.UserGroupRoleLinkMapper;
import cn.youngkbt.uac.sys.model.dto.link.RoleLinkUserGroupDTO;
import cn.youngkbt.uac.sys.model.dto.link.UserGroupLinkRoleDTO;
import cn.youngkbt.uac.sys.model.po.UserGroupRoleLink;
import cn.youngkbt.uac.sys.service.UserGroupRoleLinkService;
import cn.youngkbt.utils.ListUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_user_group_role_link(用户组关联角色表)】的数据库操作Service实现
 */
@Service
public class UserGroupRoleLinkServiceImpl extends ServiceImpl<UserGroupRoleLinkMapper, UserGroupRoleLink> implements UserGroupRoleLinkService {

    @Override
    public boolean checkRolesExistUserGroup(List<String> roleIds, String userGroupId) {
        return baseMapper.exists(Wrappers.<UserGroupRoleLink>lambdaQuery()
                .in(UserGroupRoleLink::getRoleId, roleIds)
                .eq(UserGroupRoleLink::getUserGroupId, userGroupId));
    }

    @Override
    public boolean checkRoleExistUserGroups(String roleId, List<String> userGroupIds) {
        return baseMapper.exists(Wrappers.<UserGroupRoleLink>lambdaQuery()
                .eq(UserGroupRoleLink::getRoleId, roleId)
                .in(UserGroupRoleLink::getUserGroupId, userGroupIds));
    }

    @Override
    public boolean addUserGroupToRoles(UserGroupLinkRoleDTO userGroupLinkRoleDTO) {
        List<String> roleIds = userGroupLinkRoleDTO.getRoleIds();

        List<UserGroupRoleLink> userGroupLinkList = ListUtil.newArrayList(roleIds, roleId ->
                        new UserGroupRoleLink().setRoleId(roleId)
                                .setUserGroupId(userGroupLinkRoleDTO.getUserGroupId())
                                .setValidFrom(userGroupLinkRoleDTO.getValidFrom())
                                .setExpireOn(userGroupLinkRoleDTO.getExpireOn())
                                .setAppId(userGroupLinkRoleDTO.getAppId())
                , UserGroupRoleLink.class);

        return Db.saveBatch(userGroupLinkList);
    }

    @Override
    public boolean addUserGroupsToRole(RoleLinkUserGroupDTO roleLinkUserGroupDTO) {
        List<String> userGroupIds = roleLinkUserGroupDTO.getUserGroupIds();

        List<UserGroupRoleLink> userGroupLinkList = ListUtil.newArrayList(userGroupIds, userGroupId ->
                        new UserGroupRoleLink().setUserGroupId(userGroupId)
                                .setRoleId(roleLinkUserGroupDTO.getRoleId())
                                .setValidFrom(roleLinkUserGroupDTO.getValidFrom())
                                .setExpireOn(roleLinkUserGroupDTO.getExpireOn())
                                .setAppId(roleLinkUserGroupDTO.getAppId())
                , UserGroupRoleLink.class);

        return Db.saveBatch(userGroupLinkList);
    }
}




