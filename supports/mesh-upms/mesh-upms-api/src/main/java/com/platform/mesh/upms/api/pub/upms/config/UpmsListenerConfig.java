package com.platform.mesh.upms.api.pub.upms.config;


import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.upms.api.pub.upms.UpmsFeedbackService;
import com.platform.mesh.upms.api.pub.upms.bo.MsgUpmsBO;
import com.platform.mesh.upms.api.pub.upms.factory.UpmsFeedbackFactory;
import com.platform.mesh.upms.api.pub.upms.impl.UpmsListenerMsgImpl;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @description Bpm配置
 * @author 蝉鸣
 */
@Configuration
public class UpmsListenerConfig implements SmartInitializingSingleton {

    private final static Logger log = LoggerFactory.getLogger(UpmsListenerConfig.class);

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private UpmsFeedbackFactory upmsFeedbackFactory;

    @Override()
    public void afterSingletonsInstantiated() {
        Map<String, UpmsFeedbackService> actionTypeMaps = upmsFeedbackFactory.getActionTypeMaps();
        if(CollUtil.isEmpty(actionTypeMaps)) {
            return;
        }
        Set<String> keySet = actionTypeMaps.keySet();
        // 如果需要添加元素，创建新的HashSet
        Set<String> newKeySet = new HashSet<>(keySet);
        newKeySet.add(StrConst.ALL);
        newKeySet.forEach((actionName)-> {
            RTopic topic = redissonClient.getTopic(actionName);
            // 注册自定义对象监听器
            topic.addListener(MsgUpmsBO.class,new UpmsListenerMsgImpl(upmsFeedbackFactory));
        });
    }
}
