package com.platform.mesh.message.jpush.configuration;

import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.message.jpush.constant.JPushConst;
import com.platform.mesh.message.jpush.properties.JPushProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

/**
 * @description Elasticsearch自动装配
 * @author 蝉鸣
 */
@Import(JPushConfig.class)
@EnableConfigurationProperties(JPushProperties.class)
@ConditionalOnProperty(prefix = JPushConst.CONFIG_PREFIX, name = StrConst.CONFIG_ENABLE,
        havingValue = StrConst.CONFIG_ENABLE_VALUE)
public class JPushAutoConfiguration {


}


