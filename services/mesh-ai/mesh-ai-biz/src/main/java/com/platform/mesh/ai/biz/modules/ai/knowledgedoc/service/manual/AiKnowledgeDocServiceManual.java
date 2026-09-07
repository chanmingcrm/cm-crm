package com.platform.mesh.ai.biz.modules.ai.knowledgedoc.service.manual;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Time;
import co.elastic.clients.elasticsearch._types.mapping.DenseVectorSimilarity;
import com.platform.mesh.ai.biz.chat.exception.AiChatExceptionEnum;
import com.platform.mesh.ai.biz.modules.ai.knowledge.domain.po.AiKnowledge;
import com.platform.mesh.ai.biz.modules.ai.knowledge.service.IAiKnowledgeService;
import com.platform.mesh.ai.biz.modules.ai.knowledgedoc.domain.po.AiKnowledgeDoc;
import com.platform.mesh.ai.biz.modules.ai.knowledgedoc.enums.ContentSourceEnum;
import com.platform.mesh.ai.biz.modules.ai.knowledgeslice.domain.po.AiKnowledgeSlice;
import com.platform.mesh.ai.biz.modules.ai.knowledgeslice.service.IAiKnowledgeSliceService;
import com.platform.mesh.ai.biz.modules.ai.model.domain.po.AiModel;
import com.platform.mesh.ai.biz.modules.ai.model.service.IAiModelService;
import com.platform.mesh.ai.biz.soa.base.constant.AiBaseConst;
import com.platform.mesh.ai.biz.soa.base.properties.AiBaseProperties;
import com.platform.mesh.ai.biz.soa.model.AiModelService;
import com.platform.mesh.ai.biz.soa.model.enums.ModelFlagEnum;
import com.platform.mesh.ai.biz.soa.model.factory.AiModelFactory;
import com.platform.mesh.ai.biz.soa.store.AiStoreService;
import com.platform.mesh.ai.biz.soa.store.enums.StoreFlagEnum;
import com.platform.mesh.ai.biz.soa.store.factory.AiStoreFactory;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.core.enums.custom.YesOrNoEnum;
import com.platform.mesh.mybatis.plus.constant.MybatisPlusConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.tokenizer.JTokkitTokenCountEstimator;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description AI知识库解析
 * @author 蝉鸣
 */
@Service
public class AiKnowledgeDocServiceManual {

    private static final Logger log = LoggerFactory.getLogger(AiKnowledgeDocServiceManual.class);

    @Autowired
    private AiModelFactory aiModelFactory;

    @Autowired
    private AiStoreFactory aiStoreFactory;

    @Autowired
    private IAiModelService aiModelServiceImpl;

    @Autowired
    private IAiKnowledgeService aiKnowledgeService;

    @Autowired
    private IAiKnowledgeSliceService aiKnowledgeSliceService;

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    /**
     * 功能描述:
     * 〈新增AI文档分片〉
     * @param aiKnowledgeDoc aiKnowledgeDoc
     * @author 蝉鸣
     */
    public void addAiKnowledgeDoc(AiKnowledgeDoc aiKnowledgeDoc) {
        //创建索引
//        this.createEsDenseVectorIndex("",NumberConst.NUM_1536);
        AiKnowledge knowledge = this.getKnowledgeById(aiKnowledgeDoc.getKnowledgeId());
        //获取模型
        AiModel aiModel = this.getModel(knowledge.getModelId());
        //获取向量存储
        VectorStore vectorStore = this.getVectorStore(aiModel, knowledge.getStoreFlag());
        //解析文档内容
        String content = this.getContent(aiKnowledgeDoc);
        //文档切片
        List<Document> documents = this.splitContent(content, NumberConst.NUM_10);
        //保存DB切片
        this.saveAiKnowledgeSlices(aiKnowledgeDoc,documents);
        //保存向量切片
        this.vectorStoreSave(vectorStore,documents);
        //更新DB切片
        this.updateAiKnowledgeSlices(documents);
    }

    /**
     * 功能描述:
     * 〈初始化索引〉
     * @param indexName indexName
     * @param dim dim
     * @author 蝉鸣
     */
    public void createEsDenseVectorIndex(String indexName, Integer dim){
        indexName = "custom-index";
        String finalIndexName = indexName;
        try{
            elasticsearchClient.indices().create(c -> c
                    .index(finalIndexName)
                    .settings(s -> s
                            .numberOfShards(NumberConst.NUM_3.toString())
                            .numberOfReplicas(NumberConst.NUM_1.toString())
                            .refreshInterval(Time.of(time->time.time("30s")))
                    )
                    .mappings(m -> m
                            .properties("knowledgeId", p -> p.keyword(k -> k))
                            .properties("knowledgeDocId", p -> p.keyword(k -> k))
                            .properties("knowledgeSliceId", p -> p.keyword(k -> k))
//                            .properties("content", p -> p.text(t -> t.analyzer("ik_max_word")))
                            .properties("embedding", p -> p
                                    .denseVector(d -> d
                                            .dims(dim)
                                            .index(Boolean.TRUE)
                                            .similarity(DenseVectorSimilarity.Cosine)
                                    )
                            )
                            .properties("metadata", p -> p.object(o -> o))
                            .properties("timestamp", p -> p.date(d -> d))
                    )
            );
        }catch (Exception exception){
            log.error(exception.getMessage(),exception);
        }

    }

    /**
     * 功能描述:
     * 〈更新AI文档分片〉
     * @param aiKnowledgeDoc aiKnowledgeDoc
     * @author 蝉鸣
     */
    public void deleteAiKnowledgeDoc(AiKnowledgeDoc aiKnowledgeDoc) {
        //获取模型知识库
        AiKnowledge knowledge = this.getKnowledgeById(aiKnowledgeDoc.getKnowledgeId());
        //获取模型
        AiModel aiModel = this.getModel(knowledge.getModelId());
        //获取向量存储
        VectorStore vectorStore = this.getVectorStore(aiModel, knowledge.getStoreFlag());
        //查询所有分片
        List<AiKnowledgeSlice> knowledgeSlices = aiKnowledgeSliceService.lambdaQuery().eq(AiKnowledgeSlice::getKnowledgeDocId, aiKnowledgeDoc.getId()).list();
        //删除DB分片
        aiKnowledgeSliceService.lambdaUpdate().eq(AiKnowledgeSlice::getKnowledgeDocId, aiKnowledgeDoc.getId()).remove();
        //删除向量
        List<String> vectorIds = knowledgeSlices.stream().map(AiKnowledgeSlice::getVectorId).toList();
        if(CollUtil.isEmpty(vectorIds)){
            return;
        }
        this.vectorStoreDelete(vectorStore,vectorIds);

    }

    /**
     * 功能描述:
     * 〈根据ID获取模型〉
     * @param modelId modelId
     * @return 正常返回:{@link AiModel}
     * @author 蝉鸣
     */
    public AiModel getModel(Long modelId) {
        return aiModelServiceImpl.getById(modelId);
    }

    /**
     * 功能描述:
     * 〈文档切片〉
     * @param storeFlag storeFlag
     * @return 正常返回:{@link AiStoreService}
     * @author 蝉鸣
     */
    public AiStoreService getAiStoreService(Integer storeFlag) {
        //获取向量数据库
        StoreFlagEnum storeByValue = BaseEnum.getEnumByValue(StoreFlagEnum.class, storeFlag,StoreFlagEnum.ELASTICSEARCH);
        AiStoreService aiStoreService = aiStoreFactory.getAiStoreService(storeByValue);
        if(ObjectUtil.isNull(aiStoreService)){
            throw AiChatExceptionEnum.ADD_NO_MODEL_SERVICE.getBaseException();
        }
        return aiStoreService;
    }

    /**
     * 功能描述:
     * 〈文档切片〉
     * @param aiModel aiModel
     * @param storeFlag storeFlag
     * @return 正常返回:{@link VectorStore}
     * @author 蝉鸣
     */
    public VectorStore getVectorStore(AiModel aiModel,Integer storeFlag) {
        //获取模型配置
        AiBaseProperties properties = BeanUtil.copyProperties(aiModel, AiBaseProperties.class);
        //获取向量模型
        ModelFlagEnum modelByValue = BaseEnum.getEnumByValue(ModelFlagEnum.class, aiModel.getModelFlag());
        AiModelService aiModelService = aiModelFactory.getAiModelService(modelByValue);
        EmbeddingModel embeddingModel = aiModelService.getEmbeddingModel(properties);
        //获取向量数据库
        AiStoreService aiStoreService = this.getAiStoreService(storeFlag);
        VectorStore vectorStore = aiStoreService.getVectorStore(embeddingModel);
        if(ObjectUtil.isNull(vectorStore)){
            throw AiChatExceptionEnum.ADD_NO_MODEL.getBaseException();
        }
        return vectorStore;
    }

    /**
     * 功能描述:
     * 〈解析文档〉
     * @param aiKnowledgeDoc aiKnowledgeDoc
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public String getContent(AiKnowledgeDoc aiKnowledgeDoc) {
        //文本内容
        if(ContentSourceEnum.TEXT.getValue().equals(aiKnowledgeDoc.getContentSource())){
            return aiKnowledgeDoc.getContent();
        }
        //系统在线文档
        if(ContentSourceEnum.ONLINE.getValue().equals(aiKnowledgeDoc.getContentSource())){
            return aiKnowledgeDoc.getContent();
        }
        //判断不同类型获取Resource
        Resource resource = this.getFileResource(aiKnowledgeDoc.getContentSource(),aiKnowledgeDoc.getContent());
        aiKnowledgeDoc.setDocName(resource.getFilename());
        //通过Tika进行文档解析
        TikaDocumentReader loader = new TikaDocumentReader(resource);
        List<Document> documents = loader.get();
        return documents.stream().map(Document::getText).collect(Collectors.joining(SymbolConst.NEW_LINE_1));
    }

    /**
     * 功能描述:
     * 〈获取不同类型的Resource〉
     * @return 正常返回:{@link Resource}
     * @author 蝉鸣
     */
    public Resource getFileResource(Integer docType, String content) {
        //1:本地文件
        if(ContentSourceEnum.FILE.getValue().equals(docType)){
            return new FileSystemResource(content);
        }else{
            ResourceLoader resourceLoader = new DefaultResourceLoader();
            return resourceLoader.getResource(content);
        }
    }

    /**
     * 功能描述:
     * 〈文档切片〉
     * @param content content
     * @return 正常返回:{@link List<Document>}
     * @author 蝉鸣
     */
    public List<Document> splitContent(String content, Integer segmentMaxTokens) {
        TextSplitter textSplitter = this.textSplitter(segmentMaxTokens);
        return textSplitter.apply(CollUtil.newArrayList(new Document(content)));
    }

    /**
     * 功能描述:
     * 〈文本分块〉
     * @param segmentMaxTokens segmentMaxTokens
     * @return 正常返回:{@link TextSplitter}
     * @author 蝉鸣
     */
    public TextSplitter textSplitter(Integer segmentMaxTokens) {
        return TokenTextSplitter.builder()
                .withChunkSize(segmentMaxTokens)
                // 忽略字符的截断
                .withMinChunkSizeChars(Integer.MAX_VALUE)
                // 允许的最小有效分段长度
                .withMinChunkLengthToEmbed(NumberConst.NUM_1)
                .withMaxNumChunks(Integer.MAX_VALUE)
                // 保留分隔符
                .withKeepSeparator(Boolean.TRUE)
                .build();
    }

    /**
     * 功能描述:
     * 〈获取知识库〉
     * @param knowledgeId knowledgeId
     * @return 正常返回:{@link AiKnowledge}
     * @author 蝉鸣
     */
    public AiKnowledge getKnowledgeById(Long knowledgeId) {
        if(ObjectUtil.isEmpty(knowledgeId)){
            //查询默认知识库
            return aiKnowledgeService.lambdaQuery()
                    .eq(AiKnowledge::getDelFlag, YesOrNoEnum.YES.getValue())
                    .last(MybatisPlusConst.LIMIT_1).one();
        }
        return aiKnowledgeService.getById(knowledgeId);
    }

    /**
     * 功能描述:
     * 〈文档切片〉
     * @param aiKnowledgeDoc aiKnowledgeDoc
     * @param documents documents
     * @author 蝉鸣
     */
    public void saveAiKnowledgeSlices(AiKnowledgeDoc aiKnowledgeDoc,List<Document> documents) {
        JTokkitTokenCountEstimator tokenCountEstimator = new JTokkitTokenCountEstimator();
        List<AiKnowledgeSlice> aiKnowledgeSlices = documents.stream().map(document -> {
            String documentText = document.getText();
            int estimate = tokenCountEstimator.estimate(documentText);
            //创建DB切片对象
            AiKnowledgeSlice aiKnowledgeSlice = new AiKnowledgeSlice();
            aiKnowledgeSlice.setId(IdUtil.getSnowflake().nextId());
            aiKnowledgeSlice.setKnowledgeId(aiKnowledgeDoc.getKnowledgeId());
            aiKnowledgeSlice.setKnowledgeDocId(aiKnowledgeDoc.getId());
            aiKnowledgeSlice.setVectorId(StrUtil.EMPTY);
            aiKnowledgeSlice.setVectorIndex(StrUtil.EMPTY);
            aiKnowledgeSlice.setContent(documentText);
            //设置填充对象
            documentMateData(aiKnowledgeSlice, document);
            return aiKnowledgeSlice;
        }).toList();
        //批量保存DB
        aiKnowledgeSliceService.saveBatch(aiKnowledgeSlices);
    }

    /**
     * 功能描述:
     * 〈更新切片向量ID〉
     * @param documents documents
     * @author 蝉鸣
     */
    public void updateAiKnowledgeSlices(List<Document> documents) {
        List<AiKnowledgeSlice> aiKnowledgeSlices = documents.stream().map(document -> {
            Map<String, Object> metadata = document.getMetadata();
            Object object = metadata.get(AiBaseConst.VECTOR_STORE_METADATA_KNOWLEDGE_SLICE_ID);
            Long dataId = Convert.toLong(object);
            return new AiKnowledgeSlice().setId(dataId).setVectorId(document.getId());
        }).toList();
        aiKnowledgeSliceService.updateBatchById(aiKnowledgeSlices);
    }

    /**
     * 功能描述:
     * 〈文档切片〉
     * @author 蝉鸣
     */
    public void documentMateData(AiKnowledgeSlice aiKnowledgeSlice,Document document) {
        document.getMetadata().put(AiBaseConst.VECTOR_STORE_METADATA_KNOWLEDGE_ID, aiKnowledgeSlice.getKnowledgeId().toString());
        document.getMetadata().put(AiBaseConst.VECTOR_STORE_METADATA_KNOWLEDGE_DOC_ID, aiKnowledgeSlice.getKnowledgeDocId().toString());
        document.getMetadata().put(AiBaseConst.VECTOR_STORE_METADATA_KNOWLEDGE_SLICE_ID, aiKnowledgeSlice.getId().toString());
    }

    /**
     * 功能描述:
     * 〈保存文档切片〉
     * @author 蝉鸣
     */
    public void vectorStoreSave(VectorStore vectorStore,List<Document> documents) {
        vectorStore.add(documents);
    }

    /**
     * 功能描述:
     * 〈删除文档切片〉
     * @author 蝉鸣
     */
    public void vectorStoreDelete(VectorStore vectorStore,List<String> documentVectorIds) {
        vectorStore.delete(documentVectorIds);
    }

    /**
     * 功能描述:
     * 〈构建搜索表达式〉
     * @author 蝉鸣
     */
    public Filter.Expression getFilterExpression(Long knowledgeId) {
        FilterExpressionBuilder builder = new FilterExpressionBuilder();
        return builder.eq(AiBaseConst.VECTOR_STORE_METADATA_KNOWLEDGE_ID, knowledgeId).build();
    }
}
