package com.platform.mesh.core.config;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.annotation.JacksonStdImpl;
import tools.jackson.databind.ser.jdk.NumberSerializer;

/***
 * 功能描述:
 * 〈 Long 序列化规则 会将超长 long 值转换为 string，解决前端 JavaScript 最大安全整数是 2^53-1 的问题〉
 * @author 蝉鸣
 * @since 2024/9/2 15:46
 */
@JacksonStdImpl
public class NumSerializer extends NumberSerializer {

    private static final long MAX_SAFE_INTEGER = 9007199254740991L;
    private static final long MIN_SAFE_INTEGER = -9007199254740991L;

    public static final NumSerializer INSTANCE = new NumSerializer(Number.class);

    public NumSerializer(Class<? extends Number> rawType) {
        super(rawType);
    }

    @Override
    public void serialize(Number value, JsonGenerator gen, SerializationContext provider) throws JacksonException {
        if (value.longValue() > MIN_SAFE_INTEGER && value.longValue() < MAX_SAFE_INTEGER) {
            super.serialize(value, gen, provider);
        } else {
            // 超出范围 序列化位字符串
            gen.writeString(value.toString());
        }
    }
}
