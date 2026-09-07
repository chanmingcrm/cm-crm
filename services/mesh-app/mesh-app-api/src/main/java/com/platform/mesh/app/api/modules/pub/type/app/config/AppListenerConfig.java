package com.platform.mesh.app.api.modules.pub.type.app.config;


import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.app.api.modules.pub.exception.PubExceptionEnum;
import com.platform.mesh.app.api.modules.pub.type.app.AppFeedbackService;
import com.platform.mesh.app.api.modules.pub.type.app.domain.bo.MsgAppBO;
import com.platform.mesh.app.api.modules.pub.type.app.factory.AppFeedbackFactory;
import com.platform.mesh.app.api.modules.pub.type.app.impl.AppListenerMsgImpl;
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
public class AppListenerConfig implements SmartInitializingSingleton {

    private final static Logger log = LoggerFactory.getLogger(AppListenerConfig.class);

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private AppFeedbackFactory appFeedbackFactory;

    @Override()
    public void afterSingletonsInstantiated() {
        Map<String, AppFeedbackService> actionTypeMaps = appFeedbackFactory.getActionTypeMaps();
        if(CollUtil.isEmpty(actionTypeMaps)) {
            log.info(PubExceptionEnum.INIT_TOPIC_EMPTY.getDesc());
            return;
        }
        actionTypeMaps.forEach((actionName,actionService)-> {
            RTopic topic = redissonClient.getTopic(actionName);
            // 注册自定义对象监听器
            topic.addListener(MsgAppBO.class,new AppListenerMsgImpl(appFeedbackFactory));
        });
    }
}
