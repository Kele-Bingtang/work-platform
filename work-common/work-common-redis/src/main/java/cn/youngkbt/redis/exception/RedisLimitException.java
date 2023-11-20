package cn.youngkbt.redis.exception;

import cn.youngkbt.core.exception.BaseException;

/**
 * @author Kele-Bingtang
 * @date 2023/6/30 23:26
 * @note
 */
public class RedisLimitException extends BaseException {
    public RedisLimitException(String msg) {
        super(msg);
    }
}