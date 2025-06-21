package com.platform.mesh.es.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.PageUtil;
import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ErrorCause;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.es.constant.EsConst;
import com.platform.mesh.es.domain.bo.EsDocGetBO;
import com.platform.mesh.es.domain.bo.EsDocPutBO;
import com.platform.mesh.es.exception.EsExceptionEnum;
import com.platform.mesh.es.service.IEsDocService;
import com.platform.mesh.es.util.EsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
                                .action(a->a
                                        .docAsUpsert(Boolean.TRUE)
                                        .doc(esDocPutBO.getDocMap())
                                        .scriptedUpsert(Boolean.TRUE)
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
        GetRequest request = GetRequest.of(builder -> builder.index(indexName).id(StrUtil.toString(dataId)));
        try {
            GetResponse<Object> userGetResponse = elasticsearchClient.get(request, Object.class);
            return userGetResponse.source();
        } catch (Exception e) {
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
            SearchResponse<Object> response = elasticsearchClient.search(searchBuilder.build(), Object.class);
            if(CollUtil.isEmpty(response.hits().hits())){
                return null;
            }else{
                Hit<Object> first = CollUtil.getFirst(response.hits().hits());
                return first.id();
            }
        } catch (Exception e) {
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
        try {
            //获取响应值
            SearchResponse<Object> response = elasticsearchClient.search(searchBuilder.build(), Object.class);
            //获取查询后的命中条数：其中包括 TotalHitsRelation 以及 total
            TotalHits total = response.hits().total();
            assert total != null;
            //获取结果集
            List<Hit<Object>> hits = response.hits().hits();
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
            //如果总数小于10000，或者如果总数大于10000，又回到<10000的查询页面则关闭快照
            this.closePit(esDocGetBO, total.value());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return pageVO;
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



}
