package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.core.constants.ColumnConstant;
import cn.youngkbt.uac.sys.mapper.UserGroupLinkMapper;
import cn.youngkbt.uac.sys.model.dto.UserGroupLinkDTO;
import cn.youngkbt.uac.sys.model.dto.link.UserGroupLinkUserDTO;
import cn.youngkbt.uac.sys.model.dto.link.UserLinkInfoDTO;
import cn.youngkbt.uac.sys.model.dto.link.UserLinkUserGroupDTO;
import cn.youngkbt.uac.sys.model.po.SysUserGroup;
import cn.youngkbt.uac.sys.model.po.UserGroupLink;
import cn.youngkbt.uac.sys.model.vo.link.UserBindSelectVO;
import cn.youngkbt.uac.sys.model.vo.link.UserGroupBindSelectVO;
import cn.youngkbt.uac.sys.model.vo.link.UserGroupLinkVO;
import cn.youngkbt.uac.sys.model.vo.link.UserLinkVO;
import cn.youngkbt.uac.sys.service.UserGroupLinkService;
import cn.youngkbt.utils.ListUtil;
import cn.youngkbt.utils.MapstructUtil;
import cn.youngkbt.utils.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_user_group_link(用户关联用户组表)】的数据库操作Service实现
 */
@Service
@RequiredArgsConstructor
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
    public boolean removeUserFromUserGroup(List<Long> ids) {
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public List<UserGroupLinkVO> listUserGroupByUserId(String appId, String userId) {
        QueryWrapper<SysUserGroup> wrapper = Wrappers.query();
        wrapper.eq("tugl.is_deleted", ColumnConstant.NON_DELETED)
                .eq("tsug.app_id", appId)
                .eq("tugl.user_id", userId);
        return baseMapper.listUserGroupByUserId(wrapper);
    }

    @Override
    public List<UserLinkVO> listUserLinkByGroupId(String userGroupId, UserLinkInfoDTO userLinkInfoDTO) {
        QueryWrapper<UserGroupLink> queryWrapper = Wrappers.query();
        queryWrapper.eq("tsu.is_deleted", 0)
                .eq("tugl.user_group_id", userGroupId)
                .like(StringUtil.hasText(userLinkInfoDTO.getUsername()), "tsu.username", userLinkInfoDTO.getUsername())
                .like(StringUtil.hasText(userLinkInfoDTO.getNickname()), "tsu.nickname", userLinkInfoDTO.getNickname());
        return baseMapper.listUserLinkByGroupId(queryWrapper);
    }

    @Override
    public List<UserGroupBindSelectVO> listWithDisabledByUserId(String appId, String userId) {
        return baseMapper.selectWithDisabledByUserId(appId, userId);
    }

    @Override
    public List<UserBindSelectVO> listWithDisabledByGroupId(String userGroupId) {
        return baseMapper.listWithDisabledByGroupId(userGroupId);
    }


    @Override
    public boolean updateOne(UserGroupLinkDTO userGroupLinkDTO) {
        UserGroupLink userGroupLink = MapstructUtil.convert(userGroupLinkDTO, UserGroupLink.class);
        return baseMapper.updateById(userGroupLink) > 0;
    }
}




