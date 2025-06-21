package com.platform.mesh.uaa.biz.auth.support.grant.third;

import com.platform.mesh.security.constants.GrantTypeConstant;
import com.platform.mesh.security.utils.OAuth2AuthorizationUtils;
import com.platform.mesh.uaa.biz.auth.support.grant.base.OAuth2ResourceOwnerBaseAuthenticationConverter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.util.MultiValueMap;

import java.util.Map;
import java.util.Set;

/**
 * @description 短信登录转换器
 * @author 蝉鸣
 */
public class OAuth2ResourceOwnerThirdAuthenticationConverter
		extends OAuth2ResourceOwnerBaseAuthenticationConverter<OAuth2ResourceOwnerThirdAuthenticationToken> {

	/**
	 * 功能描述:
	 * 〈是否支持此convert〉
	 * @param grantType 授权类型
	 * @return 正常返回:{@link boolean}
	 * @author 蝉鸣
	 */
	@Override
	public boolean support(String grantType) {
		return GrantTypeConstant.THIRD.equals(grantType);
	}

	/**
	 * 功能描述:
	 * 〈构造token〉
	 * @param clientPrincipal clientPrincipal
	 * @param requestedScopes requestedScopes
	 * @param additionalParameters additionalParameters
	 * @return 正常返回:{@link OAuth2ResourceOwnerThirdAuthenticationToken}
	 * @author 蝉鸣
	 */
	@Override
	public OAuth2ResourceOwnerThirdAuthenticationToken buildToken(Authentication clientPrincipal, Set<String> requestedScopes,
                                                                Map<String,Object> additionalParameters) {
		return new OAuth2ResourceOwnerThirdAuthenticationToken(new AuthorizationGrantType(GrantTypeConstant.THIRD),
				clientPrincipal, requestedScopes, additionalParameters);
	}

	/**
	 * 功能描述:
	 * 〈校验扩展参数 第三方模式〉
	 * @param request 参数列表
	 * @author 蝉鸣
	 */
	@Override
	public void checkParams(HttpServletRequest request) {
		MultiValueMap<String, String> parameters = OAuth2AuthorizationUtils.getParameters(request);
//		// PHONE (REQUIRED)
//		String phone = parameters.getFirst(UaaParamsConstant.SMS_PARAMETER_NAME);
//		if (!StringUtils.hasText(phone) || parameters.get(UaaParamsConstant.SMS_PARAMETER_NAME).size() != 1) {
//			OAuth2AuthorizationUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST, UaaParamsConstant.SMS_PARAMETER_NAME,
//					OAuth2AuthorizationUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
//		}
	}

}
