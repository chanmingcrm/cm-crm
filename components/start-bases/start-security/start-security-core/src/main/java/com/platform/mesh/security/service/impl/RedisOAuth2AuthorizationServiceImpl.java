package com.platform.mesh.security.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.redis.service.constants.CacheConstants;
import com.platform.mesh.security.constants.SecurityConstant;
import com.platform.mesh.security.domain.bo.LoginUserBO;
import com.platform.mesh.security.event.UaaOauthEvent;
import com.platform.mesh.security.utils.OAuth2AuthorizationUtils;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @description 实现储存redis个性化
 * @author 蝉鸣
 */
public class RedisOAuth2AuthorizationServiceImpl implements OAuth2AuthorizationService {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 功能描述:
	 * 〈Redis 保存授权信息〉
	 * @param authorization authorization
	 * @author 蝉鸣
	 */
	@Override
	public void save(OAuth2Authorization authorization) {
		Assert.notNull(authorization, "authorization cannot be null");
		Long userId = ((LoginUserBO) ((UsernamePasswordAuthenticationToken) Objects.requireNonNull(authorization.getAttribute(SecurityConstant.PRINCIPAL))).getPrincipal()).getUserId();

		//redisTemplate.setValueSerializer(RedisSerializer.java())用RedisSerializer的原因是因为 OAuth2Authorization有些字段类型的原因，用其他的就会抛一些序列化异常的。
		//Spring Security 对象由于其内部包含复杂的对象（如 OAuth2Token、Principal 等）,直接使用默认的序列化方式（如 Jackson 或 JDK 序列化）可能会导致问题
		//保存state
		if (OAuth2AuthorizationUtils.isState(authorization)) {
			String state = authorization.getAttribute(OAuth2ParameterNames.STATE);
			String registeredClientId = authorization.getRegisteredClientId();
			String principalName = authorization.getPrincipalName();
			redisTemplate.setValueSerializer(RedisSerializer.java());
			redisTemplate.opsForValue().set(buildKey(OAuth2ParameterNames.STATE, state), authorization, NumberConst.NUM_10.longValue(),
					TimeUnit.MINUTES);
		}
		//保存AuthorizationCode
		if (OAuth2AuthorizationUtils.isAuthorizationCode(authorization)) {
			OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = authorization
					.getToken(OAuth2AuthorizationCode.class);
			OAuth2AuthorizationCode authorizationCodeToken = Objects.requireNonNull(authorizationCode).getToken();
			long between = ChronoUnit.MINUTES.between(Objects.requireNonNull(authorizationCodeToken.getIssuedAt()),
					authorizationCodeToken.getExpiresAt());
			redisTemplate.setValueSerializer(RedisSerializer.java());
			redisTemplate.opsForValue().set(buildKey(OAuth2ParameterNames.CODE, authorizationCodeToken.getTokenValue()),
					authorization, between, TimeUnit.MINUTES);
		}
		//保存AccessToken
		if (OAuth2AuthorizationUtils.isAccessToken(authorization)) {
			OAuth2AccessToken accessToken = authorization.getAccessToken().getToken();
			long between = ChronoUnit.SECONDS.between(Objects.requireNonNull(accessToken.getIssuedAt()), accessToken.getExpiresAt());
			redisTemplate.setValueSerializer(RedisSerializer.java());
			redisTemplate.opsForValue().set(buildKey(OAuth2ParameterNames.ACCESS_TOKEN, accessToken.getTokenValue()),
					authorization, between, TimeUnit.SECONDS);
		}
		//保存RefreshToken
		if (OAuth2AuthorizationUtils.isRefreshToken(authorization)) {
			OAuth2RefreshToken refreshToken = Objects.requireNonNull(authorization.getRefreshToken()).getToken();
			long between = ChronoUnit.SECONDS.between(Objects.requireNonNull(refreshToken.getIssuedAt()), refreshToken.getExpiresAt());
			redisTemplate.setValueSerializer(RedisSerializer.java());
			redisTemplate.opsForValue().set(buildKey(OAuth2ParameterNames.REFRESH_TOKEN, refreshToken.getTokenValue()),
					authorization, between, TimeUnit.SECONDS);
		}
		//保存IdToken
		if (OAuth2AuthorizationUtils.isIdToken(authorization)) {
			OidcIdToken oidcIdToken = Objects.requireNonNull(authorization.getToken(OidcIdToken.class)).getToken();
			long between = ChronoUnit.SECONDS.between(Objects.requireNonNull(oidcIdToken.getIssuedAt()), oidcIdToken.getExpiresAt());
			redisTemplate.setValueSerializer(RedisSerializer.java());
			redisTemplate.opsForValue().set(buildKey(OidcParameterNames.ID_TOKEN, oidcIdToken.getTokenValue()),
					authorization, between, TimeUnit.SECONDS);
		}
		//保存DeviceCode
		if (OAuth2AuthorizationUtils.isDeviceCode(authorization)) {
			OAuth2DeviceCode deviceCode = Objects.requireNonNull(authorization.getToken(OAuth2DeviceCode.class)).getToken();
			long between = ChronoUnit.SECONDS.between(Objects.requireNonNull(deviceCode.getIssuedAt()), deviceCode.getExpiresAt());
			redisTemplate.setValueSerializer(RedisSerializer.java());
			redisTemplate.opsForValue().set(buildKey(OAuth2ParameterNames.DEVICE_CODE, deviceCode.getTokenValue()),
					authorization, between, TimeUnit.SECONDS);
		}
		//保存UserCode
		if (OAuth2AuthorizationUtils.isUserCode(authorization)) {
			OAuth2UserCode userCode = Objects.requireNonNull(authorization.getToken(OAuth2UserCode.class)).getToken();
			long between = ChronoUnit.SECONDS.between(Objects.requireNonNull(userCode.getIssuedAt()), userCode.getExpiresAt());
			redisTemplate.setValueSerializer(RedisSerializer.java());
			redisTemplate.opsForValue().set(buildKey(OAuth2ParameterNames.USER_CODE, userCode.getTokenValue()),
					authorization, between, TimeUnit.SECONDS);
		}
		//添加到db
		SpringContextHolderUtil.publishEvent(new UaaOauthEvent(authorization));
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
			keys.add(buildKey(OAuth2ParameterNames.STATE, token));
		}
		//保存AuthorizationCode
		if (OAuth2AuthorizationUtils.isAuthorizationCode(authorization)) {
			OAuth2AuthorizationCode authorizationCodeToken = Objects.requireNonNull(authorization.getToken(OAuth2AuthorizationCode.class)).getToken();
			keys.add(buildKey(OAuth2ParameterNames.CODE, authorizationCodeToken.getTokenValue()));
		}
		//保存AccessToken
		if (OAuth2AuthorizationUtils.isAccessToken(authorization)) {
			OAuth2AccessToken accessToken = authorization.getAccessToken().getToken();
			keys.add(buildKey(OAuth2ParameterNames.ACCESS_TOKEN, accessToken.getTokenValue()));
		}
		//保存RefreshToken
		if (OAuth2AuthorizationUtils.isRefreshToken(authorization)) {
			OAuth2RefreshToken refreshToken = Objects.requireNonNull(authorization.getRefreshToken()).getToken();
			keys.add(buildKey(OAuth2ParameterNames.REFRESH_TOKEN, refreshToken.getTokenValue()));
		}
		//保存IdToken
		if (OAuth2AuthorizationUtils.isIdToken(authorization)) {
			OidcIdToken oidcIdToken = Objects.requireNonNull(authorization.getToken(OidcIdToken.class)).getToken();
			keys.add(buildKey(OidcParameterNames.ID_TOKEN, oidcIdToken.getTokenValue()));
		}
		//保存DeviceCode
		if (OAuth2AuthorizationUtils.isDeviceCode(authorization)) {
			OAuth2DeviceCode deviceCode = Objects.requireNonNull(authorization.getToken(OAuth2DeviceCode.class)).getToken();
			keys.add(buildKey(OAuth2ParameterNames.DEVICE_CODE, deviceCode.getTokenValue()));
		}
		//保存UserCode
		if (OAuth2AuthorizationUtils.isUserCode(authorization)) {
			OAuth2UserCode userCode = Objects.requireNonNull(authorization.getToken(OAuth2UserCode.class)).getToken();
			keys.add(buildKey(OAuth2ParameterNames.USER_CODE, userCode.getTokenValue()));
		}
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
//		return (OAuth2Authorization) redisTemplate.opsForValue().get(buildKey("", id));
		throw new UnsupportedOperationException();
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
		Assert.notNull(tokenType, "tokenType cannot be empty");
		redisTemplate.setValueSerializer(RedisSerializer.java());
		return (OAuth2Authorization) redisTemplate.opsForValue().get(buildKey(tokenType.getValue(), token));
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