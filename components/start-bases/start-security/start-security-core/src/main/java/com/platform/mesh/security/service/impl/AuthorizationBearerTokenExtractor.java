package com.platform.mesh.security.service.impl;

import cn.hutool.core.util.StrUtil;
import com.platform.mesh.security.config.AuthIgnoreConfiguration;
import com.platform.mesh.security.constants.SecurityConstant;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.oauth2.server.resource.BearerTokenErrors;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description 请求令牌的抽取逻辑
 * @author 蝉鸣
 */
public class AuthorizationBearerTokenExtractor implements BearerTokenResolver {

	private static final Pattern AUTHORIZATION_PATTERN = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-:._~+/]+=*)$",
			Pattern.CASE_INSENSITIVE);

	private boolean allowFormEncodedBodyParameter = false;

	private boolean allowUriQueryParameter = false;

	private String bearerTokenHeaderName = HttpHeaders.AUTHORIZATION;

	private final PathMatcher pathMatcher = new AntPathMatcher();

	private final AuthIgnoreConfiguration authIgnoreConfiguration;

	public AuthorizationBearerTokenExtractor(AuthIgnoreConfiguration authIgnoreConfiguration) {
		this.authIgnoreConfiguration = authIgnoreConfiguration;
	}

	/**
	 * 功能描述:
	 * 〈接收token〉
	 * @param request request
	 * @return 正常返回:{@link String}
	 * @author 蝉鸣
	 */
	@Override
	public String resolve(HttpServletRequest request) {
		boolean match = authIgnoreConfiguration.getUrls().stream()
				.anyMatch(url -> pathMatcher.match(url, request.getRequestURI()));

		if (match) {
			return null;
		}

		final String authorizationHeaderToken = resolveFromAuthorizationHeader(request);
		final String parameterToken = isParameterTokenSupportedForRequest(request)
				? resolveFromRequestParameters(request) : null;
		if (authorizationHeaderToken != null) {
			if (parameterToken != null) {
				final BearerTokenError error = BearerTokenErrors
						.invalidRequest("Found multiple bearer tokens in the request");
				throw new OAuth2AuthenticationException(error);
			}
			return authorizationHeaderToken;
		}
		if (parameterToken != null && isParameterTokenEnabledForRequest(request)) {
			return parameterToken;
		}
		return null;
	}

	/**
	 * 功能描述:
	 * 〈从认证请求头中接收token〉
	 * @param request request
	 * @return 正常返回:{@link String}
	 * @author 蝉鸣
	 */
	private String resolveFromAuthorizationHeader(HttpServletRequest request) {
		String authorization = request.getHeader(this.bearerTokenHeaderName);
		if (!StrUtil.startWithIgnoreCase(authorization, SecurityConstant.BEARER)) {
			return null;
		}
		Matcher matcher = AUTHORIZATION_PATTERN.matcher(authorization);
		if (!matcher.matches()) {
			BearerTokenError error = BearerTokenErrors.invalidToken("Bearer token is malformed");
			throw new OAuth2AuthenticationException(error);
		}
		return matcher.group("token");
	}

	/**
	 * 功能描述:
	 * 〈从请求参数中获取token〉
	 * @param request request
	 * @return 正常返回:{@link String}
	 * @author 蝉鸣
	 */
	private static String resolveFromRequestParameters(HttpServletRequest request) {
		String[] values = request.getParameterValues(OAuth2ParameterNames.ACCESS_TOKEN);
		if (values == null || values.length == 0) {
			return null;
		}
		if (values.length == 1) {
			return values[0];
		}
		BearerTokenError error = BearerTokenErrors.invalidRequest("Found multiple bearer tokens in the request");
		throw new OAuth2AuthenticationException(error);
	}

	/**
	 * 功能描述:
	 * 〈是否支持请求方式获取token〉
	 * @param request request
	 * @return 正常返回:{@link boolean}
	 * @author 蝉鸣
	 */
	private boolean isParameterTokenSupportedForRequest(final HttpServletRequest request) {
		return ((HttpMethod.POST.name().equals(request.getMethod())
				&& MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(request.getContentType()))
				|| HttpMethod.GET.name().equals(request.getMethod()));
	}

	/**
	 * 功能描述:
	 * 〈是否支持请求方式获取token〉
	 * @param request request
	 * @return 正常返回:{@link boolean}
	 * @author 蝉鸣
	 */
	private boolean isParameterTokenEnabledForRequest(final HttpServletRequest request) {
		return ((this.allowFormEncodedBodyParameter && HttpMethod.POST.name().equals(request.getMethod())
				&& MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(request.getContentType()))
				|| (this.allowUriQueryParameter && HttpMethod.GET.name().equals(request.getMethod())));
	}

}