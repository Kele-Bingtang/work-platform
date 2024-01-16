package cn.youngkbt.core.exception;

import cn.youngkbt.core.base.BaseCommonEnum;
import cn.youngkbt.core.http.ResponseStatusEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Kele-Bingtang
 * @date 2023/6/30 22:59
 * @note
 */
@Setter
@Getter
public class BaseException extends RuntimeException {

    private Integer code = ResponseStatusEnum.FAIL.getCode();
    private String status = ResponseStatusEnum.FAIL.getStatus();
    private String message = ResponseStatusEnum.FAIL.getMessage();

    public BaseException(Integer code) {
        this.code = code;
    }

    public BaseException(String message) {
        this.message = message;
    }

    public BaseException(Integer code, String status) {
        this.code = code;
        this.status = status;
    }

    public BaseException(Integer code, String status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public BaseException(BaseCommonEnum baseCommonEnum) {
        this.code = baseCommonEnum.getCode();
        this.status = baseCommonEnum.getStatus();
        this.message = baseCommonEnum.getMessage();
    }
}
