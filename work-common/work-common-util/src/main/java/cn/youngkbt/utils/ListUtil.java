package cn.youngkbt.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * @author Kele-Bingtang
 * @date 2023/12/11 23:50
 * @note
 */
public class ListUtil {

    public static <T> List<T> newArrayList() {
        return new ArrayList<>();
    }

    public static <T> List<T> newArrayList(T... objects) {
        return Arrays.asList(objects);
    }

    public static <T> List<T> newArrayList(List<T> useList, Function<T, T> function) {
        List<T> list = newArrayList();
        useList.forEach(l -> list.add(function.apply(l)));
        return list;
    }

    public static <T, V> List<V> newArrayList(List<T> useList, Function<T, V> function, Class<V> clazz) {
        List<V> list = newArrayList();
        useList.forEach(l -> list.add(function.apply(l)));
        return list;
    }
}
