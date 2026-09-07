package com.platform.mesh.ai.biz.modules.cc.webset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.ai.biz.modules.cc.webset.domain.dto.CcWebSetDTO;
import com.platform.mesh.ai.biz.modules.cc.webset.domain.dto.CcConsultationGuideDTO;
import com.platform.mesh.ai.biz.modules.cc.webset.domain.dto.CcConsultationLeadDTO;
import com.platform.mesh.ai.biz.modules.cc.webset.domain.po.CcWebSet;
import com.platform.mesh.ai.biz.modules.cc.webset.domain.vo.CcWebSetVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 页面配置信息
 * @author 蝉鸣
 */
public interface ICcWebSetService extends IService<CcWebSet> {

    /**
     * 功能描述:
     * 〈获取当前页面配置信息〉
     * @param webSetId webSetId
     * @return 正常返回:{@link CcWebSetVO}
     * @author 蝉鸣
     */
    CcWebSetVO getCcWebSetById(Long webSetId);

    /**
     * 获取访客端可公开读取的咨询引导配置
     */
    CcConsultationGuideDTO getConsultationGuideById(Long webSetId);

    /**
     * 保存官网咨询手机号到对应租户的客服会话。
     * */
    void saveConsultationLead(CcConsultationLeadDTO leadDTO);

    /**
     * 功能描述:
     * 〈新增页面配置〉
     * @param webSetDTO webSetDTO
     * @return 正常返回:{@link CcWebSetVO}
     * @author 蝉鸣
     */
    CcWebSetVO addCcWebSet(CcWebSetDTO webSetDTO);

    /**
     * 功能描述:
     * 〈修改页面配置〉
     * @param webSetDTO webSetDTO
     * @return 正常返回:{@link CcWebSetVO}
     * @author 蝉鸣
     */
    CcWebSetVO editCcWebSet(CcWebSetDTO webSetDTO);

    /**
     * 功能描述:
     * 〈删除页面配置〉
     * @param webSetId webSetId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteCcWebSet(Long webSetId);
}
