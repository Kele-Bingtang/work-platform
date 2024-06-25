package cn.youngkbt.ag.system.permission.annotation;

import java.lang.annotation.*;

/**
 * @author Kele-Bingtang
 * @date 2024/6/26 01:32:48
 * @note 团队权限注解，检查当前登录人是否拥有操作团队数据的权限。必须放在有团队 ID 的接口或者类上，如果获取不到团队 ID，则不进行权限校验
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TeamAuthorize {
}
