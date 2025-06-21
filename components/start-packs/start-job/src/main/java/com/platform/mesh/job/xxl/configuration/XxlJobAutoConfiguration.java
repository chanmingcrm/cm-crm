package com.platform.mesh.job.xxl.configuration;

import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.job.xxl.constant.JobConst;
import com.platform.mesh.job.xxl.properties.XxlJobProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

/**
 * @description XXL-JOB 自动配置信息
 * @author 蝉鸣
 */
@Import(XxlJobConfig.class)
@EnableConfigurationProperties(XxlJobProperties.class)
@ConditionalOnProperty(prefix = JobConst.CONFIG_PREFIX, name = StrConst.CONFIG_ENABLE,
        havingValue = StrConst.CONFIG_ENABLE_VALUE)
public class XxlJobAutoConfiguration {

}