package com.platform.mesh.ai.biz.modules.ai.mcp.security.external;

import cn.hutool.json.JSONUtil;
import com.platform.mesh.core.web.CanonicalRequestPath;
import com.platform.mesh.ai.api.modules.mcp.constants.McpConst;
import com.platform.mesh.security.constants.SecurityConstant;
import com.platform.mesh.security.domain.bo.LoginUserBO;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.feign.context.FeignIdentityContext;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 功能描述:
 * 〈将 OAuth2 MCP 登录身份转换为内部可信租户上下文〉
 *
 * @author qingfeng
 */
public class OAuthMcpIdentityFilter implements Filter {

    /**
     * 功能描述:
     * 〈在 OAuth2 认证完成后建立 MCP 业务调用所需的可信身份〉
     *
     * @param servletRequest Servlet 请求
     * @param servletResponse Servlet 响应
     * @param chain 过滤器链
     * @throws IOException 请求响应异常
     * @throws ServletException Servlet 处理异常
     * @author qingfeng
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (!McpConst.SERVER_PATH.equals(CanonicalRequestPath.applicationPath(request))) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }
        // Access Key 过滤器已经建立完整身份，OAuth 适配器不得覆盖其认证来源标识。
        if (Boolean.TRUE.equals(request.getAttribute(
                SecurityConstant.AUTHENTICATION_ACCESS_KEY_ATTRIBUTE))) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }
        LoginUserBO loginUser = UserCacheUtil.getLoginUser();
        if (loginUser == null || loginUser.getAccountId() == null) {
            chain.doFilter(servletRequest, servletResponse);
            return;
        }
        Object previousUser = request.getAttribute(
                SecurityConstant.AUTHENTICATION_ENCODED_USER_ATTRIBUTE);
        Object previousPrincipal = request.getAttribute(
                SecurityConstant.AUTHENTICATION_PRINCIPAL_ATTRIBUTE);
        Object previousAccessKey = request.getAttribute(
                SecurityConstant.AUTHENTICATION_ACCESS_KEY_ATTRIBUTE);
        String encodedUser = URLEncoder.encode(JSONUtil.toJsonStr(loginUser), StandardCharsets.UTF_8);
        try (FeignIdentityContext.Scope ignored = FeignIdentityContext.open(
                encodedUser, loginUser.getAccountId().toString(), false)) {
            // 保存服务端生成的认证属性，供 MCP 传输上下文读取，禁止直接信任外部身份请求头。
            request.setAttribute(SecurityConstant.AUTHENTICATION_ENCODED_USER_ATTRIBUTE, encodedUser);
            request.setAttribute(SecurityConstant.AUTHENTICATION_PRINCIPAL_ATTRIBUTE, loginUser);
            request.setAttribute(SecurityConstant.AUTHENTICATION_ACCESS_KEY_ATTRIBUTE, Boolean.FALSE);
            chain.doFilter(servletRequest, servletResponse);
        }
        finally {
            restoreAttribute(request, SecurityConstant.AUTHENTICATION_ENCODED_USER_ATTRIBUTE,
                    previousUser);
            restoreAttribute(request, SecurityConstant.AUTHENTICATION_PRINCIPAL_ATTRIBUTE,
                    previousPrincipal);
            restoreAttribute(request, SecurityConstant.AUTHENTICATION_ACCESS_KEY_ATTRIBUTE,
                    previousAccessKey);
        }
    }

    /**
     * 功能描述:
     * 〈恢复请求进入过滤器前的属性值〉
     *
     * @param request HTTP 请求
     * @param name 属性名称
     * @param value 原属性值
     * @author qingfeng
     */
    private void restoreAttribute(HttpServletRequest request, String name, Object value) {
        if (value == null) {
            request.removeAttribute(name);
        }
        else {
            request.setAttribute(name, value);
        }
    }
}
