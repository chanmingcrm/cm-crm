package com.platform.mesh.job.xxl.properties;

import com.platform.mesh.job.xxl.constant.JobConst;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description XXL-JOB 自动配置信息
 * @author 蝉鸣
 */
@Data
@Component
@ConfigurationProperties(JobConst.CONFIG_PREFIX_ADMIN)
public class XxlJobAdminProperties {

    private String addresses;

    private String accessToken;

    private int timeout;



}