package com.platform.mesh.uaa.biz.auth.support.handler;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpUtil;
import com.platform.mesh.utils.spring.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description 表单登录失败处理逻辑
 * @author 蝉鸣
 */

public class FormAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private static final Logger log = LoggerFactory.getLogger(FormAuthenticationFailureHandler.class);

	/**
	 * 功能描述:
	 * 〈表单认证失败处理〉
	 * @param request request
	 * @param response response
	 * @param exception exception
	 * @author 蝉鸣
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) {
		log.debug("表单登录失败:{}", exception.getLocalizedMessage());
		String url = HttpUtil.encodeParams(String.format("/token/login?error=%s", exception.getMessage()),
				CharsetUtil.CHARSET_UTF_8);
		try {
			ServletUtil.getResponse().sendRedirect(url);
		}
		catch (IOException e) {
			throw new RuntimeException("重定向失败");
		}
	}

}
