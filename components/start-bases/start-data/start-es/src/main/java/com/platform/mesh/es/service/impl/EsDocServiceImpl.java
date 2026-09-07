package com.platform.mesh.es.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.PageUtil;
import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ErrorCause;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.SumAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.ValueCountAggregate;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.mget.MultiGetResponseItem;
import co.elastic.clients.elasticsearch.core.msearch.MultiSearchResponseItem;
import co.elastic.clients.elasticsearch.core.msearch.RequestItem;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.es.constant.EsConst;
import com.platform.mesh.es.domain.bo.EsDocGetBO;
import com.platform.mesh.es.domain.bo.EsDocPutBO;
import com.platform.mesh.es.domain.dto.EsDocUGetDTO;
import com.platform.mesh.es.exception.EsExceptionEnum;
import com.platform.mesh.es.service.IEsDocService;
import com.platform.mesh.es.util.EsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * @description Es文档处理
 * @author 蝉鸣
 */
@Service
public class EsDocServiceImpl implements IEsDocService {

    private static final Logger log = LoggerFactory.getLogger(EsDocServiceImpl.class);

    @Autowired
    private ElasticsearchClient elasticsearchClient;


    /**
     * 功能描述:
     * 〈创建文档〉
     * @param esDocPutBO esDocPutBO
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    @Override
    public String createDocument(EsDocPutBO esDocPutBO){
        CreateRequest<Object> request = CreateRequest.of(builder -> builder
                .index(esDocPutBO.getIndexName())
                .id(StrUtil.toString(CollUtil.getFirst(esDocPutBO.getDataIds())))
                .document(esDocPutBO.getDocMap())
                .refresh(Refresh.True));
        try {
            CreateResponse createResponse = elasticsearchClient.create(request);
            return createResponse.result().jsonValue();
        } catch (Exception e) {
            if (isEsUnavailable(e)) {
                log.warn("Elasticsearch unavailable, skip create document. index={}", esDocPutBO.getIndexName(), e);
                return null;
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能描述:
     * 〈批量创建文档〉
     * @param esDocPutBOS esDocPutBOS
     * @author 蝉鸣
     */
    @Override
    public void createBatchDocument(List<EsDocPutBO> esDocPutBOS){
        if(CollUtil.isEmpty(esDocPutBOS)){
            return;
        }
        // 使用BulkRequest的构造器
        BulkRequest.Builder request = new BulkRequest.Builder();
        for(EsDocPutBO esDocPutBO : esDocPutBOS) {
            request.operations(l -> l
                    .index(i -> i
                            .index(esDocPutBO.getIndexName())
                            .id(StrUtil.toString(CollUtil.getFirst(esDocPutBO.getDataIds())))
                            .document(esDocPutBO.getDocMap())
                    )
            ).refresh(Refresh.True);
        }
        try {
            BulkResponse response = elasticsearchClient.bulk(request.build());
            if(response.errors()) {
                List<Map<String, ErrorCause>> errorPairs = response.items().stream()
                        .filter(item -> item.error() != null)
                        .map(item -> Map.of(item.index(), item.error()))
                        .collect(Collectors.toList());
                log.error("批量保存失败:{}",errorPairs);
                throw EsExceptionEnum.ES_DOC_CREATE.getBaseException();
            } else {
                log.info("批量保存成功");
            }
        } catch (Exception e) {
            if (isEsUnavailable(e)) {
                log.warn("Elasticsearch unavailable, skip batch create documents. size={}", esDocPutBOS.size(), e);
                return;
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能描述:
     * 〈修改文档〉
     * @param esDocPutBO esDocPutBO
     * @author 蝉鸣
     */
    @Override
    public void updateDocument(EsDocPutBO esDocPutBO){
       if(CollUtil.isEmpty(esDocPutBO.getDataIds())) {
           return;
       }
       if(esDocPutBO.getDataIds().size() > NumberConst.NUM_1){
           //批量处理
           updateBatchDocument(CollUtil.newArrayList(esDocPutBO));
       }else{
           //单个处理
           updateSingDocument(esDocPutBO);
       }
    }

    /**
     * 功能描述:
     * 〈修改文档〉
     * @param esDocPutBO esDocPutBO
     * @author 蝉鸣
     */
    public void updateSingDocument(EsDocPutBO esDocPutBO){
        UpdateRequest<Object, Object> request = UpdateRequest.of(builder ->builder
                .index(esDocPutBO.getIndexName())
                .id(StrUtil.toString(CollUtil.getFirst(esDocPutBO.getDataIds())))
                .doc(esDocPutBO.getDocMap())
                .refresh(Refresh.True));
        try {
            UpdateResponse<Object> response = elasticsearchClient.update(request, Object.class);
        } catch (Exception e) {
            if (isEsUnavailable(e)) {
                log.warn("Elasticsearch unavailable, skip update document. index={}", esDocPutBO.getIndexName(), e);
                return;
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能描述:
     * 〈批量修改文档〉
     * @param esDocPutBOS esDocPutBOS
     * @author 蝉鸣
     */
    @Override
    public void updateBatchDocument(List<EsDocPutBO> esDocPutBOS){
        if(CollUtil.isEmpty(esDocPutBOS)) {
            return;
        }
        // 使用BulkRequest的构造器
        BulkRequest.Builder request = new BulkRequest.Builder();
        for (EsDocPutBO esDocPutBO : esDocPutBOS) {
            if(CollUtil.isEmpty(esDocPutBO.getDataIds())) {
                continue;
            }
            for(Object dataId : esDocPutBO.getDataIds()) {
                request.operations(l -> l
                        .update(i->i
                                .index(esDocPutBO.getIndexName())
                                .id(StrUtil.toString(dataId))
                                .action(a->{
                                            a.docAsUpsert(Boolean.TRUE)
                                            .doc(esDocPutBO.getDocMap())
                                            .docAsUpsert(Boolean.TRUE);
                                            return a;
                                        }
                                )
                        )
                ).refresh(Refresh.True);
            }
        }
        try {
            BulkResponse response = elasticsearchClient.bulk(request.build());
            if(response.errors()) {
                // 仅抛出非版本冲突的错误
                List<Map<String, ErrorCause>> errorPairs = response.items().stream()
                        .filter(item -> item.error() != null)
                        .filter(item->ObjectUtil.isNotNull(item.error().type()) && !item.error().type().equals(EsConst.BULK_RESPONSE_ERROR_VERSION))
                        .map(item -> Map.of(item.index(), item.error()))
                        .collect(Collectors.toList());
                if(CollUtil.isNotEmpty(errorPairs)) {
                    log.error("批量更新失败:{}",errorPairs);
                }else{
                    log.info("批量更新完毕");
                }
            } else {
                log.info("批量更新成功");
            }
        } catch (Exception e) {
            if (isEsUnavailable(e)) {
                log.warn("Elasticsearch unavailable, skip batch update documents. size={}", esDocPutBOS.size(), e);
                return;
            }
            log.info(e.getMessage());
            throw EsExceptionEnum.ES_DOC_UPDATE.getBaseException();
        }
    }

    /**
     * 功能描述:
     * 〈删除文档〉
     * @param esDocPutBO esDocPutBO
     * @author 蝉鸣
     */
    @Override
    public void deleteDocument(EsDocPutBO esDocPutBO){
        if(CollUtil.isEmpty(esDocPutBO.getDataIds())) {
            return;
        }
        if(esDocPutBO.getDataIds().size() > NumberConst.NUM_1){
            //批量处理
            deleteBatchDocument(esDocPutBO);
        }else{
            //单个处理
            deleteSingDocument(esDocPutBO);
        }
    }

    /**
     * 功能描述:
     * 〈删除文档〉
     * @param esDocPutBO esDocPutBO
     * @author 蝉鸣
     */
    public void deleteSingDocument(EsDocPutBO esDocPutBO){
        DeleteRequest request = DeleteRequest.of(builder -> builder
                .index(esDocPutBO.getIndexName())
                .id(StrUtil.toString(CollUtil.getFirst(esDocPutBO.getDataIds())))
                .refresh(Refresh.True));
        try {
            DeleteResponse response = elasticsearchClient.delete(request);
        } catch (Exception e) {
            if (isEsUnavailable(e)) {
                log.warn("Elasticsearch unavailable, skip delete document. index={}", esDocPutBO.getIndexName(), e);
                return;
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能描述:
     * 〈批量删除文档〉
     * @param esDocPutBO esDocPutBO
     * @author 蝉鸣
     */
    public void deleteBatchDocument(EsDocPutBO esDocPutBO){
        BulkRequest.Builder request = new BulkRequest.Builder();
        for(Object dataId : esDocPutBO.getDataIds()) {
            request.operations(l -> l
                    .delete(i -> i
                            .index(esDocPutBO.getIndexName())
                            .id(StrUtil.toString(dataId))
                    )
            ).refresh(Refresh.True);
        }
        try {
            BulkResponse response = elasticsearchClient.bulk(request.build());
            if(response.errors()) {
                List<Map<String, ErrorCause>> errorPairs = response.items().stream()
                        .filter(item -> item.error() != null)
                        .map(item -> Map.of(item.index(), item.error()))
                        .collect(Collectors.toList());
                log.error("批量删除失败:{}",errorPairs);
            } else {
                log.info("批量删除成功");
            }
        } catch (Exception e) {
            if (isEsUnavailable(e)) {
                log.warn("Elasticsearch unavailable, skip batch delete documents. index={}", esDocPutBO.getIndexName(), e);
                return;
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能描述:
     * 〈是否存在文档〉
     * @param esDocPutBO esDocPutBO
     * @author 蝉鸣
     */
    public boolean existDocument(EsDocPutBO esDocPutBO){
        ExistsRequest request = ExistsRequest.of(builder -> builder.index(esDocPutBO.getIndexName()).id(StrUtil.toString(CollUtil.getFirst(esDocPutBO.getDataIds()))));
        try {
            BooleanResponse response = elasticsearchClient.exists(request);
            return response.value();
        } catch (Exception e) {
            if (isEsUnavailable(e)) {
                log.warn("Elasticsearch unavailable, return document not exists. index={}", esDocPutBO.getIndexName(), e);
                return false;
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能描述:
     * 〈是否存在文档〉
     * @param moduleIndex moduleIndex
     * @param docMap docMap
     * @author 蝉鸣
     */
    public boolean existDocument(String moduleIndex, Map<String, Object> docMap){
        if(ObjectUtil.isEmpty(moduleIndex) || CollUtil.isEmpty(docMap)) {
            return false;
        }
        BoolQuery.Builder builderBool = EsUtil.getSearchBuilderBool();
        List<Query> mustQueries = new ArrayList<>();
        List<Query> mustNotQueries = new ArrayList<>();
        docMap.forEach((key,value)->{
            Query query = QueryBuilders.term(eq -> {
                eq.field(key);
                eq.value(StrUtil.toString(value));
                return eq;
            });
            if(StrConst.ID.equals(key)) {
                mustNotQueries.add(query);
            }else{
                mustQueries.add(query);
            }

        });
        builderBool.must(mustQueries);
        builderBool.mustNot(mustNotQueries);
        try {
            SearchResponse<Object> response = elasticsearchClient.search(s -> s
                            .index(moduleIndex)
                            .query(q -> q.bool(bool -> builderBool))
                            .size(NumberConst.NUM_0),
                    Object.class
            );
            TotalHits totalHits = response.hits().total();
            if(ObjectUtil.isNull(totalHits)){
                return false;
            }
            long total = response.hits().total().value();
            return total >= NumberConst.NUM_1;
        } catch (Exception e) {
            if (isEsUnavailable(e)) {
                log.warn("Elasticsearch unavailable, return document not exists. index={}", moduleIndex, e);
                return false;
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能描述:
     * 〈是否存在文档〉
     * @param moduleIndex moduleIndex
     * @param docMap docMap
     * @author 蝉鸣
     */
    public Object getExistData(String moduleIndex, Map<String, Object> docMap){
        if(ObjectUtil.isEmpty(moduleIndex) || CollUtil.isEmpty(docMap)) {
            return null;
        }
        BoolQuery.Builder builderBool = EsUtil.getSearchBuilderBool();
        List<Query> mustQueries = new ArrayList<>();
        List<Query> mustNotQueries = new ArrayList<>();
        docMap.forEach((key,value)->{
            Query query = QueryBuilders.matchPhrase(eq -> {
                eq.field(key);
                eq.query(StrUtil.toString(value));
                return eq;
            });
            if(StrConst.ID.equals(key)) {
                mustNotQueries.add(query);
            }else{
                mustQueries.add(query);
            }

        });
        builderBool.must(mustQueries);
        builderBool.mustNot(mustNotQueries);
        try {
            SearchResponse<Object> response = elasticsearchClient.search(s -> s
                            .index(moduleIndex)
                            .query(q -> q.bool(bool -> builderBool))
                            .source(source -> source.filter(f -> f.excludes(excludeFields())))
                            .size(NumberConst.NUM_1),
                    Object.class
            );
            List<Hit<Object>> hits = response.hits().hits();
            if(CollUtil.isEmpty(hits)){
                return null;
            }
            return hits.getFirst().source();
        } catch (Exception e) {
            if (isEsUnavailable(e)) {
                log.warn("Elasticsearch unavailable, return null existing data. index={}", moduleIndex, e);
                return null;
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能描述:
     * 〈获取时间快照Pit〉
     * @param indexName indexName
     * @param keepAlive keepAlive
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public String createPitId(String indexName ,Integer keepAlive){
        try {
            OpenPointInTimeResponse openPointInTime = elasticsearchClient.openPointInTime(builder ->
                    builder.index(indexName)
                           .keepAlive(keep -> keep.offset(keepAlive))
            );
            return openPointInTime.id();
        } catch (Exception e) {
            if (isEsUnavailable(e)) {
                log.warn("Elasticsearch unavailable, skip open PIT. index={}", indexName, e);
                return null;
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能描述:
     * 〈关闭时间快照〉
     * @param pitId pitId
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    public Boolean closePitById(String pitId){
        try {
            ClosePointInTimeResponse closePointInTime = elasticsearchClient
                    .closePointInTime(builder -> builder.id(pitId));
            return closePointInTime.succeeded();
        } catch (Exception e) {
            if (isEsUnavailable(e)) {
                log.warn("Elasticsearch unavailable, skip close PIT.", e);
                return false;
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能描述:
     * 〈获取文档〉
     * @param indexName indexName
     * @param dataId dataId
     * @return 正常返回:{@link Object}
     * @author 蝉鸣
     */
    public Object getDocumentById(String indexName,Object dataId){
        GetRequest request = GetRequest.of(builder -> builder
                .index(indexName)
                .id(StrUtil.toString(dataId))
                .sourceExcludes(excludeFields())
        );
        try {
            GetResponse<Object> userGetResponse = elasticsearchClient.get(request, Object.class);
            return userGetResponse.source();
        } catch (Exception e) {
            if (isEsUnavailable(e)) {
                log.warn("Elasticsearch unavailable, return null document. index={}, dataId={}", indexName, dataId, e);
                return null;
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能描述:
     * 〈获取文档〉
     * @param indexName indexName
     * @param dataIds dataIds
     * @return 正常返回:{@link Object}
     * @author 蝉鸣
     */
    @Override
    public List<Object> getDocumentByIds(String indexName, List<?> dataIds) {
        List<String> idsStr = dataIds.stream().map(StrUtil::toString).toList();
        // 构建 mget 请求
        MgetRequest request = MgetRequest.of(builder -> builder
                .index(indexName)
                .ids(idsStr)
                .sourceExcludes(excludeFields())
        );
        try {
            MgetResponse<Object> mGet = elasticsearchClient.mget(request, Object.class);
            // 处理结果
            return mGet.docs().stream()
                    .filter(MultiGetResponseItem::isResult)
                    .map(result -> result.result().source()).toList();
        } catch (Exception e) {
            if (isEsUnavailable(e)) {
                log.warn("Elasticsearch unavailable, return empty documents. index={}, size={}", indexName, dataIds.size(), e);
                return CollUtil.newArrayList();
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能描述:
     * 〈获取文档〉
     * @param indexName indexName
     * @param dataName dataName
     * @param dataValue dataValue
     * @return 正常返回:{@link Object}
     * @author 蝉鸣
     */
    public String getDocumentIdByName(String indexName,String dataName,String dataValue){
        //绑定索引
        SearchRequest.Builder searchBuilder = EsUtil.getSearchBuilder(indexName);
        try {
            searchBuilder.query(builder -> builder.term(m -> m.field(dataName).value(dataValue)));
            searchBuilder.source(s -> s.filter(f -> f.excludes(excludeFields())));
            SearchResponse<Object> response = elasticsearchClient.search(searchBuilder.build(), Object.class);
            if(CollUtil.isEmpty(response.hits().hits())){
                return null;
            }else{
                Hit<Object> first = CollUtil.getFirst(response.hits().hits());
                return first.id();
            }
        } catch (Exception e) {
            if (isEsUnavailable(e)) {
                log.warn("Elasticsearch unavailable, return null document id. index={}", indexName, e);
                return null;
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能描述:
     * 〈检索文档〉
     * @param esDocGetBO esDocGetBO
     * @author 蝉鸣
     */
    public PageVO<Object> searchDocument(EsDocGetBO esDocGetBO){
        //返回分页信息
        PageVO<Object> pageVO = new PageVO<>();
        //绑定索引
        SearchRequest.Builder searchBuilder = EsUtil.getSearchBuilder(esDocGetBO.getIndexName());
        //设置分页
        EsUtil.setSearchBuilderPage(searchBuilder,esDocGetBO);
        //设置筛选条件
        EsUtil.setSearchBuilderQuery(searchBuilder,esDocGetBO.getBoolBuilder(),esDocGetBO.getQueryMap());
        //设置排序
        EsUtil.setSearchBuilderSort(searchBuilder,esDocGetBO.getSortOptions());
        //true 全量命中数,false <10000 命中数
        searchBuilder.trackTotalHits(builder -> builder.enabled(Boolean.TRUE));
        //分析条件
        searchBuilder.aggregations(esDocGetBO.getAggrMap());
        //过滤字段
        searchBuilder.source(s -> s.filter(f -> f.excludes(excludeFields())));
        try {
            //获取响应值
            SearchResponse<Object> response = elasticsearchClient.search(searchBuilder.build(), Object.class);
            //获取查询后的命中条数：其中包括 TotalHitsRelation 以及 total
            TotalHits total = response.hits().total();
            assert total != null;
            //获取结果集
            List<Hit<Object>> hits = response.hits().hits();
            Map<String, Aggregate> aggregations = response.aggregations();
            //如果总数大于10000，并且当前查询处理10000条之内的 需要生成快照，保证大于10000的数据查询序列幂等
            String pitId = this.openPit(esDocGetBO, total.value());
            //获取最后排序快照
            List<Map<String, String>> searchAfter = this.getSearchAfter(pitId, hits);
            //封装分页结果集
            pageVO.setCurrent(esDocGetBO.getPageNum());
            pageVO.setSize(esDocGetBO.getPageSize());
            pageVO.setTotal(total.value());
            pageVO.setPages(PageUtil.totalPage(pageVO.getTotal(), Integer.parseInt(StrUtil.toString(pageVO.getSize()))));
            pageVO.setRecords(hits.stream().map(Hit::source).toList());
            pageVO.setPit(pitId);
            pageVO.setSearchAfter(searchAfter);
            //组装分析值
            pageVO.setAggregations(this.getAggregations(aggregations));
            //如果总数小于10000，或者如果总数大于10000，又回到<10000的查询页面则关闭快照
            this.closePit(esDocGetBO, total.value());
        } catch (Exception e) {
            if (isEsUnavailable(e)) {
                log.warn("Elasticsearch unavailable, return empty search page. index={}", esDocGetBO.getIndexName(), e);
                return emptyPage(esDocGetBO.getPageNum(), esDocGetBO.getPageSize());
            }
            throw new RuntimeException(e);
        }
        return pageVO;
    }


    /**
     * 功能描述:
     * 〈联合查询〉
     * @param esDocUGetDTO uniQueryBO
     * @author 蝉鸣
     */
    public PageVO<Object> uniDocument(EsDocUGetDTO esDocUGetDTO){
        //返回分页信息
        PageVO<Object> pageVO = new PageVO<>();
        if(ObjectUtil.isEmpty(esDocUGetDTO.getSearchValue()) || CollUtil.isEmpty(esDocUGetDTO.getIndexFieldMap())) {
            return pageVO;
        }
        //联合查询仅限小数据量查询,避免大数据
        List<RequestItem> requests = new ArrayList<>();
        esDocUGetDTO.getIndexFieldMap().forEach((key, value)->{
            PageUtil.setOneAsFirstPageNo();
            int start = PageUtil.getStart(esDocUGetDTO.getPageNum(), esDocUGetDTO.getPageSize());
            requests.add(RequestItem.of(b -> b
                    .header(header -> header
                            //设置索引
                            .index(key)
                    )
                    .body(body -> body
                            //设置返回字段
                            .source(source -> source.filter(f -> {
                                List<String> sourceFiled = new ArrayList<>(StrConst.getFixFiled());
                                sourceFiled.addAll(value);
                                f.includes(sourceFiled);
                                return f;
                            }))
                            .from(start)
                            .size(esDocUGetDTO.getPageSize())
                            //设置查询条件
                            .query(query -> query.bool(bool->{
                                List<Query> queries = value.stream()
                                        .map(field ->
                                                Query.of(filedQuery ->
                                                        //采用短语匹配
                                                        filedQuery.matchPhrase(t -> t.field(field).query(esDocUGetDTO.getSearchValue()))
                                                    )
                                            )
                                        .toList();
                                return bool.should(queries);
                            }))
                    )
            ));

        });
        MsearchRequest msearchRequest = MsearchRequest.of(builder -> builder.searches(requests));
        try {
            MsearchResponse<Object> msearchResponse = elasticsearchClient.msearch(msearchRequest, Object.class);
            List<MultiSearchResponseItem<Object>> responses = msearchResponse.responses();
            AtomicLong totalHits = new AtomicLong();
            List<Hit<Object>> hits = responses.stream().flatMap(m -> {
                totalHits.addAndGet(Objects.requireNonNull(m.result().hits().total()).value());
                return m.result().hits().hits().stream();
            }).toList();
            log.info(hits.toString());
            //封装分页结果集
            pageVO.setCurrent(esDocUGetDTO.getPageNum());
            pageVO.setSize(esDocUGetDTO.getPageSize());
            pageVO.setTotal(totalHits.get());
            pageVO.setPages(PageUtil.totalPage(pageVO.getTotal(), Integer.parseInt(StrUtil.toString(pageVO.getSize()))));
            pageVO.setRecords(hits.stream().map(Hit::source).toList());
            return pageVO;
        } catch (Exception e) {
            if (isEsUnavailable(e)) {
                log.warn("Elasticsearch unavailable, return empty multi search page.", e);
                return emptyPage(esDocUGetDTO.getPageNum(), esDocUGetDTO.getPageSize());
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * 功能描述:
     * 〈打开时间快照〉
     * @param esDocGetBO esDocGetBO
     * @param total total
     * @author 蝉鸣
     */
    private String openPit(EsDocGetBO esDocGetBO,Long total){
        //如果总数大于10000，并且当前查询处理10000条之内的 需要生成快照，保证大于10000的数据查询序列幂等
        if((total > NumberConst.NUM_10000)
                && (esDocGetBO.getPageNum() * esDocGetBO.getPageSize()) < NumberConst.NUM_10000
                && ((esDocGetBO.getPageNum()+ NumberConst.NUM_1) * esDocGetBO.getPageSize()) > NumberConst.NUM_10000
        ){
            //开启快照
            return this.createPitId(esDocGetBO.getIndexName(), NumberConst.NUM_1);
        }else{
            return esDocGetBO.getPit();
        }
    }

    /**
     * 功能描述:
     * 〈关闭时间快照〉
     * @param esDocGetBO esDocGetBO
     * @param total total
     * @author 蝉鸣
     */
    private void closePit(EsDocGetBO esDocGetBO,Long total){
        //如果总数小于10000，或者如果总数大于10000，又回到<10000的查询页面则关闭快照
        if(ObjectUtil.isNotEmpty(esDocGetBO.getPit()) && (
                (total < NumberConst.NUM_10000)
                        || ((esDocGetBO.getPageNum()+ NumberConst.NUM_1) * esDocGetBO.getPageSize()) < NumberConst.NUM_10000
        )){
            //关闭快照
            this.closePitById(esDocGetBO.getPit());
        }
    }

    /**
     * 功能描述:
     * 〈搜索后置处理〉
     * @param pitId pitId
     * @param hits hits
     * @author 蝉鸣
     */
    private List<Map<String, String>> getSearchAfter(String pitId, List<Hit<Object>> hits){
        //没有开启快照
        if(StrUtil.isBlank(pitId)){
            return null;
        }
        //开启快照下获取最后排序快照
        if(CollUtil.isNotEmpty(hits)){
            return CollUtil.getLast(hits).sort().stream().map(fieldValue -> {
                Map<String, String> map = new HashMap<>();
                map.put(fieldValue._toJsonString(), fieldValue._get().toString());
                return map;
            }).toList();
        }
        return null;
    }

    /**
     * 功能描述:
     * 〈搜索后置处理〉
     * @param aggregates aggregates
     * @author 蝉鸣
     */
    private Map<String, Object> getAggregations(Map<String, Aggregate> aggregates){
        Map<String, Object> aggregationMap = new HashMap<>();
        //没有开启快照
        if(CollUtil.isEmpty(aggregates)){
            return aggregationMap;
        }
        aggregates.forEach((field,aggregate)->{
            if(field.endsWith(EsConst.MAPPING_SUFFIX_NUM) || field.endsWith(EsConst.MAPPING_SUFFIX_MONEY)){
                SumAggregate sum = aggregate.sum();
                aggregationMap.put(field,sum.value());
            }else{
                ValueCountAggregate valueCount = aggregate.valueCount();
                aggregationMap.put(field,valueCount.value());
            }
        });
        return aggregationMap;
    }

    /**
     * 功能描述:
     * 〈排除字段〉
     * @return 正常返回:{@link List<String>}
     * @author 蝉鸣
     */
    private List<String> excludeFields(){
        List<String> fields = CollUtil.newArrayList();
        fields.add(StrConst.ID_UNI);
        fields.add(StrConst.NAME_UNI);
        return fields;
    }

    /**
     * 功能描述:
     * 〈构建空分页结果〉
     * @param current current
     * @param size size
     * @return 正常返回:{@link PageVO<Object>}
     * @author Codex
     */
    private PageVO<Object> emptyPage(long current, long size) {
        PageVO<Object> pageVO = new PageVO<>();
        pageVO.setCurrent(current);
        pageVO.setSize(size);
        pageVO.setTotal(NumberConst.NUM_0);
        pageVO.setPages(NumberConst.NUM_0);
        pageVO.setRecords(CollUtil.newArrayList());
        pageVO.setAggregations(new HashMap<>());
        return pageVO;
    }

    /**
     * 功能描述:
     * 〈判断ES是否不可用〉
     * @param throwable throwable
     * @return 正常返回:{@link boolean}
     * @author Codex
     */
    private boolean isEsUnavailable(Throwable throwable) {
        Throwable cause = throwable;
        while (cause != null) {
            if (cause instanceof java.net.ConnectException) {
                return true;
            }
            String message = cause.getMessage();
            if (StrUtil.isNotBlank(message)
                    && (message.contains("Connection refused")
                    || message.contains("Connect to http")
                    || message.contains("No route to host")
                    || message.contains("Connection timed out"))) {
                return true;
            }
            cause = cause.getCause();
        }
        return false;
    }



}
