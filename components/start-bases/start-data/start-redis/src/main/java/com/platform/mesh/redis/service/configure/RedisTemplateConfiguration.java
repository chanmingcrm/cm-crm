package com.platform.mesh.redis.service.configure;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @description Redis 配置类
 * @author 蝉鸣
 */
@EnableCaching
@AutoConfiguration
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class RedisTemplateConfiguration {

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.activateDefaultTyping(
				LaissezFaireSubTypeValidator.instance
				,ObjectMapper.DefaultTyping.NON_FINAL
				,JsonTypeInfo.As.PROPERTY);
		objectMapper.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(objectMapper
				,Object.class);
		redisTemplate.setKeySerializer(RedisSerializer.string());
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.setHashKeySerializer(RedisSerializer.string());
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.setConnectionFactory(factory);
		return redisTemplate;
	}

	@Bean
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
		StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(factory);
		return template;
	}

	@Bean
	public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForHash();
	}

	@Bean
	public ValueOperations<String, String> valueOperations(RedisTemplate<String, String> redisTemplate) {
		return redisTemplate.opsForValue();
	}

	@Bean
	public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForList();
	}

	@Bean
	public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForSet();
	}

	@Bean
	public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForZSet();
	}

}