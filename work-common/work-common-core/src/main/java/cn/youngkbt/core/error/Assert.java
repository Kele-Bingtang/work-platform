package cn.youngkbt.core.error;

import cn.youngkbt.core.http.ResponseStatusEnum;
import cn.youngkbt.core.exception.ServiceException;
import cn.youngkbt.utils.StringUtil;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

/**
 * @author Kele-Bingtang
 * @date 2023/6/30 23:10
 * @note
 */
public class Assert {

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new ServiceException(ResponseStatusEnum.ERROR.getCode(), ResponseStatusEnum.ERROR.getStatus(), message);
        }
    }

    public static void isTrue(boolean expression, ResponseStatusEnum responseStatusEnum) {
        if (!expression) {
            throw new ServiceException(responseStatusEnum);
        }
    }

    public static void isNull(@Nullable Object object, String message) {
        if (object != null) {
            throw new ServiceException(ResponseStatusEnum.ERROR.getCode(), ResponseStatusEnum.ERROR.getStatus(), message);
        }
    }

    public static void isNull(@Nullable Object object, ResponseStatusEnum responseStatusEnum) {
        if (object != null) {
            throw new ServiceException(responseStatusEnum);
        }
    }

    public static void nonNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new ServiceException(ResponseStatusEnum.ERROR.getCode(), ResponseStatusEnum.ERROR.getStatus(), message);
        }
    }

    public static void nonNull(@Nullable Object object, ResponseStatusEnum responseStatusEnum) {
        if (object == null) {
            throw new ServiceException(responseStatusEnum);
        }
    }

    public static void notBlank(String text, String message) {
        if (!StringUtil.hasText(text)) {
            throw new ServiceException(ResponseStatusEnum.ERROR.getCode(), ResponseStatusEnum.ERROR.getStatus(), message);
        }
    }

    public static void notBlank(String text, ResponseStatusEnum responseStatusEnum) {
        if (!StringUtil.hasText(text)) {
            throw new ServiceException(responseStatusEnum);
        }
    }

    public static void isBlank(String text, String message) {
        if (StringUtil.hasText(text)) {
            throw new ServiceException(ResponseStatusEnum.ERROR.getCode(), ResponseStatusEnum.ERROR.getStatus(), message);
        }
    }

    public static void isBlank(String text, ResponseStatusEnum responseStatusEnum) {
        if (StringUtil.hasText(text)) {
            throw new ServiceException(responseStatusEnum);
        }
    }

    public static void hasLength(String text, String message) {
        if (!StringUtil.hasLength(text)) {
            throw new ServiceException(ResponseStatusEnum.ERROR.getCode(), ResponseStatusEnum.ERROR.getStatus(), message);
        }
    }
}