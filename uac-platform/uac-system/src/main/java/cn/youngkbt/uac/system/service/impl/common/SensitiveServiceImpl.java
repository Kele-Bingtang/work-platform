package cn.youngkbt.uac.system.service.impl.common;

import cn.youngkbt.sensitive.service.SensitiveService;
import cn.youngkbt.tenant.helper.TenantHelper;
import cn.youngkbt.uac.core.helper.UacHelper;
import cn.youngkbt.utils.StringUtil;
import org.springframework.stereotype.Service;

/**
 * @author Kele-Bingtang
 * @date 2024/6/8 01:54:41
 * @note
 */
@Service
public class SensitiveServiceImpl implements SensitiveService {

    @Override
    public boolean isSensitive(String roleCode, String perms) {
        if (!UacHelper.isLogin()) {
            return true;
        }

        if (StringUtil.hasText(roleCode, perms)) {
            // 判断是否存在角色码或者菜单权限
            if (UacHelper.hasRole(roleCode) && UacHelper.hasMenuPermission(perms)) {
                return false;
            }
        } else if (UacHelper.hasRole(roleCode)) {
            return true;
        } else if (UacHelper.hasMenuPermission(perms)) {
            return true;
        }

        if (TenantHelper.isEnable()) {
            // 管理员不需要脱敏
            return !UacHelper.isAdmin() && !UacHelper.isTenantAdmin();
        }

        return !UacHelper.isAdmin();
    }
}
