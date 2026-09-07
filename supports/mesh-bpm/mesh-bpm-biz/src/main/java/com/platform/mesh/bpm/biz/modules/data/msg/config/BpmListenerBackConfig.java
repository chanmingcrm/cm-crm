package com.platform.mesh.bpm.biz.modules.data.msg.config;


import com.platform.mesh.bpm.biz.modules.data.msg.service.IBpmDataMsgQueueService;
import com.platform.mesh.bpm.biz.modules.data.msg.service.impl.BpmListenerMsgBackImpl;
import com.platform.mesh.core.constants.StrConst;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @description Bpm配置
 * @author 蝉鸣
 */
@Configuration
public class BpmListenerBackConfig implements SmartInitializingSingleton {

    private final static Logger log = LoggerFactory.getLogger(BpmListenerBackConfig.class);

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private IBpmDataMsgQueueService bpmDataMsgQueueService;

    @Override()
    public void afterSingletonsInstantiated() {
        RTopic topic = redissonClient.getTopic(StrConst.BPM_MSG_ID);
        // 注册自定义对象监听器
        topic.addListener(Long.class,new BpmListenerMsgBackImpl(bpmDataMsgQueueService));
    }
}
