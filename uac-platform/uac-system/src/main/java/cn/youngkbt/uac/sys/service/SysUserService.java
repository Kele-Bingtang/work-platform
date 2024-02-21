package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.security.domain.SecurityUser;
import cn.youngkbt.uac.sys.model.dto.SysUserDto;
import cn.youngkbt.uac.sys.model.po.SysUser;
import cn.youngkbt.uac.sys.model.vo.SysUserVo;
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

    SysUserVo queryById(Long id);

    List<SysUserVo> queryListWithPage(SysUserDto sysUserDto, PageQuery pageQuery);

    boolean checkUserNameUnique(SysUserDto sysUserDto);

    boolean checkPhoneUnique(SysUserDto sysUserDto);

    boolean checkEmailUnique(SysUserDto sysUserDto);

    boolean insertOne(SysUserDto sysUserDto);

    boolean updateOne(SysUserDto sysUserDto);

    boolean updateOneByUserId(SysUserDto sysUserDto);

    boolean removeOne(List<Long> ids);

    RolePostVo rolePostList();
}
