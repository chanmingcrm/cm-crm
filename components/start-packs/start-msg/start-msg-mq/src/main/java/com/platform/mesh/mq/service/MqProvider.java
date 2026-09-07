package com.platform.mesh.mq.service;

import com.platform.mesh.mq.domain.ro.MqMessage;
import com.platform.mesh.mq.domain.ro.MqPublishResult;
import com.platform.mesh.mq.enums.MqMode;
import com.platform.mesh.mq.enums.MqProviderType;

import java.util.Set;

/**
 * 功能描述:
 * 〈中间件提供方扩展接口〉
 * @author 蝉鸣
 */
public interface MqProvider {
    /**
     * 功能描述:
     * 〈获取中间件提供方类型〉
     * @return 中间件提供方类型
     * @author 蝉鸣
     */
    MqProviderType type();
    /**
     * 功能描述:
     * 〈获取提供方支持的投递模式〉
     * @return 支持的投递模式
     * @author 蝉鸣
     */
    Set<MqMode> supportedModes();
    /**
     * 功能描述:
     * 〈通过当前提供方发布消息〉
     * @param message 统一 MQ 消息
     * @return MQ 发布结果
     * @author 蝉鸣
     */
    MqPublishResult publish(MqMessage<?> message);

    /**
     * 功能描述:
     * 〈判断是否支持指定投递模式〉
     * @param mode 投递模式
     * @return 是否支持
     * @author 蝉鸣
     */
    default boolean supports(MqMode mode) {
        return mode != null && supportedModes().contains(mode);
    }
}
