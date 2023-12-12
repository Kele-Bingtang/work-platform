package cn.youngkbt.core.instance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Kele-Bingtang
 * @date 2023/12/9 1:56
 * @note
 */
public class BeanInstance {

    public static <T> List<T> newArrayList() {
        return new ArrayList<>();
    }

    public static <T> List<T> newArrayList(List<T> useList, Function<T, T> function) {
        List<T> list = new ArrayList<>();
        useList.forEach(t -> list.add(function.apply(t)));
        return list;
    }

    public static <T, V> Map<T, V> newHashMap() {
        return new HashMap<>();
    }

    public static <T, V> Map<T, V> newHashMap(T key, V value) {
        return new HashMap<>();
    }

    public static Boolean newTrue() {
        return Boolean.TRUE;
    }

    public static Boolean newFalse() {
        return Boolean.FALSE;
    }
}
