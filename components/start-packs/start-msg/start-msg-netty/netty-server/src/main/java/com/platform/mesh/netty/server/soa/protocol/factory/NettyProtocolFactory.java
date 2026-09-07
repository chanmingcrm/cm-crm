package com.platform.mesh.netty.server.soa.protocol.factory;

import com.platform.mesh.netty.server.soa.protocol.NettyProtocolService;
import com.platform.mesh.netty.server.soa.protocol.enums.NettyProtocolEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description 协议平台工厂
 * @author 蝉鸣
 */
@Service
public class NettyProtocolFactory implements InitializingBean {

    @Autowired
    private List<NettyProtocolService> nettyProtocolServiceList;

    private final Map<NettyProtocolEnum, NettyProtocolService> nettyProtocolMaps = new ConcurrentHashMap<>();

    /**
     * 功能描述:
     * 〈获取协议处理服务〉
     * @param nettyProtocolEnum nettyProtocolEnum
     * @return 正常返回:{@link NettyProtocolService}
     * @author 蝉鸣
     */
    public NettyProtocolService getNettyProtocolService(NettyProtocolEnum nettyProtocolEnum){
        return nettyProtocolMaps.get(nettyProtocolEnum);
    }

    /**
     * 功能描述:
     * 〈初始化bean后置处理〉
     * @author 蝉鸣
     */
    @Override
    public void afterPropertiesSet() {
        for (NettyProtocolService service : nettyProtocolServiceList){
            nettyProtocolMaps.put(service.protocol(), service);
        }
    }
}
