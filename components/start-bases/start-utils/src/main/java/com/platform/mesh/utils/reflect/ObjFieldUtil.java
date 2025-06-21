package com.platform.mesh.utils.reflect;

import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.constants.SymbolConst;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @description 文建工具
 * @author 蝉鸣
 */
@Slf4j
public class ObjFieldUtil {
    /**
     * 通过对应实体类getter方法对应的属性名称。
     * 1:需自定义一个函数式注解该注解继承自Function<T,R>注解
     * 2:且必须继承自Serializable注解（作用，必须实例化才能玩“writeReplace”
     */
    @FunctionalInterface
    public interface FieldFunction<T, R> extends Function<T, R>, Serializable {
    }

    /**
     * 字段名注解,声明表字段
     */
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ObjField {
        String value() default SymbolConst.BLANK;
    }

    //默认配置
    static String DEFAULT_START = "is";
    static String DEFAULT_MATCH = "[A-Z]";
    static String DEFAULT_TO_REPLACE = "$0";

    /**
     * 获取实体类的字段名称(实体声明的字段名称)
     */
    public static <T> String getColumnName(FieldFunction<T, ?> fn) {
        return StrUtil.toUnderlineCase(getFieldName(fn, SymbolConst.BLANK));
    }

    /**
     * 获取实体类的字段名称(实体声明的字段名称)
     */
    public static <T> String getFieldName(FieldFunction<T, ?> fn) {
        return getFieldName(fn, SymbolConst.BLANK);
    }

    /**
     * 获取实体类的字段名称
     * @param split 分隔符，多个字母自定义分隔符
     */
    public static <T> String getFieldName(FieldFunction<T, ?> fn, String split) {
        return getFieldName(fn, split, NumberConst.NUM_0);
    }

    /**
     * 获取实体类的字段名称
     * @param split 分隔符，多个字母自定义分隔符
     * @param toType 转换方式，多个字母以大小写方式返回 0.不做转换 1.大写 2.小写
     */
    public static <T> String getFieldName(FieldFunction<T, ?> fn, String split, Integer toType) {
        // 获取SerializedLambda对象
        SerializedLambda serializedLambda = getSerializedLambda(fn);

        // 从lambda信息取出method、field、class等
        String fieldName = parseFieldName(serializedLambda.getImplMethodName());
        Field field;
        try {
            field = Class.forName(serializedLambda.getImplClass().replace(SymbolConst.FORWARD_SLASH, SymbolConst.PERIOD)).getDeclaredField(fieldName);
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        // 从field取出字段名，可以根据实际情况调整
        ObjField tableField = field.getAnnotation(ObjField.class);
        if (tableField != null && ObjectUtil.isNotEmpty(tableField.value())) {
            return tableField.value();
        } else {
            //0.不做转换 1.大写 2.小写
            return switch (toType) {
                case 1 -> fieldName.replaceAll(DEFAULT_MATCH, split + DEFAULT_TO_REPLACE).toUpperCase();
                case 2 -> fieldName.replaceAll(DEFAULT_MATCH, split + DEFAULT_TO_REPLACE).toLowerCase();
                default -> fieldName.replaceAll(DEFAULT_MATCH, split + DEFAULT_TO_REPLACE);
            };
        }

    }

    /**
     * 获取SerializedLambda对象
     */
    private static <T> SerializedLambda getSerializedLambda(FieldFunction<T, ?> fn) {
        //反射对象
        Class<?> clazz = fn.getClass();
        // 从function取出序列化方法
        try {
            //虚拟机会自动给实现Serializable接口的lambda表达式生成 writeReplace()方法
            Method writeReplaceMethod = clazz.getDeclaredMethod("writeReplace");
            //writeReplace是私有方法，需要去掉私有属性
            writeReplaceMethod.setAccessible(Boolean.TRUE);
            //从序列化方法取出序列化的lambda信息
            return (SerializedLambda) writeReplaceMethod.invoke(fn);
        } catch (NoSuchMethodException |IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 去掉方法的“get/is”前缀，首字母小写
     * @param methodName 方法名称
     */
    private static String parseFieldName(String methodName) {
        if (StrUtil.isEmpty(methodName) || methodName.length() < NumberConst.NUM_4) {
            return methodName;
        }
        String fieldName = methodName.startsWith(HttpConst.METHOD_GET.toLowerCase()) ?
                methodName.substring(NumberConst.NUM_3) : methodName.startsWith(DEFAULT_START) ? methodName.substring(NumberConst.NUM_2) : methodName;

        fieldName = fieldName.replaceFirst(StrUtil.toString(fieldName.charAt(NumberConst.NUM_0)), StrUtil.toString(fieldName.charAt(NumberConst.NUM_0)).toLowerCase());
        return fieldName;
    }

    /**
     * 填充class对象字段信息
     * @param entityClass 方法名称
     * @param fieldMap fieldMap
     */
    public static Object fillObjField(Class<?> entityClass, Map<String, Object> fieldMap) {
        Object entity = null;
        try {
            entity = entityClass.getDeclaredConstructor().newInstance();
            for (Map.Entry<String, Object> entry : fieldMap.entrySet()) {
                String fieldName = entry.getKey();
                Object value = entry.getValue();
                Field field = entityClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(entity, value);
            }
            return entity;
        } catch (Exception ignore) {
        }
        return entity;
    }

    /**
     * 获取对象字段值
     * @param objClass objClass
     * @param fieldName fieldName
     */
    public static Object getObjField(Class<?> objClass,String fieldName) {
        try {
            // 根据字段名获取 Field 对象
            Field field = objClass.getDeclaredField(fieldName);
            // 设置访问权限（如果字段是私有的，则需要这一步）
            field.setAccessible(true);
            // 获取字段的值
            Object value = field.get(objClass);
            log.info("The value of the field '{}' is:{} ",fieldName,value);
            return value;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return null;
    }

    /***
     * 功能描述:
     * 〈获取对象所有字段名称〉
     * @return 正常返回:{@link List<String>}
     * @author 蝉鸣
     */
    public static List<String> getObjFieldNames(Class<?> clazz) {
        List<String> fieldNames = CollUtil.newArrayList();
        Class<?> currentClass = clazz;
        while (currentClass != null && currentClass != Object.class) {
            Field[] fields = ReflectUtil.getFields(currentClass);
            for (Field field : fields) {
                fieldNames.add(field.getName());
            }
            currentClass = currentClass.getSuperclass();
        }
        return fieldNames;
    }

    /***
     * 功能描述:
     * 〈默认拷贝忽略项〉
     * @return 正常返回:{@link CopyOptions}
     * @author 蝉鸣
     */
    public static CopyOptions ignoreDefault(){
        CopyOptions options = CopyOptions.create();
        options.setIgnoreProperties(
                StrConst.ID
                ,StrConst.CREATE_TIME
                ,StrUtil.toCamelCase(StrConst.CREATE_TIME)
                ,StrConst.CREATE_USER_ID
                ,StrUtil.toCamelCase(StrConst.CREATE_USER_ID)
                ,StrConst.UPDATE_TIME
                ,StrUtil.toCamelCase(StrConst.UPDATE_TIME)
                ,StrConst.UPDATE_USER_ID
                ,StrUtil.toCamelCase(StrConst.UPDATE_USER_ID));
        return options;
    }

}
