package com.platform.mesh.netty.server.soa.mq.impl;

import com.platform.mesh.netty.server.domain.bo.CcMsgBO;
import com.platform.mesh.netty.server.soa.mq.MqService;
import com.platform.mesh.netty.server.soa.mq.enums.MQTypeEnum;
import org.springframework.stereotype.Service;

/**
 * @description 客服消息处理
 * @author 蝉鸣
 */
@Service
public class RedisMQImpl implements MqService {

    /**
     * 功能描述:
     * 〈REDIS〉
     * @return 正常返回:{@link MQTypeEnum}
     * @author 蝉鸣
     */
    @Override
    public MQTypeEnum mqType() {
        return MQTypeEnum.REDIS;
    }

    /**
     * 功能描述:
     * 〈生产者〉
     * @param ccMsgBO ccMsgBO
     * @author 蝉鸣
     */
    @Override
    public void producer(CcMsgBO ccMsgBO) {

    }

}
