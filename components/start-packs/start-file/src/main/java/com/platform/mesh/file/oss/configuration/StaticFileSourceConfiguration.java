package com.platform.mesh.file.oss.configuration;

import cn.hutool.core.text.CharPool;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.file.oss.constant.OssBaseConst;
import com.platform.mesh.file.oss.constant.OssTypeConst;
import com.platform.mesh.file.oss.modules.local.properties.LocalOssProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description 配置本地文件Url访问规则
 * @author 蝉鸣
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(LocalOssProperties.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnProperty(prefix = OssBaseConst.OSS, name = OssTypeConst.LOCAL + CharPool.DOT + StrConst.CONFIG_ENABLE,
		havingValue = StrConst.CONFIG_ENABLE_VALUE)
public class StaticFileSourceConfiguration implements WebMvcConfigurer {

	@Autowired
	private LocalOssProperties localProperties;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler(OssBaseConst.PATTERN_PREVIEW_IMAGES_PATH).addResourceLocations(OssBaseConst.LOCALE_FILE+localProperties.getBasePath());
	}

}