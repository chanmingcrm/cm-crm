package com.platform.mesh.uaa.biz.modules.registered.service.manual;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.uaa.biz.modules.client.domain.po.Oauth2RegisteredClient;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description 终端配置Service接口
 * @author 蝉鸣
 */
@Service
public class RegisteredClientServiceManual {


	/**
	 * 功能描述:
	 * 〈获取默认TokenSettings〉
	 * @return 正常返回:{@link TokenSettings}
	 * @author 蝉鸣
	 */
	public TokenSettings getDefaultTokenSettings(){
		return TokenSettings.builder()
				// 令牌存活时间：2小时
				.accessTokenTimeToLive(Duration.ofDays(NumberConst.NUM_7))
				// 令牌可以刷新，重新获取
				.reuseRefreshTokens(Boolean.TRUE)
				// 刷新时间：30天（30天内当令牌过期时，可以用刷新令牌重新申请新令牌，不需要再认证）
				.refreshTokenTimeToLive(Duration.ofDays(NumberConst.NUM_31))
				//加上则token为不加密
//				.accessTokenFormat(OAuth2TokenFormat.REFERENCE)
				.build();
	}

	/**
	 * 功能描述:
	 * 〈获取默认ClientSettings〉
	 * @return 正常返回:{@link ClientSettings}
	 * @author 蝉鸣
	 */
	public ClientSettings getDefaultClientSettings(){
		// 客户端相关配置
		return ClientSettings.builder()
				// 是否需要用户授权确认
				.requireAuthorizationConsent(false)
				.build();
	}

	/**
	 * 功能描述:
	 * 〈获取默认初始化终端〉
	 * @return 正常返回:{@link RegisteredClient}
	 * @author 蝉鸣
	 */
	public RegisteredClient getDefaultRegisteredClient() {
		RegisteredClient registeredClient = RegisteredClient
				// 客户端ID和密码
				.withId(UUID.randomUUID().toString())
				//.withId(id)
				.clientId("my_client")
				//.clientSecret("{noop}123456")
				.clientSecret(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("123456"))
				// 客户端名称：可省略
				.clientName("my_client_name")
				// 授权方法
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				// 授权模式
				// ---- 【授权码模式】
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				// ---------- 刷新令牌（授权码模式）
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				/* 回调地址：
				 * 授权服务器向当前客户端响应时调用下面地址；
				 * 不在此列的地址将被拒统；
				 * 只能使用IP或域名，不能使用localhost
				 */
				.redirectUri("http://www.baidu.com")
//                .redirectUri("http://127.0.0.1:8000/login/oauth2/code/myClient")
				//.redirectUri("http://127.0.0.1:8000")
				//.redirectUri("http://127.0.0.1:8000/**")
				// 授权范围（当前客户端的授权范围）
				.scope("server")
				// JWT（Json Web Token）配置项
				.tokenSettings(this.getDefaultTokenSettings())
				// 客户端配置项
				.clientSettings(this.getDefaultClientSettings())
				.build();
		return registeredClient;
	}


	/**
	 * 功能描述:
	 * 〈转换终端对象〉
	 * @param clientDetails 终端配置
	 * @return 正常返回:{@link Oauth2RegisteredClient}
	 * @author 蝉鸣
	 */
	public Oauth2RegisteredClient toOauth2RegisteredClient(RegisteredClient clientDetails) {
		Oauth2RegisteredClient registeredClient = new Oauth2RegisteredClient();
		// 客户端Id
		registeredClient.setClientId(clientDetails.getClientId());
		// 客户端秘钥
		registeredClient.setClientSecret(clientDetails.getClientSecret());
		// 客户端名称
		registeredClient.setClientName(clientDetails.getClientName());
		// 客户端Id签发时间
		if(ObjectUtil.isNotEmpty(clientDetails.getClientIdIssuedAt())){
			OffsetDateTime dateTime = Objects.requireNonNull(clientDetails.getClientIdIssuedAt()).atOffset(ZoneOffset.ofHours(8));
			registeredClient.setClientIdIssuedAt(dateTime.toLocalDateTime());
		}
		// 授权模式
		Optional.ofNullable(clientDetails.getAuthorizationGrantTypes())
				.ifPresent(grants -> registeredClient.setAuthorizationGrantTypes(StringUtils.collectionToCommaDelimitedString(grants.stream().map(AuthorizationGrantType::getValue).collect(Collectors.toList())))
				);
		// 授权方法
		Optional.ofNullable(clientDetails.getClientAuthenticationMethods())
				.ifPresent(methods -> registeredClient.setClientAuthenticationMethods(StringUtils.collectionToCommaDelimitedString(methods.stream().map(ClientAuthenticationMethod::getValue).collect(Collectors.toList())))
				);
		// 回调地址
		Optional.ofNullable(clientDetails.getRedirectUris())
				.ifPresent(uris ->
						registeredClient.setRedirectUris(StringUtils.collectionToCommaDelimitedString(uris))
				);
		// scopes
		Optional.ofNullable(clientDetails.getScopes())
				.ifPresent(scopes ->
						registeredClient.setScopes(StringUtils.collectionToCommaDelimitedString(scopes))
				);
		// ClientSettings
		Optional.ofNullable(clientDetails.getClientSettings())
				.ifPresent(clientSettings -> registeredClient.setClientSettings(JSONUtil.toJsonStr(clientSettings))
				);
		// TokenSettings
		Optional.ofNullable(clientDetails.getTokenSettings())
				.ifPresent(tokenSettings -> registeredClient.setTokenSettings(JSONUtil.toJsonStr(tokenSettings))
				);
		return registeredClient;
	}



	/**
	 * 功能描述:
	 * 〈转换终端对象〉
	 * @param client client
	 * @return 正常返回:{@link RegisteredClient}
	 * @author 蝉鸣
	 */
	public RegisteredClient toRegisteredClient(Oauth2RegisteredClient client) {

		// 授权模式
		Set<AuthorizationGrantType> authorizationGrantTypes;
		if(ObjectUtil.isEmpty(client.getAuthorizationGrantTypes())){
			authorizationGrantTypes = CollUtil.newHashSet(AuthorizationGrantType.AUTHORIZATION_CODE);
		}else{
			 authorizationGrantTypes = StringUtils.commaDelimitedListToSet(client.getAuthorizationGrantTypes()).stream().map(item -> new AuthorizationGrantType(item)).collect(Collectors.toSet());
		}

		// 授权模式
		Set<ClientAuthenticationMethod> clientAuthenticationMethod;
		if(ObjectUtil.isEmpty(client.getClientAuthenticationMethods())){
			clientAuthenticationMethod = CollUtil.newHashSet(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
		}else{
			clientAuthenticationMethod = StringUtils.commaDelimitedListToSet(client.getClientAuthenticationMethods()).stream().map(item -> new ClientAuthenticationMethod(item)).collect(Collectors.toSet());
		}

		// 回调地址
		Set<String> redirectUri;
		if(ObjectUtil.isEmpty(client.getRedirectUris())){
			redirectUri = CollUtil.newHashSet("http://www.baidu.com");
		}else{
			redirectUri = StringUtils.commaDelimitedListToSet(client.getRedirectUris());
		}

		// scopes
		Set<String> scope;
		if(ObjectUtil.isEmpty(client.getScopes())){
			scope = CollUtil.newHashSet("server");
		}else{
			scope = StringUtils.commaDelimitedListToSet(client.getScopes());
		}

		// TokenSettings
		TokenSettings tokenSettings;
		if(ObjectUtil.isEmpty(client.getTokenSettings())){
			tokenSettings = this.getDefaultTokenSettings();
		}else{
			tokenSettings = this.getDefaultTokenSettings();
		}

		// ClientSettings
		ClientSettings clientSettings;
		if(ObjectUtil.isEmpty(client.getClientSettings())){
			clientSettings = this.getDefaultClientSettings();
		}else{
			Map<String, Object> settings  = JSONUtil.parseObj(client.getClientSettings());
			clientSettings = ClientSettings.withSettings(settings).build();
		}

		return RegisteredClient.withId(StrUtil.toString(client.getId()))
				// 客户端Id
				.clientId(client.getClientId())
				// 客户端名称
				.clientName(client.getClientName())
				// 客户端秘钥
				.clientSecret(client.getClientSecret())
				// 转换GrantTypes
				.authorizationGrantTypes(authenticationGrantTypes -> authenticationGrantTypes.addAll(authorizationGrantTypes))
				// 转换认证方式
				.clientAuthenticationMethods(authenticationMethods -> authenticationMethods.addAll(clientAuthenticationMethod))
				// 客户端拥有的授权范围
				.scopes(scopes -> scopes.addAll(scope))
				// 客户端回调地址
				.redirectUris(uris -> uris.addAll(redirectUri))
				// 通过客户端授权Token的配置
				.tokenSettings(tokenSettings)
				// 客户端设置
				.clientSettings(clientSettings)
				// 客户端Id签发时间
				.clientIdIssuedAt(client.getClientIdIssuedAt().toInstant(ZoneOffset.UTC))
				.build();
	}


}
