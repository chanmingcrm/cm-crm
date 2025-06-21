package com.platform.mesh.feign.configuration;

import com.platform.mesh.feign.interceptor.FeignRequestInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * @description Feign 配置注册
 * @author 蝉鸣
 */
public class FeignConfiguration {

	@Bean
	public RequestInterceptor requestInterceptor() {
		return new FeignRequestInterceptor();
	}

}
