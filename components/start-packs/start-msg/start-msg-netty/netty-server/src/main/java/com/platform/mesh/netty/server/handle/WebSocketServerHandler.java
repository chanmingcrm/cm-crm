package com.platform.mesh.netty.server.handle;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.core.enums.base.BaseEnum;
import com.platform.mesh.netty.server.domain.bo.CcMsgBO;
import com.platform.mesh.netty.server.exception.NettyExceptionEnum;
import com.platform.mesh.netty.server.soa.msg.CcMsgTypeService;
import com.platform.mesh.netty.server.soa.msg.enums.CcMsgTypeEnum;
import com.platform.mesh.netty.server.soa.msg.factory.CcMsgTypeFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @description Websocket 处理实现
 * @author 蝉鸣
 */
@Component
@ChannelHandler.Sharable
public class WebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static final Logger log = LoggerFactory.getLogger(WebSocketServerHandler.class);

    @Autowired
    private CcMsgTypeFactory ccMsgTypeFactory;

    /**
     * 功能描述:
     * 〈处理客户端连接建立〉
     * @param ctx ctx
     * @author 蝉鸣
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        System.out.println("新客户端连接: " + channel.id().asShortText());
    }

    /**
     * 功能描述:
     * 〈处理客户端断开连接〉
     * @param ctx ctx
     * @author 蝉鸣
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {

        System.out.println("客户端断开连接: ");
    }

    /**
     * 功能描述:
     * 〈处理客户端发送的消息〉
     * @param ctx ctx
     * @param msg msg
     * @author 蝉鸣
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        String message = msg.text();
        System.out.println("channelRead0 收到消息: " + message);
        try {
            // 解析JSON消息
            CcMsgBO ccMsgBO = JSONUtil.toBean(message, CcMsgBO.class);
            // 解析消息
            if(ObjectUtil.isEmpty(ccMsgBO) || ObjectUtil.isEmpty(ccMsgBO.getMsgType())) {
                this.sendError(ctx);
                return;
            }
            // 解析消息类型
            CcMsgTypeEnum enumByValue = BaseEnum.getEnumByValue(CcMsgTypeEnum.class, ccMsgBO.getMsgType());
            if(ObjectUtil.isEmpty(enumByValue)) {
                this.sendError(ctx);
                return;
            }
            // 获取对应服务
            CcMsgTypeService ccMsgTypeService = ccMsgTypeFactory.getCcMsgService(enumByValue);
            if(ObjectUtil.isEmpty(ccMsgTypeService)) {
                this.sendError(ctx);
                return;
            }
            //触发消息处理服务
            ccMsgTypeService.handle(ctx,ccMsgBO);
        } catch (Exception e) {
            log.error("WebSocket消息处理失败", e);
        }
    }

    /**
     * 功能描述:
     * 〈处理WebSocket异常〉
     * @param ctx ctx
     * @param cause cause
     * @author 蝉鸣
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

    /**
     * 功能描述:
     * 〈发送异常消息〉
     * @param ctx ctx
     * @author 蝉鸣
     */
    private void sendError(ChannelHandlerContext ctx) {
        try {
            log.error(NettyExceptionEnum.NO_INVALID.getDesc());
            ctx.channel().writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(NettyExceptionEnum.CONNECT_ERROR.getDesc())));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
