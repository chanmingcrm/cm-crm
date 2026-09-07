package com.platform.mesh.security.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.redis.service.constants.CacheConstants;
import com.platform.mesh.security.event.UaaOauthEvent;
import com.platform.mesh.security.utils.OAuth2AuthorizationUtils;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.jspecify.annotations.Nullable;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2DeviceCode;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2UserCode;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.util.Assert;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @description 实现储存redis个性化
 * @author 蝉鸣
 */
public class RedisOAuth2AuthorizationServiceImpl implements OAuth2AuthorizationService {

	private static final String AUTHORIZATION_ID = "authorization-id";
	private static final String ANY_TOKEN = "any-token";

	private final RedisTemplate<String, Object> redisTemplate;

	/**
	 * 功能描述:
	 * 〈创建使用独立 Java 序列化配置的 OAuth2 授权存储〉
	 * @param sourceRedisTemplate 基础 RedisTemplate
	 * @author qingfeng
	 */
	public RedisOAuth2AuthorizationServiceImpl(RedisTemplate<String, Object> sourceRedisTemplate) {
		RedisConnectionFactory connectionFactory = sourceRedisTemplate.getConnectionFactory();
		if (connectionFactory == null) {
			this.redisTemplate = sourceRedisTemplate;
			return;
		}
		RedisTemplate<String, Object> oauthRedisTemplate = new RedisTemplate<>();
		oauthRedisTemplate.setConnectionFactory(connectionFactory);
		oauthRedisTemplate.setKeySerializer(sourceRedisTemplate.getKeySerializer());
		oauthRedisTemplate.setHashKeySerializer(sourceRedisTemplate.getHashKeySerializer());
		oauthRedisTemplate.setValueSerializer(RedisSerializer.java());
		oauthRedisTemplate.setHashValueSerializer(RedisSerializer.java());
		oauthRedisTemplate.afterPropertiesSet();
		this.redisTemplate = oauthRedisTemplate;
	}

	/**
	 * 功能描述:
	 * 〈Redis 保存授权信息〉
	 * @param authorization authorization
	 * @author 蝉鸣
	 */
	@Override
	public void save(OAuth2Authorization authorization) {
		Assert.notNull(authorization, "authorization cannot be null");
		//redisTemplate.setValueSerializer(RedisSerializer.java())用RedisSerializer的原因是因为 OAuth2Authorization有些字段类型的原因，用其他的就会抛一些序列化异常的。
		//Spring Security 对象由于其内部包含复杂的对象（如 OAuth2Token、Principal 等）,直接使用默认的序列化方式（如 Jackson 或 JDK 序列化）可能会导致问题
		//保存state
		if (OAuth2AuthorizationUtils.isState(authorization)) {
			String state = authorization.getAttribute(OAuth2ParameterNames.STATE);
			saveAuthorization(OAuth2ParameterNames.STATE, state, authorization,
					NumberConst.NUM_10.longValue(), TimeUnit.MINUTES);
		}
		//保存AuthorizationCode
		if (OAuth2AuthorizationUtils.isAuthorizationCode(authorization)) {
			OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = authorization
					.getToken(OAuth2AuthorizationCode.class);
			OAuth2AuthorizationCode authorizationCodeToken = Objects.requireNonNull(authorizationCode).getToken();
			long between = ChronoUnit.MINUTES.between(Objects.requireNonNull(authorizationCodeToken.getIssuedAt()),
					authorizationCodeToken.getExpiresAt());
			saveAuthorization(OAuth2ParameterNames.CODE, authorizationCodeToken.getTokenValue(),
					authorization, between, TimeUnit.MINUTES);
		}
		//保存AccessToken
		if (OAuth2AuthorizationUtils.isAccessToken(authorization)) {
			OAuth2AccessToken accessToken = authorization.getAccessToken().getToken();
			long between = ChronoUnit.SECONDS.between(Objects.requireNonNull(accessToken.getIssuedAt()), accessToken.getExpiresAt());
			saveAuthorization(OAuth2ParameterNames.ACCESS_TOKEN, accessToken.getTokenValue(),
					authorization, between, TimeUnit.SECONDS);
			// 仅在 Access Token 已生成后同步授权记录，避免 state、code 等中间状态重复入库。
			SpringContextHolderUtil.publishEvent(new UaaOauthEvent(authorization));
		}
		//保存RefreshToken
		if (OAuth2AuthorizationUtils.isRefreshToken(authorization)) {
			OAuth2RefreshToken refreshToken = Objects.requireNonNull(authorization.getRefreshToken()).getToken();
			long between = ChronoUnit.SECONDS.between(Objects.requireNonNull(refreshToken.getIssuedAt()), refreshToken.getExpiresAt());
			saveAuthorization(OAuth2ParameterNames.REFRESH_TOKEN, refreshToken.getTokenValue(),
					authorization, between, TimeUnit.SECONDS);
		}
		//保存IdToken
		if (OAuth2AuthorizationUtils.isIdToken(authorization)) {
			OidcIdToken oidcIdToken = Objects.requireNonNull(authorization.getToken(OidcIdToken.class)).getToken();
			long between = ChronoUnit.SECONDS.between(Objects.requireNonNull(oidcIdToken.getIssuedAt()), oidcIdToken.getExpiresAt());
			saveAuthorization(OidcParameterNames.ID_TOKEN, oidcIdToken.getTokenValue(),
					authorization, between, TimeUnit.SECONDS);
		}
		//保存DeviceCode
		if (OAuth2AuthorizationUtils.isDeviceCode(authorization)) {
			OAuth2DeviceCode deviceCode = Objects.requireNonNull(authorization.getToken(OAuth2DeviceCode.class)).getToken();
			long between = ChronoUnit.SECONDS.between(Objects.requireNonNull(deviceCode.getIssuedAt()), deviceCode.getExpiresAt());
			saveAuthorization(OAuth2ParameterNames.DEVICE_CODE, deviceCode.getTokenValue(),
					authorization, between, TimeUnit.SECONDS);
		}
		//保存UserCode
		if (OAuth2AuthorizationUtils.isUserCode(authorization)) {
			OAuth2UserCode userCode = Objects.requireNonNull(authorization.getToken(OAuth2UserCode.class)).getToken();
			long between = ChronoUnit.SECONDS.between(Objects.requireNonNull(userCode.getIssuedAt()), userCode.getExpiresAt());
			saveAuthorization(OAuth2ParameterNames.USER_CODE, userCode.getTokenValue(),
					authorization, between, TimeUnit.SECONDS);
		}
	}

	/**
	 * 功能描述:
	 * 〈Redis移除授权信息〉
	 * @param authorization authorization
	 * @author 蝉鸣
	 */
	@Override
	public void remove(OAuth2Authorization authorization) {
		Assert.notNull(authorization, "authorization cannot be null");

		List<String> keys = CollUtil.newArrayList();
		//保存state
		if (OAuth2AuthorizationUtils.isState(authorization)) {
			String token = authorization.getAttribute(OAuth2ParameterNames.STATE);
			addTokenKeys(keys, OAuth2ParameterNames.STATE, token);
		}
		//保存AuthorizationCode
		if (OAuth2AuthorizationUtils.isAuthorizationCode(authorization)) {
			OAuth2AuthorizationCode authorizationCodeToken = Objects.requireNonNull(authorization.getToken(OAuth2AuthorizationCode.class)).getToken();
			addTokenKeys(keys, OAuth2ParameterNames.CODE, authorizationCodeToken.getTokenValue());
		}
		//保存AccessToken
		if (OAuth2AuthorizationUtils.isAccessToken(authorization)) {
			OAuth2AccessToken accessToken = authorization.getAccessToken().getToken();
			addTokenKeys(keys, OAuth2ParameterNames.ACCESS_TOKEN, accessToken.getTokenValue());
		}
		//保存RefreshToken
		if (OAuth2AuthorizationUtils.isRefreshToken(authorization)) {
			OAuth2RefreshToken refreshToken = Objects.requireNonNull(authorization.getRefreshToken()).getToken();
			addTokenKeys(keys, OAuth2ParameterNames.REFRESH_TOKEN, refreshToken.getTokenValue());
		}
		//保存IdToken
		if (OAuth2AuthorizationUtils.isIdToken(authorization)) {
			OidcIdToken oidcIdToken = Objects.requireNonNull(authorization.getToken(OidcIdToken.class)).getToken();
			addTokenKeys(keys, OidcParameterNames.ID_TOKEN, oidcIdToken.getTokenValue());
		}
		//保存DeviceCode
		if (OAuth2AuthorizationUtils.isDeviceCode(authorization)) {
			OAuth2DeviceCode deviceCode = Objects.requireNonNull(authorization.getToken(OAuth2DeviceCode.class)).getToken();
			addTokenKeys(keys, OAuth2ParameterNames.DEVICE_CODE, deviceCode.getTokenValue());
		}
		//保存UserCode
		if (OAuth2AuthorizationUtils.isUserCode(authorization)) {
			OAuth2UserCode userCode = Objects.requireNonNull(authorization.getToken(OAuth2UserCode.class)).getToken();
			addTokenKeys(keys, OAuth2ParameterNames.USER_CODE, userCode.getTokenValue());
		}
		keys.add(buildKey(AUTHORIZATION_ID, authorization.getId()));
		//批量删除keys
		redisTemplate.delete(keys);
	}

	/**
	 * 功能描述:
	 * 〈根据ID查询授权信息〉
	 * @param id id
	 * @return 正常返回:{@link OAuth2Authorization}
	 * @author 蝉鸣
	 */
	@Override
	@Nullable
	public OAuth2Authorization findById(String id) {
		Assert.hasText(id, "id cannot be empty");
		return (OAuth2Authorization) redisTemplate.opsForValue().get(buildKey(AUTHORIZATION_ID, id));
	}

	/**
	 * 功能描述:
	 * 〈根据token查询授权信息〉
	 * @param token token
	 * @param tokenType tokenType
	 * @return 正常返回:{@link OAuth2Authorization}
	 * @author 蝉鸣
	 */
	@Override
	@Nullable
	public OAuth2Authorization findByToken(String token, @Nullable OAuth2TokenType tokenType) {
		Assert.hasText(token, "token cannot be empty");
		String type = tokenType == null ? ANY_TOKEN : tokenType.getValue();
		return (OAuth2Authorization) redisTemplate.opsForValue().get(buildKey(type, token));
	}

	/**
	 * 功能描述:
	 * 〈同时保存类型索引、通用 Token 索引和授权 ID 索引〉
	 * @param type Token 类型
	 * @param token Token 值
	 * @param authorization OAuth2 授权信息
	 * @param timeout 有效期
	 * @param timeUnit 有效期单位
	 * @author qingfeng
	 */
	private void saveAuthorization(String type, String token, OAuth2Authorization authorization,
			long timeout, TimeUnit timeUnit) {
		Duration expiration = Duration.of(timeout, timeUnit.toChronoUnit());
		redisTemplate.opsForValue().set(buildKey(type, token), authorization, expiration);
		redisTemplate.opsForValue().set(buildKey(ANY_TOKEN, token), authorization, expiration);
		redisTemplate.opsForValue().set(buildKey(AUTHORIZATION_ID, authorization.getId()),
				authorization, expiration);
	}

	/**
	 * 功能描述:
	 * 〈收集指定 Token 的类型索引和通用索引〉
	 * @param keys 待删除 Redis Key
	 * @param type Token 类型
	 * @param token Token 值
	 * @author qingfeng
	 */
	private void addTokenKeys(List<String> keys, String type, String token) {
		keys.add(buildKey(type, token));
		keys.add(buildKey(ANY_TOKEN, token));
	}

	/**
	 * 功能描述:
	 * 〈构建Redis存储key〉
	 * @param token token
	 * @param type type
	 * @return 正常返回:{@link String}
	 * @author 蝉鸣
	 */
	private String buildKey(String type, String token) {
		return String.format("%s:%s:%s", CacheConstants.OAUTH_TOKEN_PREFIX, type, token);
	}

}
