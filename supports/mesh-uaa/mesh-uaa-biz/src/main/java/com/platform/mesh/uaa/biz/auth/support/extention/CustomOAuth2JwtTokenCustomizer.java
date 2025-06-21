package com.platform.mesh.uaa.biz.auth.support.extention;

import com.platform.mesh.security.constants.SecurityConstant;
import com.platform.mesh.security.domain.bo.LoginUserBO;
import com.platform.mesh.upms.api.modules.sys.user.domain.bo.SysUserBO;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @description 〈自定义OAuth2TokenCustomizer〉
 * 				{@code id_token} produced by this authorization server.
 * @author 蝉鸣
 */
public final class CustomOAuth2JwtTokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {

	private static final Set<String> ID_TOKEN_CLAIMS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
			IdTokenClaimNames.ISS,
			IdTokenClaimNames.SUB,
			IdTokenClaimNames.AUD,
			IdTokenClaimNames.EXP,
			IdTokenClaimNames.IAT,
			IdTokenClaimNames.AUTH_TIME,
			IdTokenClaimNames.NONCE,
			IdTokenClaimNames.ACR,
			IdTokenClaimNames.AMR,
			IdTokenClaimNames.AZP,
			IdTokenClaimNames.AT_HASH,
			IdTokenClaimNames.C_HASH
	)));

	/**
	 * 功能描述:
	 * 〈自定义JWTToken〉
	 * @param context context
	 * @author 蝉鸣
	 */
	@Override
	public void customize(JwtEncodingContext context) {
		if (OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())) {
			Map<String, Object> thirdPartyClaims = extractClaims(context.getPrincipal());
			context.getClaims().claims(existingClaims -> {
				// Remove conflicting claims set by this authorization server
				existingClaims.keySet().forEach(thirdPartyClaims::remove);

				// Remove standard id_token claims that could cause problems with clients
				ID_TOKEN_CLAIMS.forEach(thirdPartyClaims::remove);

				// Add all other claims directly to id_token
				existingClaims.putAll(thirdPartyClaims);
			});
		}
		AuthorizationGrantType[] types = {AuthorizationGrantType.AUTHORIZATION_CODE, AuthorizationGrantType.CLIENT_CREDENTIALS, AuthorizationGrantType.REFRESH_TOKEN, AuthorizationGrantType.PASSWORD};
		List<AuthorizationGrantType> grantTypes = new ArrayList<>(Arrays.asList(types));
		if (grantTypes.contains(context.getAuthorizationGrantType()) &&
				OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
			Authentication principal = context.getPrincipal();
			if (principal.getPrincipal() instanceof LoginUserBO) {
				// 预防客户端模式下类型转换错误
				LoginUserBO loginUserBO = (LoginUserBO) context.getPrincipal().getPrincipal();
				Set<String> authorities = principal.getAuthorities().stream()
						.map(GrantedAuthority::getAuthority)
						.collect(Collectors.toSet());
				context.getClaims().claim(SecurityConstant.DETAILS_USER, loginUserBO);
				context.getClaims().claim("code", HttpStatus.OK.value());
                // 保存用户ID至token中
//                context.getClaims().id(String.valueOf(sysUser.getUserId()));
//				// 保存用户昵称至Token中
//				context.getClaims().claim(JwtClaimsConstants.USER_NICKNAME, sysUser.getNickName());
//				// 保存用户权限至token中
//				context.getClaims().claim(JwtClaimsConstants.AUTHORITIES, authorities);
			}
		}
	}

	/**
	 * 功能描述:
	 * 〈扩展claims〉
	 * @param principal principal
	 * @return 正常返回:{@link Map<String,Object>}
	 * @author 蝉鸣
	 */
	private Map<String, Object> extractClaims(Authentication principal) {
		Map<String, Object> claims;
		if (principal.getPrincipal() instanceof OidcUser) {
			OidcUser oidcUser = (OidcUser) principal.getPrincipal();
			OidcIdToken idToken = oidcUser.getIdToken();
			claims = idToken.getClaims();
		} else if (principal.getPrincipal() instanceof OAuth2User) {
			OAuth2User oauth2User = (OAuth2User) principal.getPrincipal();
			claims = oauth2User.getAttributes();
		} else {
			claims = Collections.emptyMap();
		}

		return new HashMap<>(claims);
	}

}
