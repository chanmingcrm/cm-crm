package com.platform.mesh.security.utils;

import cn.hutool.core.map.MapUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.endpoint.PkceParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @description 授权终端工具类
 * @author 蝉鸣
 */
public class OAuth2AuthorizationUtils {

	public final static String ACCESS_TOKEN_REQUEST_ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";

	/**
	 * 功能描述:
	 * 〈获取请求参数〉
	 * @param request request
	 * @return 正常返回:{@link MultiValueMap}
	 * @author 蝉鸣
	 */
	public static MultiValueMap<String, String> getParameters(HttpServletRequest request) {
		Map<String, String[]> parameterMap = request.getParameterMap();
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>(parameterMap.size());
		parameterMap.forEach((key, values) -> {
			if (values.length > 0) {
				for (String value : values) {
					parameters.add(key, value);
				}
			}
		});
		return parameters;
	}

	/**
	 * 功能描述:
	 * 〈请求是否匹配〉
	 * @param request request
	 * @return 正常返回:{@link boolean}
	 * @author 蝉鸣
	 */
	public static boolean matchesPeckTokenRequest(HttpServletRequest request) {
		return AuthorizationGrantType.AUTHORIZATION_CODE.getValue()
				.equals(request.getParameter(OAuth2ParameterNames.GRANT_TYPE))
				&& request.getParameter(OAuth2ParameterNames.CODE) != null
				&& request.getParameter(PkceParameterNames.CODE_VERIFIER) != null;
	}

	/**
	 * 功能描述:
	 * 〈授权异常处理〉
	 * @param errorCode errorCode
	 * @param parameterName parameterName
	 * @param errorUri errorUri
	 * @author 蝉鸣
	 */
	public static void throwError(String errorCode, String parameterName, String errorUri) {
		OAuth2Error error = new OAuth2Error(errorCode, "OAuth 2.0 Parameter: " + parameterName, errorUri);
		throw new OAuth2AuthenticationException(error);
	}

	/**
	 * 功能描述:
	 * 〈格式化输出token 信息〉
	 * @param authentication 用户认证信息
	 * @param claims 扩展信息
	 * @return 正常返回:{@link OAuth2AccessTokenResponse}
	 * @author 蝉鸣
	 */
	public static OAuth2AccessTokenResponse sendAccessTokenResponse(OAuth2Authorization authentication,
			Map<String, Object> claims) {

		OAuth2AccessToken accessToken = authentication.getAccessToken().getToken();
		OAuth2RefreshToken refreshToken = Objects.requireNonNull(authentication.getRefreshToken()).getToken();
		return  sendAccessTokenResponse(accessToken,refreshToken,claims);
	}

	/**
	 * 功能描述:
	 * 〈格式化输出token 信息〉
	 * @param accessToken accessToken
	 * @param refreshToken refreshToken
	 * @param additionalParameters additionalParameters
	 * @return 正常返回:{@link OAuth2AccessTokenResponse}
	 * @author 蝉鸣
	 */
	public static OAuth2AccessTokenResponse sendAccessTokenResponse(OAuth2AccessToken accessToken,
																	OAuth2RefreshToken refreshToken,
																	Map<String, Object> additionalParameters) {

		OAuth2AccessTokenResponse.Builder builder = OAuth2AccessTokenResponse.withToken(accessToken.getTokenValue())
				.tokenType(accessToken.getTokenType()).scopes(accessToken.getScopes());
		if (accessToken.getIssuedAt() != null && accessToken.getExpiresAt() != null) {
			builder.expiresIn(ChronoUnit.SECONDS.between(accessToken.getIssuedAt(), accessToken.getExpiresAt()));
		}
		if (refreshToken != null) {
			builder.refreshToken(refreshToken.getTokenValue());
		}

		if (MapUtil.isNotEmpty(additionalParameters)) {
			builder.additionalParameters(additionalParameters);
		}
		return builder.build();
	}


	/**
	 * 功能描述:
	 * 〈是否是state〉
	 * @param authorization authorization
	 * @return 正常返回:{@link boolean}
	 * @author 蝉鸣
	 */
	public static boolean isState(OAuth2Authorization authorization) {
		return Objects.nonNull(authorization.getAttribute(OAuth2ParameterNames.STATE));
	}

	/**
	 * 功能描述:
	 * 〈是否是state〉
	 * @param authorization authorization
	 * @return 正常返回:{@link boolean}
	 * @author 蝉鸣
	 */
	public static boolean isAuthorizationCode(OAuth2Authorization authorization) {
		OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = authorization
				.getToken(OAuth2AuthorizationCode.class);
		return Objects.nonNull(authorizationCode);
	}

	/**
	 * 功能描述:
	 * 〈是否是AccessToken〉
	 * @param authorization authorization
	 * @return 正常返回:{@link boolean}
	 * @author 蝉鸣
	 */
	public static boolean isAccessToken(OAuth2Authorization authorization) {
		return Objects.nonNull(authorization.getAccessToken());
	}

	/**
	 * 功能描述:
	 * 〈是否是RefreshToken〉
	 * @param authorization authorization
	 * @return 正常返回:{@link boolean}
	 * @author 蝉鸣
	 */
	public static boolean isRefreshToken(OAuth2Authorization authorization) {
		return Objects.nonNull(authorization.getRefreshToken());
	}

	/**
	 * 功能描述:
	 * 〈是否是IdToken〉
	 * @param authorization authorization
	 * @return 正常返回:{@link boolean}
	 * @author 蝉鸣
	 */
	public static boolean isIdToken(OAuth2Authorization authorization) {
		OAuth2Authorization.Token<OidcIdToken> idToken =
				authorization.getToken(OidcIdToken.class);
		return Objects.nonNull(idToken) && Objects.nonNull(idToken.getToken().getTokenValue());
	}

	/**
	 * 功能描述:
	 * 〈是否是DeviceCode〉
	 * @param authorization authorization
	 * @return 正常返回:{@link boolean}
	 * @author 蝉鸣
	 */
	public static boolean isDeviceCode(OAuth2Authorization authorization) {
		OAuth2Authorization.Token<OAuth2DeviceCode> deviceCode =
				authorization.getToken(OAuth2DeviceCode.class);
		return Objects.nonNull(deviceCode) && Objects.nonNull(deviceCode.getToken().getTokenValue());
	}

	/**
	 * 功能描述:
	 * 〈是否是UserCode〉
	 * @param authorization authorization
	 * @return 正常返回:{@link boolean}
	 * @author 蝉鸣
	 */
	public static boolean isUserCode(OAuth2Authorization authorization) {
		OAuth2Authorization.Token<OAuth2UserCode> userCode =
				authorization.getToken(OAuth2UserCode.class);
		return Objects.nonNull(userCode) && Objects.nonNull(userCode.getToken().getTokenValue());
	}


}