package com.platform.mesh.security.aspect;

import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.security.annotation.AuthIgnore;
import com.platform.mesh.utils.spring.ServletUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.security.access.AccessDeniedException;

/**
 * @description 忽略内部请求的授权
 * @author 蝉鸣
 */
@Aspect
public class AuthIgnoreSecurityAspect implements Ordered {

	private final static Logger log = LoggerFactory.getLogger(AuthIgnoreSecurityAspect.class);

	@Around("@annotation(authIgnore)")
	public Object around(ProceedingJoinPoint point, AuthIgnore authIgnore) throws Throwable {
		String requestSource = ServletUtil.getRequestInst().getHeader(HttpConst.REQUEST_SOURCE);

		if (authIgnore.value() && !HttpConst.INNER.equals(requestSource)) {
			log.warn("访问接口 {} 没有权限", point.getSignature().getName());
			throw new AccessDeniedException("Access is denied");
		}
		return point.proceed();
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE + 1;
	}

}
