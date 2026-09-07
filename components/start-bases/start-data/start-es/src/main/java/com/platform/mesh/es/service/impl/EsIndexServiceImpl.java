package com.platform.mesh.es.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch._types.analysis.*;
import co.elastic.clients.elasticsearch._types.mapping.*;
import co.elastic.clients.elasticsearch.indices.*;
import co.elastic.clients.elasticsearch.indices.get_mapping.IndexMappingRecord;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import co.elastic.clients.util.NamedValue;
import com.platform.mesh.core.constants.DateConst;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.es.constant.EsConst;
import com.platform.mesh.es.domain.bo.EsIndexMappingBO;
import com.platform.mesh.es.domain.bo.EsIndexSettingBO;
import com.platform.mesh.es.service.IEsIndexService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description Es索引处理
 * @author 蝉鸣
 */
@Service
public class EsIndexServiceImpl implements IEsIndexService {

    private static final Logger log = LoggerFactory.getLogger(EsIndexServiceImpl.class);


    @Autowired
    private ElasticsearchClient elasticsearchClient;

    /**
     * 功能描述:
     * 〈创建索引〉
     * @param indexName indexName
     * @return 正常返回:{@link boolean}
     * @author 蝉鸣
     */
    public boolean createIndex(String indexName){
        if(StrUtil.isBlank(indexName)){
            return false;
        }
        //查询是否存在当前模块索引
        boolean existIndex = existIndex(CollUtil.newArrayList(indexName));
        if(existIndex){
            return true;
        }
        //初始化索引配置
        CreateIndexRequest request = CreateIndexRequest.of(builder -> builder
                .index(indexName)
                .settings(setting->setting.analysis(analyzer -> analyzer
                                        .analyzer(this.setAnalyzer())
                                        .normalizer(this.setNormalizer())
                                )
                                .maxResultWindow(10000)

                ));
        try {
            CreateIndexResponse createIndexResponse = elasticsearchClient.indices().create(request);
            return createIndexResponse.acknowledged();
        } catch (Exception e) {
            if((e instanceof ElasticsearchException)) {
                if(ObjectUtil.isNotNull(((ElasticsearchException) e).response())){
                    String type = ((ElasticsearchException) e).response().error().type();
                    if (ObjectUtil.isNotNull(type) && type.equals("resource_already_exists_exception")) {
                        return true;
                    }
                }
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能描述:
     * 〈获取索引〉
     * @param indexNames indexNames
     * @return 正常返回:{@link Map}
     * @author 蝉鸣
     */
    public Map<String, IndexState> getIndex(List<String> indexNames){
        GetIndexRequest request = GetIndexRequest.of(builder -> builder.index(indexNames));
        try {
            GetIndexResponse getIndexResponse = elasticsearchClient.indices().get(request);
            return getIndexResponse.indices();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能描述:
     * 〈索引是否存在〉
     * @param indexNames indexNames
     * @return 正常返回:{@link boolean}
     * @author 蝉鸣
     */
    public boolean existIndex(List<String> indexNames){
        ExistsRequest request = ExistsRequest.of(builder -> builder.index(indexNames));
        try {
            BooleanResponse response = elasticsearchClient.indices().exists(request);
            return response.value();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能描述:
     * 〈删除索引〉
     * @param indexNames indexNames
     * @return 正常返回:{@link boolean}
     * @author 蝉鸣
     */
    public boolean deleteIndex(List<String> indexNames){
        DeleteIndexRequest request = DeleteIndexRequest.of(builder -> builder.index(indexNames));
        try {
            DeleteIndexResponse response = elasticsearchClient.indices().delete(request);
            return response.acknowledged();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能描述:
     * 〈设置索引配置〉
     * @param esIndexSettingBO esIndexSettingBO
     * @return 正常返回:{@link boolean}
     * @author 蝉鸣
     */
    public boolean setSetting(EsIndexSettingBO esIndexSettingBO){
        PutIndicesSettingsRequest request = PutIndicesSettingsRequest.of(builder -> builder
                .index(esIndexSettingBO.getIndexName())
                .settings(y -> y
                        .analysis(analyzer -> analyzer
                                .analyzer(this.setAnalyzer())
                                .normalizer(this.setNormalizer())
                        )
                        .maxResultWindow(esIndexSettingBO.getMaxResultWindow())
                        )
        );
        try {
            PutIndicesSettingsResponse putSettingResponse = elasticsearchClient.indices().putSettings(request);
            return putSettingResponse.acknowledged();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能描述:
     * 〈设置索引映射〉
     * @param esIndexMappingBO esIndexMappingBO
     * @return 正常返回:{@link boolean}
     * @author 蝉鸣
     */
    public boolean setMapping(EsIndexMappingBO esIndexMappingBO){
        PutMappingRequest request = PutMappingRequest.of(builder -> builder
                .index(esIndexMappingBO.getIndexName())
                .dynamic(DynamicMapping.True)
                //dynamicDateFormats优先级最高,匹配后将固定格式无法多格式解析时间和DynamicTemplate动态模板中时间解析冲突
//                .dynamicDateFormats(CollUtil.toList(DateConst.PARSE_PATTERNS))
                .dynamicTemplates(this.setDynamicTemplate())
                .properties(esIndexMappingBO.getProperties()));
        try {
            PutMappingResponse putMappingResponse = elasticsearchClient.indices().putMapping(request);
            return putMappingResponse.acknowledged();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能描述:
     * 〈获取索引映射〉
     * @param indexNames indexNames
     * @return 正常返回:{@link Map}
     * @author 蝉鸣
     */
    public Map<String, IndexMappingRecord> getMapping(List<String> indexNames){
        GetMappingRequest request = GetMappingRequest.of(builder -> builder.index(indexNames));
        try {
            GetMappingResponse response = elasticsearchClient.indices().getMapping(request);
            return response.mappings();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 功能描述:
     * 〈设置索引分词器配置〉
     * @return 正常返回:{@link Map}
     * @author 蝉鸣
     */
    public Map<String, Analyzer> setAnalyzer(){
        Map<String, Analyzer> analyzerMap = new HashMap<>() {};
        IcuAnalyzer.Builder builder = new IcuAnalyzer.Builder();
        builder.mode(IcuNormalizationMode.Compose);
        builder.method(IcuNormalizationType.NfkcCf);
        analyzerMap.put(EsConst.ANALYZER_ICU,builder.build()._toAnalyzer());
        return analyzerMap;
    }

    /**
     * 功能描述:
     * 〈设置索引规范化器配置〉
     * @return 正常返回:{@link Map}
     * @author 蝉鸣
     */
    public Map<String, Normalizer> setNormalizer(){
        Map<String, Normalizer> normalizerMap = new HashMap<>() {};
        Normalizer.Builder builder = new Normalizer.Builder();
        builder.custom(custom->custom.charFilter("[]").filter("lowercase"));
        builder.lowercase(new LowercaseNormalizer.Builder().build());
        normalizerMap.put(EsConst.NORMALIZER_LOWERCASE,builder.build());
        return normalizerMap;
    }

    /**
     * 功能描述:
     * 〈设置索引规范化器配置〉
     * @return 正常返回:{@link List<NamedValue<DynamicTemplate>>}
     * @author 蝉鸣
     */
    public List<NamedValue<DynamicTemplate>> setDynamicTemplate(){
        List<NamedValue<DynamicTemplate>> templates = CollUtil.newArrayList();
        //模板具有顺序,动态字段将根据从上到下解析，匹配后返回不再向下继续匹配
        templates.add(setIdDynamicTemplate());
        templates.add(setNumDynamicTemplate());
        templates.add(setDateDynamicTemplate());
        templates.add(setListDynamicTemplate());
        templates.add(setTextDynamicTemplate());
        templates.add(setKeyDynamicTemplate());
        return templates;
    }

    /**
     * 功能描述:
     * 〈设置字符串转Long类型〉
     * @return 正常返回:{@link Map}
     * @author 蝉鸣
     */
    public NamedValue<DynamicTemplate> setIdDynamicTemplate(){
        //文本映射
        Property property = LongNumberProperty.of(builder -> builder)._toProperty();
        DynamicTemplate dynamicTemplate = DynamicTemplate.of(builder -> builder
                .matchMappingType(EsConst.MAPPING_TYPE_STR)
                .match(EsConst.MAPPING_MATCH_ID)
                .mapping(property)
        );
        return NamedValue.of(EsConst.MAPPING_TEMP_LONG, dynamicTemplate);
    }

    /**
     * 功能描述:
     * 〈设置字符串转文本类型〉
     * @return 正常返回:{@link NamedValue<DynamicTemplate>}
     * @author 蝉鸣
     */
    public NamedValue<DynamicTemplate> setTextDynamicTemplate(){
        //文本映射
        Property property = TextProperty.of(builder -> builder
                .analyzer(EsConst.ANALYZER_ICU)
                .fields(EsConst.KEYWORD, field -> {
                    field._custom(EsConst.KEYWORD, JSONUtil.createObj().putOpt(EsConst.MAPPING_TYPE, EsConst.KEYWORD).putOpt(EsConst.IGNORE_ABOVE, NumberConst.NUM_256));
                    return field;
                })
        )._toProperty();
        DynamicTemplate dynamicTemplate = DynamicTemplate.of(builder -> builder
                .matchMappingType(EsConst.MAPPING_TYPE_STR)
                .match(EsConst.MAPPING_MATCH_TEXT,EsConst.MAPPING_MATCH_TEXTAREA,EsConst.MAPPING_MATCH_TEXT_MULTI,EsConst.MAPPING_PREFIX_SIGN)
                .mapping(property)
        );
        return NamedValue.of(EsConst.MAPPING_TEMP_TEXT, dynamicTemplate);
    }

    /**
     * 功能描述:
     * 〈设置字符串转关键字类型〉
     * @return 正常返回:{@link NamedValue<DynamicTemplate>}
     * @author 蝉鸣
     */
    public NamedValue<DynamicTemplate> setKeyDynamicTemplate(){
        //文本映射
        Property property = KeywordProperty.of(builder -> builder)._toProperty();
        DynamicTemplate dynamicTemplate = DynamicTemplate.of(builder -> builder
                .matchMappingType(EsConst.MAPPING_TYPE_STR)
                .mapping(property)
        );
        return NamedValue.of(EsConst.MAPPING_TEMP_KEY, dynamicTemplate);
    }

    /**
     * 功能描述:
     * 〈设置字符串转数字格式类型〉
     * @return 正常返回:{@link NamedValue<DynamicTemplate>}
     * @author 蝉鸣
     */
    public NamedValue<DynamicTemplate> setNumDynamicTemplate(){
        //文本映射
        Property property = DoubleNumberProperty.of(builder -> builder)._toProperty();
        DynamicTemplate dynamicTemplate = DynamicTemplate.of(builder -> builder
                .matchMappingType(EsConst.MAPPING_TYPE_OBJ)
                .match(EsConst.MAPPING_MATCH_NUM,EsConst.MAPPING_MATCH_MONEY)
                .mapping(property)
        );
        return NamedValue.of(EsConst.MAPPING_TEMP_NUM, dynamicTemplate);
    }

    /**
     * 功能描述:
     * 〈设置字符串转日期格式类型〉
     * @return 正常返回:{@link NamedValue<DynamicTemplate>}
     * @author 蝉鸣
     */
    public NamedValue<DynamicTemplate> setDateDynamicTemplate(){
        //文本映射
        String join = String.join(SymbolConst.OR, DateConst.PARSE_PATTERNS);
        Property property = DateProperty.of(builder -> {
            builder.format(join);
            return builder;
        })._toProperty();
        DynamicTemplate dynamicTemplate = DynamicTemplate.of(builder -> builder
                .matchMappingType(EsConst.MAPPING_TYPE_STR)
                .match(EsConst.MAPPING_MATCH_DATE,EsConst.MAPPING_MATCH_TIME)
                .mapping(property)
        );
        return NamedValue.of(EsConst.MAPPING_TEMP_TIME, dynamicTemplate);
    }

    /**
     * 功能描述:
     * 〈设置字符串转JSON格式类型〉
     * @return 正常返回:{@link NamedValue<DynamicTemplate>}
     * @author 蝉鸣
     */
    public NamedValue<DynamicTemplate> setListDynamicTemplate(){
        //文本映射
        Property property = FlattenedProperty.of(builder -> builder)._toProperty();
        DynamicTemplate dynamicTemplate = DynamicTemplate.of(builder -> builder
                .matchMappingType(EsConst.MAPPING_TYPE_OBJ)
                .match(EsConst.MAPPING_MATCH_JSON,EsConst.MAPPING_MATCH_ARRAY)
                .mapping(property)
        );
        return NamedValue.of(EsConst.MAPPING_TEMP_JSON, dynamicTemplate);
    }

}
