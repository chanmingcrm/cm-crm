package com.platform.mesh.es.properties;

import com.platform.mesh.es.constant.EsConst;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * @description 数据库常量
 * @author 蝉鸣
 */
@Data
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
    private Duration connectTimeout = Duration.ofSeconds(9);

    /**
     * socket超时时间
     */
    private Duration socketTimeout = Duration.ofSeconds(9);

    /**
     * i/o 线程数
     */
    private Integer ioThreadCount = 4;

    /**
     * 最大路由数
     */
    private Integer maxConnPerRoute = 10;

    /**
     * 最大连接数
     */
    private Integer maxConnTotal = 20;

}

