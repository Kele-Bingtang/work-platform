package cn.youngkbt.utils;

import cn.youngkbt.helper.SpringHelper;
import io.github.linpeilie.Converter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author Kele-Bingtang
 * @date 2023/11/29 0:54
 * @note
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MapstructUtil {
    private final static Converter CONVERTER = SpringHelper.getBean(Converter.class);

    /**
     * 将 T 类型对象，转换为 desc 类型的对象并返回
     *
     * @param source     数据来源实体
     * @param targetType 描述对象 转换后的对象
     * @return desc
     */
    public static <S, T> T convert(S source, Class<T> targetType) {
        return CONVERTER.convert(source, targetType);
    }

    /**
     * 将 T 类型对象，按照配置的映射字段规则，给 desc 类型的对象赋值并返回 desc 对象
     *
     * @param source 数据来源实体
     * @param target   转换后的对象
     * @return desc
     */
    public static <T, V> V convert(T source, V target) {
        return CONVERTER.convert(source, target);
    }

    /**
     * 将 T 类型的集合，转换为 desc 类型的集合并返回
     *
     * @param sourceList 数据来源实体列表
     * @param desc       描述对象 转换后的对象
     * @return desc
     */
    public static <T, V> List<V> convert(List<T> sourceList, Class<V> desc) {
        return CONVERTER.convert(sourceList, desc);
    }

    /**
     * 将 Map 转换为 beanClass 类型的集合并返回
     *
     * @param map       数据来源
     * @param beanClass bean类
     * @return bean对象
     */
    public static <T> T convert(Map<String, Object> map, Class<T> beanClass) {
        return CONVERTER.convert(map, beanClass);
    }
}
