package cn.youngkbt.uac.sys.listen;

import cn.hutool.core.util.ObjectUtil;
import cn.youngkbt.core.event.LoginInfoEvent;
import cn.youngkbt.uac.core.bo.LoginUserBO;
import cn.youngkbt.uac.core.constant.AuthConstant;
import cn.youngkbt.uac.core.constant.AuthRedisConstant;
import cn.youngkbt.uac.core.exception.AuthException;
import cn.youngkbt.uac.sys.service.SysLoginLogService;
import cn.youngkbt.utils.ServletUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * @author Kele-Bingtang
 * @date 2023/11/18 17:22
 * @note
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class LoginEventListen {
    @Value("${login.fail.maxRetryCount}")
    private Integer maxRetryCount;

    @Value("${login.fail.lockTime}")
    private Integer lockTime;

    private final SysLoginLogService sysLoginLogService;

    private final RedisTemplate<String, Object> redisTemplate;

    @Async
    @EventListener
    public void listenerLoginInfo(LoginInfoEvent loginInfoEvent) {
        if (loginInfoEvent.getStatus().equals(AuthConstant.LOGIN_SUCCESS)) {
            tryClearErrorCount(loginInfoEvent);
        } else if (loginInfoEvent.getStatus().equals(AuthConstant.LOGIN_FAIL)) {
            recordErrorCount(loginInfoEvent);
        }
        sysLoginLogService.recordLoginLog(loginInfoEvent);
    }

    /**
     * 解锁账号：累计的登录错误次数
     */
    public void tryClearErrorCount(LoginInfoEvent loginInfoEvent) {
        redisTemplate.delete(AuthRedisConstant.PWD_ERR_CNT_KEY + loginInfoEvent.getUsername());
    }

    /**
     * 记录累计的登录错误次数
     */
    public void recordErrorCount(LoginInfoEvent loginInfoEvent) {
        String errorKey = AuthRedisConstant.PWD_ERR_CNT_KEY + loginInfoEvent.getUsername();
        BoundValueOperations<String, Object> valueOps = redisTemplate.boundValueOps(errorKey);
        // 获取登录失败次数
        int errorCount = ObjectUtil.defaultIfNull((Integer) valueOps.get(), 0);
        errorCount++;
        valueOps.set(errorCount, Duration.ofMinutes(lockTime));
        // 到达锁定次数阈值
        if (errorCount >= maxRetryCount) {
            String message = "密码输入错误 " + maxRetryCount + " 次，帐户锁定 " + lockTime + " 分钟";
            loginInfoEvent.setMessage(message);
            sysLoginLogService.recordLoginLog(loginInfoEvent);
            throw new AuthException(message);
        } else {
            String message = "密码输入错误 " + maxRetryCount + " 次";
            loginInfoEvent.setMessage(message);
            sysLoginLogService.recordLoginLog(loginInfoEvent);
            throw new AuthException(message);
        }
    }

    public void checkLogin(LoginUserBO loginUserBO) {
        String errorKey = AuthRedisConstant.PWD_ERR_CNT_KEY + loginUserBO.getUsername();
        BoundValueOperations<String, Object> valueOps = redisTemplate.boundValueOps(errorKey);
        // 获取登录失败次数
        int errorCount = ObjectUtil.defaultIfNull((Integer) valueOps.get(), 0);
        if (errorCount >= maxRetryCount) {
            String message = "密码输入错误 " + maxRetryCount + " 次，帐户锁定 " + lockTime + " 分钟";

            LoginInfoEvent loginInfoEvent = LoginInfoEvent.builder()
                    .tenantId(loginUserBO.getTenantId())
                    .userId("")
                    .username(loginUserBO.getUsername())
                    .status(AuthConstant.LOGIN_FAIL)
                    .request(ServletUtil.getRequest())
                    .build();

            loginInfoEvent.setMessage(message);
            sysLoginLogService.recordLoginLog(loginInfoEvent);
            throw new AuthException(message);
        }
    }
}
