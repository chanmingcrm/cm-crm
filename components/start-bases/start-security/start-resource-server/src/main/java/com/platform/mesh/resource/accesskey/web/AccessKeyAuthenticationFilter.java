package com.platform.mesh.resource.accesskey.web;

import com.platform.mesh.core.web.CanonicalRequestPath;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.resource.accesskey.authentication.AccessKeyAuthenticationToken;
import com.platform.mesh.resource.accesskey.registry.AccessKeyVerifierRegistry;
import com.platform.mesh.resource.channel.AuthenticationChannelResolver;
import com.platform.mesh.feign.context.FeignIdentityContext;
import com.platform.mesh.security.accesskey.model.AccessKeyCredential;
import com.platform.mesh.security.accesskey.model.AuthenticatedAccessKey;
import com.platform.mesh.security.accesskey.spi.AccessKeyVerifier;
import com.platform.mesh.security.authentication.AuthenticationChannel;
import com.platform.mesh.security.domain.bo.LoginUserBO;
import com.platform.mesh.security.exception.SecurityExceptionEnum;
import com.platform.mesh.security.constants.SecurityConstant;
import com.platform.mesh.security.service.impl.ResourceAuthExceptionEntryPoint;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.core.constants.SymbolConst;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 功能描述:
 * 〈统一处理 Access Key 认证，业务密钥校验由对应模块的 SPI 实现〉
 *
 * @author qingfeng
 */
public class AccessKeyAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationChannelResolver channelResolver;
    private final AccessKeyVerifierRegistry verifierRegistry;
    private final ResourceAuthExceptionEntryPoint authenticationEntryPoint;
    private final SecurityContextRepository contextRepository =
            new RequestAttributeSecurityContextRepository();

    /**
     * 功能描述:
     * 〈创建 Access Key 认证过滤器〉
     * @param channelResolver 认证通道解析器
     * @param verifierRegistry Access Key 校验器注册表
     * @param authenticationEntryPoint 统一认证异常响应处理器
     * @author qingfeng
     */
    public AccessKeyAuthenticationFilter(AuthenticationChannelResolver channelResolver,
            AccessKeyVerifierRegistry verifierRegistry,
            ResourceAuthExceptionEntryPoint authenticationEntryPoint) {
        this.channelResolver = channelResolver;
        this.verifierRegistry = verifierRegistry;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    /**
     * 功能描述:
     * 〈识别 Access Key 通道并完成密钥校验、授权范围校验和安全上下文写入〉
     * @param request HTTP 请求
     * @param response HTTP 响应
     * @param filterChain 过滤器链
     * @throws ServletException Servlet 处理异常
     * @throws IOException 请求响应异常
     * @author qingfeng
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            // 非 Access Key 请求交由 Bearer、Inner 或匿名通道继续处理。
            if (channelResolver.resolve(request) != AuthenticationChannel.ACCESS_KEY) {
                filterChain.doFilter(request, response);
                return;
            }
            // 解析统一凭证格式，并按凭证类型路由到业务模块 SPI。
            ParsedCredential parsed = parse(request.getHeader(HttpHeaders.AUTHORIZATION));
            AccessKeyVerifier verifier = verifierRegistry.require(parsed.type);
            AuthenticatedAccessKey accessKey = verifier.verify(
                    new AccessKeyCredential(parsed.type, parsed.secret));
            // 校验 SPI 返回的身份类型，防止错误实现返回其他类型的身份。
            if (accessKey == null || !verifier.supports(accessKey.credentialType())) {
                throw SecurityExceptionEnum.ACCESS_KEY_IDENTITY_INVALID.getBaseException();
            }
            // 默认拒绝所有接口，只有业务 SPI 显式允许的路径才能继续访问。
            if (!verifier.isRequestAllowed(accessKey, request.getMethod(),
                    CanonicalRequestPath.applicationPath(request))) {
                throw SecurityExceptionEnum.ACCESS_KEY_ENDPOINT_FORBIDDEN.getBaseException();
            }
            // 将 Access Key 绑定的用户和账户转换为 Spring Security 身份。
            LoginUserBO principal = toPrincipal(accessKey);
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(new AccessKeyAuthenticationToken(principal, accessKey));
            SecurityContextHolder.setContext(context);
            contextRepository.saveContext(context, request, response);
            // 保存可信请求属性，供 MCP 传输上下文和后续内部 Feign 调用使用。
            String encodedUser = URLEncoder.encode(JSONUtil.toJsonStr(principal), StandardCharsets.UTF_8);
            request.setAttribute(SecurityConstant.AUTHENTICATION_ENCODED_USER_ATTRIBUTE, encodedUser);
            request.setAttribute(SecurityConstant.AUTHENTICATION_PRINCIPAL_ATTRIBUTE, principal);
            request.setAttribute(SecurityConstant.AUTHENTICATION_ACCESS_KEY_ATTRIBUTE, Boolean.TRUE);
            // 在当前请求线程绑定可信身份，供后续内部 Feign 调用读取。
            try (FeignIdentityContext.Scope ignored = FeignIdentityContext.open(
                    encodedUser, accessKey.accountId().toString(), true)) {
                // 移除已消费的 Authorization，避免后续 Bearer Filter 重复解析。
                filterChain.doFilter(withoutAuthorization(request), response);
            }
        }
        catch (RuntimeException exception) {
            SecurityContextHolder.clearContext();
            authenticationEntryPoint.write(request, response, exception);
        }
    }

    /**
     * 功能描述:
     * 〈解析 AccessKey type:secret 格式的 Authorization 请求头〉
     * @param authorization Authorization 请求头
     * @return 待业务模块校验的凭证类型和密钥
     * @author qingfeng
     */
    private ParsedCredential parse(String authorization) {
        String prefix = StrConst.ACCESS_KEY + SymbolConst.SPACE;
        if (authorization.regionMatches(true, 0, prefix, 0, prefix.length())) {
            String value = authorization.substring(prefix.length()).trim();
            int separator = value.indexOf(SymbolConst.COLON);
            if (separator <= 0 || separator == value.length() - 1) {
                throw SecurityExceptionEnum.ACCESS_KEY_FORMAT_INVALID.getBaseException();
            }
            return new ParsedCredential(value.substring(0, separator), value.substring(separator + 1));
        }
        throw SecurityExceptionEnum.AUTH_SCHEME_UNSUPPORTED.getBaseException();
    }

    /**
     * 功能描述:
     * 〈将 Access Key 绑定身份转换为统一登录用户〉
     * @param accessKey 已认证的 Access Key 身份
     * @return Spring Security 登录用户
     * @author qingfeng
     */
    private LoginUserBO toPrincipal(AuthenticatedAccessKey accessKey) {
        var authorities = accessKey.scopes().stream()
                .map(scope -> new SimpleGrantedAuthority("SCOPE_" + scope))
                .toList();
        String username = "access-key:" + accessKey.accountId();
        return new LoginUserBO(accessKey.userId(), accessKey.accountId(), username,
                username, "", true, true, true, true, authorities);
    }

    /**
     * 功能描述:
     * 〈包装请求并隐藏已经消费的 Authorization 请求头〉
     * @param request 原始 HTTP 请求
     * @return 不再暴露 Authorization 的请求
     * @author qingfeng
     */
    private HttpServletRequest withoutAuthorization(HttpServletRequest request) {
        return new HttpServletRequestWrapper(request) {
            @Override
            public String getHeader(String name) {
                return HttpHeaders.AUTHORIZATION.equalsIgnoreCase(name) ? null : super.getHeader(name);
            }

            @Override
            public Enumeration<String> getHeaders(String name) {
                return HttpHeaders.AUTHORIZATION.equalsIgnoreCase(name)
                        ? Collections.emptyEnumeration() : super.getHeaders(name);
            }

            @Override
            public Enumeration<String> getHeaderNames() {
                // 同步移除请求头名称，保证名称集合与 getHeader、getHeaders 的读取结果一致。
                return Collections.enumeration(Collections.list(super.getHeaderNames()).stream()
                        .filter(name -> !HttpHeaders.AUTHORIZATION.equalsIgnoreCase(name))
                        .toList());
            }
        };
    }

    /** Access Key 请求头解析后的内部凭证结构。 */
    private record ParsedCredential(String type, String secret) {
    }
}
