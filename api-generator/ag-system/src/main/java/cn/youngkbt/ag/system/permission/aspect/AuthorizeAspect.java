package cn.youngkbt.ag.system.permission.aspect;

import cn.youngkbt.ag.system.permission.annotation.ProjectAuthorize;
import cn.youngkbt.ag.system.permission.annotation.TeamAuthorize;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author Kele-Bingtang
 * @date 2024/6/26 01:54:46
 * @note 团队权限、项目权限切面
 */
@Slf4j
@Aspect
@Component
public class AuthorizeAspect {

    @Before("@annotation(projectAuthorize)")
    public void before(JoinPoint joinPoint, ProjectAuthorize projectAuthorize) {

    }

    @Before("@annotation(teamAuthorize)")
    public void before(JoinPoint joinPoint, TeamAuthorize teamAuthorize) {
    }
}
