package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysUserGroupDTO;
import cn.youngkbt.uac.sys.model.dto.link.UserLinkUserGroupDTO;
import cn.youngkbt.uac.sys.model.po.SysUserGroup;
import cn.youngkbt.uac.sys.model.vo.SysUserGroupVO;
import cn.youngkbt.uac.sys.model.vo.extra.UserGroupBindUserVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_user_group(用户组表)】的数据库操作Service
 */
public interface SysUserGroupService extends IService<SysUserGroup> {

    /**
     * 通过条件查询用户组信息
     *
     * @param sysUserGroupDTO 查询条件
     * @param pageQuery       分页条件
     * @return 用户组信息
     */
    List<SysUserGroupVO> list(SysUserGroupDTO sysUserGroupDTO, PageQuery pageQuery);

    /**
     * 通过用户 ID 查询用户组列表
     *
     * @param appId  应用 ID
     * @param userId 用户 ID
     * @return 用户组列表
     */
    List<SysUserGroupVO> listUserGroupByUserId(String appId, String userId);

    /**
     * 通过用户 ID 查询用户组列表（包含禁用）
     *
     * @param appId  应用 ID
     * @param userId 用户 ID
     * @return 用户组列表
     */
    List<UserGroupBindUserVO> listUserGroupWithDisabledByUserId(String appId, String userId);

    /**
     * 添加用户到用户组（支持多个用户组）
     *
     * @param userLinkUserGroupDTO 用户和用户组数据
     * @return 是否添加成功
     */
    boolean addUserToGroups(UserLinkUserGroupDTO userLinkUserGroupDTO);

}
