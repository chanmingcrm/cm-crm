package com.platform.mesh.file.oss.modules.local.configuration;

import cn.hutool.core.text.CharPool;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.file.oss.base.BaseOssClient;
import com.platform.mesh.file.oss.constant.OssBaseConst;
import com.platform.mesh.file.oss.constant.OssTypeConst;
import com.platform.mesh.file.oss.modules.local.LocalOssClient;
import com.platform.mesh.file.oss.modules.local.properties.LocalOssProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description AliOss 自动配置信息
 * @author 蝉鸣
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(LocalOssProperties.class)
@ConditionalOnProperty(prefix = OssBaseConst.OSS, name = OssTypeConst.LOCAL + CharPool.DOT + StrConst.CONFIG_ENABLE,
        havingValue = StrConst.CONFIG_ENABLE_VALUE)
public class LocalOssAutoConfiguration {

    @Autowired
    private LocalOssProperties localProperties;


    /**
     * 功能描述:
     * 〈注入自定义ossClient〉
     * @param localProperties localProperties
     * @return 正常返回:{@link BaseOssClient}
     * @author 蝉鸣
     */
    @Bean
    public BaseOssClient localOssClient(LocalOssProperties localProperties){
        return new LocalOssClient(localProperties);
    }



}
