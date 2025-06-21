package com.platform.mesh.uaa.biz.auth.support.grant.third;

import me.zhyd.oauth.cache.AuthCacheConfig;
import me.zhyd.oauth.cache.AuthStateCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @description 自定义JustAuth state 存储。避免集群环境state校验失败
 * @author 蝉鸣
 */
@Service
public class AuthStateRedisCache implements AuthStateCache {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 存入缓存，默认3分钟
     * @param key   缓存key
     * @param value 缓存内容
     */
    @Override
    public void cache(String key, String value) {
        redisTemplate.opsForValue().set(key, value, AuthCacheConfig.timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * 存入缓存
     * @param key     缓存key
     * @param value   缓存内容
     * @param timeout 指定缓存过期时间（毫秒）
     */
    @Override
    public void cache(String key, String value, long timeout) {
        redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取缓存内容
     * @param key 缓存key
     * @return 缓存内容
     */
    @Override
    public String get(String key) {
        return Objects.requireNonNull(redisTemplate.opsForValue().get(key)).toString();
    }

    /**
     * 是否存在key，如果对应key的value值已过期，也返回false
     * @param key 缓存key
     * @return true：存在key，并且value没过期；false：key不存在或者已过期
     */
    @Override
    public boolean containsKey(String key) {
        return redisTemplate.hasKey(key);
    }
}
