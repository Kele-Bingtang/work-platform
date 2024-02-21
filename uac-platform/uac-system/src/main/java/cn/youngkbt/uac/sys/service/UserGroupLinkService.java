package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.UserGroupLinkDto;
import cn.youngkbt.uac.sys.model.po.UserGroupLink;
import cn.youngkbt.uac.sys.model.vo.UserGroupLinkVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_user_group_link(用户关联用户组表)】的数据库操作Service
 */
public interface UserGroupLinkService extends IService<UserGroupLink> {
    List<UserGroupLinkVo> queryLinkByTenantId(UserGroupLinkDto userGroupLinkDto, PageQuery pageQuery);

    boolean checkUserExistUserGroup(String userId);

    boolean checkUserGroupExistUser(String userGroupId);

    boolean addOneLink(UserGroupLinkDto userGroupLinkDto);

    boolean updateOneLink(UserGroupLinkDto userGroupLinkDto);

    boolean removeOneLink(Long id);
}
