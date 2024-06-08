package cn.youngkbt.redis.utils;

import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * @author Kele-Bingtang
 * @date 2024/1/21 21:47
 * @note
 */
@NoArgsConstructor
public class RedisUtil {

    private static RedisTemplate<String, Object> redisTemplate;

    public static void init(RedisTemplate<String, Object> redisTemplate) {
        RedisUtil.redisTemplate = redisTemplate;
    }

    public static Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    public static boolean hasKey(String key) {
        Boolean hasKey = redisTemplate.hasKey(key);
        if (Objects.nonNull(hasKey)) {
            return hasKey;
        }
        return false;
    }

    public static Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    public static boolean expireAt(String key, Date date) {
        Boolean result = redisTemplate.expireAt(key, date);
        if (Objects.nonNull(result)) {
            return result;
        }
        return false;
    }

    public static Object getForValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public static void setForValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public static void setForValue(String key, Object value, Duration timeout) {
        redisTemplate.opsForValue().set(key, value, timeout);
    }

    public static boolean delete(String key) {
        Boolean result = redisTemplate.delete(key);
        if (Objects.nonNull(result)) {
            return result;
        }
        return false;
    }
}
