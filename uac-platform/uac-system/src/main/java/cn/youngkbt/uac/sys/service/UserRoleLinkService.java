package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.UserRoleLinkDTO;
import cn.youngkbt.uac.sys.model.dto.link.UserLinkRoleDTO;
import cn.youngkbt.uac.sys.model.po.UserRoleLink;
import cn.youngkbt.uac.sys.model.vo.UserRoleLinkVO;
import cn.youngkbt.uac.sys.model.vo.link.RoleBindSelectVO;
import cn.youngkbt.uac.sys.model.vo.link.RoleLinkVO;
import cn.youngkbt.uac.sys.model.vo.link.UserBindSelectVO;
import cn.youngkbt.uac.sys.model.vo.link.UserLinkVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_user_role_link(用户关联角色表)】的数据库操作Service
 */
public interface UserRoleLinkService extends IService<UserRoleLink> {
    List<UserRoleLinkVO> queryLinkByAppId(UserRoleLinkDTO userRoleLinkDTO, PageQuery pageQuery);

    boolean checkUserExistRole(String userId);

    boolean checkRoleExistUser(String roleId);

    /**
     * 检查用户是否已存在某些角色中
     *
     * @param userId  用户 ID
     * @param roleIds 角色 ID 列表
     * @return 是否存在
     */
    boolean checkUserExistRoles(String userId, List<String> roleIds);

    /**
     * 添加用户到角色
     *
     * @param userLinkRoleDTO 用户绑定角色信息
     * @return 是否成功
     */
    boolean addUserToRoles(UserLinkRoleDTO userLinkRoleDTO);

    boolean updateOne(UserRoleLinkDTO userRoleLinkDTO);

    /**
     * 将用户移出角色
     *
     * @param ids 关联 ID
     * @return 是否成功
     */
    boolean removeUserFromRole(List<Long> ids);

    /**
     * 通过角色 ID 查询用户列表
     *
     * @param roleId 角色 ID
     * @return 用户列表
     */
    List<UserLinkVO> listUserLinkByRoleId(String roleId);

    /**
     * 根据应用 ID、用户 ID 查询角色列表
     *
     * @param appId  应用ID
     * @param userId 用户ID
     * @return 角色列表
     */
    List<RoleLinkVO> listRoleLinkByUserId(String appId, String userId);

    /**
     * 根据应用 ID、用户 ID 查询角色列表，如果角色绑定了用户，则 disabled 属性为 false
     *
     * @param appId  应用ID
     * @param userId 用户ID
     * @return 角色列表
     */
    List<RoleBindSelectVO> listWithDisabledByUserId(String appId, String userId);

    /**
     * 下拉查询用户列表，如果用户绑定了角色，则 disabled 属性为 true
     *
     * @param roleId 角色 ID
     * @return 用户列表
     */
    List<UserBindSelectVO> listWithDisabledByRoleId(String roleId);

}
