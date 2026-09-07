package com.platform.mesh.uaa.biz.auth.support.grant.base;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.platform.mesh.core.exception.BaseException;
import com.platform.mesh.security.constants.GrantTypeConstant;
import com.platform.mesh.security.domain.bo.LoginUserBO;
import com.platform.mesh.security.utils.ScopeException;
import com.platform.mesh.uaa.biz.auth.exception.AuthExceptionEnum;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;

import java.security.Principal;
import java.time.Instant;
import java.util.*;
import java.util.function.Supplier;

/**
 * @description 处理自定义授权登录验证 注意：目前已经实现UserDetailsService的
 * @author 蝉鸣
 */
public abstract class OAuth2ResourceOwnerBaseAuthenticationProvider<T extends OAuth2ResourceOwnerBaseAuthenticationToken>
		implements AuthenticationProvider {

	private static final Logger log = LoggerFactory.getLogger(OAuth2ResourceOwnerBaseAuthenticationProvider.class);

	private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-4.1.2.1";

	@Resource
	private OAuth2AuthorizationService authorizationService;

	@Resource
	private OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

	@Resource
	private AuthenticationManager authenticationManager;

	@Resource
	private MessageSourceAccessor messages;

	@Deprecated
	private Supplier<String> refreshTokenGenerator;

	/**
	 * 功能描述:
	 * 〈构造入参〉
	 * @param authenticationManager authenticationManager
	 * @param authorizationService authorizationService
	 * @param tokenGenerator tokenGenerator
	 * @author 蝉鸣
	 */
	public OAuth2ResourceOwnerBaseAuthenticationProvider(AuthenticationManager authenticationManager,
			OAuth2AuthorizationService authorizationService,
			OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
		Assert.notNull(authorizationService, "authorizationService cannot be null");
		Assert.notNull(tokenGenerator, "tokenGenerator cannot be null");
		this.authenticationManager = authenticationManager;
		this.authorizationService = authorizationService;
		this.tokenGenerator = tokenGenerator;

		// 国际化配置
		this.messages = new MessageSourceAccessor(SpringUtil.getBean("securityMessageSource"), Locale.CHINA);
	}

	@Deprecated
	public void setRefreshTokenGenerator(Supplier<String> refreshTokenGenerator) {
		Assert.notNull(refreshTokenGenerator, "refreshTokenGenerator cannot be null");
		this.refreshTokenGenerator = refreshTokenGenerator;
	}

	/**
	 * 功能描述:
	 * 〈构造入参〉
	 * @param reqParameters 构建参数
	 * @return 正常返回:{@link UsernamePasswordAuthenticationToken}
	 * @author 蝉鸣
	 */
	public abstract UsernamePasswordAuthenticationToken buildToken(Map<String, Object> reqParameters);

	/**
	 * 功能描述:
	 * 〈当前provider是否支持此令牌类型〉
	 * @param authentication Class<?>
	 * @return 正常返回:{@link boolean}
	 * @author 蝉鸣
	 */
	@Override
	public abstract boolean supports(Class<?> authentication);

	/**
	 * 功能描述:
	 * 〈当前的请求客户端是否支持此模式〉
	 * @param registeredClient registeredClient
	 * @author 蝉鸣
	 */
	public abstract void checkClient(RegisteredClient registeredClient);

	/**
	 * 功能描述:
	 * 〈必须重写此方法，以此校验你的登录〉
	 * Performs authentication with the same contract as {@link AuthenticationManager#authenticate(Authentication)} .
	 * @param authentication authentication
	 * @return 正常返回:{@link Authentication}
	 * @throws AuthenticationException if authentication fails.
	 * @author 蝉鸣
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws BaseException {
		// ----- resouceOwnerBaseAuthenticationScopes -----
		T resouceOwnerBaseAuthenticationScopes = (T) authentication;
		// ----- clientPrincipal -----
		OAuth2ClientAuthenticationToken clientPrincipal = getAuthenticatedClientElseThrowInvalidClient(
				resouceOwnerBaseAuthenticationScopes);
		// ----- registeredClient -----
		RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();
		checkClient(registeredClient);
		// ----- authorizedScopes -----
		Set<String> authorizedScopes = getAuthorizedScopes(resouceOwnerBaseAuthenticationScopes,registeredClient);
		// ----- reqParameters -----
		Map<String, Object> reqParameters = resouceOwnerBaseAuthenticationScopes.getAdditionalParameters();
		try {
			// ----- usernamePasswordAuthenticationToken -----
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = buildToken(reqParameters);

			log.debug("got usernamePasswordAuthenticationToken=" + usernamePasswordAuthenticationToken);

			// 会自动调用UserDetailsServiceImpl.loadUserByUsername()
			Authentication usernamePasswordAuthentication = authenticationManager
					.authenticate(usernamePasswordAuthenticationToken);
			// ----- tokenContextBuilder -----
			DefaultOAuth2TokenContext.Builder tokenContextBuilder = getTokenContextBuilder(
					registeredClient,
					usernamePasswordAuthentication,
					authorizedScopes,
					resouceOwnerBaseAuthenticationScopes);
			// ----- authorizationBuilder -----
			OAuth2Authorization.Builder authorizationBuilder = getAuthorizationBuilder(registeredClient,
					usernamePasswordAuthentication,
					authorizedScopes);
			// ----- Access token -----
			OAuth2AccessToken accessToken = getAccessToken(
					tokenContextBuilder,
					authorizationBuilder,
					usernamePasswordAuthentication,
					authorizedScopes);
			// ----- Refresh token -----
			OAuth2RefreshToken refreshToken =getRefreshToken(
					tokenContextBuilder,
					authorizationBuilder,
					registeredClient,
					clientPrincipal
					);

			OAuth2Authorization authorization = authorizationBuilder.build();
			// ----- 保存授权信息 -----
			this.authorizationService.save(authorization);

			return new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken,
					refreshToken, Objects.requireNonNull(authorization.getAccessToken().getClaims()));

		}
		catch (Exception ex) {
			log.error("problem in authenticate", ex);
			if (ex instanceof BaseException) {
				throw ex;
			}else{
				throw oAuth2AuthenticationException(authentication, (AuthenticationException) ex);
			}
		}

	}

	/**
	 * 功能描述:
	 * 〈〉
	 * @param authentication authentication
	 * @return 正常返回:{@link OAuth2ClientAuthenticationToken}
	 * @author 蝉鸣
	 */
	private OAuth2ClientAuthenticationToken getAuthenticatedClientElseThrowInvalidClient(
			Authentication authentication) {

		OAuth2ClientAuthenticationToken clientPrincipal = null;

		if (OAuth2ClientAuthenticationToken.class.isAssignableFrom(authentication.getPrincipal().getClass())) {
			clientPrincipal = (OAuth2ClientAuthenticationToken) authentication.getPrincipal();
		}

		if (clientPrincipal != null && clientPrincipal.isAuthenticated()) {
			return clientPrincipal;
		}

		throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
	}

	/**
	 * 功能描述:
	 * 〈获取scopes〉
	 * @param resouceOwnerBaseAuthenticationScopes resouceOwnerBaseAuthenticationScopes
	 * @param registeredClient registeredClient
	 * @return 正常返回:{@link Set<String>}
	 * @author 蝉鸣
	 */
	private Set<String> getAuthorizedScopes(T resouceOwnerBaseAuthenticationScopes,RegisteredClient registeredClient){
		Set<String> authorizedScopes;
		// Default to configured scopes
		if (CollUtil.isNotEmpty(resouceOwnerBaseAuthenticationScopes.getScopes())) {
			for (String requestedScope : resouceOwnerBaseAuthenticationScopes.getScopes()) {
				if (!registeredClient.getScopes().contains(requestedScope)) {
					throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_SCOPE);
				}
			}
			authorizedScopes = new LinkedHashSet<>(resouceOwnerBaseAuthenticationScopes.getScopes());
		}
		else {
			throw new ScopeException(AuthExceptionEnum.AUTH_SCOPE_IS_EMPTY.name());
		}
		return authorizedScopes;
	}

	/**
	 * 功能描述:
	 * 〈获取TokenContext〉
	 * @param registeredClient registeredClient
	 * @param authenticate authenticate
	 * @param authorizedScopes authorizedScopes
	 * @param resourceOwnerBaseAuthenticationScopes resourceOwnerBaseAuthenticationScopes
	 * @return 正常返回:{@link DefaultOAuth2TokenContext.Builder}
	 * @author 蝉鸣
	 */
	private DefaultOAuth2TokenContext.Builder getTokenContextBuilder(RegisteredClient registeredClient,
																	 Authentication authenticate,
																	 Set<String> authorizedScopes,
																	 T resourceOwnerBaseAuthenticationScopes){
		DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
				.registeredClient(registeredClient)
				.principal(authenticate)
				.authorizationServerContext(AuthorizationServerContextHolder.getContext())
				.authorizedScopes(authorizedScopes)
				.authorizationGrantType(new AuthorizationGrantType(GrantTypeConstant.PASSWORD))
				.authorizationGrant(resourceOwnerBaseAuthenticationScopes);
		return tokenContextBuilder;
	}

	/**
	 * 功能描述:
	 * 〈获取授权对象〉
	 * @param registeredClient registeredClient
	 * @param authenticate authenticate
	 * @param authorizedScopes authorizedScopes
	 * @return 正常返回:{@link OAuth2Authorization.Builder}
	 * @author 蝉鸣
	 */
	private OAuth2Authorization.Builder getAuthorizationBuilder(RegisteredClient registeredClient,
																Authentication authenticate,
																Set<String> authorizedScopes){
		LoginUserBO principal = (LoginUserBO)authenticate.getPrincipal();
		OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization
				.withRegisteredClient(registeredClient).principalName(principal.getUserId().toString())
				.authorizationGrantType(new AuthorizationGrantType(GrantTypeConstant.PASSWORD))
				.authorizedScopes(authorizedScopes);
		return authorizationBuilder;
	}

	/**
	 * 功能描述:
	 * 〈获取accessToken〉
	 * @param tokenContextBuilder tokenContextBuilder
	 * @param authorizationBuilder authorizationBuilder
	 * @param authenticate authenticate
	 * @param authorizedScopes authorizedScopes
	 * @return 正常返回:{@link OAuth2AccessToken}
	 * @author 蝉鸣
	 */
	private OAuth2AccessToken getAccessToken(
				DefaultOAuth2TokenContext.Builder tokenContextBuilder,
				OAuth2Authorization.Builder authorizationBuilder,
				Authentication authenticate,
				Set<String> authorizedScopes
	) {
		OAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.ACCESS_TOKEN).build();
		OAuth2Token generatedAccessToken = this.tokenGenerator.generate(tokenContext);
		if (generatedAccessToken == null) {
			OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
					"The token generator failed to generate the access token.", ERROR_URI);
			throw new OAuth2AuthenticationException(error);
		}
		OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
				generatedAccessToken.getTokenValue(), generatedAccessToken.getIssuedAt(),
				generatedAccessToken.getExpiresAt(), tokenContext.getAuthorizedScopes());
		long nextId = IdUtil.getSnowflake().nextId();
		if (generatedAccessToken instanceof ClaimAccessor) {
			authorizationBuilder.id(Long.toString(nextId))
					.token(accessToken,
							(metadata) -> metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME,
									((ClaimAccessor) generatedAccessToken).getClaims()))
					// 0.4.0 新增的方法
					.authorizedScopes(authorizedScopes)
					.attribute(Principal.class.getName(), authenticate);
		}
		else {
			authorizationBuilder.id(Long.toString(nextId)).accessToken(accessToken);
		}
		return accessToken;
	}

	/**
	 * 功能描述:
	 * 〈获取refreshToken〉
	 * @param tokenContextBuilder tokenContextBuilder
	 * @param authorizationBuilder authorizationBuilder
	 * @param registeredClient registeredClient
	 * @param clientPrincipal clientPrincipal
	 * @return 正常返回:{@link OAuth2RefreshToken}
	 * @author 蝉鸣
	 */
	private OAuth2RefreshToken getRefreshToken(
				DefaultOAuth2TokenContext.Builder tokenContextBuilder,
				OAuth2Authorization.Builder authorizationBuilder,
				RegisteredClient registeredClient,
				OAuth2ClientAuthenticationToken clientPrincipal

	) {
		OAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build();
		OAuth2RefreshToken refreshToken = null;
		if (registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN) &&
				//
				!clientPrincipal.getClientAuthenticationMethod().equals(ClientAuthenticationMethod.NONE)) {

			if (this.refreshTokenGenerator != null) {
				Instant issuedAt = Instant.now();
				Instant expiresAt = issuedAt.plus(registeredClient.getTokenSettings().getRefreshTokenTimeToLive());
				refreshToken = new OAuth2RefreshToken(this.refreshTokenGenerator.get(), issuedAt, expiresAt);
			}
			else {
				tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build();
				OAuth2Token generatedRefreshToken = this.tokenGenerator.generate(tokenContext);
				if (!(generatedRefreshToken instanceof OAuth2RefreshToken)) {
					OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
							"The token generator failed to generate the refresh token.", ERROR_URI);
					throw new OAuth2AuthenticationException(error);
				}
				refreshToken = (OAuth2RefreshToken) generatedRefreshToken;
			}
			authorizationBuilder.refreshToken(refreshToken);
		}
		return refreshToken;
	}


	/**
	 * 功能描述:
	 * 〈登录异常转换为oauth2异常〉
	 * @param authentication authentication
	 * @param authenticationException authenticationException
	 * @return 正常返回:{@link OAuth2AuthenticationException}
	 * @author 蝉鸣
	 */
	private BaseException oAuth2AuthenticationException(Authentication authentication,
                                                        AuthenticationException authenticationException) {
		if (authenticationException instanceof UsernameNotFoundException) {
            throw AuthExceptionEnum.USER_NO_EXIST.getBaseException();
		}
		if (authenticationException instanceof BadCredentialsException) {
            throw AuthExceptionEnum.AUTH_BAD_CREDENTIALS.getBaseException();
		}
		if (authenticationException instanceof LockedException) {
            throw AuthExceptionEnum.USER_LOCKED.getBaseException();
		}
		if (authenticationException instanceof DisabledException) {
            throw AuthExceptionEnum.USER_DISABLE.getBaseException();
		}
		if (authenticationException instanceof AccountExpiredException) {
            throw AuthExceptionEnum.USER_EXPIRED.getBaseException();
		}
		if (authenticationException instanceof CredentialsExpiredException) {
            throw AuthExceptionEnum.AUTH_CREDENTIALS_EXPIRED.getBaseException();
		}
		if (authenticationException instanceof ScopeException) {
            throw AuthExceptionEnum.AUTH_SCOPE_IS_EMPTY.getBaseException();
		}
        throw AuthExceptionEnum.AUTH_UN_KNOW_LOGIN_ERROR.getBaseException();
	}


}
