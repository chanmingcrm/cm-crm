package com.platform.mesh.uaa.biz.auth.support.grant.third;

import com.platform.mesh.security.constants.GrantTypeConstant;
import com.platform.mesh.uaa.api.constants.UaaParamsConstant;
import com.platform.mesh.uaa.biz.auth.support.grant.base.OAuth2ResourceOwnerBaseAuthenticationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import java.util.Map;

/**
 * @description 短信登录的核心处理
 * @author 蝉鸣
 */
public class OAuth2ResourceOwnerThirdAuthenticationProvider
		extends OAuth2ResourceOwnerBaseAuthenticationProvider<OAuth2ResourceOwnerThirdAuthenticationToken> {

	private static final Logger log = LoggerFactory.getLogger(OAuth2ResourceOwnerThirdAuthenticationProvider.class);

	/**
	 * 功能描述:
	 * 〈构造函数〉
	 * {@code OAuth2AuthorizationCodeAuthenticationProvider}
	 * @param authenticationManager authenticationManager
	 * @param authorizationService authorizationService
	 * @param tokenGenerator tokenGenerator
	 * @author 蝉鸣
	 */
	public OAuth2ResourceOwnerThirdAuthenticationProvider(AuthenticationManager authenticationManager,
                                                          OAuth2AuthorizationService authorizationService,
                                                          OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
		super(authenticationManager, authorizationService, tokenGenerator);
	}

	/**
	 * 功能描述:
	 * 〈当前provider是否支持此令牌类型〉
	 * @param authentication authentication
	 * @return 正常返回:{@link boolean}
	 * @author 蝉鸣
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		boolean supports = OAuth2ResourceOwnerThirdAuthenticationToken.class.isAssignableFrom(authentication);
		log.debug("supports authentication=" + authentication + " returning " + supports);
		return supports;
	}

	/**
	 * 功能描述:
	 * 〈当前的请求客户端是否支持此模式〉
	 * @param registeredClient registeredClient
	 * @author 蝉鸣
	 */
	@Override
	public void checkClient(RegisteredClient registeredClient) {
		assert registeredClient != null;
		if (!registeredClient.getAuthorizationGrantTypes()
				.contains(new AuthorizationGrantType(GrantTypeConstant.THIRD))) {
			throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
		}
	}

	/**
	 * 功能描述:
	 * 〈构建token〉
	 * @param reqParameters reqParameters
	 * @return 正常返回:{@link UsernamePasswordAuthenticationToken}
	 * @author 蝉鸣
	 */
	@Override
	public UsernamePasswordAuthenticationToken buildToken(Map<String, Object> reqParameters) {
		String uuid = (String) reqParameters.get(UaaParamsConstant.THIRD_PARAMETER_UUID);
		return new UsernamePasswordAuthenticationToken(uuid, null);
	}

}
