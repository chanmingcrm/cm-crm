package com.platform.mesh.netty.server.configuration;

import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.netty.server.constant.NettyConst;
import com.platform.mesh.netty.server.handle.WebSocketServerHandler;
import com.platform.mesh.netty.server.properties.NettyProperties;
import com.platform.mesh.netty.server.service.NettyWebSocketServer;
import com.platform.mesh.netty.server.soa.msg.factory.CcChannelFactory;
import com.platform.mesh.netty.server.soa.msg.factory.CcMsgTypeFactory;
import com.platform.mesh.netty.server.soa.msg.impl.*;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

/**
 * @description Netty自动装配
 * @author 蝉鸣
 */
@AutoConfiguration
@EnableConfigurationProperties(NettyProperties.class)
@ConditionalOnProperty(prefix = NettyConst.CONFIG_PREFIX, name = StrConst.CONFIG_ENABLE,
        havingValue = StrConst.CONFIG_ENABLE_VALUE)
@Import({
        CcMsgTypeFactory.class,
        CcChannelFactory.class,
        UserOnLineImpl.class,
        UserOffLineImpl.class,
        GroupCreateImpl.class,
        GroupJoinImpl.class,
        GroupLeaveImpl.class,
        MsgSendImpl.class,
        SysPingImpl.class
})
public class NettyAutoConfiguration {

    /**
     * WebSocket服务配置
     */
    @Import({
            NettyWebSocketServer.class,
            WebSocketServerHandler.class
    })
    @ConditionalOnProperty(prefix = NettyConst.CONFIG_PREFIX, name = StrConst.CONFIG_ENABLE,
            havingValue = StrConst.CONFIG_ENABLE_VALUE)
    static class NettyWebSocketConfiguration {

    }

}


