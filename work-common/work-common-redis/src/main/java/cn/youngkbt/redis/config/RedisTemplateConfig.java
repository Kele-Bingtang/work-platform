package cn.youngkbt.redis.config;

import cn.youngkbt.redis.utils.RedisUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author Kele-Bingtang
 * @date 2023/9/18 22:53
 * @note
 */
@AutoConfiguration
@ConditionalOnClass(RedisAutoConfiguration.class)
public class RedisTemplateConfig {

    private final RedisConnectionFactory redisConnectionFactory;

    public RedisTemplateConfig(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, String> redisTemplateString() {
        return initRedisTemplate(new StringRedisTemplate());
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = initRedisTemplate(new RedisTemplate<>());
        RedisUtil.init(redisTemplate);
        return redisTemplate;
    }

    /**
     * 支持 Spring 事务
     *
     * @return redisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> tranRedisTemplate() {
        RedisTemplate<String, Object> redisTemplate = redisTemplate();
        // 支持 spring 事务，如 @Transactional 注解
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }
    
    public <T> RedisTemplate<String, T> initRedisTemplate(RedisTemplate<String, T> redisTemplate) {
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);

        // 使用 Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);

        /*
         * 设置 value 的序列化规则和 key 的序列化规则
         * RedisTemplate 默认序列化使用的 jdkSerializable, 存储到 Redis 会变成二进制字节码，有风险！
         * 所以官网建议转成其他序列化
         */
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
}
