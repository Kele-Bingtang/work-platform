package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.UserGroupRoleLinkDto;
import cn.youngkbt.uac.sys.model.po.UserGroupRoleLink;
import cn.youngkbt.uac.sys.model.vo.UserGroupRoleLinkVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_user_group_role_link(用户组关联角色表)】的数据库操作Service
 */
public interface UserGroupRoleLinkService extends IService<UserGroupRoleLink> {
    List<UserGroupRoleLinkVo> queryLinkByAppId(UserGroupRoleLinkDto userGroupRoleLinkDto, PageQuery pageQuery);

    Boolean checkUserGroupExistRole(String userGroupId);

    Boolean checkRoleExistUserGroup(String roleId);

    Boolean addOneLink(UserGroupRoleLinkDto userGroupRoleLinkDto);

    Boolean updateOneLink(UserGroupRoleLinkDto userGroupRoleLinkDto);

    Boolean removeOneLink(Long id);
}
