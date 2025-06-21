package com.platform.mesh.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @description 基础环境参数配置
 * @author 蝉鸣
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "mesh.endpoint")
public class EnvironmentProperty {

    /**
     * 认证中心服务名称
     */
    private String uaaServiceName;
    /**
     * 用户中心服务名称
     */
    private String upmsServiceName;

    /**
     * 统一网关服务地址。可以是IP+端口，可以是域名
     */
    private String gatewayServiceUri;

    /**
     * 统一认证中心服务地址
     */
    private String uaaServiceUri;
    /**
     * 统一权限管理服务地址
     */
    private String upmsServiceUri;
    /**
     * OAuth2 /oauth2/token 申请 Token uri 地址，可修改为自定义地址
     */
    private String accessTokenUri;

    /**
     * OAuth2 Authorization Code 模式认证端点，/oauth2/authorize uri 地址，可修改为自定义地址
     */
    private String authorizationUri;

    /**
     * OAuth2 /oauth2/revoke 撤销 Token uri 地址，可修改为自定义地址
     */
    private String tokenRevocationUri;
    /**
     * OAuth2 /oauth2/introspect 查看 Token uri地址，可修改为自定义地址
     */
    private String tokenIntrospectionUri;
    /**
     * OAuth2 /oauth2/jwks uri 地址，可修改为自定义地址
     */
    private String jwkSetUri;

    /**
     * Spring Authorization Server Issuer Url 认证颁发地址
     */
    private String issuerUri;

    /**
     * OAuth2 OIDC /connect/register uri 地址，可修改为自定义地址
     */
    private String oidcClientRegistrationUri;
    /**
     * OAuth2 OIDC /userinfo uri 地址，可修改为自定义地址
     */
    private String oidcUserInfoUri;
    /**
     * 校验token地址
     */
    private String checkTokenUri;

    /**
     * license地址
     */
    private String licenseUri;
    /**
     * nacos地址
     */
    private String nacosUri;
    /**
     * sentinel地址
     */
    private String sentinelUri;
}
