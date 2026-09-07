package com.platform.mesh.web.configuration;

import com.platform.mesh.web.filter.EncodingFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * @description 配置国际化
 * @author 蝉鸣
 */
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class MessageSourceConfiguration implements WebMvcConfigurer {


	@Bean
	public EncodingFilter encodingFilter(){
		return  new EncodingFilter();
	}

	@Bean
	@Primary
	public ReloadableResourceBundleMessageSource securityMessageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.addBasenames("classpath:i18n/errors/messages");
		messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
		messageSource.setDefaultLocale(Locale.CHINA);
		return messageSource;
	}

}