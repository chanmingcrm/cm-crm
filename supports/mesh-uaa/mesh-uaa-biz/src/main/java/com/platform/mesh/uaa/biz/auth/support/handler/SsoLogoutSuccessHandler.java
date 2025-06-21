package com.platform.mesh.uaa.biz.auth.support.handler;

import cn.hutool.core.util.StrUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description sso 退出功能 ，根据客户端传入跳转
 * @author 蝉鸣
 */
public class SsoLogoutSuccessHandler implements LogoutSuccessHandler {

	private static final String REDIRECT_URL = "redirect_url";

	/**
	 * 功能描述:
	 * 〈登出成功处理〉
	 * @param request request
	 * @param response response
	 * @param authentication authentication
	 * @author 蝉鸣
	 */
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {
		if (response == null) {
			return;
		}

		// 获取请求参数中是否包含 回调地址
		String redirectUrl = request.getParameter(REDIRECT_URL);
		if (StrUtil.isNotBlank(redirectUrl)) {
			response.sendRedirect(redirectUrl);
		}
		else if (StrUtil.isNotBlank(request.getHeader(HttpHeaders.REFERER))) {
			// 默认跳转referer 地址
			String referer = request.getHeader(HttpHeaders.REFERER);
			response.sendRedirect(referer);
		}
	}

}
