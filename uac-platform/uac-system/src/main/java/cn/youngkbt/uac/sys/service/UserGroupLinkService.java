package cn.youngkbt.uac.sys.service;

import cn.youngkbt.uac.sys.model.dto.UserGroupLinkDTO;
import cn.youngkbt.uac.sys.model.dto.link.UserGroupLinkUserDTO;
import cn.youngkbt.uac.sys.model.dto.link.UserLinkUserGroupDTO;
import cn.youngkbt.uac.sys.model.po.UserGroupLink;
import cn.youngkbt.uac.sys.model.vo.link.UserInfoByGroupVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_user_group_link(用户关联用户组表)】的数据库操作Service
 */
public interface UserGroupLinkService extends IService<UserGroupLink> {

    /**
     * 检查用户是否在某些用户组中（多个用户组）
     *
     * @param userId       用户用户 ID
     * @param userGroupIds 用户组 ID 列表
     * @return 是否在用户组中
     */
    boolean checkUserExistUserGroups(String userId, List<String> userGroupIds);

    /**
     * 检查用户是否在某些用户组中（多个用户）
     *
     * @param userIds     用户用户 ID 列表
     * @param userGroupId 用户组 ID
     * @return 是否在用户组中
     */
    boolean checkUsersExistUserGroup(List<String> userIds, String userGroupId);

    /**
     * 添加用户到用户组（多个用户组）
     *
     * @param userLinkUserGroupDTO 用户和用户组数据
     * @return 是否添加成功
     */
    boolean addUserToUserGroups(UserLinkUserGroupDTO userLinkUserGroupDTO);

    /**
     * 添加用户到用户组（多个用户）
     *
     * @param userGroupLinkUserDTO 用户和用户组数据
     * @return 是否添加成功
     */
    boolean addUsersToUserGroup(UserGroupLinkUserDTO userGroupLinkUserDTO);

    /**
     * 将用户移出项目组
     *
     * @param ids 关联 ID
     * @return 是否移出成功
     */
    boolean removeUserFromUserGroup(List<Long> ids);

    /**
     * 查询用户组下的用户列表
     *
     * @param appId       应用 ID
     * @param userGroupId 用户组 ID
     * @return 用户列表
     */
    List<UserInfoByGroupVO> listUserLinkByGroupId(String appId, String userGroupId);

    /**
     * 修改用户组和用户的关联信息
     *
     * @param userGroupLinkDTO 用户组信息
     * @return 是否修改成功
     */
    boolean updateOne(UserGroupLinkDTO userGroupLinkDTO);
}
