package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysUserGroupDTO;
import cn.youngkbt.uac.sys.model.po.SysUserGroup;
import cn.youngkbt.uac.sys.model.vo.SysUserGroupVO;
import cn.youngkbt.uac.sys.model.vo.link.UserGroupBindSelectVO;
import cn.youngkbt.uac.sys.model.vo.link.UserGroupLinkVO;
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
    List<UserGroupLinkVO> listUserGroupByUserId(String appId, String userId);

    /**
     * 通过用户 ID 查询用户组列表（包含禁用）
     *
     * @param appId  应用 ID
     * @param userId 用户 ID
     * @return 用户组列表
     */
    List<UserGroupBindSelectVO> listUserGroupWithDisabledByUserId(String appId, String userId);

    /**
     * 检查用户组名是否唯一
     *
     * @param sysUserGroupDto 用户组信息
     * @return 是否唯一
     */
    boolean checkUserGroupNameUnique(SysUserGroupDTO sysUserGroupDto);

    /**
     * 新增用户组
     *
     * @param sysUserGroupDto 用户组信息
     * @return 是否新增成功
     */
    boolean insertOne(SysUserGroupDTO sysUserGroupDto);

    /**
     * 修改用户组
     *
     * @param sysUserGroupDto 用户组信息
     * @return 是否修改成功
     */
    boolean updateOne(SysUserGroupDTO sysUserGroupDto);

    /**
     * 批量删除用户组
     *
     * @param ids 主键列表
     * @return 是否删除成功
     */
    boolean removeBatch(List<Long> ids);
}
