package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.UserGroupRoleLinkDTO;
import cn.youngkbt.uac.sys.model.po.UserGroupRoleLink;
import cn.youngkbt.uac.sys.model.vo.UserGroupRoleLinkVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_user_group_role_link(用户组关联角色表)】的数据库操作Service
 */
public interface UserGroupRoleLinkService extends IService<UserGroupRoleLink> {
    List<UserGroupRoleLinkVO> queryLinkByAppId(UserGroupRoleLinkDTO userGroupRoleLinkDto, PageQuery pageQuery);

    boolean checkUserGroupExistRole(String userGroupId);

    boolean checkRoleExistUserGroup(String roleId);

    boolean addOneLink(UserGroupRoleLinkDTO userGroupRoleLinkDto);

    boolean updateOneLink(UserGroupRoleLinkDTO userGroupRoleLinkDto);

    boolean removeOneLink(Long id);
}
