package com.platform.mesh.job.xxl.configuration;

import com.platform.mesh.job.xxl.properties.XxlJobAdminProperties;
import com.platform.mesh.job.xxl.properties.XxlJobExecutorProperties;
import com.platform.mesh.job.xxl.properties.XxlJobProperties;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class XxlJobConfig {

    private static final Logger log = LoggerFactory.getLogger(XxlJobConfig.class);

    private final XxlJobProperties xxlJobProperties;

    /**
     * 功能描述:
     * 〈获取同步客户端〉
     * @return 正常返回:{@link XxlJobSpringExecutor}
     * @author 蝉鸣
     */
    @Bean
    @ConditionalOnMissingBean
    public XxlJobSpringExecutor xxlJobExecutor() {
        log.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobAdminProperties adminProperties = xxlJobProperties.getAdmin();
        XxlJobExecutorProperties executorProperties = xxlJobProperties.getExecutor();
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        //服务端属性
        xxlJobSpringExecutor.setAdminAddresses(adminProperties.getAddresses());
        xxlJobSpringExecutor.setAccessToken(adminProperties.getAccessToken());
        xxlJobSpringExecutor.setTimeout(adminProperties.getTimeout());
        //执行器属性
        xxlJobSpringExecutor.setAppname(executorProperties.getAppName());
        xxlJobSpringExecutor.setAddress(executorProperties.getAddress());
        xxlJobSpringExecutor.setIp(executorProperties.getIp());
        xxlJobSpringExecutor.setPort(executorProperties.getPort());
        xxlJobSpringExecutor.setLogPath(executorProperties.getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(executorProperties.getLogRetentionDays());
        return xxlJobSpringExecutor;
    }
}
