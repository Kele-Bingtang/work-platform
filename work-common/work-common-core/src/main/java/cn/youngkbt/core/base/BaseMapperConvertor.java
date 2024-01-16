package cn.youngkbt.core.base;

import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * @author Kele-Bingtang
 * @date 2023/9/19 0:29
 * @note
 */
public interface BaseMapperConvertor<S, T> {
    
    T convert(S sourceClass);

    S convertInvert(T targetClass);

    List<T> convert(List<S> sourceClass);

    List<S> convertInvert(List<T> targetClass);

    void update(S sourceClass, @MappingTarget T targetClass);

    void updateInvert(T targetClass, @MappingTarget S sourceClass);
}
