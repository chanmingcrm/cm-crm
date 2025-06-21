package com.platform.mesh.uaa.biz.auth.support.grant.third;

import com.platform.mesh.uaa.biz.auth.support.grant.base.OAuth2ResourceOwnerBaseAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Map;
import java.util.Set;

/**
 * @description 短信登录token信息
 * @author 蝉鸣
 */
public class OAuth2ResourceOwnerThirdAuthenticationToken extends OAuth2ResourceOwnerBaseAuthenticationToken {

	/**
	 * 功能描述:
	 * 〈构造函数〉
	 * @param authorizationGrantType authorizationGrantType
	 * @param clientPrincipal clientPrincipal
	 * @param scopes scopes
	 * @param additionalParameters additionalParameters
	 * @author 蝉鸣
	 */
	public OAuth2ResourceOwnerThirdAuthenticationToken(AuthorizationGrantType authorizationGrantType,
                                                       Authentication clientPrincipal, Set<String> scopes, Map<String, Object> additionalParameters) {
		super(authorizationGrantType, clientPrincipal, scopes, additionalParameters);
	}

}
