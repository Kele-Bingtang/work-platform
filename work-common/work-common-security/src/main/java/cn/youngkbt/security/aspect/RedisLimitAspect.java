package cn.youngkbt.security.aspect;

import cn.hutool.core.util.StrUtil;
import cn.youngkbt.redis.constants.RedisConstants;
import cn.youngkbt.redis.exception.RedisLimitException;
import cn.youngkbt.redis.limit.CurrentLimitProperties;
import cn.youngkbt.redis.limit.annotations.RedisLimit;
import cn.youngkbt.security.utils.LoginHelper;
import cn.youngkbt.utils.StringUtil;
import cn.youngkbt.utils.WebUtil;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2023/6/30 23:25
 * @note
 */
@Slf4j
@Aspect
@Component
public class RedisLimitAspect {

    private final StringRedisTemplate stringRedisTemplate;

    private final CurrentLimitProperties currentLimitProperties;

    public RedisLimitAspect(StringRedisTemplate stringRedisTemplate, CurrentLimitProperties currentLimitProperties) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.currentLimitProperties = currentLimitProperties;
    }

    @Pointcut("execution(* com..controller..*.*(..)) || @annotation(cn.youngkbt.redis.limit.annotations.RedisLimit)")
    private void check() {
    }

    private DefaultRedisScript<Long> redisScript;

    @PostConstruct
    public void init() {
        redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(Long.class);
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("rateLimiter.lua")));
    }

    @Before("check()")
    public void before(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 拿到 RedisLimit 注解，如果存在则说明需要限流
        RedisLimit redisLimit = method.getAnnotation(RedisLimit.class);
        HttpServletRequest request = WebUtil.getRequest();
        if (Objects.isNull(request)) {
            return;
        }
        // StrUtil.containsAnyIgnoreCase 判断请求的 url 是否有配置文件限流的 urls
        if(null != redisLimit || StrUtil.containsAnyIgnoreCase(request.getRequestURI(), currentLimitProperties.getUrls())){
            // 获取 redis 的 key
            String username = LoginHelper.getUsername();
            String key;
            long limit;
            long expire;
            if (Objects.nonNull(redisLimit)) {
                // 将数组用分隔字符串合并为字符串：AUTH:CAPTCHA:RECORD:userId:redisLimit.key()
                key = RedisConstants.AUTH_CAPTCHA_RECORD + ":" + username + ":" + redisLimit.key();
                limit = redisLimit.permitsPerSecond();
                expire = redisLimit.expire();
            } else {
                // AUTH:CAPTCHA:RECORD:userId:request.getRequestURI()
                key = RedisConstants.SERVER_REQUEST_LIMIT + ":" + username + ":" + request.getRequestURI();
                limit = currentLimitProperties.getLimit();
                expire= currentLimitProperties.getExpire();
            }
            if (!StringUtil.hasText(key)) {
                throw new RedisLimitException("redis key cannot be null");
            }

            List<String> keys = new ArrayList<>();
            keys.add(key);
            Long count = stringRedisTemplate.execute(redisScript, keys, String.valueOf(limit), String.valueOf(expire));
            log.info("接口限流, Access try count is {} for key={}", count, key);

            if (null != count && count == 0) {
                log.debug("接口限流, 获取 key 失败，key为{}", key);
                throw new RedisLimitException(Objects.nonNull(redisLimit) ? redisLimit.msg() : "请求过于频繁！");
                // throw new RedisLimitException(Objects.nonNull(redisLimit) ? redisLimit.msg() : GlobalErrorCodeConstants.TOO_MANY_REQUESTS.getMsg());
            }
        }
    }
}
