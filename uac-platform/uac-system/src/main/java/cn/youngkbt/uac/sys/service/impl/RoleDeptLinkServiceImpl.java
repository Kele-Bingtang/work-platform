package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.uac.sys.mapper.RoleDeptLinkMapper;
import cn.youngkbt.uac.sys.model.po.RoleDeptLink;
import cn.youngkbt.uac.sys.service.RoleDeptLinkService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Kele-Bingtang
 * @date 2023-11-11 23:40:21
 * @note 针对表【t_role_dept_link(角色关联部门表)】的数据库操作Service实现
 */
@Service
public class RoleDeptLinkServiceImpl extends ServiceImpl<RoleDeptLinkMapper, RoleDeptLink> implements RoleDeptLinkService {

    @Override
    public boolean checkDeptExistRole(String deptId) {
        return baseMapper.exists(Wrappers.<RoleDeptLink>lambdaQuery()
                .eq(RoleDeptLink::getDeptId, deptId));
    }

    @Override
    public boolean checkRoleExistDept(String roleId) {
        return baseMapper.exists(Wrappers.<RoleDeptLink>lambdaQuery()
                .eq(RoleDeptLink::getRoleId, roleId));
    }
}




