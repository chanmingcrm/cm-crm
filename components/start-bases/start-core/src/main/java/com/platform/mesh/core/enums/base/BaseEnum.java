package com.platform.mesh.core.enums.base;

import com.platform.mesh.core.constants.SymbolConst;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @description 枚举基类
 * @param <E> 子枚举
 * @param <V> 枚举value的类型
 * @author 蝉鸣
 */
//@JsonSerialize(using = EnumJsonSerializer.class)
//@JsonDeserialize(using = EnumJsonDeserializer.class)
public interface BaseEnum<E extends Enum<E>, V extends Serializable>{

    /**
     * 功能描述:
     * 〈获取枚举类的值〉
     * @return 正常返回:{@link Object}
     * @author 蝉鸣
     */
    V getValue();

    /**
     * 功能描述:
     * 〈获取枚举类的说明〉
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    String getDesc();

    /**
     * 根据子类Class获取对应的实例
     * @param <E> 子类class类型
     * @param <V> 枚举值类型
     * @param clazz 子类class
     * @param value value的值
     * @return 根据参数value获取参数clazz的实例
     */
    static <E extends BaseEnum<?, V>, V extends Serializable> E getEnumByValue(Class<E> clazz, V value) {
        return Arrays.stream(clazz.getEnumConstants()).filter(e -> e.getValue().equals(value)).findFirst().orElse(null);
    }

    /**
     * 根据子类Class获取对应的实例
     * @param <E> 子类class类型
     * @param <V> 枚举值类型
     * @param clazz 子类class
     * @param value value的值
     * @return 根据参数value获取参数clazz的实例
     */
    static <E extends BaseEnum<?, V>, V extends Serializable> E getEnumByValue(Class<E> clazz, V value, E defaultEnum) {
        return Arrays.stream(clazz.getEnumConstants()).filter(e -> e.getValue().equals(value)).findFirst().orElse(defaultEnum);
    }

    /**
     * 根据子类Class获取对应的实例
     * @param <E> 子类class类型
     * @param <V> 枚举值类型
     * @param clazz 子类class
     * @param desc desc的值
     * @return 根据参数value获取参数clazz的实例
     */
    static <E extends BaseEnum<?, V>, V extends Serializable> E getEnumByDesc(Class<E> clazz, String desc) {
        return Arrays.stream(clazz.getEnumConstants()).filter(e -> e.getDesc().equals(desc)).findFirst().orElse(null);
    }

    /**
     * 根据子类Class获取对应的实例
     * @param <E> 子类class类型
     * @param <V> 枚举值类型
     * @param clazz 子类class
     * @param desc desc的值
     * @return 根据参数value获取参数clazz的实例
     */
    static <E extends BaseEnum<?, V>, V extends Serializable> E getEnumByDesc(Class<E> clazz, String desc, E defaultEnum) {
        return Arrays.stream(clazz.getEnumConstants()).filter(e -> e.getDesc().equals(desc)).findFirst().orElse(defaultEnum);
    }

    /**
     * 根据子类Class value获取对应描述
     * @param <E> 子类class类型
     * @param <V> 枚举值类型
     * @param clazz 子类class
     * @param value value的值
     * @return 根据参数value获取参数clazz的实例
     */
    static <E extends BaseEnum<?, V>, V extends Serializable> String getDescByValue(Class<E> clazz, V value) {
        return Arrays.stream(clazz.getEnumConstants()).filter(e -> e.getValue().equals(value)).findFirst().map(item->item.getDesc()).orElse(SymbolConst.BLANK);
    }

    /**
     * 根据子类Class value获取对应描述
     * @param <E> 子类class类型
     * @param <V> 枚举值类型
     * @param clazz 子类class
     * @param desc desc
     * @return 根据参数value获取参数clazz的实例
     */
    static <E extends BaseEnum<?, V>, V extends Serializable> V getValueByDesc(Class<E> clazz, String desc) {
        return Arrays.stream(clazz.getEnumConstants()).filter(e -> e.getDesc().equals(desc)).findFirst().map(item->item.getValue()).orElse(null);
    }

    /**
     * 根据子类Class value获取对应描述
     * @param <E> 子类class类型
     * @param <V> 枚举值类型
     * @param clazz 子类class
     * @param desc desc
     * @return 根据参数value获取参数clazz的实例
     */
    static <E extends BaseEnum<?, V>, V extends Serializable> V getValueByDesc(Class<E> clazz, String desc,V defaultValue) {
        return Arrays.stream(clazz.getEnumConstants()).filter(e -> e.getDesc().equals(desc)).findFirst().map(item->item.getValue()).orElse(defaultValue);
    }

}