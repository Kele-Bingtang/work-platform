package cn.youngkbt.redis.limit.annotations;

/**
 * @author Kele-Bingtang
 * @date 2023/6/30 23:01
 * @note
 */

import java.lang.annotation.*;

/**
 * 例： @RedisLimit(key = "redis-limit:test", permitsPerSecond = 4, expire = 1, msg = "请求太频繁，等一下啦")
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface RedisLimit {
    /**
     * 资源的key,唯一
     * 作用：不同的接口，不同的流量控制
     */
    String key() default "";

    /**
     * 最多的访问限制次数
     */
    long permitsPerSecond() default 2;

    /**
     * 过期时间也可以理解为单位时间，单位秒，默认60
     */
    long expire() default 60;


    /**
     * 得不到令牌的提示语
     */
    String msg() default "系统繁忙,请稍后再试.";
}