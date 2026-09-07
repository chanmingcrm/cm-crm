package com.platform.mesh.ai.biz.modules.ai.mcp.security.external;

import com.platform.mesh.ai.api.modules.mcp.constants.McpConst;
import com.platform.mesh.ai.biz.modules.ai.mcp.accesskey.service.IAiMcpAccessKeyService;
import com.platform.mesh.ai.biz.modules.ai.mcp.security.accesskey.AiMcpAccessKeyVerifier;
import com.platform.mesh.upms.api.modules.sys.user.feign.RemoteUserService;
import org.redisson.api.RedissonClient;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 功能描述:
 * 〈AI MCP 安全过滤器统一配置〉
 *
 * @author qingfeng
 */
@Configuration
public class AiMcpExternalSecurityConfiguration {

    /**
     * 功能描述:
     * 〈创建 AI MCP Access Key SPI 校验器〉
     * @param keyService MCP 访问密钥服务
     * @param remoteUserService 用户远程服务
     * @param redisson Redis 客户端
     * @return AI MCP Access Key SPI 校验器
     * @author qingfeng
     */
    @Bean
    public AiMcpAccessKeyVerifier aiMcpAccessKeyVerifier(
            IAiMcpAccessKeyService keyService,
            RemoteUserService remoteUserService,
            RedissonClient redisson) {
        return new AiMcpAccessKeyVerifier(keyService, remoteUserService, redisson);
    }

    /**
     * 功能描述:
     * 〈创建 OAuth2 MCP 身份桥接过滤器〉
     * @return OAuth2 MCP 身份桥接过滤器
     * @author qingfeng
     */
    @Bean
    public OAuthMcpIdentityFilter oauthMcpIdentityFilter() {
        return new OAuthMcpIdentityFilter();
    }

    /**
     * 功能描述:
     * 〈注册 OAuth2 MCP 身份桥接过滤器〉
     * @param filter OAuth2 MCP 身份桥接过滤器
     * @return 过滤器注册信息
     * @author qingfeng
     */
    @Bean
    public FilterRegistrationBean<OAuthMcpIdentityFilter> oauthMcpIdentityFilterRegistration(
            OAuthMcpIdentityFilter filter) {
        // 在 Spring Security 完成认证后桥接可信租户身份。
        FilterRegistrationBean<OAuthMcpIdentityFilter> registration =
                new FilterRegistrationBean<>(filter);
        registration.setOrder(McpConst.OAUTH_IDENTITY_FILTER_ORDER);
        return registration;
    }

}
