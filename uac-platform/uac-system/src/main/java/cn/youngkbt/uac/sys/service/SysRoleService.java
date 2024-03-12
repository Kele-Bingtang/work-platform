package cn.youngkbt.uac.sys.service;

import cn.youngkbt.mp.base.PageQuery;
import cn.youngkbt.uac.sys.model.dto.SysRoleDTO;
import cn.youngkbt.uac.sys.model.po.SysRole;
import cn.youngkbt.uac.sys.model.vo.SysRoleVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_sys_role(应用角色信息表)】的数据库操作Service
 */
public interface SysRoleService extends IService<SysRole> {

    SysRoleVO listById(Long id);

    List<SysRoleVO> queryListWithPage(SysRoleDTO sysRoleDto, PageQuery pageQuery);

    boolean checkRoleCodeUnique(SysRoleDTO sysRoleDto);
    
    boolean checkRoleNameUnique(SysRoleDTO sysRoleDto);

    boolean insertOne(SysRoleDTO sysRoleDto);

    boolean updateOne(SysRoleDTO sysRoleDto);

    boolean removeBatch(List<Long> ids);
}
