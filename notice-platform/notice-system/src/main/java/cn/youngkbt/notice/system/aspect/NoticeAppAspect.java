package cn.youngkbt.notice.system.aspect;

import cn.youngkbt.helper.SpElParserHelper;
import cn.youngkbt.notice.system.aspect.annotation.AppAuthorize;
import cn.youngkbt.notice.system.mapper.AppInfoMapper;
import cn.youngkbt.notice.system.model.po.AppInfo;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author Kele-Bingtang
 * @date 2024/8/23 00:35:04
 * @note
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class NoticeAppAspect {
    private final AppInfoMapper appInfoMapper;

    @Before("@annotation(appAuthorize)")
    public void before(JoinPoint joinPoint, AppAuthorize appAuthorize) {
        // 注解只能放在方法上，所以强转 MethodSignature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String appId = SpElParserHelper.parse(signature.getMethod(), joinPoint.getArgs(), appAuthorize.value(), String.class);
        Assert.hasText(appId, "AppId 不能为空");

        AppInfo appInfo = appInfoMapper.selectOne(Wrappers.<AppInfo>lambdaQuery()
                .eq(AppInfo::getAppId, appId));

        Assert.notNull(appInfo, "该 AppId 没有认证");
        Assert.isTrue(appInfo.getStatus() == 1, "该 AppId 已被禁用");
    }
}
