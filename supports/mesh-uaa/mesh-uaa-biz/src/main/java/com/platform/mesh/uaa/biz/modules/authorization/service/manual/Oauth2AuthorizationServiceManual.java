package com.platform.mesh.uaa.biz.modules.authorization.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.security.utils.OAuth2AuthorizationUtils;
import com.platform.mesh.uaa.biz.modules.authorization.domain.po.Oauth2Authorization;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2DeviceCode;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2UserCode;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Optional;

/**
 * @description 授权处理
 * @author 蝉鸣
 */
@Service
public class Oauth2AuthorizationServiceManual {


	public Oauth2Authorization authorizationToPO(OAuth2Authorization oAuth2Authorization) {

		Oauth2Authorization oauth2Authorization = BeanUtil.copyProperties(oAuth2Authorization, Oauth2Authorization.class);
		oauth2Authorization.setAuthorizationGrantType(oAuth2Authorization.getAuthorizationGrantType().getValue());
		oauth2Authorization.setAuthorizedScopes(JSONUtil.toJsonStr(oAuth2Authorization.getAuthorizedScopes()));
		oauth2Authorization.setAttributes(JSONUtil.toJsonStr(oAuth2Authorization.getAttributes()));
		//保存state
		if (OAuth2AuthorizationUtils.isState(oAuth2Authorization)) {
			String state = oAuth2Authorization.getAttribute(OAuth2ParameterNames.STATE);
			oauth2Authorization.setState(state);
		}
		//保存AuthorizationCode
		if (OAuth2AuthorizationUtils.isAuthorizationCode(oAuth2Authorization)) {
			Optional<OAuth2Authorization.Token<OAuth2AuthorizationCode>> authorizationCodeTokenOptional = Optional.ofNullable(oAuth2Authorization.getToken(OAuth2AuthorizationCode.class));
			if(authorizationCodeTokenOptional.isPresent()){
				OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCodeToken = authorizationCodeTokenOptional.get();
				OAuth2AuthorizationCode token = authorizationCodeToken.getToken();
				oauth2Authorization.setAuthorizationCodeIssuedAt(LocalDateTime.ofInstant(Objects.requireNonNull(token.getIssuedAt()), ZoneId.systemDefault()));
				oauth2Authorization.setAuthorizationCodeExpiresAt(LocalDateTime.ofInstant(Objects.requireNonNull(token.getExpiresAt()), ZoneId.systemDefault()));
				oauth2Authorization.setAuthorizationCodeValue(token.getTokenValue());
				oauth2Authorization.setAuthorizationCodeMetadata(JSONUtil.toJsonStr(authorizationCodeToken.getMetadata()));
			}
		}
		//保存AccessToken
		if (OAuth2AuthorizationUtils.isAccessToken(oAuth2Authorization)) {
			Optional<OAuth2Authorization.Token<OAuth2AccessToken>> accessTokenOptional = Optional.ofNullable(oAuth2Authorization.getToken(OAuth2AccessToken.class));
			if(accessTokenOptional.isPresent()){
				OAuth2Authorization.Token<OAuth2AccessToken> oAuth2AccessToken = accessTokenOptional.get();
				OAuth2AccessToken token = oAuth2AccessToken.getToken();
				oauth2Authorization.setAccessTokenIssuedAt(LocalDateTime.ofInstant(Objects.requireNonNull(token.getIssuedAt()), ZoneId.systemDefault()));
				oauth2Authorization.setAccessTokenExpiresAt(LocalDateTime.ofInstant(Objects.requireNonNull(token.getExpiresAt()), ZoneId.systemDefault()));
				oauth2Authorization.setAccessTokenValue(token.getTokenValue());
				oauth2Authorization.setAccessTokenMetadata(JSONUtil.toJsonStr(oAuth2AccessToken.getMetadata()));
			}
		}
		//保存RefreshToken
		if (OAuth2AuthorizationUtils.isRefreshToken(oAuth2Authorization)) {
			Optional<OAuth2Authorization.Token<OAuth2RefreshToken>> refreshTokenOptional = Optional.ofNullable(oAuth2Authorization.getToken(OAuth2RefreshToken.class));
			if(refreshTokenOptional.isPresent()){
				OAuth2Authorization.Token<OAuth2RefreshToken> refreshToken = refreshTokenOptional.get();
				OAuth2RefreshToken token = refreshToken.getToken();
				oauth2Authorization.setRefreshTokenIssuedAt(LocalDateTime.ofInstant(Objects.requireNonNull(token.getIssuedAt()), ZoneId.systemDefault()));
				oauth2Authorization.setRefreshTokenExpiresAt(LocalDateTime.ofInstant(Objects.requireNonNull(token.getExpiresAt()), ZoneId.systemDefault()));
				oauth2Authorization.setRefreshTokenValue(token.getTokenValue());
				oauth2Authorization.setRefreshTokenMetadata(JSONUtil.toJsonStr(refreshToken.getMetadata()));
			}
		}
		//保存IdToken
		if (OAuth2AuthorizationUtils.isIdToken(oAuth2Authorization)) {
			Optional<OAuth2Authorization.Token<OidcIdToken>> oidcIdTokenOptional = Optional.ofNullable(oAuth2Authorization.getToken(OidcIdToken.class));
			if(oidcIdTokenOptional.isPresent()){
				OAuth2Authorization.Token<OidcIdToken> oidcIdToken = oidcIdTokenOptional.get();
				OidcIdToken token = oidcIdToken.getToken();
				oauth2Authorization.setOidcIdTokenIssuedAt(LocalDateTime.ofInstant(Objects.requireNonNull(token.getIssuedAt()), ZoneId.systemDefault()));
				oauth2Authorization.setOidcIdTokenExpiresAt(LocalDateTime.ofInstant(Objects.requireNonNull(token.getExpiresAt()), ZoneId.systemDefault()));
				oauth2Authorization.setOidcIdTokenValue(token.getTokenValue());
				oauth2Authorization.setOidcIdTokenMetadata(JSONUtil.toJsonStr(oidcIdToken.getMetadata()));
			}
		}
		//保存DeviceCode
		if (OAuth2AuthorizationUtils.isDeviceCode(oAuth2Authorization)) {
			Optional<OAuth2Authorization.Token<OAuth2DeviceCode>> deviceCodeOptional = Optional.ofNullable(oAuth2Authorization.getToken(OAuth2DeviceCode.class));
			if(deviceCodeOptional.isPresent()){
				OAuth2Authorization.Token<OAuth2DeviceCode> deviceCode = deviceCodeOptional.get();
				OAuth2DeviceCode token = deviceCode.getToken();
				oauth2Authorization.setDeviceCodeIssuedAt(LocalDateTime.ofInstant(Objects.requireNonNull(token.getIssuedAt()), ZoneId.systemDefault()));
				oauth2Authorization.setDeviceCodeExpiresAt(LocalDateTime.ofInstant(Objects.requireNonNull(token.getExpiresAt()), ZoneId.systemDefault()));
				oauth2Authorization.setDeviceCodeValue(token.getTokenValue());
				oauth2Authorization.setDeviceCodeMetadata(JSONUtil.toJsonStr(deviceCode.getMetadata()));
			}
		}
		//保存UserCode
		if (OAuth2AuthorizationUtils.isUserCode(oAuth2Authorization)) {
			Optional<OAuth2Authorization.Token<OAuth2UserCode>> userCodeOptional = Optional.ofNullable(oAuth2Authorization.getToken(OAuth2UserCode.class));
			if(userCodeOptional.isPresent()){
				OAuth2Authorization.Token<OAuth2UserCode> userCode = userCodeOptional.get();
				OAuth2UserCode token = userCode.getToken();
				oauth2Authorization.setUserCodeIssuedAt(LocalDateTime.ofInstant(Objects.requireNonNull(token.getIssuedAt()), ZoneId.systemDefault()));
				oauth2Authorization.setUserCodeExpiresAt(LocalDateTime.ofInstant(Objects.requireNonNull(token.getExpiresAt()), ZoneId.systemDefault()));
				oauth2Authorization.setUserCodeValue(token.getTokenValue());
				oauth2Authorization.setUserCodeMetadata(JSONUtil.toJsonStr(userCode.getMetadata()));
			}
		}

		return oauth2Authorization;
	}
}