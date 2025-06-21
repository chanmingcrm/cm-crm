package com.platform.mesh.core.enums.convert;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.enums.base.BaseEnum;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @description 枚举反序列化处理器
 * @param <T> 枚举类
 * @param <V> 枚举值的类型
 * @author 蝉鸣
 */
@Slf4j
public class EnumJsonSerializer<T extends BaseEnum<?, V>, V extends Serializable> extends JsonSerializer<T> {


    /**
     * 功能描述:
     * 〈序列化〉
     * @param t t
     * @param jsonGenerator jsonGenerator
     * @param serializerProvider serializerProvider
     * @author 蝉鸣
     */
    @Override
    public void serialize(T t, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (Objects.isNull(t)) {
            jsonGenerator.writeNull();
            return;
        }
        // 写入原本字段的值
        jsonGenerator.writeObject(t.getValue());

        // 获取字段名
        String currentName = jsonGenerator.getOutputContext().getCurrentName();

        // 获取当前枚举的class
        Class<?> clazz = t.getClass();
        // 获取枚举中所有字段
        Field[] fields = clazz.getDeclaredFields();
        // 去掉静态字段
        List<Field> fieldList = Arrays.stream(fields).filter(f -> !Modifier.isStatic(f.getModifiers())).toList();
        fieldList.forEach(field -> {
            try {
                field.setAccessible(true);
                // 序列化是插入枚举中的字段，规则为原字段名+枚举字段名(首字母大写)
                jsonGenerator.writeObjectField(currentName + upperCaseFirstString(field.getName()), field.get(t));
            } catch (IOException e) {
                log.warn("Can not serializer enum field [{}]. cause: [{}].", field.getName(), e.getMessage());
            } catch (IllegalAccessException e) {
                log.warn("Can not access a member of field [{}] with modifiers “private”. cause: [{}].", field.getName(), e.getMessage());
            }
        });
    }

    /**
     * 功能描述:
     * 〈首字母大写(进行字母的ascii编码前移，效率是最高的)〉
     * @param fieldName 需要转化的字符串
     * @return 正常返回:{@link String} 首字母大写
     * @author 蝉鸣
     */
    private String upperCaseFirstString(String fieldName) {
        char[] chars = fieldName.toCharArray();
        chars[NumberConst.NUM_0] = toUpperCase(chars[NumberConst.NUM_0]);
        return String.valueOf(chars);
    }

    /**
     * 功能描述:
     * 〈字符转成大写〉
     * @param c 需要转化的字符
     * @return 正常返回:{@link char} 转为字母的大写
     * @author 蝉鸣
     */
    private char toUpperCase(char c) {
        if (NumberConst.NUM_97 <= c && c <= NumberConst.NUM_122) {
            c ^= NumberConst.NUM_32;
        }
        return c;
    }

}