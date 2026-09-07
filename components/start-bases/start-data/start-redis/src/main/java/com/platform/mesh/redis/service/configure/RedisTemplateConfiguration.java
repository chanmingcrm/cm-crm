package com.platform.mesh.redis.service.configure;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import org.redisson.spring.starter.RedissonAutoConfigurationV4;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import tools.jackson.databind.DefaultTyping;
import tools.jackson.databind.cfg.DateTimeFeature;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;

/**
 * @description Redis 配置类
 * @author 蝉鸣
 */
@EnableCaching
@AutoConfiguration(before = RedissonAutoConfigurationV4.class)
@ConditionalOnClass(RedisTemplate.class)
public class RedisTemplateConfiguration {

	@Bean
	@ConditionalOnMissingBean(RedisTemplate.class)
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		JsonMapper jsonMapper = JsonMapper.builder()
				// 启用默认类型信息（替代 activateDefaultTyping）
				.activateDefaultTyping(
						BasicPolymorphicTypeValidator.builder().build(),
						DefaultTyping.NON_FINAL,
						JsonTypeInfo.As.PROPERTY
				)
				.changeDefaultVisibility(incl ->
						incl.withVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY))
				// 可见性配置（替代 setVisibility）
				.changeDefaultPropertyInclusion(incl ->
						incl.withValueInclusion(JsonInclude.Include.ALWAYS)
				)
				.enable(DateTimeFeature.WRITE_DATES_AS_TIMESTAMPS)
				.build();
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		JacksonJsonRedisSerializer<Object> jackson2JsonRedisSerializer = new JacksonJsonRedisSerializer<>(jsonMapper
				,Object.class);
		redisTemplate.setKeySerializer(RedisSerializer.string());
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.setHashKeySerializer(RedisSerializer.string());
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.setConnectionFactory(factory);
		return redisTemplate;
	}
//
//	@Bean
//	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
//		StringRedisTemplate template = new StringRedisTemplate();
//		template.setConnectionFactory(factory);
//		return template;
//	}
//
//	@Bean
//	public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
//		return redisTemplate.opsForHash();
//	}
//
//	@Bean
//	public ValueOperations<String, String> valueOperations(RedisTemplate<String, String> redisTemplate) {
//		return redisTemplate.opsForValue();
//	}
//
//	@Bean
//	public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
//		return redisTemplate.opsForList();
//	}
//
//	@Bean
//	public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
//		return redisTemplate.opsForSet();
//	}
//
//	@Bean
//	public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
//		return redisTemplate.opsForZSet();
//	}

}