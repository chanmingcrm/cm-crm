package com.platform.mesh.core.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.platform.mesh.core.constants.DateConst;
import com.platform.mesh.core.constants.SymbolConst;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

@Configuration
public class JacksonConfig {

    @Bean
    @ConditionalOnMissingBean
    public ObjectMapper objectMapperInit() {
        ObjectMapper objectMapper = new ObjectMapper();
        return getObjectMapper(objectMapper);
    }

    @Bean
    public ObjectMapper objectMapper(ObjectMapper objectMapper) {
        return getObjectMapper(objectMapper);
    }

    private ObjectMapper getObjectMapper(ObjectMapper objectMapper) {
        objectMapper.registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module()).registerModule(new JavaTimeModule());
        // 忽略序列化多余字段不报错
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // 忽略序列化多余字段不报错
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        // 忽略序列化多余字段不报错
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 忽略无法转换的对象
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // PrettyPrinter 格式化输出
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        // 如果值为null时字段key还是否输出:ALWAYS(总是输出),NON_NULL(为null的不输出)
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //序列化地区
        objectMapper.setTimeZone(TimeZone.getTimeZone(SymbolConst.TIME_ZONE));
        //序列化国家
        objectMapper.setLocale(Locale.CHINA);
        //默认时间格式
        objectMapper.setDateFormat(new SimpleDateFormat(DateConst.YYYY_MM_DD_HH_MM_SS));
        return objectMapper;
    }
}
