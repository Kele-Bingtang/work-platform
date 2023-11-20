package cn.youngkbt.core.exception;


import cn.youngkbt.core.base.BaseCommonEnum;

/**
 * @author Kele-Bingtang
 * @date 2023/6/30 23:15
 * @note
 */
public class ServerException extends BaseException{
    public ServerException(Integer code) {
        super(code);
    }

    public ServerException(String message) {
        super(message);
    }

    public ServerException(Integer code, String status) {
        super(code, status);
    }

    public ServerException(Integer code, String status, String message) {
        super(code, status, message);
    }

    public ServerException(BaseCommonEnum baseCommonEnum) {
        super(baseCommonEnum);
    }
}
