package cn.youngkbt.redis.config;

import cn.youngkbt.core.date.DatePatternPlus;
import cn.youngkbt.redis.utils.RedisUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
    // @Bean
    // public RedisTemplate<String, Object> tranRedisTemplate() {
    //     RedisTemplate<String, Object> redisTemplate = redisTemplate();
    //     // 支持 spring 事务，如 @Transactional 注解
    //     redisTemplate.setEnableTransactionSupport(true);
    //     return redisTemplate;
    // }
    
    public <T> RedisTemplate<String, T> initRedisTemplate(RedisTemplate<String, T> redisTemplate) {
        
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        
        // 解决 Redis 无法存入 LocalDateTime 等 JDK8 的时间类
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        /*
         * 新增 LocalDateTime 序列化、反序列化规则
         */
        javaTimeModule
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DatePatternPlus.NORM_DATETIME_FORMATTER)) // yyyy-MM-dd HH:mm:ss
                .addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ISO_LOCAL_TIME)) // HH:mm:ss
                .addSerializer(Instant.class, InstantSerializer.INSTANCE) // Instant 类型序列化
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DatePatternPlus.NORM_DATETIME_FORMATTER)) // yyyy-MM-dd HH:mm:ss
                .addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE)) // yyyy-MM-dd
                .addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ISO_LOCAL_TIME)) // HH:mm:ss
                .addDeserializer(Instant.class, InstantDeserializer.INSTANT);// Instant 反序列化

        objectMapper.registerModules(javaTimeModule);

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
