package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.uac.sys.mapper.UserGroupRoleLinkMapper;
import cn.youngkbt.uac.sys.model.dto.link.RoleLinkInfoDTO;
import cn.youngkbt.uac.sys.model.dto.link.RoleLinkUserGroupDTO;
import cn.youngkbt.uac.sys.model.dto.link.UserGroupLinkRoleDTO;
import cn.youngkbt.uac.sys.model.po.UserGroupLink;
import cn.youngkbt.uac.sys.model.po.UserGroupRoleLink;
import cn.youngkbt.uac.sys.model.vo.link.UserGroupLinkRoleVO;
import cn.youngkbt.uac.sys.service.UserGroupRoleLinkService;
import cn.youngkbt.utils.ListUtil;
import cn.youngkbt.utils.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
                                .setAppId(roleLinkUserGroupDTO.getAppId())
                , UserGroupRoleLink.class);

        return Db.saveBatch(userGroupLinkList);
    }

    @Override
    public List<UserGroupLinkRoleVO> listRoleLinkByGroupId(String userGroupId, RoleLinkInfoDTO roleLinkInfoDTO) {
        QueryWrapper<UserGroupLink> queryWrapper = Wrappers.query();
        queryWrapper.eq("tugrl.is_deleted", 0)
                .eq("tugrl.user_group_id", userGroupId)
                .like(StringUtil.hasText(roleLinkInfoDTO.getRoleCode()), "tsr.role_code", roleLinkInfoDTO.getRoleCode())
                .like(StringUtil.hasText(roleLinkInfoDTO.getRoleName()), "tsr.role_name", roleLinkInfoDTO.getRoleName());
        
        return baseMapper.listRoleLinkByGroupId(queryWrapper);
    }

    @Override
    public boolean removeUserGroupFromRole(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}




