package com.platform.mesh.ai.biz.soa.store.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.transport.rest5_client.Rest5ClientTransport;
import co.elastic.clients.transport.rest5_client.low_level.Rest5Client;
import com.platform.mesh.ai.biz.soa.store.AiStoreService;
import com.platform.mesh.ai.biz.soa.store.enums.StoreFlagEnum;
import com.platform.mesh.core.constants.NumberConst;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.elasticsearch.ElasticsearchVectorStore;
import org.springframework.ai.vectorstore.elasticsearch.ElasticsearchVectorStoreOptions;
import org.springframework.ai.vectorstore.elasticsearch.SimilarityFunction;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

/**
 * @description AI平台工厂实现
 * @author 蝉鸣
 */
@Service
@RequiredArgsConstructor
@ConditionalOnBean(ElasticsearchClient.class)
public class ElasticStoreFactoryImpl implements AiStoreService {

    /**
     * Elasticsearch客户端提供器
     */
    @Autowired
    private final ObjectProvider<ElasticsearchClient> elasticsearchClientProvider;

    /**
     * 功能描述:
     * 〈AI平台类型〉
     * @return 正常返回:{@link StoreFlagEnum}
     * @author 蝉鸣
     */
    @Override
    public StoreFlagEnum aiStore() {
        return StoreFlagEnum.ELASTICSEARCH;
    }

    /**
     * 功能描述:
     * 〈获取对象〉
     * @return 正常返回:{@link VectorStore}
     * @author 蝉鸣
     */
    @Override
    public VectorStore getVectorStore(EmbeddingModel embeddingModel) {
        ElasticsearchVectorStoreOptions storeOptions = new ElasticsearchVectorStoreOptions();
        //设置索引名称
        storeOptions.setIndexName("custom-index");
        //设置向量字段名称
        storeOptions.setEmbeddingFieldName("embedding");
        //定义向量相似度计算的算法
        storeOptions.setSimilarity(SimilarityFunction.cosine);
        //设置向量的维度数
        storeOptions.setDimensions(NumberConst.NUM_1536);
        // 创建 ElasticVectorStore 对象
        ElasticsearchClient elasticsearchClient = elasticsearchClientProvider.getObject();
        Rest5ClientTransport transport = (Rest5ClientTransport) elasticsearchClient._transport();
        Rest5Client client = transport.restClient();
        return ElasticsearchVectorStore.builder(client, embeddingModel)
                .options(storeOptions)
                .initializeSchema(Boolean.TRUE)
                .build();
    }
}
