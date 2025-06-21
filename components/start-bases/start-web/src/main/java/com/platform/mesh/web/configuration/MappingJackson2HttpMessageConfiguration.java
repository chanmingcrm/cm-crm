package com.platform.mesh.web.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/***
 * 功能描述:
 * 〈自定义http Message, 自定义的优先级会高于框架默认的转换器〉
 * @author 蝉鸣
 * @since 2024/9/2 15:31
 */
@Configuration
public class MappingJackson2HttpMessageConfiguration {

    /**
     * 自定义全局 http请求序列化方式
     */
    @Bean
    public HttpMessageConverters httpMessageConverters(ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter httpMessageConverter = new MappingJackson2HttpMessageConverter(objectMapper);
        return new HttpMessageConverters(httpMessageConverter);
    }

}
