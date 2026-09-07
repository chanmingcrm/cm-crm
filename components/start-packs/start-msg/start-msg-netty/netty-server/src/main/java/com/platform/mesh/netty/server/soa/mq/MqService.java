package com.platform.mesh.netty.server.soa.mq;


import com.platform.mesh.netty.server.domain.bo.CcMsgBO;
import com.platform.mesh.netty.server.soa.mq.enums.MQTypeEnum;

/**
 * @description 消息队列类型
 * @author 蝉鸣
 */
public interface MqService {

    /**
     * 功能描述:
     * 〈消息队列类型〉
     * @return 正常返回:{@link MQTypeEnum}
     * @author 蝉鸣
     */
    MQTypeEnum mqType();


    /**
     * 功能描述:
     * 〈生产者〉
     * @author 蝉鸣
     */
    void producer(CcMsgBO ccMsgBO);

}
