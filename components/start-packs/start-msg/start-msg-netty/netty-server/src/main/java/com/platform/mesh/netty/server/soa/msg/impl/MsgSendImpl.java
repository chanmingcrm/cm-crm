package com.platform.mesh.netty.server.soa.msg.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.netty.server.domain.bo.CcMsgBO;
import com.platform.mesh.netty.server.domain.bo.CcMsgVO;
import com.platform.mesh.netty.server.enums.CcMsgNoticeEnum;
import com.platform.mesh.netty.server.soa.msg.CcMsgExecuteService;
import com.platform.mesh.netty.server.soa.msg.CcMsgTypeService;
import com.platform.mesh.netty.server.soa.msg.enums.CcMsgTypeEnum;
import com.platform.mesh.netty.server.soa.msg.factory.CcChannelFactory;
import com.platform.mesh.utils.function.FutureHandleUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description 客服消息处理
 * @author 蝉鸣
 */
@Service
public class MsgSendImpl implements CcMsgTypeService {

    private static final Logger log = LoggerFactory.getLogger(MsgSendImpl.class);

    @Autowired
    private CcChannelFactory ccChannelFactory;

    private final  CcMsgExecuteService ccMsgExecuteService;

    @Autowired
    public MsgSendImpl(@Autowired(required = false) CcMsgExecuteService ccMsgExecuteService) {
        this.ccMsgExecuteService = ccMsgExecuteService;
    }

    /**
     * 功能描述:
     * 〈发送消息〉
     * @return 正常返回:{@link CcMsgTypeEnum}
     * @author 蝉鸣
     */
    @Override
    public CcMsgTypeEnum ccMsgType() {
        return CcMsgTypeEnum.MSG_SEND;
    }

    /**
     * 功能描述:
     * 〈消息处理〉
     * @param ctx ctx
     * @param ccMsgBO ccMsgBO
     * @author 蝉鸣
     */
    @Override
    public void handle(ChannelHandlerContext ctx, CcMsgBO ccMsgBO) {
        String groupHash = ccMsgBO.getGroupHash();
        ChannelGroup channelGroup = ccChannelFactory.getGroup(groupHash);
        if (ObjectUtil.isEmpty(channelGroup)) {
            return;
        }
        //持久化信息
        if (ObjectUtil.isEmpty(ccMsgExecuteService)) {
            return;
        }
        // 响应到客户端通道
        CcMsgVO ccMsgVO = new CcMsgVO();
        ccMsgVO.setNoticeType(CcMsgNoticeEnum.MSG_BACK.getValue());
        ccMsgVO.setGroupHash(ccMsgBO.getGroupHash());
        ccMsgVO.setUserHash(ccMsgBO.getUserHash());
        ccMsgVO.setData(ccMsgBO.getMsgContent());
        ccChannelFactory.sendGroupMsg(ccMsgBO.getGroupHash(), ccMsgVO);
        //后置处理
        FutureHandleUtil.runNoResult(ccMsgBO,ccMsgExecuteService::msgSend);
    }

}
