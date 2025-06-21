package com.platform.mesh.feign.interceptor;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.core.constants.StrConst;
import com.platform.mesh.utils.http.IpUtil;
import com.platform.mesh.utils.spring.ServletUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

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
		//装载请求头
		Enumeration<String> headerNames = request.getHeaderNames();
		// 装载web请求所有头部
		if (ObjectUtil.isEmpty(headerNames)) {
			while (headerNames.hasMoreElements()) {
				String name = headerNames.nextElement();
				String values = request.getHeader(name);
				requestTemplate.header(name, values);
			}
		}
		//传递token
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		if(ObjectUtil.isNotEmpty(token)){
			if(token.startsWith(StrConst.BEARER)){
				requestTemplate.header(HttpHeaders.AUTHORIZATION,token);
			}else{
				requestTemplate.header(HttpHeaders.AUTHORIZATION,
						String.format("%s %s", StrConst.BEARER, token));
			}
		}

		Collection<String> fromHeader = requestTemplate.headers().get(HttpConst.REQUEST_SOURCE);
		// 带from 请求直接跳过
		if (CollUtil.isNotEmpty(fromHeader) && fromHeader.contains(HttpConst.INNER)) {
			return;
		}

		// 配置客户端IP
		requestTemplate.header("X-Forwarded-For", IpUtil.getIpAddr());
	}

}
