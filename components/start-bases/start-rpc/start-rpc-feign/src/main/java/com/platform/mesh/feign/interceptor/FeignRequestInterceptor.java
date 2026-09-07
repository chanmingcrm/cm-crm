package com.platform.mesh.feign.interceptor;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.feign.context.FeignIdentityContext;
import com.platform.mesh.utils.http.IpUtil;
import com.platform.mesh.utils.spring.ServletUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collection;
import java.util.Enumeration;

/**
 * @description Feign 请求拦截器
 * @author 蝉鸣
 */
public class FeignRequestInterceptor implements RequestInterceptor {

	/**
	 * 配置请求体带上access_token(feign默认不带任何信息)
	 * @param requestTemplate RequestTemplate
	 */
	@Override
	public void apply(RequestTemplate requestTemplate) {
		Collection<String> fromHeader = requestTemplate.headers().get(HttpConst.REQUEST_SOURCE);
		boolean innerRequest = CollUtil.isNotEmpty(fromHeader) && fromHeader.contains(HttpConst.INNER);

		// MCP 工具可能在异步线程执行，优先使用由传输上下文恢复的可信身份完成内部签名。
		if (innerRequest) {
			FeignIdentityContext.Identity identity =
					FeignIdentityContext.current().orElse(null);
			if (identity != null) {
				applyTrustedIdentity(requestTemplate, identity.encodedUser());
				return;
			}
			// 普通登录请求调用受保护的内部接口时，Inner 仅作为调用来源标识，Bearer 仍负责认证。
			HttpServletRequest currentRequest = getCurrentRequest();
			if (currentRequest != null) {
				String authorization = currentRequest.getHeader(HttpHeaders.AUTHORIZATION);
				String bearerPrefix = StrConst.BEARER + " ";
				if (StrUtil.startWithIgnoreCase(authorization, bearerPrefix)) {
					requestTemplate.removeHeader(HttpConst.LOGIN_USER);
					requestTemplate.header(HttpHeaders.AUTHORIZATION, authorization);
					return;
				}
			}
			// 未登录内部调用只保留 Inner 标识，供下游 @AuthIgnore 接口识别。
			applyTrustedIdentity(requestTemplate, null);
			return;
		}

		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (ObjectUtil.isEmpty(requestAttributes)) {
			return;
		}

		RequestContextHolder.setRequestAttributes(requestAttributes, true);

		// 非web 请求直接跳过
		if (ServletUtil.getRequest().isEmpty()) {
			return;
		}
		// 获取请求实例
		HttpServletRequest request = ServletUtil.getRequestInst();
		if(ObjectUtil.isEmpty(request)) {
			return;
		}

		//传递token
		String token;
		try {
			token = request.getHeader(HttpHeaders.AUTHORIZATION);
		} catch (IllegalStateException exception) {
			// 异步线程中的 Servlet Request 可能已经被容器回收
			return;
		}
		if(ObjectUtil.isNotEmpty(token)){
			if(token.startsWith(StrConst.BEARER)){
				requestTemplate.header(HttpHeaders.AUTHORIZATION,token);
			}else{
				requestTemplate.header(HttpHeaders.AUTHORIZATION,
						String.format("%s %s", StrConst.BEARER, token));
			}
		}

		// 配置客户端IP
		try {
			requestTemplate.header(HttpConst.X_FORWARDED_FOR, IpUtil.getHostIp());
		} catch (IllegalStateException exception) {
			// IpUtil 读取异步线程中已被容器回收的 Servlet Request 时直接跳过
			return;
		}

		//装载请求头
		Enumeration<String> headerNames = request.getHeaderNames();
		// 装载web请求所有头部
		if (ObjectUtil.isNotEmpty(headerNames)) {
			while (headerNames.hasMoreElements()) {
				String name = headerNames.nextElement();
				// Content-Length 必须由 Feign 根据新的请求体重新计算，不能沿用入口请求长度。
				if (HttpHeaders.CONTENT_LENGTH.equalsIgnoreCase(name)) {
					continue;
				}
				String values = request.getHeader(name);
				requestTemplate.header(name, values);
			}
		}
	}

	/**
	 * 功能描述:
	 * 〈安全获取当前 Servlet 请求，兼容启动任务及异步线程等无请求上下文场景〉
	 * @return 存在 Web 请求时返回请求对象，否则返回 null
	 * @author qingfeng
	 */
	private HttpServletRequest getCurrentRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes instanceof ServletRequestAttributes servletRequestAttributes) {
			return servletRequestAttributes.getRequest();
		}
		return null;
	}

	/**
	 * 功能描述:
	 * 〈向内部 Feign 请求写入可信身份与防重放签名〉
	 * @param requestTemplate Feign 请求模板
	 * @param user 编码后的登录用户
	 * @author qingfeng
	 */
	private void applyTrustedIdentity(RequestTemplate requestTemplate, String user) {
		requestTemplate.removeHeader(HttpHeaders.AUTHORIZATION);
		requestTemplate.removeHeader(HttpConst.LOGIN_USER);
		requestTemplate.removeHeader(HttpConst.REQUEST_SOURCE);
		if (StrUtil.isNotBlank(user)) requestTemplate.header(HttpConst.LOGIN_USER, user);
		requestTemplate.header(HttpConst.REQUEST_SOURCE, HttpConst.INNER);
	}

}
