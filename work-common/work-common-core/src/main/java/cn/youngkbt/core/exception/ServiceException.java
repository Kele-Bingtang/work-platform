package cn.youngkbt.core.exception;


import cn.youngkbt.core.base.BaseCommonEnum;

/**
 * @author Kele-Bingtang
 * @date 2023/6/30 23:16
 * @note
 */
public class ServiceException extends BaseException {
    public ServiceException(Integer code) {
        super(code);
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Integer code, String status) {
        super(code, status);
    }

    public ServiceException(Integer code, String status, String message) {
        super(code, status, message);
    }

    public ServiceException(BaseCommonEnum baseCommonEnum) {
        super(baseCommonEnum);
    }
}
