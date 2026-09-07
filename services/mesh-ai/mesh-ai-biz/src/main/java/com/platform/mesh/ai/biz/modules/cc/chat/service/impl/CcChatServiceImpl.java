package com.platform.mesh.ai.biz.modules.cc.chat.service.impl;

import com.platform.mesh.ai.biz.modules.cc.chat.service.ICcChatService;
import com.platform.mesh.ai.biz.modules.cc.chat.service.maual.CcChatServiceManual;
import com.platform.mesh.netty.server.domain.bo.CcMsgBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 约定当前serviceImpl 只实现当前service 相关方法，所有封装转换方法在Manual中进行
 * @description AIMcp
 * @author 蝉鸣
 */
@Service
public class CcChatServiceImpl extends ICcChatService {

    private static final Logger log = LoggerFactory.getLogger(CcChatServiceImpl.class);

    @Autowired
    private CcChatServiceManual ccChatServiceManual;

    /**
     * 功能描述:
     * 〈创建房间〉
     * @param ccMsgBO ccMsgBO
     * @author 蝉鸣
     */
    @Override
    public void createGroup(CcMsgBO ccMsgBO) {
        ccChatServiceManual.createGroupSave(ccMsgBO);

        ccChatServiceManual.createGroupBack(ccMsgBO);

    }

    /**
     * 功能描述:
     * 〈加入房间〉
     * @param ccMsgBO ccMsgBO
     * @author 蝉鸣
     */
    @Override
    public void joinGroup(CcMsgBO ccMsgBO) {
        //保存加入群关系
        ccChatServiceManual.joinGroupSave(ccMsgBO);
        //返回客户端信息
        ccChatServiceManual.joinGroupBack(ccMsgBO);
        //返回群成员连接状态
        ccChatServiceManual.backToGroupForOnline(ccMsgBO);
        //返回系统提醒未读消息数量
        ccChatServiceManual.backToSystemForUnReadNum(ccMsgBO);
    }

    /**
     * 功能描述:
     * 〈离开房间〉
     * @param ccMsgBO ccMsgBO
     * @author 蝉鸣
     */
    @Override
    public void leaveGroup(CcMsgBO ccMsgBO) {
        //更新会话离线状态
        ccChatServiceManual.leaveGroupSave(ccMsgBO);
        //返回群成员连接状态
        ccChatServiceManual.backToGroupForOnline(ccMsgBO);
    }

    /**
     * 功能描述:
     * 〈消息发送〉
     * @param ccMsgBO ccMsgBO
     * @author 蝉鸣
     */
    @Override
    public void msgSend(CcMsgBO ccMsgBO) {
        //实例化存储信息
        Long currentMessageId = ccChatServiceManual.msgSendSave(ccMsgBO);
        //是否需要智能体回答
        ccChatServiceManual.msgSendBack(ccMsgBO, currentMessageId);

    }

}
