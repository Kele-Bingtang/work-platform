package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysRoleDto;
import cn.youngkbt.uac.sys.model.po.SysRole;
import cn.youngkbt.uac.sys.model.vo.SysRoleVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_role(应用角色信息表)】的数据库操作Service
 */
public interface SysRoleService extends IService<SysRole> {

    SysRoleVo listById(Long id);

    List<SysRoleVo> queryListWithPage(SysRoleDto sysRoleDto, PageQuery pageQuery);

    boolean checkRoleCodeUnique(SysRoleDto sysRoleDto);
    
    boolean checkRoleNameUnique(SysRoleDto sysRoleDto);

    boolean insertOne(SysRoleDto sysRoleDto);

    boolean updateOne(SysRoleDto sysRoleDto);

    boolean removeBatch(List<Long> ids);
}
