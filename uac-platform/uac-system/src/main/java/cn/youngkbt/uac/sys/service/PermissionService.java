package cn.youngkbt.uac.sys.service;

import java.util.Set;

/**
 * @author Kele-Bingtang
 * @date 2024/6/8 15:02:31
 * @note
 */
public interface PermissionService {
    /**
     * 获取角色数据权限
     *
     * @param userId  用户 id
     * @return 角色权限信息
     */
    Set<String> getRoleCodes(String userId);

    /**
     * 获取菜单数据权限
     *
     * @param userId  用户 id
     * @return 菜单权限信息
     */
    Set<String> getMenuPermission(String userId);
}
