package com.platform.mesh.netty.server.soa.protocol.impl;

import com.platform.mesh.netty.server.soa.protocol.NettyProtocolService;
import com.platform.mesh.netty.server.soa.protocol.enums.NettyProtocolEnum;
import org.springframework.stereotype.Service;

/**
 * @description 协议处理
 * @author 蝉鸣
 */
@Service
public class NettyWebsocketImpl implements NettyProtocolService {

    /**
     * 功能描述:
     * 〈创建房间〉
     * @return 正常返回:{@link NettyProtocolEnum}
     * @author 蝉鸣
     */
    @Override
    public NettyProtocolEnum protocol() {
        return NettyProtocolEnum.WEBSOCKET;
    }

    /**
     * 功能描述:
     * 〈消息处理〉
     * @author 蝉鸣
     */
    @Override
    public void handle() {

    }

}
