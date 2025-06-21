package com.platform.mesh.uaa.biz.auth.support.handler;

import cn.hutool.core.map.MapUtil;
import com.platform.mesh.log.enums.LoginFlagEnum;
import com.platform.mesh.log.event.SysLoginLogEvent;
import com.platform.mesh.log.utils.SysLogUtils;
import com.platform.mesh.security.constants.SecurityConstant;
import com.platform.mesh.security.domain.bo.LoginUserBO;
import com.platform.mesh.security.utils.OAuth2AuthorizationUtils;
import com.platform.mesh.upms.api.modules.sys.log.domain.bo.LogLoginBO;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Map;

/**
 * @description 处理登录成功
 * @author 蝉鸣
 */
public class AuthenticationSuccessEventHandler implements AuthenticationSuccessHandler {

	private static final Logger log = LoggerFactory.getLogger(AuthenticationSuccessEventHandler.class);

	private final HttpMessageConverter<OAuth2AccessTokenResponse> accessTokenHttpResponseConverter = new OAuth2AccessTokenResponseHttpMessageConverter();

	/**
	 * 功能描述:
	 * 〈授权成功处理〉
	 * @param request request
	 * @param response response
	 * @param authentication authentication
	 * @author 蝉鸣
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {
		SecurityContextHolder.getContext().setAuthentication(authentication);
		OAuth2AccessTokenAuthenticationToken accessTokenAuthentication = (OAuth2AccessTokenAuthenticationToken) authentication;
		Map<String, Object> map = accessTokenAuthentication.getAdditionalParameters();
		if (MapUtil.isNotEmpty(map)) {
			// 发送异步日志事件
			if(map.containsKey(SecurityConstant.DETAILS_USER)){
				LoginUserBO userInfo = (LoginUserBO) map.get(SecurityConstant.DETAILS_USER);
				String username = userInfo.getName();
				LogLoginBO logLoginBO = SysLogUtils.getSysLoginLog();
				logLoginBO.setUserId(userInfo.getUserId());
				logLoginBO.setAccountId(userInfo.getAccountId());
				logLoginBO.setLoginUserName(username);
				logLoginBO.setLoginFlag(LoginFlagEnum.LOGIN.getValue());
				logLoginBO.setRemark("登录成功");
				// 发送异步日志事件
				SpringContextHolderUtil.publishEvent(new SysLoginLogEvent(logLoginBO));
			}
		}

		// 输出token
		try {
			sendAccessTokenResponse(request, response, authentication);
		}
		catch (IOException e) {
			log.error("返回消息失败", e);
		}
	}

	/**
	 * 功能描述:
	 * 〈发送AccessToken响应〉
	 * @param request request
	 * @param response response
	 * @param authentication authentication
	 * @author 蝉鸣
	 */
	private void sendAccessTokenResponse(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {

		OAuth2AccessTokenAuthenticationToken accessTokenAuthentication = (OAuth2AccessTokenAuthenticationToken) authentication;

		OAuth2AccessToken accessToken = accessTokenAuthentication.getAccessToken();
		OAuth2RefreshToken refreshToken = accessTokenAuthentication.getRefreshToken();
		Map<String, Object> additionalParameters = accessTokenAuthentication.getAdditionalParameters();
		//格式化输出token信息
		OAuth2AccessTokenResponse accessTokenResponse = OAuth2AuthorizationUtils.sendAccessTokenResponse(accessToken, refreshToken, additionalParameters);
		ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);

		// 无状态 注意删除 context 上下文的信息
		SecurityContextHolder.clearContext();
		this.accessTokenHttpResponseConverter.write(accessTokenResponse, null, httpResponse);
	}

}
