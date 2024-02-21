package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.UserGroupRoleGroupLinkDto;
import cn.youngkbt.uac.sys.model.po.UserGroupRoleGroupLink;
import cn.youngkbt.uac.sys.model.vo.UserGroupRoleGroupLinkVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_user_group_role_group_link(用户组关联角色组表)】的数据库操作Service
 */
public interface UserGroupRoleGroupLinkService extends IService<UserGroupRoleGroupLink> {
    List<UserGroupRoleGroupLinkVo> queryLinkByAppId(UserGroupRoleGroupLinkDto userGroupRoleGroupLinkDto, PageQuery pageQuery);

    boolean checkUserGroupExistRoleGroup(String roleGroupId);

    boolean checkRoleGroupExistUserGroup(String userGroupId);

    boolean addOneLink(UserGroupRoleGroupLinkDto userGroupRoleGroupLinkDto);

    boolean updateOneLink(UserGroupRoleGroupLinkDto userGroupRoleGroupLinkDto);

    boolean removeOneLink(Long id);
}
