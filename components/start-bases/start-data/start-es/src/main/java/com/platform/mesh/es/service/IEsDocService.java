package com.platform.mesh.es.service;

import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.es.domain.bo.EsDocGetBO;
import com.platform.mesh.es.domain.bo.EsDocPutBO;

import java.util.List;
import java.util.Map;

/**
 * @description Es文档处理
 * @author 蝉鸣
 */
public interface IEsDocService {

    /**
     * 功能描述:
     * 〈创建文档〉
     * @param esDocPutBO esDocPutBO
     * @return 正常返回:{@link String}
     * @author 蝉鸣
     */
    String createDocument(EsDocPutBO esDocPutBO);

    /**
     * 功能描述:
     * 〈批量创建文档〉
     * @param esDocPutBOS esDocPutBOS
     * @author 蝉鸣
     */
    void createBatchDocument(List<EsDocPutBO> esDocPutBOS);

    /**
     * 功能描述:
     * 〈修改文档〉
     * @param esDocPutBO esDocPutBO
     * @author 蝉鸣
     */
    void updateDocument(EsDocPutBO esDocPutBO);

    /**
     * 功能描述:
     * 〈批量修改文档〉
     * @param esDocPutBOS esDocPutBOS
     * @author 蝉鸣
     */
    void updateBatchDocument(List<EsDocPutBO> esDocPutBOS);

    /**
     * 功能描述:
     * 〈删除文档〉
     * @param esDocPutBO esDocPutBO
     * @author 蝉鸣
     */
    void deleteDocument(EsDocPutBO esDocPutBO);

    /**
     * 功能描述:
     * 〈是否存在文档〉
     * @param esDocPutBO esDocPutBO
     * @author 蝉鸣
     */
    boolean existDocument(EsDocPutBO esDocPutBO);

    /**
     * 功能描述:
     * 〈是否存在文档〉
     * @param indexName indexName
     * @param docMap docMap
     * @author 蝉鸣
     */
    boolean existDocument(String indexName, Map<String, Object> docMap);

    /**
     * 功能描述:
     * 〈获取文档〉
     * @param indexName indexName
     * @param dataId dataId
     * @return 正常返回:{@link Object}
     * @author 蝉鸣
     */
    Object getDocumentById(String indexName,Object dataId);

    /**
     * 功能描述:
     * 〈根据名称获取文档ID:主要用于Excel数据导入解析〉
     * @param indexName indexName
     * @param dataName dataName
     * @param dataValue dataValue
     * @return 正常返回:{@link Object}
     * @author 蝉鸣
     */
    String getDocumentIdByName(String indexName,String dataName,String dataValue);

    /**
     * 功能描述:
     * 〈检索文档〉
     * @param esDocGetBO esDocGetBO
     * @author 蝉鸣
     */
    PageVO<Object> searchDocument(EsDocGetBO esDocGetBO);


}
