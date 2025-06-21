package com.platform.mesh.es.properties;

import com.platform.mesh.es.constant.EsConst;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description 数据库常量
 * @author 蝉鸣
 */
@Data
@Component
@ConfigurationProperties(EsConst.CONFIG_PREFIX)
public class EsProperties {

    private Boolean enable = false;

    /**
     * es服务端地址
     */
    private String uris;

    /**
     * es用户名
     */
    private String username;

    /**
     * es密码
     */
    private String password;

    /**
     * 连接超时时间
     */
    private Integer connectTimeout = 9000;

    /**
     * socket超时时间
     */
    private Integer socketTimeout = 9000;

    /**
     * 响应时间配置
     */
    private Integer keepAliveStrategy = 180000;

    /**
     * 响应限制大小
     */
    private Integer bufferLimitBytes = 200*1024*1024;

}

