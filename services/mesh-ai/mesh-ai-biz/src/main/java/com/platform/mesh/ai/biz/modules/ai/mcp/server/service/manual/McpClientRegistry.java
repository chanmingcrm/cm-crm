package com.platform.mesh.ai.biz.modules.ai.mcp.server.service.manual;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.platform.mesh.ai.biz.modules.ai.mcp.server.constant.McpServerConst;
import com.platform.mesh.ai.biz.modules.ai.mcp.server.domain.po.AiMcpServer;
import com.platform.mesh.ai.biz.modules.ai.mcp.server.domain.ro.RegisteredMcpClient;
import com.platform.mesh.ai.biz.modules.ai.mcp.server.mapper.AiMcpServerMapper;
import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientStreamableHttpTransport;
import io.modelcontextprotocol.spec.McpClientTransport;
import io.modelcontextprotocol.spec.McpSchema;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 功能描述:
 * 〈租户级外部 MCP 客户端注册表〉
 * @author qingfeng
 */
@Service
public class McpClientRegistry {

    private static final Logger log = LoggerFactory.getLogger(McpClientRegistry.class);
    private final AiMcpServerMapper mapper;
    private final McpServerUrlValidator urlValidator;
    private final Map<Long, ClientHolder> clients = new ConcurrentHashMap<>();

    /**
     * 功能描述:
     * 〈创建租户级外部 MCP 客户端注册表〉
     * @param mapper 外部 MCP 连接 Mapper
     * @param urlValidator 服务地址校验器
     * @author qingfeng
     */
    public McpClientRegistry(AiMcpServerMapper mapper, McpServerUrlValidator urlValidator) {
        this.mapper = mapper;
        this.urlValidator = urlValidator;
    }

    /**
     * 功能描述:
     * 〈获取租户全部启用的外部 MCP 客户端〉
     * @param tenantId 租户 ID
     * @return 外部 MCP 客户端列表
     * @author qingfeng
     */
    public List<RegisteredMcpClient> clients() {
        List<AiMcpServer> connections = mapper.selectEnabledExternalServers();
        List<RegisteredMcpClient> result = new ArrayList<>();
        for (AiMcpServer connection : connections) {
            try {
                result.add(client(connection));
            }
            catch (RuntimeException exception) {
                log.warn("外部 MCP 连接初始化失败，connectionId={}", connection.getId(), exception);
            }
        }
        return result;
    }

    /**
     * 功能描述:
     * 〈获取租户指定的外部 MCP 客户端〉
     * @param tenantId 租户 ID
     * @param connectionId 连接 ID
     * @return 已注册的外部 MCP 客户端
     * @author qingfeng
     */
    public RegisteredMcpClient client(long connectionId) {
        AiMcpServer connection = mapper.selectOne(Wrappers.<AiMcpServer>lambdaQuery()
                .eq(AiMcpServer::getId, connectionId)
                .eq(AiMcpServer::getStatusFlag, McpServerConst.STATUS_ENABLED)
                .eq(AiMcpServer::getDelFlag, McpServerConst.DATA_ACTIVE));
        if (connection == null) {
            throw new IllegalArgumentException("外部 MCP 连接不存在或未启用");
        }
        return client(connection);
    }

    /**
     * 功能描述:
     * 〈使指定租户连接缓存失效〉
     * @param tenantId 租户 ID
     * @param connectionId 连接 ID
     * @author qingfeng
     */
    public void invalidate(long connectionId) {
        close(clients.remove(connectionId));
    }

    /**
     * 功能描述:
     * 〈关闭全部外部 MCP 客户端〉
     * @author qingfeng
     */
    @PreDestroy
    public void closeAll() {
        clients.values().forEach(this::close);
        clients.clear();
    }

    /**
     * 功能描述:
     * 〈根据连接版本刷新 MCP 客户端〉
     * @param connection 外部 MCP 连接
     * @param current 当前缓存客户端
     * @return 可用客户端缓存
     * @author qingfeng
     */
    private ClientHolder refresh(AiMcpServer connection, ClientHolder current) {
        String version = connection.getUpdateTime() == null
                ? String.valueOf(connection.getCreateTime()) : connection.getUpdateTime().toString();
        if (current != null && current.version().equals(version)) {
            return current;
        }
        // 刷新必须先初始化新客户端再关闭旧客户端，避免新配置不可用时破坏现有连接。
        McpSyncClient client = createClient(connection);
        try {
            client.initialize();
            close(current);
            return new ClientHolder(version, client);
        }
        catch (RuntimeException exception) {
            client.closeGracefully();
            throw exception;
        }
    }

    /**
     * 功能描述:
     * 〈获取连接对应的缓存客户端〉
     * @param connection 外部 MCP 连接
     * @return 已注册的 MCP 客户端
     * @author qingfeng
     */
    private RegisteredMcpClient client(AiMcpServer connection) {
        ClientHolder holder = clients.compute(connection.getId(),
                (ignored, current) -> refresh(connection, current));
        return new RegisteredMcpClient(connection, holder.client());
    }

    /**
     * 功能描述:
     * 〈根据数据库连接配置创建 MCP 客户端〉
     * @param connection 外部 MCP 连接
     * @return MCP 同步客户端
     * @author qingfeng
     */
    private McpSyncClient createClient(AiMcpServer connection) {
        ClientConfig config = resolveConfig(connection);
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        config.headers().forEach(requestBuilder::header);
        McpClientTransport transport = HttpClientStreamableHttpTransport
                .builder(config.baseUrl())
                .endpoint(config.endpoint())
                .connectTimeout(config.connectTimeout())
                .requestBuilder(requestBuilder)
                .build();
        return McpClient.sync(transport)
                .clientInfo(McpSchema.Implementation.builder(
                        McpServerConst.CLIENT_NAME_PREFIX + connection.getId(),
                        McpServerConst.CLIENT_VERSION).build())
                .initializationTimeout(config.requestTimeout())
                .requestTimeout(config.requestTimeout())
                .build();
    }

    /**
     * 功能描述:
     * 〈解析并校验 MCP 客户端配置〉
     * @param connection 外部 MCP 连接
     * @return MCP 客户端配置
     * @author qingfeng
     */
    private ClientConfig resolveConfig(AiMcpServer connection) {
        if (!McpServerConst.TYPE_STREAMABLE_HTTP.equalsIgnoreCase(connection.getMcpType())) {
            throw new IllegalArgumentException("不支持的外部 MCP 传输类型");
        }
        URI serverUri = urlValidator.validate(connection.getServerUrl());
        String baseUrl = serverUri.getScheme() + "://" + serverUri.getRawAuthority();
        String endpoint = StrUtil.blankToDefault(serverUri.getRawPath(),
                McpServerConst.DEFAULT_STREAMABLE_ENDPOINT);
        return new ClientConfig(baseUrl, endpoint,
                timeout(connection.getConnectTimeoutSeconds(),
                        McpServerConst.DEFAULT_CONNECT_TIMEOUT_SECONDS),
                timeout(connection.getRequestTimeoutSeconds(),
                        McpServerConst.DEFAULT_REQUEST_TIMEOUT_SECONDS),
                headers(connection.getRequestHeaders()));
    }

    /**
     * 功能描述:
     * 〈解析 MCP 请求头配置〉
     * @param value JSON 格式请求头
     * @return 请求头键值对
     * @author qingfeng
     */
    private Map<String, String> headers(String value) {
        if (StrUtil.isBlank(value)) return Map.of();
        return JSONUtil.toBean(value, new TypeReference<Map<String, String>>() {
        }, false);
    }

    /**
     * 功能描述:
     * 〈解析并校验 MCP 超时时间〉
     * @param seconds 配置的超时秒数
     * @param defaultValue 默认超时秒数
     * @return 超时时间
     * @author qingfeng
     */
    private Duration timeout(Integer seconds, int defaultValue) {
        int value = seconds == null ? defaultValue : seconds;
        if (value < McpServerConst.MIN_TIMEOUT_SECONDS
                || value > McpServerConst.MAX_TIMEOUT_SECONDS) {
            throw new IllegalArgumentException("MCP 超时时间超出允许范围");
        }
        return Duration.ofSeconds(value);
    }

    /**
     * 功能描述:
     * 〈安全关闭缓存客户端〉
     * @param holder 客户端缓存
     * @author qingfeng
     */
    private void close(ClientHolder holder) {
        if (holder != null) {
            holder.client().closeGracefully();
        }
    }

    private record ClientHolder(String version, McpSyncClient client) {
    }

    private record ClientConfig(String baseUrl, String endpoint, Duration connectTimeout,
            Duration requestTimeout, Map<String, String> headers) {
    }

}
