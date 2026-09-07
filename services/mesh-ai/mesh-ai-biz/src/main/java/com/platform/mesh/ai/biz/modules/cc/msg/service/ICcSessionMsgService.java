package com.platform.mesh.ai.biz.modules.cc.msg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.mesh.ai.biz.modules.cc.msg.domain.dto.CcSessionMsgDTO;
import com.platform.mesh.ai.biz.modules.cc.msg.domain.dto.CcSessionMsgPageDTO;
import com.platform.mesh.ai.biz.modules.cc.msg.domain.po.CcSessionMsg;
import com.platform.mesh.ai.biz.modules.cc.msg.domain.vo.CcSessionMsgVO;
import com.platform.mesh.core.application.domain.vo.PageVO;


/**
 * 约定当前service 只提供当前controller.api 相关接口
 * @description 会话消息信息
 * @author 蝉鸣
 */
public interface ICcSessionMsgService extends IService<CcSessionMsg> {

    /**
     * 功能描述:
     * 〈消息列表〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link PageVO<CcSessionMsgVO>}
     * @author 蝉鸣
     */
    PageVO<CcSessionMsgVO> selectPage(CcSessionMsgPageDTO pageDTO);

    /**
     * 功能描述:
     * 〈获取当前会话消息信息〉
     * @param sessionMsgId sessionMsgId
     * @return 正常返回:{@link CcSessionMsgVO}
     * @author 蝉鸣
     */
    CcSessionMsgVO getCcSessionMsgById(Long sessionMsgId);

    /**
     * 功能描述:
     * 〈新增会话消息〉
     * @param sessionMsgDTO sessionMsgDTO
     * @return 正常返回:{@link CcSessionMsgVO}
     * @author 蝉鸣
     */
    CcSessionMsgVO addCcSessionMsg(CcSessionMsgDTO sessionMsgDTO);

    /**
     * 功能描述:
     * 〈修改会话消息〉
     * @param sessionMsgDTO sessionMsgDTO
     * @return 正常返回:{@link CcSessionMsgVO}
     * @author 蝉鸣
     */
    CcSessionMsgVO editCcSessionMsg(CcSessionMsgDTO sessionMsgDTO);

    /**
     * 功能描述:
     * 〈删除会话消息〉
     * @param sessionId sessionId
     * @return 正常返回:{@link Boolean}
     * @author 蝉鸣
     */
    Boolean deleteCcSessionMsg(Long sessionId);

}
