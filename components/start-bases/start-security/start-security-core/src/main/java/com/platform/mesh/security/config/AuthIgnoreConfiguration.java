package com.platform.mesh.security.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.platform.mesh.core.constants.SymbolConst;
import com.platform.mesh.security.annotation.AuthIgnore;
import com.platform.mesh.security.constants.SecurityConstant;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @description 忽略服务间的认证
 * @author 蝉鸣
 */
@Data
@ConfigurationProperties(prefix = "security.oauth2.ignore")
public class AuthIgnoreConfiguration implements InitializingBean {

	private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");

	private List<String> urls = CollUtil.newArrayList();

	/**
	 * 功能描述:
	 * 〈重写bean注入后〉
	 * @author 蝉鸣
	 */
	@Override
	public void afterPropertiesSet() {
		RequestMappingHandlerMapping mapping = SpringUtil.getBean("requestMappingHandlerMapping");
		Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

		// 遍历所有mapping
		map.keySet().forEach(mappingInfo -> {

			HandlerMethod handlerMethod = map.get(mappingInfo);
			// 获取方法上边的注解 替代path variable 为 *
			AuthIgnore method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), AuthIgnore.class);
			Optional.ofNullable(method)
					.ifPresent(authIgnore -> Objects.requireNonNull(mappingInfo.getPathPatternsCondition())
							.getPatternValues().forEach(url -> urls.add(ReUtil.replaceAll(url, PATTERN, SymbolConst.STAR))));

			// 获取类上边的注解, 替代path variable 为 *
			AuthIgnore controller = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), AuthIgnore.class);
			Optional.ofNullable(controller)
					.ifPresent(inner -> Objects.requireNonNull(mappingInfo.getPathPatternsCondition())
							.getPatternValues().forEach(url -> urls.add(ReUtil.replaceAll(url, PATTERN, SymbolConst.STAR))));
		});
	}

	/**
	 * 功能描述:
	 * 〈忽略认证请求〉
	 * @return 正常返回:{@link RequestMatcher}
	 * @author 蝉鸣
	 */
	public RequestMatcher[] getPermitAllRequestMatchers() {
		if (CollUtil.isNotEmpty(this.urls)) {
			this.urls.addAll(SecurityConstant.STATIC_IGNORE_URLS);
			List<AntPathRequestMatcher> matchers = this.urls.stream().map(AntPathRequestMatcher::new).toList();
			RequestMatcher[] result = new RequestMatcher[matchers.size()];
			return matchers.toArray(result);
		} else {
			return new RequestMatcher[]{};
		}
	}

}
