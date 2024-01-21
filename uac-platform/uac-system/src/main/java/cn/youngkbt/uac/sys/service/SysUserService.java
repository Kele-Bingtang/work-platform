package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.mp.base.SysUserBO;
import cn.youngkbt.uac.sys.model.dto.SysUserDto;
import cn.youngkbt.uac.sys.model.po.SysUser;
import cn.youngkbt.uac.sys.model.vo.SysUserVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_user(用户信息表)】的数据库操作Service
 */
public interface SysUserService extends IService<SysUser> {

    SysUserBO selectTenantUserByUsername(String tenantId, String username);

    SysUserBO selectUserByUsername(String username);

    SysUserVo queryById(Long id);

    List<SysUserVo> queryListWithPage(SysUserDto sysUserDto, PageQuery pageQuery);

    Boolean insertOne(SysUserDto sysUserDto);

    Boolean updateOne(SysUserDto sysUserDto);

    Boolean updateOneByUserId(SysUserDto sysUserDto);

    Boolean removeOne(List<Long> ids);
}
