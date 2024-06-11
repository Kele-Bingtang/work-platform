package cn.youngkbt.cache.manager;

import cn.youngkbt.cache.properties.RedisCacheProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Kele-Bingtang
 * @date 2024/6/11 23:14:15
 * @note Caffeine + Redis 双重缓存管理
 */
@RequiredArgsConstructor
public class CaffeineRedisCacheManager implements CacheManager {
    Map<String, Cache> cacheMap = new ConcurrentHashMap<>();

    private final RedisConnectionFactory redisConnectionFactory;
    private final RedisCacheProperties redisCacheProperties;

    @Override
    public Cache getCache(String cacheName) {
        // 重写 cacheName 支持多参数。如：#cacheName#ttl
        String[] array = StringUtils.delimitedListToStringArray(cacheName, "#");
        cacheName = array[0];

        Cache cache = cacheMap.get(cacheName);
        if (Objects.nonNull(cache)) {
            return cache;
        }

        // Redis 缓存配置项，不允许有空值
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(redisCacheProperties.getExpire())).disableCachingNullValues();
        
        // cacheName 后面的参数默认是 ttl
        if (array.length > 1) {
            long seconds = DurationStyle.detectAndParse(array[1]).toSeconds();
            configuration.entryTtl(Duration.ofSeconds(seconds));
        }

        // 创建 RedisCacheManager
        RedisCacheManager redisCacheManager = new RedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory), configuration);

        // 获取 RedisCache，里面只想 Redis 封装了 Redis 的命令操作
        Cache redisCache = redisCacheManager.getCache(cacheName);

        CaffeineRedisCache newCache = new CaffeineRedisCache(redisCache);
        cacheMap.put(cacheName, newCache);
        return newCache;
    }


    @Override
    public Collection<String> getCacheNames() {
        return Collections.unmodifiableSet(cacheMap.keySet());
    }
}
