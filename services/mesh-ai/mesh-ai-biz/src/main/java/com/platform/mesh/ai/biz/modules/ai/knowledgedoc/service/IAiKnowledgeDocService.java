package com.platform.mesh.ai.biz.modules.ai.knowledgedoc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.ai.biz.modules.ai.knowledgedoc.domain.dto.AiKnowledgeDocDTO;
import com.platform.mesh.ai.biz.modules.ai.knowledgedoc.domain.po.AiKnowledgeDoc;
import com.platform.mesh.ai.biz.modules.ai.knowledgedoc.domain.vo.AiKnowledgeDocVO;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import org.springframework.ai.document.Document;

import java.util.List;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description AI知识库附件信息
 * @author 蝉鸣
 */
public interface IAiKnowledgeDocService extends IService<AiKnowledgeDoc> {

    /**
     * 功能描述:
     * 〈获取当前AI知识库附件分页信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<AiKnowledgeDocVO>}
     * @author 蝉鸣
     */
    MPage<AiKnowledgeDocVO> selectPage(PageDTO pageDTO);

    /**
     * 功能描述:
     * 〈获取当前AI知识库附件信息〉
     * @param knowledgeDocId knowledgeDocId
     * @return 正常返回:{@link AiKnowledgeDocVO}
     * @author 蝉鸣
     */
    AiKnowledgeDocVO getAiKnowledgeDocById(Long knowledgeDocId);

    /**
     * 功能描述:
     * 〈新增AI知识库附件〉
     * @param knowledgeDocDTO knowledgeDocDTO
     * @return 正常返回:{@link AiKnowledgeDocVO}
     * @author 蝉鸣
     */
    AiKnowledgeDocVO addAiKnowledgeDoc(AiKnowledgeDocDTO knowledgeDocDTO);

    /**
     * 功能描述:
     * 〈修改AI知识库附件〉
     * @param knowledgeDocDTO knowledgeDocDTO
     * @return 正常返回:{@link AiKnowledgeDocVO}
     * @author 蝉鸣
     */
    AiKnowledgeDocVO editAiKnowledgeDoc(AiKnowledgeDocDTO knowledgeDocDTO);

    /**
     * 功能描述:
     * 〈删除AI知识库附件〉
     * @param knowledgeDocId knowledgeDocId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteAiKnowledgeDoc(Long knowledgeDocId);

    /**
     * 功能描述:
     * 〈根据知识库ID 获取向量文档〉
     * @param knowledgeId knowledgeId
     * @param content content
     * @return 正常返回:{@link List<Document>}
     * @author 蝉鸣
     */
    List<Document> getKnowledgeDocument(Long knowledgeId, String content);

}
