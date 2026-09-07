package com.platform.mesh.ai.biz.soa.store.impl;

import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.ai.biz.soa.store.AiStoreService;
import com.platform.mesh.ai.biz.soa.store.enums.StoreFlagEnum;
import com.platform.mesh.utils.file.DocFileUtil;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.net.URI;

/**
 * @description AI平台工厂实现
 * @author 蝉鸣
 */
@Service
public class FileStoreFactoryImpl implements AiStoreService {

    /**
     * 功能描述:
     * 〈AI平台类型〉
     * @return 正常返回:{@link StoreFlagEnum}
     * @author 蝉鸣
     */
    @Override
    public StoreFlagEnum aiStore() {
        return StoreFlagEnum.FILE;
    }

    /**
     * 功能描述:
     * 〈获取对象〉
     * @return 正常返回:{@link VectorStore}
     * @author 蝉鸣
     */
    public VectorStore getVectorStore(EmbeddingModel embeddingModel) {
        SimpleVectorStore vectorStore = SimpleVectorStore.builder(embeddingModel).build();
        // 启动加载
        URI uri = DocFileUtil.getUrlStrToUri("");
        if(ObjectUtil.isNull(uri)){
            return null;
        }
        Resource resource = DocFileUtil.getUriToResource(uri);
        vectorStore.load(resource);
        return vectorStore;
    }

}
