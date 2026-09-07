package com.platform.mesh.ai.biz.modules.ai.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.ai.biz.modules.ai.knowledge.domain.dto.AiKnowledgeDTO;
import com.platform.mesh.ai.biz.modules.ai.knowledge.domain.po.AiKnowledge;
import com.platform.mesh.ai.biz.modules.ai.knowledge.domain.vo.AiKnowledgeVO;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.mybatis.plus.extention.MPage;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description AI知识库信息
 * @author 蝉鸣
 */
public interface IAiKnowledgeService extends IService<AiKnowledge> {


    /**
     * 功能描述:
     * 〈获取当前AI知识库分页信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<AiKnowledgeVO>}
     * @author 蝉鸣
     */
    MPage<AiKnowledgeVO> selectPage(PageDTO pageDTO);

    /**
     * 功能描述:
     * 〈获取当前AI知识库信息〉
     * @param knowledgeId knowledgeId
     * @return 正常返回:{@link AiKnowledgeVO}
     * @author 蝉鸣
     */
    AiKnowledgeVO getAiKnowledgeById(Long knowledgeId);

    /**
     * 功能描述:
     * 〈新增AI知识库〉
     * @param knowledgeDTO knowledgeDTO
     * @return 正常返回:{@link AiKnowledgeVO}
     * @author 蝉鸣
     */
    AiKnowledgeVO addAiKnowledge(AiKnowledgeDTO knowledgeDTO);

    /**
     * 功能描述:
     * 〈修改AI知识库〉
     * @param knowledgeDTO knowledgeDTO
     * @return 正常返回:{@link AiKnowledgeVO}
     * @author 蝉鸣
     */
    AiKnowledgeVO editAiKnowledge(AiKnowledgeDTO knowledgeDTO);

    /**
     * 功能描述:
     * 〈删除AI知识库〉
     * @param knowledgeId knowledgeId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteAiKnowledge(Long knowledgeId);

}
