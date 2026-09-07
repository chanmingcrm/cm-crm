package com.platform.mesh.uaa.biz.auth.config;

import cn.hutool.core.collection.CollUtil;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import com.platform.mesh.security.constants.SecurityConstant;
import com.platform.mesh.security.service.impl.AuthorizationBearerTokenExtractor;
import com.platform.mesh.security.service.impl.ResourceAuthExceptionEntryPoint;
import com.platform.mesh.uaa.biz.auth.support.extention.CustomDaoAuthenticationProvider;
import com.platform.mesh.uaa.biz.auth.support.extention.CustomOAuth2JwtTokenCustomizer;
import com.platform.mesh.uaa.biz.auth.support.extention.CustomOAuth2TokenCustomizer;
import com.platform.mesh.uaa.biz.auth.support.extention.FormIdentityLoginConfigurer;
import com.platform.mesh.uaa.biz.auth.support.grant.password.OAuth2ResourceOwnerPasswordAuthenticationConverter;
import com.platform.mesh.uaa.biz.auth.support.grant.password.OAuth2ResourceOwnerPasswordAuthenticationProvider;
import com.platform.mesh.uaa.biz.auth.support.grant.sms.OAuth2ResourceOwnerSmsAuthenticationConverter;
import com.platform.mesh.uaa.biz.auth.support.grant.sms.OAuth2ResourceOwnerSmsAuthenticationProvider;
import com.platform.mesh.uaa.biz.auth.support.grant.third.OAuth2ResourceOwnerThirdAuthenticationConverter;
import com.platform.mesh.uaa.biz.auth.support.grant.third.OAuth2ResourceOwnerThirdAuthenticationProvider;
import com.platform.mesh.uaa.biz.auth.support.handler.AuthenticationFailureEventHandler;
import com.platform.mesh.uaa.biz.auth.support.handler.AuthenticationSuccessEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.*;
import org.springframework.security.oauth2.server.authorization.web.authentication.*;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.DelegatingAuthenticationConverter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @description 授权配置
 * @author 蝉鸣
 */
@Configuration
public class AuthorizationServerConfiguration {

	@Autowired
	private OAuth2AuthorizationService authorizationService;

	@Autowired
	protected ResourceAuthExceptionEntryPoint resourceAuthExceptionEntryPoint;

	@Autowired
	private AuthorizationBearerTokenExtractor authorizationBearerTokenExtractor;

	@Autowired
	private OpaqueTokenIntrospector customOpaqueTokenIntrospector;
	/**
	 * 功能描述:
	 * 〈授权配置〉
	 * @param http HttpSecurity
	 * @return 正常返回:{@link SecurityFilterChain}
	 * @author 蝉鸣
	 */
	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
		OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
		// 个性化认证授权端点
		http
			.with(authorizationServerConfigurer
				//令牌请求的端点
				.tokenEndpoint(tokenEndpoint -> {
									// 注入自定义的授权认证Converter
									tokenEndpoint
										 .accessTokenRequestConverter(accessTokenRequestConverter())
										 // 登录成功处理器
										 .accessTokenResponseHandler(new AuthenticationSuccessEventHandler())
										 // 登录失败处理器
										 .errorResponseHandler(new AuthenticationFailureEventHandler());
												}
							   )
				// 个性化客户端认证
				.clientAuthentication(oAuth2ClientAuthenticationConfigurer -> {
									oAuth2ClientAuthenticationConfigurer
										 // 处理客户端认证异常
										 .errorResponseHandler(new AuthenticationFailureEventHandler());
																			  }
									 )
				// 授权端点
				.authorizationEndpoint(authorizationEndpoint ->
									authorizationEndpoint
										// 授权码端点个性化confirm页面
										.consentPage(SecurityConstant.CUSTOM_CONSENT_PAGE_URI)
									  )
				//设备认证
				.deviceAuthorizationEndpoint(deviceEndpoint->
									deviceEndpoint
										// 处理客户端认证异常
										.errorResponseHandler(new AuthenticationFailureEventHandler())
											)
				//设备验证
				.deviceVerificationEndpoint(deviceEndpoint->
									deviceEndpoint
										// 处理客户端认证异常
										.errorResponseHandler(new AuthenticationFailureEventHandler())
											)


					, Customizer.withDefaults());
		// 授权码登录的登录页个性化
		http.with(new FormIdentityLoginConfigurer(), Customizer.withDefaults());
		// authorizationService的实现
		http
			// redis存储token的实现
			.with(authorizationServerConfigurer.authorizationService(authorizationService), Customizer.withDefaults());
		RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();
		// 拦截 OAuth2 Authorization Server 的相关 endpoint
		List<String> ignoreUrls = SecurityConstant.STATIC_IGNORE_URLS;
		http.authorizeHttpRequests(authorizeRequests -> {
			// 自定义接口、端点暴露
			RequestMatcher[] requestMatchers;
			if (CollUtil.isNotEmpty(ignoreUrls)) {
				List<PathPatternRequestMatcher> matchers = ignoreUrls.stream().map(PathPatternRequestMatcher::pathPattern).toList();
				RequestMatcher[] result = new RequestMatcher[matchers.size()];
				requestMatchers =  matchers.toArray(result);
			} else {
				requestMatchers = new RequestMatcher[]{};
			}
			authorizeRequests
					.requestMatchers(requestMatchers).permitAll()
					.anyRequest().authenticated();;
		});
		// 添加BearerTokenAuthenticationFilter，将认证服务当做一个资源服务，解析请求头中的token
//		http.oauth2ResourceServer((resourceServer) -> resourceServer
//				.jwt(Customizer.withDefaults())
//		);
		http.oauth2ResourceServer(
				oauth2 -> oauth2
						.opaqueToken(token -> token.introspector(customOpaqueTokenIntrospector))
						.authenticationEntryPoint(resourceAuthExceptionEntryPoint)
						.bearerTokenResolver(authorizationBearerTokenExtractor));
		// 配置请求拦截
		http
			// 前后端分离工程需要 禁用csrf-取消csrf防护-参考：https://blog.csdn.net/yjclsx/article/details/80349906
//			.csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
			.csrf(AbstractHttpConfigurer::disable)
			.cors(AbstractHttpConfigurer::disable);
		// build() 方法会让以上所有的配置生效
		DefaultSecurityFilterChain securityFilterChain = http.build();
		// 注入自定义授权模式实现
		this.addCustomOAuth2GrantAuthenticationProvider(http);
		return securityFilterChain;
	}

	/**
	 * 功能描述:
	 * 〈令牌生成规则实现〉
	 * 	client:username:uuid
	 * @return 正常返回:{@link OAuth2TokenGenerator}
	 * @author 蝉鸣
	 */
	@Bean
	public OAuth2TokenGenerator<OAuth2Token> oAuth2TokenGenerator() {

		// jwt生成器
		JwtGenerator jwtGenerator = new JwtGenerator(jwtEncoder());
		// JWT自定义增强处理
		jwtGenerator.setJwtCustomizer(new CustomOAuth2JwtTokenCustomizer());
		// AccessToken生成器
		OAuth2AccessTokenGenerator accessTokenGenerator = new OAuth2AccessTokenGenerator();
		// 自定义AccessToken生成器
		//CustomOAuth2AccessTokenGenerator accessTokenGenerator = new CustomOAuth2AccessTokenGenerator();
		// AccessToken自定义增强处理
		accessTokenGenerator.setAccessTokenCustomizer(new CustomOAuth2TokenCustomizer());
		// RefreshToken生成器
		OAuth2RefreshTokenGenerator refreshTokenGenerator = new OAuth2RefreshTokenGenerator();

		return new DelegatingOAuth2TokenGenerator(jwtGenerator, accessTokenGenerator, refreshTokenGenerator);
	}

	/**
	 * 功能描述:
	 * 〈JWKSource〉
	 * @return 正常返回:{@link JWKSource<SecurityContext>}
	 * @author 蝉鸣
	 */
	@Bean
	public JWKSource<SecurityContext> jwkSource() {
		KeyPair keyPair = generateRsaKey();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAKey rsaKey = new RSAKey.Builder(publicKey)
				.privateKey(privateKey)
				.keyID(UUID.randomUUID().toString())
				.build();
		JWKSet jwkSet = new JWKSet(rsaKey);
		return new ImmutableJWKSet<>(jwkSet);
	}

	/**
	 * 功能描述:
	 * 〈jwt 解密〉
	 * @return 正常返回:{@link JwtDecoder}
	 * @author 蝉鸣
	 */
	@Bean
	public JwtDecoder jwtDecoder() {
		ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
		JWSVerificationKeySelector<SecurityContext> keySelector = new JWSVerificationKeySelector<>(JWSAlgorithm.RS256, jwkSource());
		jwtProcessor.setJWSKeySelector(keySelector);
		jwtProcessor.setJWTClaimsSetVerifier((claims, context) -> {
		});
		return new NimbusJwtDecoder(jwtProcessor);
//		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource());
	}

	/**
	 * 功能描述:
	 * 〈jwt 加密〉
	 * @return 正常返回:{@link JwtEncoder}
	 * @author 蝉鸣
	 */
	@Bean
	public JwtEncoder jwtEncoder() {
		return new NimbusJwtEncoder(jwkSource());
	}

	/**
	 * 功能描述:
	 * 〈Spring Authorization Server Setting配置〉
	 * @return 正常返回:{@link AuthorizationServerSettings}
	 * @author 蝉鸣
	 */
	@Bean
	public AuthorizationServerSettings authorizationServerSettings() {
		// issuer 由当前对外请求地址解析，禁止复用项目 license 等无关常量。
		return AuthorizationServerSettings.builder().build();
	}

	/**
	 * 功能描述:
	 * 〈Token 请求转换器〉
	 * @return 正常返回:{@link AuthenticationConverter}
	 * @author 蝉鸣
	 */
	private AuthenticationConverter accessTokenRequestConverter() {
		return new DelegatingAuthenticationConverter(Arrays.asList(
				// 密码模式
				new OAuth2ResourceOwnerPasswordAuthenticationConverter(),
				// 手机模式
				new OAuth2ResourceOwnerSmsAuthenticationConverter(),
				// 第三方模式
				new OAuth2ResourceOwnerThirdAuthenticationConverter(),
				// 刷新模式
				new OAuth2RefreshTokenAuthenticationConverter(),
				// 授权码模式
				new OAuth2AuthorizationCodeAuthenticationConverter(),
				// 客户端模式
				new OAuth2ClientCredentialsAuthenticationConverter(),
				// 设备模式
				new OAuth2DeviceCodeAuthenticationConverter(),
				new OAuth2AuthorizationCodeRequestAuthenticationConverter())

		);
	}

	/**
	 * 功能描述:
	 * 〈注入授权模式实现提供方〉
	 * 	 1. 密码模式
	 * 	 2. 短信登录
	 * @param http http
	 * @author 蝉鸣
	 */
	private void addCustomOAuth2GrantAuthenticationProvider(HttpSecurity http) {
		AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
		OAuth2AuthorizationService authorizationService = http.getSharedObject(OAuth2AuthorizationService.class);

		OAuth2ResourceOwnerPasswordAuthenticationProvider resourceOwnerPasswordAuthenticationProvider = new OAuth2ResourceOwnerPasswordAuthenticationProvider(
				authenticationManager, authorizationService, oAuth2TokenGenerator());

		OAuth2ResourceOwnerSmsAuthenticationProvider resourceOwnerSmsAuthenticationProvider = new OAuth2ResourceOwnerSmsAuthenticationProvider(
				authenticationManager, authorizationService, oAuth2TokenGenerator());

		OAuth2ResourceOwnerThirdAuthenticationProvider resourceOwnerThirdAuthenticationProvider = new OAuth2ResourceOwnerThirdAuthenticationProvider(
				authenticationManager, authorizationService, oAuth2TokenGenerator());

		// 处理 UsernamePasswordAuthenticationToken
		http.authenticationProvider(new CustomDaoAuthenticationProvider());
		// 处理 OAuth2ResourceOwnerPasswordAuthenticationToken
		http.authenticationProvider(resourceOwnerPasswordAuthenticationProvider);
		// 处理 OAuth2ResourceOwnerSmsAuthenticationToken
		http.authenticationProvider(resourceOwnerSmsAuthenticationProvider);
		// 处理 OAuth2ResourceOwnerThirdAuthenticationToken
		http.authenticationProvider(resourceOwnerThirdAuthenticationProvider);
	}

	/**
	 * 功能描述:
	 * 〈KeyPair 生成器〉
	 * @return 正常返回:{@link KeyPair}
	 * @author 蝉鸣
	 */
	private static KeyPair generateRsaKey() {
		KeyPair keyPair;
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			keyPair = keyPairGenerator.generateKeyPair();
		}
		catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
		return keyPair;
	}



}
