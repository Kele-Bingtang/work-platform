package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
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

    SecurityUser selectTenantUserByUsername(String tenantId, String username);

    SecurityUser selectUserByUsername(String username);

    SysUserVO listById(Long id);

    List<SysUserVO> queryListWithPage(SysUserDTO sysUserDto, PageQuery pageQuery);

    boolean checkUserNameUnique(SysUserDTO sysUserDto);

    boolean checkPhoneUnique(SysUserDTO sysUserDto);

    boolean checkEmailUnique(SysUserDTO sysUserDto);

    boolean insertOne(SysUserDTO sysUserDto);

    boolean updateOne(SysUserDTO sysUserDto);

    boolean updateOneByUserId(SysUserDTO sysUserDto);

    boolean removeBatch(List<Long> ids);

    RolePostVo rolePostList();
}
