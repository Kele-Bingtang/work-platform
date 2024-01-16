package cn.youngkbt.tenant.exception;

import cn.youngkbt.core.base.BaseCommonEnum;
import cn.youngkbt.core.exception.BaseException;

/**
 * @author Kele-Bingtang
 * @date 2023/11/19 23:36
 * @note
 */
public class TenantException extends BaseException {
    public TenantException(Integer code) {
        super(code);
    }

    public TenantException(String message) {
        super(message);
    }

    public TenantException(Integer code, String status) {
        super(code, status);
    }

    public TenantException(Integer code, String status, String message) {
        super(code, status, message);
    }

    public TenantException(BaseCommonEnum baseCommonEnum) {
        super(baseCommonEnum);
    }
}
