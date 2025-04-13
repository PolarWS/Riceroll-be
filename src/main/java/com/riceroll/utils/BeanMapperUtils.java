package com.riceroll.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BeanMapperUtils {

    /**
     * bean对象转换
     *
     * @param source      源对象
     * @param targetClass 目标类
     * @param <S>         源对象类型
     * @param <T>         目标对象类型
     * @return 目标对象
     */

    public static <S, T> T map(S source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        try {
            T target = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("对象转换失败", e);
        }
    }

    public static <S, T> List<T> mapList(List<S> sourceList, Class<T> targetClass) {
        return sourceList.stream()
                .map(source -> convertSingleObject(source, targetClass))
                .collect(Collectors.toList());
    }

    private static <S, T> T convertSingleObject(S source, Class<T> targetClass) {
        if (source instanceof Map) {
            return convertFromMap((Map<?, ?>) source, targetClass);
        }
        return map(source, targetClass);
    }

    private static <T> T convertFromMap(Map<?, ?> sourceMap, Class<T> targetClass) {
        try {
            T target = targetClass.getDeclaredConstructor().newInstance();
            sourceMap.forEach((key, value) -> setFieldValue(target, targetClass, key.toString(), value));
            return target;
        } catch (Exception e) {
            throw new RuntimeException("Map转换失败", e);
        }
    }

    private static <T> void setFieldValue(T target, Class<T> targetClass, String fieldName, Object value) {
        try {
            java.lang.reflect.Field field = targetClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            // 忽略无法设置的属性
        }
    }
}
