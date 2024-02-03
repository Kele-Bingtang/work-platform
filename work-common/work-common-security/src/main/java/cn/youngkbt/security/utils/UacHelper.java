package cn.youngkbt.security.utils;

import cn.youngkbt.redis.constants.AuthRedisConstant;
import cn.youngkbt.redis.utils.RedisUtil;
import cn.youngkbt.security.domain.LoginUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;

/**
 * @author Kele-Bingtang
 * @date 2024/1/25 1:28
 * @note
 */
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UacHelper {

    public static LoginUser getUserInfo() {
        Object userInfo = RedisUtil.getForValue(AuthRedisConstant.USER_INFO_KEY + SecurityUtils.getUsername());
        if (Objects.isNull(userInfo)) {
            return null;
        }
        return (LoginUser) userInfo;
    }

    public static void cacheUserInfo(LoginUser loginUser, Long timeout) {
        RedisUtil.setForValue(AuthRedisConstant.USER_INFO_KEY + loginUser.getUsername(), loginUser, Duration.ofMillis(timeout));
    }

    public static String getUsername() {
        LoginUser userInfo = getUserInfo();
        if (Objects.isNull(userInfo)) {
            return null;
        }
        return userInfo.getUsername();
    }

    public static String getTenantId() {
        LoginUser userInfo = getUserInfo();
        if (Objects.isNull(userInfo)) {
            return null;
        }
        return userInfo.getTenantId();
    }
}
