package cn.youngkbt.uac.core.exception;

import cn.youngkbt.core.base.BaseCommonEnum;
import cn.youngkbt.core.exception.BaseException;

/**
 * @author Kele-Bingtang
 * @date 2023/11/14 23:53
 * @note
 */
public class AuthException extends BaseException {
    public AuthException(Integer code) {
        super(code);
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(Integer code, String status) {
        super(code, status);
    }

    public AuthException(Integer code, String status, String message) {
        super(code, status, message);
    }

    public AuthException(BaseCommonEnum baseCommonEnum) {
        super(baseCommonEnum);
    }
}
