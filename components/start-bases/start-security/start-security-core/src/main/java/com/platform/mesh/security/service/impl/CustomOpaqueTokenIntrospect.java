package com.platform.mesh.security.service.impl;

import com.platform.mesh.security.domain.bo.LoginUserBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

import java.security.Principal;
import java.util.Map;
import java.util.Objects;

/**
 * @description 资源服务器toke内省处理器
 * @author 蝉鸣
 */
public class CustomOpaqueTokenIntrospect implements OpaqueTokenIntrospector {

	private static final Logger log = LoggerFactory.getLogger(CustomOpaqueTokenIntrospect.class);

	private final OAuth2AuthorizationService authorizationService;

	public CustomOpaqueTokenIntrospect(OAuth2AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}

	/**
	 * 功能描述:
	 * 〈通过token获取信息〉
	 * @param token token
	 * @return 正常返回:{@link OAuth2AuthenticatedPrincipal}
	 * @author 蝉鸣
	 */
	@Override
	public OAuth2AuthenticatedPrincipal introspect(String token) {
		OAuth2Authorization oldAuthorization = authorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);

		if (Objects.isNull(oldAuthorization)) {
			throw new InvalidBearerTokenException("Bearer Token 无效或已过期");
		}

		// 客户端模式默认返回
		if (AuthorizationGrantType.CLIENT_CREDENTIALS.equals(oldAuthorization.getAuthorizationGrantType())) {
			return new ClientCredentialsOAuth2AuthenticatedPrincipal(oldAuthorization.getAttributes(),
					AuthorityUtils.NO_AUTHORITIES, oldAuthorization.getPrincipalName());
		}

		try {
			UsernamePasswordAuthenticationToken principal = (UsernamePasswordAuthenticationToken) Objects
					.requireNonNull(oldAuthorization).getAttributes().get(Principal.class.getName());
			Object tokenPrincipal = principal.getPrincipal();
			return (LoginUserBO) tokenPrincipal;
		}
		catch (Exception ex) {
			log.warn("资源服务器无法恢复 Token 身份: {}", ex.getClass().getSimpleName());
			throw new InvalidBearerTokenException("Bearer Token 身份无效", ex);
		}
	}

}
