package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.UserGroupLinkDTO;
import cn.youngkbt.uac.sys.model.po.UserGroupLink;
import cn.youngkbt.uac.sys.model.vo.UserGroupLinkVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_user_group_link(用户关联用户组表)】的数据库操作Service
 */
public interface UserGroupLinkService extends IService<UserGroupLink> {
    List<UserGroupLinkVO> queryLinkByTenantId(UserGroupLinkDTO userGroupLinkDto, PageQuery pageQuery);

    boolean checkUserExistUserGroup(String userId);

    boolean checkUserGroupExistUser(String userGroupId);

    /**
     * 检查用户是否在某些用户组中
     *
     * @param userId       用户用户 ID
     * @param userGroupIds 用户组 ID 列表
     * @return 是否在用户组中
     */
    boolean checkUserExistUserGroups(String userId, List<String> userGroupIds);

    boolean addOneLink(UserGroupLinkDTO userGroupLinkDto);

    boolean updateOneLink(UserGroupLinkDTO userGroupLinkDto);

    boolean removeOneLink(Long id);

}
