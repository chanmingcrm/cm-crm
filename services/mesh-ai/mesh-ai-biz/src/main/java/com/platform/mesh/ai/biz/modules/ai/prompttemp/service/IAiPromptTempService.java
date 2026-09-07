package com.platform.mesh.ai.biz.modules.ai.prompttemp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.ai.biz.modules.ai.prompttemp.domain.dto.AiPromptTempDTO;
import com.platform.mesh.ai.biz.modules.ai.prompttemp.domain.po.AiPromptTemp;
import com.platform.mesh.ai.biz.modules.ai.prompttemp.domain.vo.AiPromptTempVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description AI提示词模板信息
 * @author 蝉鸣
 */
public interface IAiPromptTempService extends IService<AiPromptTemp> {

    /**
     * 功能描述:
     * 〈获取当前AI提示词模板信息〉
     * @param promptTempId promptTempId
     * @return 正常返回:{@link AiPromptTempVO}
     * @author 蝉鸣
     */
    AiPromptTempVO getAiPromptTempById(Long promptTempId);

    /**
     * 功能描述:
     * 〈新增AI提示词模板〉
     * @param promptTempDTO promptTempDTO
     * @return 正常返回:{@link AiPromptTempVO}
     * @author 蝉鸣
     */
    AiPromptTempVO addAiPromptTemp(AiPromptTempDTO promptTempDTO);

    /**
     * 功能描述:
     * 〈修改AI提示词模板〉
     * @param promptTempDTO promptTempDTO
     * @return 正常返回:{@link AiPromptTempVO}
     * @author 蝉鸣
     */
    AiPromptTempVO editAiPromptTemp(AiPromptTempDTO promptTempDTO);

    /**
     * 功能描述:
     * 〈删除AI提示词模板〉
     * @param promptTempId promptTempId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteAiPromptTemp(Long promptTempId);
}
