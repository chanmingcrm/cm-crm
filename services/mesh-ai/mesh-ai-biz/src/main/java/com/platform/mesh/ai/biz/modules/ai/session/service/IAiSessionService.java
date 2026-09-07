package com.platform.mesh.ai.biz.modules.ai.session.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.ai.biz.modules.ai.session.domain.dto.AiSessionDTO;
import com.platform.mesh.ai.biz.modules.ai.session.domain.dto.AiSessionPageDTO;
import com.platform.mesh.ai.biz.modules.ai.session.domain.po.AiSession;
import com.platform.mesh.ai.biz.modules.ai.session.domain.vo.AiSessionVO;
import com.platform.mesh.mybatis.plus.extention.MPage;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description AI会话信息
 * @author 蝉鸣
 */
public interface IAiSessionService extends IService<AiSession> {


    /**
     * 功能描述:
     * 〈获取当前AI会话分页信息〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link MPage<AiSessionVO>}
     * @author 蝉鸣
     */
    MPage<AiSessionVO> selectPage(AiSessionPageDTO pageDTO);

    /**
     * 功能描述:
     * 〈获取当前AI会话信息〉
     * @param sessionId sessionId
     * @return 正常返回:{@link AiSessionVO}
     * @author 蝉鸣
     */
    AiSessionVO getAiSessionById(Long sessionId);

    /**
     * 功能描述:
     * 〈新增AI会话〉
     * @param sessionDTO sessionDTO
     * @return 正常返回:{@link AiSessionVO}
     * @author 蝉鸣
     */
    AiSessionVO addAiSession(AiSessionDTO sessionDTO);

    /**
     * 功能描述:
     * 〈修改AI会话〉
     * @param sessionDTO sessionDTO
     * @return 正常返回:{@link AiSessionVO}
     * @author 蝉鸣
     */
    AiSessionVO editAiSession(AiSessionDTO sessionDTO);

    /**
     * 功能描述:
     * 〈删除AI会话〉
     * @param sessionId sessionId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteAiSession(Long sessionId);

}
