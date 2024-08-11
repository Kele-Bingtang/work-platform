package cn.youngkbt.file.system.aspect.log.annotation;

import cn.youngkbt.file.system.aspect.log.enums.OperatorType;

import java.lang.annotation.*;

/**
 * @author Kele-Bingtang
 * @date 2024/8/11 17:20:30
 * @note
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperateLog {
    /**
     * 操作类型
     */
    OperatorType operatorType();

    /**
     * AppId
     */
    String appId();

    /**
     * fileKey
     */
    String fileKey() default "";
}
