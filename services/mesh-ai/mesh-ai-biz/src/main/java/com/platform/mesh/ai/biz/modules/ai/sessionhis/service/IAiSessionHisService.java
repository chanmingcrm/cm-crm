package com.platform.mesh.ai.biz.modules.ai.sessionhis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.ai.biz.modules.ai.sessionhis.domain.dto.AiSessionHisDTO;
import com.platform.mesh.ai.biz.modules.ai.sessionhis.domain.po.AiSessionHis;
import com.platform.mesh.mybatis.plus.extention.MPage;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description AI会话历史信息
 * @author 蝉鸣
 */
public interface IAiSessionHisService extends IService<AiSessionHis> {

    /**
     * 功能描述:
     * 〈获取当前AI会话历史分页信息〉
     * @param pageDTO sessionId
     * @return 正常返回:{@link MPage<AiSessionHis>}
     * @author 蝉鸣
     */
    MPage<AiSessionHis> selectPage(AiSessionHisDTO pageDTO);

}
