package com.platform.mesh.message.jpush.properties;

import com.platform.mesh.message.jpush.constant.JPushConst;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description 自动配置属性信息
 * @author 蝉鸣
 */
@Data
@ConfigurationProperties(prefix = JPushConst.CONFIG_PREFIX)
public class JPushProperties{

    /**
     * 开启
     */
    private Boolean enabled = false;

    /**
     * 测试环境
     */
    private Boolean dev = true;

    /**
     * app Key
     */
    private String appKey;

    /**
     * app 密钥
     */
    private String appSecret;



}
