package com.platform.mesh.netty.server.soa.msg;


import com.platform.mesh.netty.server.domain.bo.CcMsgBO;
import com.platform.mesh.netty.server.soa.msg.enums.CcMsgTypeEnum;
import io.netty.channel.ChannelHandlerContext;

/**
 * @description 客服消息处理
 * @author 蝉鸣
 */
public interface CcMsgTypeService {

    /**
     * 功能描述:
     * 〈客服消息类型〉
     * @return 正常返回:{@link CcMsgTypeEnum}
     * @author 蝉鸣
     */
    CcMsgTypeEnum ccMsgType();

    /**
     * 功能描述:
     * 〈客服消息处理〉
     * @author 蝉鸣
     */
    void handle(ChannelHandlerContext ctx, CcMsgBO ccMsgBO);


}
