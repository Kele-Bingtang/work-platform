package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.UserRoleLinkDTO;
import cn.youngkbt.uac.sys.model.po.UserRoleLink;
import cn.youngkbt.uac.sys.model.vo.UserRoleLinkVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_user_role_link(用户关联角色表)】的数据库操作Service
 */
public interface UserRoleLinkService extends IService<UserRoleLink> {
    List<UserRoleLinkVO> queryLinkByAppId(UserRoleLinkDTO userRoleLinkDto, PageQuery pageQuery);

    boolean checkUserExistRole(String userId);

    boolean checkRoleExistUser(String roleId);

    boolean addOneLink(UserRoleLinkDTO userRoleLinkDto);

    boolean updateOneLink(UserRoleLinkDTO userRoleLinkDto);

    boolean removeOneLink(Long id);
}
