package com.platform.mesh.ai.biz.modules.ai.model.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.ai.biz.modules.ai.model.domain.dto.AiModelDTO;
import com.platform.mesh.ai.biz.modules.ai.model.domain.po.AiModel;
import com.platform.mesh.ai.biz.modules.ai.model.domain.vo.AiModelVO;



/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description AI模型信息
 * @author 蝉鸣
 */
public interface IAiModelService extends IService<AiModel> {

    /**
     * 功能描述:
     * 〈获取当前AI模型信息〉
     * @param modelId modelId
     * @return 正常返回:{@link AiModelVO}
     * @author 蝉鸣
     */
    AiModelVO getAiModelById(Long modelId);

    /**
     * 功能描述:
     * 〈新增AI模型〉
     * @param aiModelDTO aiModelDTO
     * @return 正常返回:{@link AiModelVO}
     * @author 蝉鸣
     */
    AiModelVO addAiModel(AiModelDTO aiModelDTO);

    /**
     * 功能描述:
     * 〈修改AI模型〉
     * @param aiModelDTO aiModelDTO
     * @return 正常返回:{@link AiModelVO}
     * @author 蝉鸣
     */
    AiModelVO editAiModel(AiModelDTO aiModelDTO);

    /**
     * 功能描述:
     * 〈删除AI模型〉
     * @param modelId modelId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteAiModel(Long modelId);
}
