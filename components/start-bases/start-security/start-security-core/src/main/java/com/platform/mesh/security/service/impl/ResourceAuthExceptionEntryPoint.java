package com.platform.mesh.security.service.impl;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.utils.result.Result;
import com.platform.mesh.core.exception.BaseException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.nio.charset.StandardCharsets;
import java.io.IOException;

/**
 * @description 资源服务器异常处理
 * @author 蝉鸣
 */
public class ResourceAuthExceptionEntryPoint implements AuthenticationEntryPoint {

	private static final Logger log = LoggerFactory.getLogger(ResourceAuthExceptionEntryPoint.class);

	private final MessageSource messageSource;

	public ResourceAuthExceptionEntryPoint(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) {
		write(request, response, authException);
	}

	/**
	 * 功能描述:
	 * 〈将 Bearer、Access Key 等认证异常统一转换为 Result 响应〉
	 * @param request HTTP 请求
	 * @param response HTTP 响应
	 * @param exception 认证异常
	 * @author qingfeng
	 */
	public void write(HttpServletRequest request, HttpServletResponse response, Throwable exception) {
		try {
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			Result<String> result = new Result<>();
			result.setCode(HttpStatus.HTTP_UNAUTHORIZED);
			result.setMsg("认证失败");
			if (exception instanceof BaseException baseException) {
				result.setModule(baseException.getModule());
				result.setCode(baseException.getCode());
				result.setMsg(baseException.getDesc());
			}
			else if (exception != null) {
				result.setMsg("认证失败");
			}

			if (exception instanceof InvalidBearerTokenException
					|| exception instanceof InsufficientAuthenticationException) {
				result.setMsg(
						this.messageSource.getMessage("OAuth2ResourceOwnerBaseAuthenticationProvider.tokenExpired",
								null, "Token 无效或已过期", LocaleContextHolder.getLocale()));
			}
			response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(JSONUtil.toJsonStr(result));
		}
		catch (IOException e) {
			log.error("鉴权返回错误失败", e);
		}
	}

}
