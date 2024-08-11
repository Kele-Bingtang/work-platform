package cn.youngkbt.file.system.aspect.app.annotation;

import java.lang.annotation.*;

/**
 * @author Kele-Bingtang
 * @date 2024/8/11 16:49:29
 * @note
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AppAuthorize {
    /**
     * SpEl 表达式，AppId
     */
    String value() default "";
}
