package com.platform.mesh.module.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.util.Assert;

import java.time.Duration;

/**
 * @description 实现储存redis个性化
 * @author 蝉鸣
 */
public class RedisOAuth2AuthorizationConsentServiceImpl implements OAuth2AuthorizationConsentService {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	private final static Long TIMEOUT = 10L;

	/**
	 * 功能描述:
	 * 〈保存〉
	 * @param authorizationConsent authorizationConsent
	 * @author 蝉鸣
	 */
	@Override
	public void save(OAuth2AuthorizationConsent authorizationConsent) {
		Assert.notNull(authorizationConsent, "authorizationConsent cannot be null");

		redisTemplate.opsForValue().set(buildKey(authorizationConsent), authorizationConsent,
				Duration.ofMinutes(TIMEOUT));

	}

	/**
	 * 功能描述:
	 * 〈移除〉
	 * @param authorizationConsent authorizationConsent
	 * @author 蝉鸣
	 */
	@Override
	public void remove(OAuth2AuthorizationConsent authorizationConsent) {
		Assert.notNull(authorizationConsent, "authorizationConsent cannot be null");
		redisTemplate.delete(buildKey(authorizationConsent));
	}

	/**
	 * 功能描述:
	 * 〈查询〉
	 * @param registeredClientId registeredClientId
	 * @param principalName principalName
	 * @return 正常返回:{@link OAuth2AuthorizationConsent}
	 * @author 蝉鸣
	 */
	@Override
	public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
		Assert.hasText(registeredClientId, "registeredClientId cannot be empty");
		Assert.hasText(principalName, "principalName cannot be empty");
		return (OAuth2AuthorizationConsent) redisTemplate.opsForValue()
				.get(buildKey(registeredClientId, principalName));
	}

	/**
	 * 功能描述:
	 * 〈构建key〉
	 * @param registeredClientId registeredClientId
	 * @param principalName principalName
	 * @return 正常返回:{@link String}
	 * @author 蝉鸣
	 */
	private static String buildKey(String registeredClientId, String principalName) {
		return "token:consent:" + registeredClientId + ":" + principalName;
	}

	/**
	 * 功能描述:
	 * 〈构建key〉
	 * @param authorizationConsent authorizationConsent
	 * @return 正常返回:{@link String}
	 * @author 蝉鸣
	 */
	private static String buildKey(OAuth2AuthorizationConsent authorizationConsent) {
		return buildKey(authorizationConsent.getRegisteredClientId(), authorizationConsent.getPrincipalName());
	}

}
