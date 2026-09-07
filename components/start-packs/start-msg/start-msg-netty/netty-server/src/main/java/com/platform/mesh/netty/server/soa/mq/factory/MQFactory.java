package com.platform.mesh.netty.server.soa.mq.factory;

import com.platform.mesh.netty.server.soa.mq.MqService;
import com.platform.mesh.netty.server.soa.mq.enums.MQTypeEnum;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description 消息队列平台工厂
 * @author 蝉鸣
 */
@Service
public class MQFactory implements InitializingBean {

    @Autowired
    private List<MqService> mqServiceList;

    private final Map<MQTypeEnum, MqService> mqMaps = new ConcurrentHashMap<>();

    /**
     * 功能描述:
     * 〈获取消息队列服务〉
     * @param mqTypeEnum mqTypeEnum
     * @return 正常返回:{@link MqService}
     * @author 蝉鸣
     */
    public MqService getMqService(MQTypeEnum mqTypeEnum){
        return mqMaps.get(mqTypeEnum);
    }

    /**
     * 功能描述:
     * 〈初始化bean后置处理〉
     * @author 蝉鸣
     */
    @Override
    public void afterPropertiesSet() {
        for (MqService service : mqServiceList){
            mqMaps.put(service.mqType(), service);
        }
    }
}
