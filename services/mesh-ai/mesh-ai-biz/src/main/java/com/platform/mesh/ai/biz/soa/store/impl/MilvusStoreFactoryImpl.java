//package com.platform.mesh.ai.biz.soa.store.impl;
//
//import com.platform.mesh.ai.biz.soa.store.AiStoreService;
//import com.platform.mesh.ai.biz.soa.store.enums.StoreFlagEnum;
//import io.milvus.client.MilvusServiceClient;
//import io.milvus.param.ConnectParam;
//import io.milvus.param.IndexType;
//import io.milvus.param.MetricType;
//import org.springframework.ai.embedding.EmbeddingModel;
//import org.springframework.ai.embedding.TokenCountBatchingStrategy;
//import org.springframework.ai.vectorstore.VectorStore;
//import org.springframework.ai.vectorstore.milvus.MilvusVectorStore;
//import org.springframework.stereotype.Service;
//
///**
// * @description AI平台工厂实现
// * @author 蝉鸣
// */
//@Service
//public class MilvusStoreFactoryImpl implements AiStoreService {
//
//    /**
//     * 功能描述:
//     * 〈AI平台类型〉
//     * @return 正常返回:{@link StoreFlagEnum}
//     * @author 蝉鸣
//     */
//    @Override
//    public StoreFlagEnum aiStore() {
//        return StoreFlagEnum.MILVUS;
//    }
//
//    /**
//     * 功能描述:
//     * 〈获取对象〉
//     * @return 正常返回:{@link VectorStore}
//     * @author 蝉鸣
//     */
//    public VectorStore getVectorStore(EmbeddingModel embeddingModel) {
//        MilvusServiceClient milvusServiceClient = new MilvusServiceClient(ConnectParam.newBuilder()
//                .withAuthorization("minioadmin", "minioadmin")
//                .withUri("")
//                .build());
//        return MilvusVectorStore.builder(milvusServiceClient, embeddingModel)
//                .collectionName("test_vector_store")
//                .databaseName("default")
//                .indexType(IndexType.IVF_FLAT)
//                .metricType(MetricType.COSINE)
//                .initializeSchema(Boolean.TRUE)
//                .batchingStrategy(new TokenCountBatchingStrategy())
//                .build();
//    }
//
//
//}
