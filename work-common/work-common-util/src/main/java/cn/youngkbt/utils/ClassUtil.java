package cn.youngkbt.utils;

import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.SynthesizingMethodParameter;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author Kele-Bingtang
 * @date 2023/7/3 22:21
 * @note
 */
public class ClassUtil extends ClassUtils {

    private static final ParameterNameDiscoverer PARAMETER_NAME_DISCOVERER = new DefaultParameterNameDiscoverer();

    /**
     * 获取方法参数信息
     *
     * @param constructor    构造器
     * @param parameterIndex 参数序号
     * @return {MethodParameter}
     */
    public static MethodParameter getMethodParameter(Constructor<?> constructor, int parameterIndex) {
        MethodParameter methodParameter = new SynthesizingMethodParameter(constructor, parameterIndex);
        methodParameter.initParameterNameDiscovery(PARAMETER_NAME_DISCOVERER);
        return methodParameter;
    }

    /**
     * 获取方法参数信息
     *
     * @param method         方法
     * @param parameterIndex 参数序号
     * @return {MethodParameter}
     */
    public static MethodParameter getMethodParameter(Method method, int parameterIndex) {
        MethodParameter methodParameter = new SynthesizingMethodParameter(method, parameterIndex);
        methodParameter.initParameterNameDiscovery(PARAMETER_NAME_DISCOVERER);
        return methodParameter;
    }

    /**
     * 获取Annotation
     *
     * @param method         Method
     * @param annotationType 注解类
     * @param <A>            泛型标记
     * @return {Annotation}
     */
    public static <A extends Annotation> A getAnnotation(Method method, Class<A> annotationType) {
        Class<?> targetClass = method.getDeclaringClass();
        // The method may be on an interface, but we need attributes from the target class.
        // If the target class is null, the method will be unchanged.
        Method specificMethod = ClassUtil.getMostSpecificMethod(method, targetClass);
        // If we are dealing with method with generic parameters, find the original method.
        specificMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);
        // 先找方法，再找方法上的类
        A annotation = AnnotatedElementUtils.findMergedAnnotation(specificMethod, annotationType);
        ;
        if (null != annotation) {
            return annotation;
        }
        // 获取类上面的Annotation，可能包含组合注解，故采用spring的工具类
        return AnnotatedElementUtils.findMergedAnnotation(specificMethod.getDeclaringClass(), annotationType);
    }

    /**
     * 判断是否有注解 Annotation
     *
     * @param method         Method
     * @param annotationType 注解类
     * @param <A>            泛型标记
     * @return {boolean}
     */
    public static <A extends Annotation> boolean isAnnotated(Method method, Class<A> annotationType) {
        // 先找方法，再找方法上的类
        boolean isMethodAnnotated = AnnotatedElementUtils.isAnnotated(method, annotationType);
        if (isMethodAnnotated) {
            return true;
        }
        // 获取类上面的Annotation，可能包含组合注解，故采用spring的工具类
        Class<?> targetClass = method.getDeclaringClass();
        return AnnotatedElementUtils.isAnnotated(targetClass, annotationType);
    }
    
}
