package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.security.domain.SecurityUser;
import cn.youngkbt.uac.sys.model.dto.SysUserDTO;
import cn.youngkbt.uac.sys.model.po.SysUser;
import cn.youngkbt.uac.sys.model.vo.SysUserVO;
import cn.youngkbt.uac.sys.model.vo.extra.RolePostVo;
import cn.youngkbt.uac.sys.model.vo.link.UserBindSelectVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_user(用户信息表)】的数据库操作Service
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据租户 ID 和用户名查询用户信息
     *
     * @param tenantId 租户 ID
     * @param username 用户名
     * @return
     */
    SecurityUser selectTenantUserByUsername(String tenantId, String username);

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    SecurityUser selectUserByUsername(String username);

    /**
     * 通过主键获取用户信息
     *
     * @param id 主键
     * @return 用户信息
     */
    SysUserVO listById(Long id);

    /**
     * 通过条件查询用户列表
     *
     * @param sysUserDto 用户信息
     * @param pageQuery  分页参数
     * @return 用户列表
     */
    List<SysUserVO> listWithPage(SysUserDTO sysUserDto, PageQuery pageQuery);

    /**
     * 检查用户名是否唯一通过条件查询用户列表
     *
     * @param sysUserDto 用户信息
     * @return 是否唯一
     */
    boolean checkUserNameUnique(SysUserDTO sysUserDto);

    /**
     * 检查手机号是否唯一
     *
     * @param sysUserDto 用户信息
     * @return 是否唯一
     */
    boolean checkPhoneUnique(SysUserDTO sysUserDto);

    /**
     * 检查邮箱是否唯一
     *
     * @param sysUserDto 用户信息
     * @return 是否唯一
     */
    boolean checkEmailUnique(SysUserDTO sysUserDto);

    /**
     * 下拉查询用户列表（已选的被禁用）
     * @param userGroupId 用户组 ID
     * @return 用户组下的用户列表
     */
    List<UserBindSelectVO> listWithDisabledByGroupId(String userGroupId);

    /**
     * 新增用户
     *
     * @param sysUserDto 用户信息
     * @return 是否成功
     */
    boolean insertOne(SysUserDTO sysUserDto);

    /**
     * 更新用户
     *
     * @param sysUserDto 用户信息
     * @return 是否成功
     */
    boolean updateOne(SysUserDTO sysUserDto);

    /**
     * 通过用户 ID 更新用户
     *
     * @param sysUserDto 用户信息
     * @return 是否成功
     */
    boolean updateOneByUserId(SysUserDTO sysUserDto);

    /**
     * 批量删除用户
     *
     * @param ids 主键列表
     * @return 是否成功
     */
    boolean removeBatch(List<Long> ids);

    /**
     * 获取角色列表和岗位列表
     *
     * @return 角色列表和岗位列表
     */
    RolePostVo rolePostList();

}
