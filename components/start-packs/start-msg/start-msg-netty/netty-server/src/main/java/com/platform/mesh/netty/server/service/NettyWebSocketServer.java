package com.platform.mesh.netty.server.service;

import com.platform.mesh.core.constants.NumberConst;
import com.platform.mesh.netty.server.handle.WebSocketServerHandler;
import com.platform.mesh.netty.server.properties.NettyProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class NettyWebSocketServer implements SmartLifecycle {

    @Autowired
    private WebSocketServerHandler webSocketServerHandler;

    @Autowired
    private NettyProperties nettyProperties;
    /**
     * 服务运行状态
     */
    private final AtomicBoolean running = new AtomicBoolean(false);

    /**
     * 主线程组：用于接收客户端连接
     */
    private EventLoopGroup bossGroup;

    /**
     * 工作线程组：用于处理客户端IO操作
     */
    private EventLoopGroup workerGroup;

    /**
     * 服务端通道
     */
    private Channel channel;

    /**
     * 启动服务器
     */
    @Override
    public void start(){
        if (!running.compareAndSet(Boolean.FALSE, Boolean.TRUE)) {
            return;
        }
        // 创建主线程组：用于接收客户端连接
        bossGroup = new MultiThreadIoEventLoopGroup(getBossThreads(), NioIoHandler.newFactory());
        // 创建工作线程组：用于处理客户端IO操作
        workerGroup = new MultiThreadIoEventLoopGroup(getWorkerThreads(), NioIoHandler.newFactory());

        try {
            // 服务器启动辅助类
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    // 设置服务器通道实现
                    .channel(NioServerSocketChannel.class)
                    // 主线程组配置
                    .option(ChannelOption.SO_BACKLOG, NumberConst.NUM_128)
                    // 工作线程组配置
                    .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                    // 设置通道初始化器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            // 获取管道
                            var pipeline = ch.pipeline();

                            // 添加HTTP编解码器
                            pipeline.addLast(new HttpServerCodec());
                            // 添加大数据流处理器
                            pipeline.addLast(new ChunkedWriteHandler());
                            // 聚合HTTP消息（将HTTP消息的多个部分合成一个完整的消息）
                            pipeline.addLast(new HttpObjectAggregator(65536));
                            // 添加空闲状态处理器（30秒无操作则关闭连接）
                            pipeline.addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS));
                            // WebSocket协议处理器，用于HTTP升级到WebSocket
                            pipeline.addLast(new WebSocketServerProtocolHandler(nettyProperties.getWebsocketPath()));
                            // 自定义WebSocket消息处理器
                            pipeline.addLast(webSocketServerHandler);
                        }
                    });

            // 绑定端口并启动服务器
            ChannelFuture future = bootstrap.bind(nettyProperties.getPort()).sync();
            channel = future.channel();
            System.out.println("WebSocket服务器已启动，监听端口：" + nettyProperties.getPort());
        }catch (Exception e){
            shutdownGroups();
            running.set(Boolean.FALSE);
            throw new IllegalStateException("Start Netty WebSocket server failed", e);
        }
    }

    /**
     * 停止服务器
     */
    @Override
    public void stop() {
        if (!running.compareAndSet(Boolean.TRUE, Boolean.FALSE)) {
            return;
        }
        try {
            if (channel != null) {
                channel.close().syncUninterruptibly();
            }
        } finally {
            channel = null;
            shutdownGroups();
        }
    }


    /**
     * 是否运行中
     * @return 运行状态
     */
    @Override
    public boolean isRunning() {
        return running.get();
    }

    /**
     * 获取主线程组线程数
     * @return 主线程组线程数
     */
    private int getBossThreads() {
        Integer bossThreads = nettyProperties.getBossThreads();
        return bossThreads == null || bossThreads < 1 ? 1 : bossThreads;
    }

    /**
     * 获取工作线程组线程数
     * @return 工作线程组线程数
     */
    private int getWorkerThreads() {
        Integer workerThreads = nettyProperties.getWorkerThreads();
        return workerThreads == null || workerThreads < 1 ? 4 : workerThreads;
    }

    /**
     * 优雅关闭线程组
     */
    private void shutdownGroups() {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
            bossGroup = null;
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
            workerGroup = null;
        }
    }

}
