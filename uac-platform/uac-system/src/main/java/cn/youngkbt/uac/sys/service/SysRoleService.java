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
 * @note 针对表【t_sys_role(角色信息表)】的数据库操作Service
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 根据主键查询角色信息
     *
     * @param id 主键
     * @return 角色信息
     */
    SysRoleVO listById(Long id);

    /**
     * 通过条件查询角色列表
     *
     * @param sysRoleDto 查询条件
     * @param pageQuery  分页参数
     * @return 角色列表
     */
    List<SysRoleVO> listWithPage(SysRoleDTO sysRoleDto, PageQuery pageQuery);

    /**
     * 校验角色编码是否唯一
     *
     * @param sysRoleDto 角色信息
     * @return 是否唯一
     */
    boolean checkRoleCodeUnique(SysRoleDTO sysRoleDto);

    /**
     * 校验角色名称是否唯一
     *
     * @param sysRoleDto 角色信息
     * @return 是否唯一
     */
    boolean checkRoleNameUnique(SysRoleDTO sysRoleDto);

    /**
     * 新增角色
     *
     * @param sysRoleDto 角色信息
     * @return 是否成功
     */
    boolean insertOne(SysRoleDTO sysRoleDto);

    /**
     * 更新角色
     *
     * @param sysRoleDto 角色信息
     * @return 是否成功
     */
    boolean updateOne(SysRoleDTO sysRoleDto);

    /**
     * 批量删除角色
     *
     * @param ids 主键列表
     * @return 是否成功
     */
    boolean removeBatch(List<Long> ids);

}
