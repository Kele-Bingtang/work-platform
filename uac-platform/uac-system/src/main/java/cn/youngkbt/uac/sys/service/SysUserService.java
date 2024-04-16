package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.TablePage;
import cn.youngkbt.security.domain.SecurityUser;
import cn.youngkbt.uac.sys.model.dto.SysUserDTO;
import cn.youngkbt.uac.sys.model.po.SysUser;
import cn.youngkbt.uac.sys.model.vo.SysUserVO;
import cn.youngkbt.uac.sys.model.vo.extra.RolePostVo;
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
     * @param sysUserDTO 用户信息
     * @return 用户列表
     */
    List<SysUserVO> queryList(SysUserDTO sysUserDTO);

    /**
     * 通过条件查询用户列表
     *
     * @param sysUserDTO 用户信息
     * @param pageQuery  分页参数
     * @return 用户列表
     */
    TablePage<SysUserVO> listPage(SysUserDTO sysUserDTO, PageQuery pageQuery);

    /**
     * 检查用户名是否唯一通过条件查询用户列表
     *
     * @param sysUserDTO 用户信息
     * @return 是否唯一
     */
    boolean checkUserNameUnique(SysUserDTO sysUserDTO);

    /**
     * 检查手机号是否唯一
     *
     * @param sysUserDTO 用户信息
     * @return 是否唯一
     */
    boolean checkPhoneUnique(SysUserDTO sysUserDTO);

    /**
     * 检查邮箱是否唯一
     *
     * @param sysUserDTO 用户信息
     * @return 是否唯一
     */
    boolean checkEmailUnique(SysUserDTO sysUserDTO);

    /**
     * 新增用户
     *
     * @param sysUserDTO 用户信息
     * @return 是否成功
     */
    boolean insertOne(SysUserDTO sysUserDTO);

    /**
     * 更新用户
     *
     * @param sysUserDTO 用户信息
     * @return 是否成功
     */
    boolean updateOne(SysUserDTO sysUserDTO);

    /**
     * 通过用户 ID 更新用户
     *
     * @param sysUserDTO 用户信息
     * @return 是否成功
     */
    boolean updateOneByUserId(SysUserDTO sysUserDTO);

    /**
     * 批量删除用户
     *
     * @param ids 主键列表
     * @return 是否成功
     */
    boolean removeBatch(List<Long> ids, List<String> userIds);

    /**
     * 获取角色列表和岗位列表
     *
     * @return 角色列表和岗位列表
     */
    RolePostVo rolePostList();

}
