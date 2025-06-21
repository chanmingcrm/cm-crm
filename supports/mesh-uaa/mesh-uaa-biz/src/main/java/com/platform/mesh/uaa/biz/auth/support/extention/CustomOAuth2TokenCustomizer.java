package com.platform.mesh.uaa.biz.auth.support.extention;

import com.platform.mesh.security.constants.GrantTypeConstant;
import com.platform.mesh.security.constants.SecurityConstant;
import com.platform.mesh.security.domain.bo.LoginUserBO;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsSet;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

/**
 * @description token 输出增强
 * @author 蝉鸣
 */
public class CustomOAuth2TokenCustomizer implements OAuth2TokenCustomizer<OAuth2TokenClaimsContext> {

	/**
	 * 功能描述:
	 * 〈OAuth2AccessTokenGenerator token 自定义需求〉
	 * @param context context
	 * @author 蝉鸣
	 */
	@Override
	public void customize(OAuth2TokenClaimsContext context) {
		OAuth2TokenClaimsSet.Builder claims = context.getClaims();
		claims.claim(SecurityConstant.DETAILS_LICENSE, SecurityConstant.PROJECT_LICENSE);
		String clientId = context.getAuthorizationGrant().getName();
		claims.claim(SecurityConstant.CLIENT_ID, clientId);
		// 客户端模式不返回具体用户信息
		if (GrantTypeConstant.CLIENT_CREDENTIALS.equals(context.getAuthorizationGrantType().getValue())) {
			return;
		}

		LoginUserBO loginUserBO = (LoginUserBO) context.getPrincipal().getPrincipal();
		claims.claim(SecurityConstant.DETAILS_USER, loginUserBO);
		claims.claim("code", HttpStatus.OK.value());
	}

}
