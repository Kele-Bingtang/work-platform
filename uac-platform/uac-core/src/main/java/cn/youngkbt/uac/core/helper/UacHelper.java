package cn.youngkbt.uac.core.helper;

import cn.youngkbt.security.utils.LoginHelper;
import cn.youngkbt.uac.core.constant.TenantConstant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

/**
 * @author Kele-Bingtang
 * @date 2024/6/8 15:06:51
 * @note
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UacHelper extends LoginHelper {

    /**
     * 是否为管理员
     *
     * @return boolean
     */
    public static boolean isAdmin() {
        return isAdmin(getUserId());
    }

    /**
     * 是否为管理员
     *
     * @param userId 用户 ID
     * @return boolean
     */
    public static boolean isAdmin(String userId) {
        return TenantConstant.DEFAULT_USER_ID.equals(userId);
    }

    /**
     * 是否为租户管理员
     *
     * @return boolean
     */
    public static boolean isTenantAdmin() {
        return isTenantAdmin(getRoleCodes());
    }

    /**
     * 是否为租户管理员
     *
     * @param rolePermission 角色权限标识组
     * @return boolean
     */
    public static boolean isTenantAdmin(Set<String> rolePermission) {
        if (Objects.isNull(rolePermission)) return false;
        return rolePermission.contains(TenantConstant.TENANT_ADMIN_ROLE_KEY);
    }

    public static boolean hasRole(String roleCode) {
        Set<String> roleCodes = getRoleCodes();
        if (Objects.isNull(roleCodes)) return false;

        return roleCodes.contains(roleCode);
    }

    public static boolean hasMenuPermission(String menuPermission) {
        Set<String> permission = getMenuPermission();
        if (Objects.isNull(permission)) return false;

        return permission.contains(menuPermission);
    }
}
