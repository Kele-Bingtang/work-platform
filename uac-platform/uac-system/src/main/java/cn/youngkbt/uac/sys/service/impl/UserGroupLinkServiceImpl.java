package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.uac.sys.mapper.UserGroupLinkMapper;
import cn.youngkbt.uac.sys.model.dto.link.UserGroupLinkUserDTO;
import cn.youngkbt.uac.sys.model.dto.link.UserLinkUserGroupDTO;
import cn.youngkbt.uac.sys.model.po.UserGroupLink;
import cn.youngkbt.uac.sys.service.UserGroupLinkService;
import cn.youngkbt.utils.ListUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_user_group_link(用户关联用户组表)】的数据库操作Service实现
 */
@Service
public class UserGroupLinkServiceImpl extends ServiceImpl<UserGroupLinkMapper, UserGroupLink> implements UserGroupLinkService {

    @Override
    public boolean checkUserExistUserGroups(String userId, List<String> userGroupIds) {
        return baseMapper.exists(Wrappers.<UserGroupLink>lambdaQuery()
                .eq(UserGroupLink::getUserId, userId)
                .in(UserGroupLink::getUserGroupId, userGroupIds));
    }

    @Override
    public boolean checkUsersExistUserGroup(List<String> userIds, String userGroupId) {
        return baseMapper.exists(Wrappers.<UserGroupLink>lambdaQuery()
                .in(UserGroupLink::getUserId, userIds)
                .eq(UserGroupLink::getUserGroupId, userGroupId)
        );
    }

    @Override
    public boolean addUserToUserGroups(UserLinkUserGroupDTO userLinkUserGroupDTO) {
        List<String> userGroupIds = userLinkUserGroupDTO.getUserGroupIds();

        List<UserGroupLink> userGroupLinkList = ListUtil.newArrayList(userGroupIds, userGroupId ->
                        new UserGroupLink().setUserGroupId(userGroupId)
                                .setUserId(userLinkUserGroupDTO.getUserId())
                                .setValidFrom(userLinkUserGroupDTO.getValidFrom())
                                .setExpireOn(userLinkUserGroupDTO.getExpireOn())
                                .setAppId(userLinkUserGroupDTO.getAppId())
                , UserGroupLink.class);

        return Db.saveBatch(userGroupLinkList);
    }

    @Override
    public boolean addUsersToUserGroup(UserGroupLinkUserDTO userGroupLinkUserDTO) {
        List<String> userIds = userGroupLinkUserDTO.getUserIds();

        List<UserGroupLink> userGroupLinkList = ListUtil.newArrayList(userIds, userId ->
                        new UserGroupLink().setUserId(userId)
                                .setUserGroupId(userGroupLinkUserDTO.getUserGroupId())
                                .setValidFrom(userGroupLinkUserDTO.getValidFrom())
                                .setExpireOn(userGroupLinkUserDTO.getExpireOn())
                                .setAppId(userGroupLinkUserDTO.getAppId())
                , UserGroupLink.class);

        return Db.saveBatch(userGroupLinkList);
    }

    @Override
    public boolean removeUserFromUserGroup(String userId, String userGroupId) {
        return baseMapper.delete(Wrappers.<UserGroupLink>lambdaQuery()
                .eq(UserGroupLink::getUserId, userId)
                .eq(UserGroupLink::getUserGroupId, userGroupId)) > 0;
    }
}




