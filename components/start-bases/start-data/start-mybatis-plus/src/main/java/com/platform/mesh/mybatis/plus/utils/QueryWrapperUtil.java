package com.platform.mesh.mybatis.plus.utils;


import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.platform.mesh.mybatis.plus.query.LambdaQueryWrapperX;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * <p>
 *    先保留，后续扩展SQL动态查询用到
 * </p>
 *
 * @author 蝉鸣
 * @since 2024/8/29 17:48
 **/
@Slf4j
public class QueryWrapperUtil {

    /***
     * 功能描述:
     * 〈将View层条件转换为DB层条件〉
     * @param preview preview
     * @param dbQuery dbQuery
     * @return 正常返回:{@link LambdaQueryWrapperX<T>}
     * @author 蝉鸣
     * @since 2024/8/30 9:41
     */
    public static <T,R> LambdaQueryWrapperX<T> viewToDBCondition(Object preview, Class<T> dbQuery) {
        LambdaQueryWrapperX<T> queryWrapperX = new LambdaQueryWrapperX<>();
        Field[] fields = dbQuery.getDeclaredFields();
        for(Field field:fields){
                // 将 name 字段转换为 SFunction<Person, String>
                SFunction<T, R> fieldSType = createSFunction(field);
                T t = (T) preview;
                queryWrapperX.eqIfPresent(fieldSType, fieldSType.apply(t));

        }
        return queryWrapperX;
    }

    /**
     * 根据给定的 Field 创建 SFunction 实例。
     *
     * @param <T> 实体类的类型
     * @param <R> 字段的类型
     * @param field 要转换为 SFunction 的 Field 对象
     * @return SFunction 实例
     */
//    public static <T, R> SFunction<T, R> createSFunction(Field field) {
//        return (T entity) -> {
//            try {
//                field.setAccessible(true); // 允许访问私有字段
//                return (R) field.get(entity);
//            } catch (IllegalAccessException e) {
//                log.info("Failed to access field: " + field.getName(), e);
//            }
//            return null;
//        };
//    }


    /**
     * 根据给定的 Field 创建 SFunction 实例。
     *
     * @param <T> 实体类的类型
     * @param <R> 字段的类型
     * @param field 要转换为 SFunction 的 Field 对象
     * @return SFunction 实例
     */
    public static <T, R> SFunction<T, R> createSFunction(Field field) {
        //这里T作为形参是操作类
        return (T entity) -> {
            try {
                String fieldName = field.getName();
                // 尝试获取字段名称对应的getter方法名称
                String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                log.info("methodName:{}",methodName);
                R fieldValue = (R) methodName;
                return fieldValue;
            } catch (Exception e) {
                throw new RuntimeException("Failed to access field: " + field.getName(), e);
            }
        };
    }



    /**
     * 根据 Field 创建方法引用。
     *
     * @param field 字段
     * @return 方法引用
     * @throws NoSuchMethodException 如果找不到对应的 getter 方法
     * @throws IllegalAccessException 如果无法访问 getter 方法
     */
    public static <T,R> SFunction<T,R> createMethodReferenceFromField(Object obj,Field field) throws NoSuchMethodException, IllegalAccessException {
        return (T Entity)->{
            try {
                String fieldName = field.getName();
                Field[]  fields = obj.getClass().getDeclaredFields();
                for(Field fieldT:fields){
                    if(fieldName.equals(fieldT.getName())){

                    }
                }
                return (R)fieldName;
            }catch (Exception e){
                throw new RuntimeException("Failed to access field: " + field.getName(), e);
            }
        };
    }


    /**
     * 将首字母大写的字符串。
     *
     * @param str 字符串
     * @return 大写首字母的字符串
     */
    private static String capitalize(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
