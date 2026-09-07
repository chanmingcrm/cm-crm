package com.platform.mesh.netty.server.soa.msg.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.netty.server.constant.ChannelConst;
import com.platform.mesh.netty.server.domain.bo.CcMsgBO;
import com.platform.mesh.netty.server.domain.bo.CcMsgVO;
import com.platform.mesh.netty.server.enums.CcMsgNoticeEnum;
import com.platform.mesh.netty.server.soa.msg.CcMsgTypeService;
import com.platform.mesh.netty.server.soa.msg.enums.CcMsgTypeEnum;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Service;

/**
 * @description 客服消息处理
 * @author 蝉鸣
 */
@Service
public class SysPingImpl implements CcMsgTypeService {

    /**
     * 功能描述:
     * 〈用户上线〉
     * @return 正常返回:{@link CcMsgTypeEnum}
     * @author 蝉鸣
     */
    @Override
    public CcMsgTypeEnum ccMsgType() {
        return CcMsgTypeEnum.SYS_PING;
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
        //发送心跳消息
        CcMsgVO ccMsgVO = new CcMsgVO();
        ccMsgVO.setNoticeType(CcMsgNoticeEnum.SYSTEM_PING.getValue());
        ccMsgVO.setGroupHash(ccMsgBO.getGroupHash());
        ccMsgVO.setUserHash(ccMsgBO.getUserHash());
        JSONObject jsonObject = JSONUtil.createObj();
        jsonObject.set(ChannelConst.SYS_PING, Boolean.TRUE);
        ccMsgVO.setData(jsonObject);
        ctx.channel().writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(ccMsgVO)));
    }

}
