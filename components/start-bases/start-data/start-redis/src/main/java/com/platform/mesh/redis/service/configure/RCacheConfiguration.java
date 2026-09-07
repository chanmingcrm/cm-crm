package com.platform.mesh.redis.service.configure;

import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.redis.service.constants.CacheConstants;
import org.springframework.cache.CacheManager;
import org.springframework.cache.jcache.config.JCacheConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @description redis使用jackson序列化
 * @author 蝉鸣
 */
@SuppressWarnings("all")
@Configuration
public class RCacheConfiguration implements JCacheConfigurer {

	/**
	 * 配置缓存信息
	 * @param redisConnectionFactory RedisConnectionFactory
	 * @return CacheManager
	 */
	@Bean
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
		return new RedisCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
				// 默认策略，未配置的 key 会使用这个
				this.getRedisCacheConfigurationWithTtl(CacheConstants.TTL_KEY_DEFAULT),
				// 指定 key 策略
				this.getRedisCacheConfigurationMap());
	}

	/**
	 * 功能描述:
	 * 〈指定key策略〉
	 * @return 正常返回:{@link Map}
	 * @author 蝉鸣
	 */
	private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
		Map<String,RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();

		// 字典默认10分钟
		redisCacheConfigurationMap.put(CacheConstants.SYS_DICT_KEY,
				this.getRedisCacheConfigurationWithTtl(CacheConstants.TTL_KEY_FIX));
		// oauth默认6小时
		redisCacheConfigurationMap.put(CacheConstants.CLIENT_DETAILS_KEY,
				this.getRedisCacheConfigurationWithTtl(CacheConstants.TTL_KEY_FIX));

		return redisCacheConfigurationMap;
	}

	/**
	 * 功能描述:
	 * 〈未指定key策略〉
	 * @return 正常返回:{@link RedisCacheConfiguration}
	 * @author 蝉鸣
	 */
	private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(long seconds) {
		return RedisCacheConfiguration.defaultCacheConfig()
				// 缓存前缀
//				.prefixCacheNameWith("mesh:")
				// 修改为一个 :
				.computePrefixWith(name -> name + SymbolConst.COLON)
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.java()))
				// 过期时间
				.entryTtl(Duration.ofSeconds(seconds));
	}

}
