package com.platform.mesh.file.oss.modules.local.properties;

import cn.hutool.core.text.CharPool;
import com.platform.mesh.file.oss.constant.OssBaseConst;
import com.platform.mesh.file.oss.constant.OssTypeConst;
import com.platform.mesh.file.oss.modules.local.model.LocalOssClientBaseConfig;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @description 自动配置属性信息
 * @author 蝉鸣
 */
@Data
@ConfigurationProperties(OssBaseConst.OSS + CharPool.DOT + OssTypeConst.LOCAL)
public class LocalOssProperties extends LocalOssClientBaseConfig implements InitializingBean {

    private Boolean enable = false;

    private Map<String, LocalOssClientBaseConfig> ossConfig = new HashMap<>();

    @Override
    public void afterPropertiesSet() {
        if (ossConfig.isEmpty()) {
            this.init();
        } else {
            ossConfig.values().forEach(LocalOssClientBaseConfig::init);
        }
    }
}
