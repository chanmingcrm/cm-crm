package com.platform.mesh.resource.configuration;

import com.platform.mesh.security.service.impl.AuthorizationBearerTokenExtractor;
import com.platform.mesh.security.service.impl.ResourceAuthExceptionEntryPoint;
import com.platform.mesh.security.config.AuthIgnoreConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @description 安全配置
 * @author 蝉鸣
 */
@EnableWebSecurity
@EnableMethodSecurity()
public class ResourceServerConfiguration {

	@Autowired
	protected ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint;

	@Autowired
	private AuthIgnoreConfiguration authIgnoreConfiguration;

	@Autowired
	private AuthorizationBearerTokenExtractor authorizationBearerTokenExtractor;

	@Autowired
	private OpaqueTokenIntrospector customOpaqueTokenIntrospector;

	/**
	 * 功能描述:
	 * 〈配置授权，开放部分url，自定义oauth处理〉
	 * @param http http
	 * @return 正常返回:{@link SecurityFilterChain}
	 * @author 蝉鸣
	 */
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// 自定义接口、端点暴露
		http.authorizeHttpRequests(authorizeRequests -> authorizeRequests
				.requestMatchers(authIgnoreConfiguration.getPermitAllRequestMatchers()).permitAll()
								.anyRequest().authenticated())
				.oauth2ResourceServer(
						oauth2 -> oauth2
								.opaqueToken(token -> token.introspector(customOpaqueTokenIntrospector))
								.authenticationEntryPoint(resourceAuthExceptionEntryPoint)
								.bearerTokenResolver(authorizationBearerTokenExtractor))
				.headers(AbstractHttpConfigurer::disable)
				.csrf(AbstractHttpConfigurer::disable)
				.cors(AbstractHttpConfigurer::disable);

		return http.build();
	}

}