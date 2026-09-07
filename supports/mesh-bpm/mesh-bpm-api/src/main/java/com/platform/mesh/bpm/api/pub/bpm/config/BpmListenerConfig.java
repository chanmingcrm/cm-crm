package com.platform.mesh.bpm.api.pub.bpm.config;


import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.bpm.api.pub.bpm.BpmFeedbackService;
import com.platform.mesh.bpm.api.pub.bpm.factory.BpmFeedbackFactory;
import com.platform.mesh.bpm.api.pub.bpm.impl.BpmListenerMsgImpl;
import com.platform.mesh.bpm.api.pub.bpm.domain.bo.MsgBpmBO;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @description Bpm配置
 * @author 蝉鸣
 */
@Configuration
public class BpmListenerConfig implements SmartInitializingSingleton {

    private final static Logger log = LoggerFactory.getLogger(BpmListenerConfig.class);

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private BpmFeedbackFactory bpmFeedbackFactory;

    @Override()
    public void afterSingletonsInstantiated() {
        Map<String, BpmFeedbackService> actionTypeMaps = bpmFeedbackFactory.getActionTypeMaps();
        if(CollUtil.isEmpty(actionTypeMaps)) {
            log.info("BpmListenerConfig:: 未发现对应服务");
            return;
        }
        actionTypeMaps.forEach((actionName,actionService)-> {
            RTopic topic = redissonClient.getTopic(actionName);
            // 注册自定义对象监听器
            topic.addListener(MsgBpmBO.class,new BpmListenerMsgImpl(bpmFeedbackFactory));
        });
    }
}
