package com.platform.mesh.uaa.biz.auth.support.grant.sms;

import com.platform.mesh.security.constants.GrantTypeConstant;
import com.platform.mesh.security.utils.OAuth2AuthorizationUtils;
import com.platform.mesh.uaa.api.constants.UaaParamsConstant;
import com.platform.mesh.uaa.biz.auth.support.grant.base.OAuth2ResourceOwnerBaseAuthenticationConverter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Set;

/**
 * @description 短信登录转换器
 * @author 蝉鸣
 */
public class OAuth2ResourceOwnerSmsAuthenticationConverter
		extends OAuth2ResourceOwnerBaseAuthenticationConverter<OAuth2ResourceOwnerSmsAuthenticationToken> {

	/**
	 * 功能描述:
	 * 〈是否支持此convert〉
	 * @param grantType 授权类型
	 * @return 正常返回:{@link boolean}
	 * @author 蝉鸣
	 */
	@Override
	public boolean support(String grantType) {
		return GrantTypeConstant.SMS.equals(grantType);
	}

	/**
	 * 功能描述:
	 * 〈构造token〉
	 * @param clientPrincipal clientPrincipal
	 * @param requestedScopes requestedScopes
	 * @param additionalParameters additionalParameters
	 * @return 正常返回:{@link OAuth2ResourceOwnerSmsAuthenticationToken}
	 * @author 蝉鸣
	 */
	@Override
	public OAuth2ResourceOwnerSmsAuthenticationToken buildToken(Authentication clientPrincipal,  Set<String> requestedScopes,
			Map<String,Object> additionalParameters) {
		return new OAuth2ResourceOwnerSmsAuthenticationToken(new AuthorizationGrantType(GrantTypeConstant.SMS),
				clientPrincipal, requestedScopes, additionalParameters);
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
		// PHONE (REQUIRED)
		String phone = parameters.getFirst(UaaParamsConstant.SMS_PARAMETER_NAME);
		if (!StringUtils.hasText(phone) || parameters.get(UaaParamsConstant.SMS_PARAMETER_NAME).size() != 1) {
			OAuth2AuthorizationUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST, UaaParamsConstant.SMS_PARAMETER_NAME,
					OAuth2AuthorizationUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
		}
	}

}
