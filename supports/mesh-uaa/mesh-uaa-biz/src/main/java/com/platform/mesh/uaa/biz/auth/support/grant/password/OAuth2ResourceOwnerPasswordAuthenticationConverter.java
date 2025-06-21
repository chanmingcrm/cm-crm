package com.platform.mesh.uaa.biz.auth.support.grant.password;

import com.platform.mesh.security.utils.OAuth2AuthorizationUtils;
import com.platform.mesh.uaa.biz.auth.support.grant.base.OAuth2ResourceOwnerBaseAuthenticationConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.Set;

/**
 * @description 密码认证转换器
 * @author 蝉鸣
 */
public class OAuth2ResourceOwnerPasswordAuthenticationConverter
		extends OAuth2ResourceOwnerBaseAuthenticationConverter<OAuth2ResourceOwnerPasswordAuthenticationToken> {

	/**
	 * 功能描述:
	 * 〈支持密码模式〉
	 * @param grantType 授权类型
	 * @return 正常返回:{@link boolean}
	 * @author 蝉鸣
	 */
	@Override
	public boolean support(String grantType) {
		return AuthorizationGrantType.PASSWORD.getValue().equals(grantType);
	}

	/**
	 * 功能描述:
	 * 〈构建token信息〉
	 * @param clientPrincipal clientPrincipal
	 * @param requestedScopes requestedScopes
	 * @param additionalParameters additionalParameters
	 * @return 正常返回:{@link OAuth2ResourceOwnerPasswordAuthenticationToken}
	 * @author 蝉鸣
	 */
	@Override
	public OAuth2ResourceOwnerPasswordAuthenticationToken buildToken(Authentication clientPrincipal,
																	 Set<String> requestedScopes, Map<String, Object> additionalParameters) {
		return new OAuth2ResourceOwnerPasswordAuthenticationToken(AuthorizationGrantType.PASSWORD, clientPrincipal,
				requestedScopes, additionalParameters);
	}

	/**
	 * 功能描述:
	 * 〈校验扩展参数 密码模式密码必须不为空〉
	 * @param request 参数列表
	 * @author 蝉鸣
	 */
	@Override
	public void checkParams(HttpServletRequest request) {
		MultiValueMap<String, String> parameters = OAuth2AuthorizationUtils.getParameters(request);
		// username (REQUIRED)
		String username = parameters.getFirst(OAuth2ParameterNames.USERNAME);
		if (!StringUtils.hasText(username) || parameters.get(OAuth2ParameterNames.USERNAME).size() != 1) {
			OAuth2AuthorizationUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.USERNAME,
					OAuth2AuthorizationUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
		}

		// password (REQUIRED)
		String password = parameters.getFirst(OAuth2ParameterNames.PASSWORD);
		if (!StringUtils.hasText(password) || parameters.get(OAuth2ParameterNames.PASSWORD).size() != 1) {
			OAuth2AuthorizationUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.PASSWORD,
					OAuth2AuthorizationUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
		}
	}

}
