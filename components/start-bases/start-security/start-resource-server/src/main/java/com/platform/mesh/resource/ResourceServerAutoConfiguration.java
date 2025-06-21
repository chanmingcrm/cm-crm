package com.platform.mesh.resource;

import com.platform.mesh.security.service.impl.AuthorizationBearerTokenExtractor;
import com.platform.mesh.security.service.impl.CustomOpaqueTokenIntrospect;
import com.platform.mesh.security.config.AuthIgnoreConfiguration;
import com.platform.mesh.security.service.impl.ResourceAuthExceptionEntryPoint;
import com.platform.mesh.security.service.PermissionService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

/**
 * @description 注入Bean配置
 * @author 蝉鸣
 */
@EnableConfigurationProperties(AuthIgnoreConfiguration.class)
public class ResourceServerAutoConfiguration {

	/**
	 * 功能描述:
	 * 〈鉴权具体的实现逻辑〉
	 * @return 正常返回:{@link PermissionService} #rolePermission.xxx
	 * @author 蝉鸣
	 */
	@Bean("rolePermission")
	public PermissionService permissionService() {
		return new PermissionService();
	}

	/**
	 * 功能描述:
	 * 〈请求令牌的抽取逻辑〉
	 * @param urlProperties 对外暴露的接口列表
	 * @return 正常返回:{@link AuthorizationBearerTokenExtractor}
	 * @author 蝉鸣
	 */
	@Bean
	public AuthorizationBearerTokenExtractor authorizationBearerTokenExtractor(AuthIgnoreConfiguration urlProperties) {
		return new AuthorizationBearerTokenExtractor(urlProperties);
	}

	/**
	 * 功能描述:
	 * 〈资源服务器异常处理〉
	 * @param securityMessageSource securityMessageSource
	 * @return 正常返回:{@link ResourceAuthExceptionEntryPoint}
	 * @author 蝉鸣
	 */
	@Bean
	public ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint(MessageSource securityMessageSource) {
		return new ResourceAuthExceptionEntryPoint(securityMessageSource);
	}

	/**
	 * 功能描述:
	 * 〈资源服务器toke内省处理器〉
	 * @param oAuth2AuthorizationService oAuth2AuthorizationService
	 * @return 正常返回:{@link OpaqueTokenIntrospector}
	 * @author 蝉鸣
	 */
	@Bean
	public OpaqueTokenIntrospector opaqueTokenIntrospector(OAuth2AuthorizationService oAuth2AuthorizationService) {
		return new CustomOpaqueTokenIntrospect(oAuth2AuthorizationService);
	}
}