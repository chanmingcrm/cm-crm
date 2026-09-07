package com.platform.mesh.core.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.platform.mesh.core.constants.DateConst;
import com.platform.mesh.core.constants.SymbolConst;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.cfg.DateTimeFeature;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

@Configuration
public class JacksonConfig {

    /**
     * 增强 ObjectMapper
     */
    @Bean
    public JsonMapperBuilderCustomizer jacksonCustomizer() {
        return builder -> {
            // 标准时区
            builder.defaultTimeZone(TimeZone.getTimeZone(SymbolConst.TIME_ZONE));
            // 国家
            builder.defaultLocale(Locale.CHINA);
            //默认时间格式
            builder.defaultDateFormat(new SimpleDateFormat(DateConst.YYYY_MM_DD_HH_MM_SS));
            // 忽略序列化日期
            builder.disable(
                    // 忽略序列化日期
                    DateTimeFeature.WRITE_DATES_AS_TIMESTAMPS,
                    // 忽略序列化日期
                    DateTimeFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE
            );
            // 忽略序列化未知对象不报错
            builder.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            // 忽略反序列化未知字段不报错
            builder.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            // PrettyPrinter 格式化输出
            builder.enable(SerializationFeature.INDENT_OUTPUT);
            // 如果值为null时字段key还是否输出:ALWAYS(总是输出),NON_NULL(为null的不输出)
            builder.changeDefaultPropertyInclusion(incl -> incl.withValueInclusion(JsonInclude.Include.ALWAYS));
            // 注册模型
            builder.addModule(new JavaTimeModule());
        };
    }

}
