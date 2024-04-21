package cn.youngkbt.uac.sys.service;

import cn.youngkbt.uac.sys.model.po.RoleDeptLink;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_role_dept_link(角色关联部门表)】的数据库操作Service
 */
public interface RoleDeptLinkService extends IService<RoleDeptLink> {

    boolean checkDeptExistRole(String deptId);

    boolean checkRoleExistDept(String roleId);
}
