package cn.youngkbt.ag.system.permission.annotation;

import java.lang.annotation.*;

/**
 * @author Kele-Bingtang
 * @date 2024/6/26 01:30:31
 * @note 项目权限注解，检查当前登录人是否有权限访问、操作项目。必须放在有项目 ID 的接口或者类上，如果获取不到项目 ID，则不进行权限校验
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ProjectAuthorize {
    /**
     * 检查当前登录人是否拥有查看项目数据的权限（包含和项目绑定的服务、报表、类配置项等）
     */
    boolean checkRead() default false;

    /**
     * 检查当前登录人是否拥有操作项目数据的权限
     */
    boolean checkOperate() default false;
}
