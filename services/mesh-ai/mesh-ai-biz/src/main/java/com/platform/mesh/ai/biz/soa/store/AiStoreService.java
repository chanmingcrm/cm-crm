package com.platform.mesh.ai.biz.soa.store;


import com.platform.mesh.ai.biz.soa.store.enums.StoreFlagEnum;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;

import java.util.List;

/**
 * @description 向量存储
 * @author 蝉鸣
 */
public interface AiStoreService {

    /**
     * 功能描述:
     * 〈向量存储类型〉
     * @return 正常返回:{@link StoreFlagEnum}
     * @author 蝉鸣
     */
    StoreFlagEnum aiStore();

    /**
     * 获得 向量存储 对象
     * @return EmbeddingModel 对象
     * @author 蝉鸣
     */
    VectorStore getVectorStore(EmbeddingModel embeddingModel);


    /**
     * 功能描述:
     * 〈查询文档〉
     * @param vectorStore vectorStore
     * @param content content
     * @param topK topK
     * @param similarityThreshold similarityThreshold
     * @param filterExpression filterExpression
     * @author 蝉鸣
     */
    default List<Document> searchVectorStore(VectorStore vectorStore, String content, Integer topK
            , Double similarityThreshold, Filter.Expression filterExpression) {
        SearchRequest request = SearchRequest.builder()
                .query(content)
                // 只返回相似度最高的一条数据
                .topK(topK)
                // 相似度阈值
                .similarityThreshold(similarityThreshold)
                //过滤条件
                .filterExpression(filterExpression)
                .build();
        return vectorStore.similaritySearch(request);
    }
}
