package com.platform.mesh.file.oss.modules.aws.properties;

import cn.hutool.core.text.CharPool;
import com.platform.mesh.file.oss.constant.OssBaseConst;
import com.platform.mesh.file.oss.constant.OssTypeConst;
import com.platform.mesh.file.oss.modules.aws.model.AwsOssClientBaseConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @description 自动配置属性信息
 * @author 蝉鸣
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ConfigurationProperties(OssBaseConst.OSS + CharPool.DOT + OssTypeConst.AWS)
public class AwsOssProperties extends AwsOssClientBaseConfig implements InitializingBean {

    private Boolean enable = false;

    private Map<String, AwsOssClientBaseConfig> ossConfig = new HashMap<>();

    @Override
    public void afterPropertiesSet() {
        if (ossConfig.isEmpty()) {
            this.init();
        } else {
            ossConfig.values().forEach(AwsOssClientBaseConfig::init);
        }
    }
}
