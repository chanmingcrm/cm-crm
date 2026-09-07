package com.platform.mesh.message.jpush.configuration;

import cn.jiguang.sdk.api.DeviceApi;
import cn.jiguang.sdk.api.GroupPushApi;
import cn.jiguang.sdk.api.PushApi;
import cn.jiguang.sdk.api.ReportApi;
import com.platform.mesh.message.jpush.properties.JPushProperties;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JPushConfig {

    private static final Logger log = LoggerFactory.getLogger(JPushConfig.class);

    private final JPushProperties jPushProperties;

    /**
     * 功能描述:
     * 〈发送消息〉
     * @return 正常返回:{@link PushApi}
     * @author 蝉鸣
     */
    @Bean
    @ConditionalOnMissingBean
    public PushApi pushApi() {
        return new PushApi.Builder()
                .setAppKey(jPushProperties.getAppKey()) // 必填
                .setMasterSecret(jPushProperties.getAppSecret()) // 必填
                .setLoggerLevel(feign.Logger.Level.FULL)
                .build();
    }
    @Bean
    @ConditionalOnMissingBean
    public DeviceApi deviceApi() {
        return new DeviceApi.Builder()
                .setAppKey(jPushProperties.getAppKey())
                .setMasterSecret(jPushProperties.getAppSecret())
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public ReportApi reportApi() {
        return new ReportApi.Builder()
                .setAppKey(jPushProperties.getAppKey())
                .setMasterSecret(jPushProperties.getAppSecret())
                .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public GroupPushApi groupPushApi() {
        return new GroupPushApi.Builder()
                .setGroupKey(jPushProperties.getAppKey())
                .setGroupMasterSecret(jPushProperties.getAppSecret())
                .setLoggerLevel(feign.Logger.Level.FULL)
                .build();
    }

}
