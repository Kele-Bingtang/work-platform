package cn.youngkbt.uac.sys.service.impl;

import cn.youngkbt.uac.core.constant.TenantConstant;
import cn.youngkbt.uac.core.helper.UacHelper;
import cn.youngkbt.uac.sys.service.PermissionService;
import cn.youngkbt.uac.sys.service.SysMenuService;
import cn.youngkbt.uac.sys.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Kele-Bingtang
 * @date 2024/6/8 15:03:25
 * @note
 */
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    
    private final SysRoleService sysRoleService;
    private final SysMenuService sysMenuService;
    
    @Override
    public Set<String> getRoleCodes(String userId) {
        Set<String> roleCode = new HashSet<>();
        if(UacHelper.isAdmin(userId)) {
            roleCode.add(TenantConstant.TENANT_ADMIN_ROLE_KEY);
        } else {
            roleCode.addAll(sysRoleService.listRoleCodesByUserId(userId));
        }
        return roleCode;
    }

    @Override
    public Set<String> getMenuPermission(String userId) {
        Set<String> perms = new HashSet<>();
        // 管理员拥有所有权限
        if (UacHelper.isAdmin(userId)) {
            perms.add("*:*:*");
        } else {
            perms.addAll(sysMenuService.listMenuPermissionByUserId(userId));
        }
        return perms;
    }
}
