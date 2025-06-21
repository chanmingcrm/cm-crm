package com.platform.mesh.file.oss.modules.aws.configuration;

import cn.hutool.core.text.CharPool;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.file.oss.base.BaseOssClient;
import com.platform.mesh.file.oss.constant.OssBaseConst;
import com.platform.mesh.file.oss.constant.OssTypeConst;
import com.platform.mesh.file.oss.modules.aws.AwsOssClient;
import com.platform.mesh.file.oss.modules.aws.properties.AwsOssProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

/**
 * @description AliOss 自动配置信息
 * @author 蝉鸣
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(AwsOssProperties.class)
@ConditionalOnProperty(prefix = OssBaseConst.OSS, name = OssTypeConst.AWS + CharPool.DOT + StrConst.CONFIG_ENABLE,
        havingValue = StrConst.CONFIG_ENABLE_VALUE)
public class AwsOssAutoConfiguration {


    /**
     * 功能描述:
     * 〈初始化s3基础组件信息〉
     * @param awsOssProperties awsOssProperties
     * @return 正常返回:{@link S3Client}
     * @author 蝉鸣
     */
    @Bean
    @ConditionalOnMissingBean
    public S3Client s3Client(AwsOssProperties awsOssProperties) {
        // 凭证配置
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(awsOssProperties.getAccessKey(), awsOssProperties.getSecretKey());
        StaticCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(awsBasicCredentials);
        // 服务配置
        S3Configuration s3Configuration = S3Configuration.builder()
                .pathStyleAccessEnabled(awsOssProperties.getExtendConfig().getPathStyleAccessEnabled())
                .chunkedEncodingEnabled(awsOssProperties.getExtendConfig().getChunkedEncodingEnabled())
                .build();
        // build amazonS3Client客户端
        return S3Client.builder()
                .credentialsProvider(credentialsProvider)
                .region(Region.of(awsOssProperties.getRegion()))
                .endpointOverride(URI.create(awsOssProperties.getEndPoint()))
                .serviceConfiguration(s3Configuration)
                .build();
    }

    /**
     * 功能描述:
     * 〈注入自定义ossClient〉
     * @param amazonS3 amazonS3
     * @param awsOssProperties awsOssProperties
     * @return 正常返回:{@link BaseOssClient}
     * @author 蝉鸣
     */
    @Bean
    @ConditionalOnBean(S3Client.class)
    public BaseOssClient awsOssClient(S3Client amazonS3, AwsOssProperties awsOssProperties){
        return new AwsOssClient(amazonS3,awsOssProperties);
    }

//    @Bean
//    @ConditionalOnBean(AmazonS3.class)
//    public UploadExtendOssClient aliUploadOssClient(AmazonS3 amazonS3, AliOssProperties aliOssProperties){
//        return new AliOssClient(amazonS3,aliOssProperties);
//    }



}
