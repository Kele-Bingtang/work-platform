package cn.youngkbt.redis.utils;

import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

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
        return redisTemplate.delete(key);
    }
}
