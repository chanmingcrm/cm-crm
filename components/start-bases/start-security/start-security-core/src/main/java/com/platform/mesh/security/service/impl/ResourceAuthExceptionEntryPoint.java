package com.platform.mesh.security.service.impl;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.platform.mesh.utils.result.Result;
import com.platform.mesh.utils.spring.ServletUtil;
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
		try {
			response.setCharacterEncoding(CharsetUtil.UTF_8);
			Result<String> result = new Result<>();
			result.setCode(HttpStatus.HTTP_UNAUTHORIZED);
			int code = HttpStatus.HTTP_UNAUTHORIZED;
			if (authException != null) {
				result.setMsg("error");
				result.setData(authException.getMessage());
			}

			// 针对令牌过期返回特殊的 424
			if (authException instanceof InvalidBearerTokenException
					|| authException instanceof InsufficientAuthenticationException) {
				code = HttpStatus.HTTP_OK;
				result.setMsg(
						this.messageSource.getMessage("OAuth2ResourceOwnerBaseAuthenticationProvider.tokenExpired",
								null, LocaleContextHolder.getLocale()));
			}

			ServletUtil.render(code, JSONUtil.toJsonStr(result));
		}
		catch (Exception e) {
			log.error("鉴权返回错误失败", e);
		}
	}

}