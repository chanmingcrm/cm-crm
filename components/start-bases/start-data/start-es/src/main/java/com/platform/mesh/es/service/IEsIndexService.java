package com.platform.mesh.es.service;

import co.elastic.clients.elasticsearch.indices.IndexState;
import co.elastic.clients.elasticsearch.indices.get_mapping.IndexMappingRecord;
import com.platform.mesh.es.domain.bo.EsIndexMappingBO;
import com.platform.mesh.es.domain.bo.EsIndexSettingBO;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @description Es索引处理
 * @author 蝉鸣
 */
public interface IEsIndexService {

    /**
     * 功能描述:
     * 〈创建索引〉
     * @param indexName indexName
     * @return 正常返回:{@link boolean}
     * @author 蝉鸣
     */
    boolean createIndex(String indexName);

    /**
     * 功能描述:
     * 〈获取索引〉
     * @param indexNames indexNames
     * @return 正常返回:{@link Map}
     * @author 蝉鸣
     */
    Map<String, IndexState> getIndex(List<String> indexNames);

    /**
     * 功能描述:
     * 〈索引是否存在〉
     * @param indexNames indexNames
     * @return 正常返回:{@link boolean}
     * @author 蝉鸣
     */
    boolean existIndex(List<String> indexNames);

    /**
     * 功能描述:
     * 〈删除索引〉
     * @param indexNames indexNames
     * @return 正常返回:{@link boolean}
     * @author 蝉鸣
     */
    boolean deleteIndex(List<String> indexNames);

    /**
     * 功能描述:
     * 〈设置索引配置〉
     * @param esIndexSettingBO esIndexSettingBO
     * @return 正常返回:{@link boolean}
     * @author 蝉鸣
     */
    boolean setSetting(EsIndexSettingBO esIndexSettingBO);

    /**
     * 功能描述:
     * 〈设置索引映射〉
     * @param esIndexMappingBO esIndexMappingBO
     * @return 正常返回:{@link boolean}
     * @author 蝉鸣
     */
    boolean setMapping(EsIndexMappingBO esIndexMappingBO);

    /**
     * 功能描述:
     * 〈获取索引映射〉
     * @param indexNames indexNames
     * @return 正常返回:{@link Map}
     * @author 蝉鸣
     */
    Map<String, IndexMappingRecord> getMapping(List<String> indexNames);



}
