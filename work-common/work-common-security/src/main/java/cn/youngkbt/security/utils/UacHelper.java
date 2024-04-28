package cn.youngkbt.security.utils;

import cn.youngkbt.redis.constants.AuthRedisConstant;
import cn.youngkbt.redis.utils.RedisUtil;
import cn.youngkbt.security.domain.LoginUser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

/**
 * @author Kele-Bingtang
 * @date 2024/1/25 1:28
 * @note
 */
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UacHelper {

    public static LoginUser getLoginUser() {
        Object userInfo = RedisUtil.getForValue(AuthRedisConstant.USER_INFO_KEY + SecurityUtils.getUsername());
        if (Objects.isNull(userInfo)) {
            return null;
        }
        return (LoginUser) userInfo;
    }

    public static LoginUser getLoginUser(String username) {
        Object userInfo = RedisUtil.getForValue(AuthRedisConstant.USER_INFO_KEY + username);
        if (Objects.isNull(userInfo)) {
            return null;
        }
        return (LoginUser) userInfo;
    }

    public static List<LoginUser> getAllLoginUser() {
        List<LoginUser> loginUserList = new ArrayList<>();
        Set<String> keys = RedisUtil.keys(AuthRedisConstant.USER_INFO_KEY + "*");
        for (String key : keys) {
            loginUserList.add((LoginUser) RedisUtil.getForValue(key));
        }
        return loginUserList;
    }

    public static void cacheUserInfo(LoginUser loginUser, Long timeout) {
        RedisUtil.setForValue(AuthRedisConstant.USER_INFO_KEY + loginUser.getUsername(), loginUser, Duration.ofMillis(timeout));
    }

    public static boolean updateUserInfo(LoginUser loginUser) {
        String key = AuthRedisConstant.USER_INFO_KEY + SecurityUtils.getUsername();
        // 单位为秒
        Long expire = RedisUtil.getExpire(key);
        // 获取过期时间
        Date date = new Date(Instant.now().toEpochMilli() + expire * 1000);
        RedisUtil.setForValue(AuthRedisConstant.USER_INFO_KEY + loginUser.getUsername(), loginUser);
        return RedisUtil.expireAt(key, date);
    }

    public static String getUsername() {
        LoginUser userInfo = getLoginUser();
        if (Objects.isNull(userInfo)) {
            return null;
        }
        return userInfo.getUsername();
    }

    public static String getTenantId() {
        LoginUser userInfo = getLoginUser();
        if (Objects.isNull(userInfo)) {
            return null;
        }
        return userInfo.getTenantId();
    }

    public static boolean logout() {
        LoginUser userInfo = getLoginUser();
        if (Objects.isNull(userInfo)) {
            return false;
        }
        return RedisUtil.delete(AuthRedisConstant.USER_INFO_KEY + userInfo.getUsername());
    }
}
