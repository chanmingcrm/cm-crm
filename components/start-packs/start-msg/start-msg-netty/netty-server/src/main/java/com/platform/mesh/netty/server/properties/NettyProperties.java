package com.platform.mesh.netty.server.properties;

import com.platform.mesh.netty.server.constant.NettyConst;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description 自动配置属性信息
 * @author 蝉鸣
 */
@Data
@ConfigurationProperties(prefix = NettyConst.CONFIG_PREFIX)
public class NettyProperties {

    /**
     * 开启
     */
    private Boolean enabled = false;

    /**
     * WebSocket服务器端口
     */
    private Integer port;

    /**
     * WebSocket路径
     */
    private String websocketPath = NettyConst.WEBSOCKET_PATH;

    /**
     * 主线程组线程数
     */
    private Integer bossThreads = 1;

    /**
     * 工作线程组线程数
     */
    private Integer workerThreads = 4;

}
