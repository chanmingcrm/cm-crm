package com.platform.mesh.netty.server.soa.protocol;


import com.platform.mesh.netty.server.soa.protocol.enums.NettyProtocolEnum;

/**
 * @description 客服消息处理
 * @author 蝉鸣
 */
public interface NettyProtocolService {

    /**
     * 功能描述:
     * 〈客服消息类型〉
     * @return 正常返回:{@link NettyProtocolEnum}
     * @author 蝉鸣
     */
    NettyProtocolEnum protocol();


    /**
     * 功能描述:
     * 〈协议类型〉
     * @author 蝉鸣
     */
    void handle();

}
