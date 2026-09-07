package com.platform.mesh.ai.biz.soa.store.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.platform.mesh.ai.biz.soa.store.AiStoreService;
import com.platform.mesh.ai.biz.soa.store.enums.StoreFlagEnum;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.boot.data.redis.autoconfigure.DataRedisProperties;
import org.springframework.stereotype.Service;
import redis.clients.jedis.RedisClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description AI平台工厂实现
 * @author 蝉鸣
 */
@Service
public class RedisStoreFactoryImpl implements AiStoreService {

    /**
     * 功能描述:
     * 〈AI平台类型〉
     * @return 正常返回:{@link StoreFlagEnum}
     * @author 蝉鸣
     */
    @Override
    public StoreFlagEnum aiStore() {
        return StoreFlagEnum.REDIS;
    }

    /**
     * 功能描述:
     * 〈获取对象〉
     * @return 正常返回:{@link VectorStore}
     * @author 蝉鸣
     */
    @Override
    public VectorStore getVectorStore(EmbeddingModel embeddingModel) {
        // 创建 JedisPooled 对象
        DataRedisProperties redisProperties = SpringUtil.getBean(DataRedisProperties.class);
        RedisClient redisClient = RedisClient.create(redisProperties.getHost(), redisProperties.getPort(),
                redisProperties.getUsername(), redisProperties.getPassword());
        return RedisVectorStore.builder(redisClient, embeddingModel)
                .indexName("custom-index")
                .prefix("custom-prefix")
                .metadataFields(getVectorStoreMeta(new HashMap<>()))
                .initializeSchema(Boolean.TRUE)
                .build();
    }

    /**
     * 功能描述:
     * 〈获取对象〉
     * @return 正常返回:{@link VectorStore}
     * @author 蝉鸣
     */
    public List<RedisVectorStore.MetadataField> getVectorStoreMeta(Map<String, Class<?>> metadataFields) {
        List<RedisVectorStore.MetadataField> mateFields = CollUtil.newArrayList();
        metadataFields.forEach((key,value) -> {
            if (Number.class.isAssignableFrom(value)) {
                mateFields.add(RedisVectorStore.MetadataField.numeric(key)) ;
            }
            if (Boolean.class.isAssignableFrom(value)) {
                mateFields.add(RedisVectorStore.MetadataField.tag(key)) ;
            }
            mateFields.add(RedisVectorStore.MetadataField.text(key)) ;
        });
        return mateFields;
    }
}
