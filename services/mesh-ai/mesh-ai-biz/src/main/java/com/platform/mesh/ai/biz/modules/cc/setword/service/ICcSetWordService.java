package com.platform.mesh.ai.biz.modules.cc.setword.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.ai.biz.modules.cc.setword.domain.dto.CcSetWordDTO;
import com.platform.mesh.ai.biz.modules.cc.setword.domain.po.CcSetWord;
import com.platform.mesh.ai.biz.modules.cc.setword.domain.vo.CcSetWordVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 提示语信息
 * @author 蝉鸣
 */
public interface ICcSetWordService extends IService<CcSetWord> {

    /**
     * 功能描述:
     * 〈获取当前提示语信息〉
     * @param wordId wordId
     * @return 正常返回:{@link CcSetWordVO}
     * @author 蝉鸣
     */
    CcSetWordVO getCcSetWordById(Long wordId);

    /**
     * 功能描述:
     * 〈新增提示语〉
     * @param wordDTO wordDTO
     * @return 正常返回:{@link CcSetWordVO}
     * @author 蝉鸣
     */
    CcSetWordVO addCcSetWord(CcSetWordDTO wordDTO);

    /**
     * 功能描述:
     * 〈修改提示语〉
     * @param wordDTO wordDTO
     * @return 正常返回:{@link CcSetWordVO}
     * @author 蝉鸣
     */
    CcSetWordVO editCcSetWord(CcSetWordDTO wordDTO);

    /**
     * 功能描述:
     * 〈删除提示语〉
     * @param wordId wordId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteCcSetWord(Long wordId);
}
