package com.platform.mesh.resource.inner;

import cn.hutool.json.JSONUtil;
import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.resource.authentication.UserAuthenticationToken;
import com.platform.mesh.resource.channel.AuthenticationChannelResolver;
import com.platform.mesh.security.authentication.AuthenticationChannel;
import com.platform.mesh.security.constants.SecurityConstant;
import com.platform.mesh.security.domain.bo.LoginUserBO;
import com.platform.mesh.security.exception.SecurityExceptionEnum;
import com.platform.mesh.security.service.impl.ResourceAuthExceptionEntryPoint;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * 功能描述:
 * 〈识别 Inner 调用标识并恢复跨服务传递的用户和账户身份〉
 *
 * @author qingfeng
 */
public class InnerIdentityFilter extends OncePerRequestFilter {

    private final AuthenticationChannelResolver channelResolver;
    private final InnerEndpointPolicy endpointPolicy;
    private final ResourceAuthExceptionEntryPoint authenticationEntryPoint;
    private final RequestAttributeSecurityContextRepository contextRepository =
            new RequestAttributeSecurityContextRepository();

    /**
     * 功能描述:
     * 〈创建 Inner 身份恢复过滤器〉
     * @param channelResolver 认证通道解析器
     * @param endpointPolicy 内部接口安全边界策略
     * @param authenticationEntryPoint 统一认证异常响应入口
     * @author qingfeng
     */
    public InnerIdentityFilter(AuthenticationChannelResolver channelResolver,
            InnerEndpointPolicy endpointPolicy,
            ResourceAuthExceptionEntryPoint authenticationEntryPoint) {
        this.channelResolver = channelResolver;
        this.endpointPolicy = endpointPolicy;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    /**
     * 功能描述:
     * 〈Inner 请求携带身份时恢复安全上下文，未携带身份时保持匿名调用〉
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
        // 先识别安全边界，再校验方法与规范路径，避免非法形式绕过认证过滤器。
        Optional<InnerEndpointPolicy.Match> endpointMatch = endpointPolicy.match(
                request.getMethod(), request.getRequestURI());
        if (endpointMatch.isPresent() && !endpointMatch.get().valid()) {
            authenticationEntryPoint.write(request, response,
                    SecurityExceptionEnum.INNER_ENDPOINT_INVALID.getBaseException());
            return;
        }
        if (channelResolver.resolve(request) != AuthenticationChannel.INNER) {
            filterChain.doFilter(request, response);
            return;
        }
        String encodedUser = request.getHeader(HttpConst.LOGIN_USER);
        // Inner 仅作为内部调用标识；没有委托身份时允许 @AuthIgnore 接口匿名执行。
        if (encodedUser == null || encodedUser.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }
        // 解析由上游服务从已认证上下文生成的身份，不读取环境或租户配置。
        var identity = JSONUtil.parseObj(URLDecoder.decode(encodedUser, StandardCharsets.UTF_8));
        Long userId = identity.getLong("userId");
        Long accountId = identity.getLong("accountId");
        String username = identity.getStr("username", "inner:" + accountId);
        String nickname = identity.getStr("nickname", username);
        LoginUserBO principal = new LoginUserBO(userId, accountId, nickname, username, "",
                true, true, true, true, AuthorityUtils.NO_AUTHORITIES);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UserAuthenticationToken(principal));
        SecurityContextHolder.setContext(context);
        contextRepository.saveContext(context, request, response);
        request.setAttribute(SecurityConstant.AUTHENTICATION_ENCODED_USER_ATTRIBUTE, encodedUser);
        request.setAttribute(SecurityConstant.AUTHENTICATION_PRINCIPAL_ATTRIBUTE, principal);
        filterChain.doFilter(request, response);
    }
}
