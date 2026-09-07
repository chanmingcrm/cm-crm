package com.platform.mesh.log.utils;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import com.platform.mesh.upms.api.modules.sys.log.domain.bo.LogLoginBO;
import com.platform.mesh.upms.api.modules.sys.log.domain.bo.LogModifyBO;
import com.platform.mesh.utils.http.IpUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @description 日志工具类
 * @author 蝉鸣
 */
public class SysLogUtils {

	/**
	 * 功能描述:
	 * 〈获取日志对象〉
	 * @return 正常返回:{@link LogLoginBO}
	 */
	public static LogLoginBO getSysLoginLog() {
		HttpServletRequest request = ((ServletRequestAttributes) Objects
				.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
		LogLoginBO logLoginBO = new LogLoginBO();
		logLoginBO.setLoginIp(IpUtil.getHostIp());
		logLoginBO.setLoginAddr(IpUtil.getIpAddr(logLoginBO.getLoginIp()));
		logLoginBO.setLoginAgent(request.getHeader(HttpHeaders.USER_AGENT));
		return logLoginBO;
	}

	/**
	 * 功能描述:
	 * 〈获取日志对象〉
	 * @return 正常返回:{@link LogModifyBO}
	 */
	public static LogModifyBO getSysModifyLog() {
		HttpServletRequest request = ((ServletRequestAttributes) Objects
				.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
		LogModifyBO logModifyBO = new LogModifyBO();
		return logModifyBO;
	}

	/**
	 * 获取spel 定义的参数值
	 * @param context 参数容器
	 * @param key key
	 * @param clazz 需要返回的类型
	 * @param <T> 返回泛型
	 * @return 参数值
	 */
	public static <T> T getValue(EvaluationContext context, String key, Class<T> clazz) {
		SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
		Expression expression = spelExpressionParser.parseExpression(key);
		return expression.getValue(context, clazz);
	}

	/**
	 * 获取参数容器
	 * @param arguments 方法的参数列表
	 * @param signatureMethod 被执行的方法体
	 * @return 装载参数的容器
	 */
	public static EvaluationContext getContext(Object[] arguments, Method signatureMethod) {
		String[] parameterNames = new StandardReflectionParameterNameDiscoverer().getParameterNames(signatureMethod);
		EvaluationContext context = new StandardEvaluationContext();
		if (parameterNames == null) {
			return context;
		}
		for (int i = 0; i < arguments.length; i++) {
			context.setVariable(parameterNames[i], arguments[i]);
		}
		return context;
	}

}