package com.platform.mesh.uaa.biz.auth.support.grant.base;

import com.platform.mesh.security.utils.OAuth2AuthorizationUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description 自定义模式认证转换器
 * @author 蝉鸣
 */
public abstract class OAuth2ResourceOwnerBaseAuthenticationConverter<T extends OAuth2ResourceOwnerBaseAuthenticationToken>
		implements AuthenticationConverter {

	/**
	 * 功能描述:
	 * 〈是否支持此convert〉
	 * @param grantType 授权类型
	 * @return 正常返回:{@link boolean}
	 * @author 蝉鸣
	 */
	public abstract boolean support(String grantType);

	/**
	 * 功能描述:
	 * 〈校验参数〉
	 * @param request 请求
	 * @author 蝉鸣
	 */
	public void checkParams(HttpServletRequest request) {

	}

	/**
	 * 功能描述:
	 * 〈构建具体类型的token(使用具体登录处理器)〉
	 * @param clientPrincipal Authentication
	 * @param requestedScopes Set<String>
	 * @param additionalParameters Map<String, Object>
	 * @return 正常返回:{@link T}
	 * @author 蝉鸣
	 */
	public abstract T buildToken(Authentication clientPrincipal, Set<String> requestedScopes,
			Map<String, Object> additionalParameters);

	/**
	 * 功能描述:
	 * 〈转换信息〉
	 * @param request HttpServletRequest
	 * @return 正常返回:{@link Authentication}
	 * @author 蝉鸣
	 */
	@Override
	public Authentication convert(HttpServletRequest request) {

		// grant_type (REQUIRED)
		String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
		if (!support(grantType)) {
			return null;
		}

		MultiValueMap<String, String> parameters = OAuth2AuthorizationUtils.getParameters(request);
		// scope (OPTIONAL)
		String scope = parameters.getFirst(OAuth2ParameterNames.SCOPE);
		if (StringUtils.hasText(scope) && parameters.get(OAuth2ParameterNames.SCOPE).size() != 1) {
			OAuth2AuthorizationUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.SCOPE,
					OAuth2AuthorizationUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
		}

		Set<String> requestedScopes = null;
		if (StringUtils.hasText(scope)) {
			requestedScopes = new HashSet<>(Arrays.asList(StringUtils.delimitedListToStringArray(scope, " ")));
		}

		// 校验个性化参数
		checkParams(request);

		// 获取当前已经认证的客户端信息
		Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();
		if (clientPrincipal == null) {
			OAuth2AuthorizationUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ErrorCodes.INVALID_CLIENT,
					OAuth2AuthorizationUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
		}

		// 扩展信息
		Map<String, Object> additionalParameters = parameters.entrySet().stream()
				.filter(e -> !e.getKey().equals(OAuth2ParameterNames.GRANT_TYPE)
						&& !e.getKey().equals(OAuth2ParameterNames.SCOPE))
				.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get(0)));

		// 创建token
		return buildToken(clientPrincipal, requestedScopes, additionalParameters);

	}

}
