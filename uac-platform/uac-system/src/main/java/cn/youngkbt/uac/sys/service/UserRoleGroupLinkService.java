package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.UserRoleGroupLinkDto;
import cn.youngkbt.uac.sys.model.po.UserRoleGroupLink;
import cn.youngkbt.uac.sys.model.vo.UserRoleGroupLinkVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_user_role_group_link(用户关联角色组表)】的数据库操作Service
 */
public interface UserRoleGroupLinkService extends IService<UserRoleGroupLink> {
    List<UserRoleGroupLinkVo> queryLinkByAppId(UserRoleGroupLinkDto userRoleGroupLinkDto, PageQuery pageQuery);

    boolean checkUserExistRoleGroup(String userId);

    boolean checkRoleGroupExistUser(String roleGroupId);

    boolean addOneLink(UserRoleGroupLinkDto userRoleGroupLinkDto);

    boolean updateOneLink(UserRoleGroupLinkDto userRoleGroupLinkDto);

    boolean removeOneLink(Long id);
}
