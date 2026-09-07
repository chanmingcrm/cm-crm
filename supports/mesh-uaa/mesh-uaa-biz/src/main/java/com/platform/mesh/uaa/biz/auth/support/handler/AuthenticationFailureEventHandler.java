package com.platform.mesh.uaa.biz.auth.support.handler;

import cn.hutool.core.util.StrUtil;
import com.platform.mesh.log.enums.LoginFlagEnum;
import com.platform.mesh.log.event.SysLoginLogEvent;
import com.platform.mesh.log.utils.SysLogUtils;
import com.platform.mesh.security.constants.GrantTypeConstant;
import com.platform.mesh.upms.api.modules.sys.log.domain.bo.LogLoginBO;
import com.platform.mesh.utils.result.Result;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

/**
 * @description 处理登录以及续签失败
 * @author 蝉鸣
 */
public class AuthenticationFailureEventHandler implements AuthenticationFailureHandler {

	private static final Logger log = LoggerFactory.getLogger(AuthenticationFailureEventHandler.class);

	private final JacksonJsonHttpMessageConverter errorHttpResponseConverter = new JacksonJsonHttpMessageConverter();

	/**
	 * 功能描述:
	 * 〈授权失败处理〉
	 * @param request request
	 * @param response response
	 * @param exception exception
	 * @author 蝉鸣
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) {
		String username = request.getParameter(GrantTypeConstant.USERNAME);

		log.error("用户：{} 登录失败，异常：", username, exception);
		if (StrUtil.isNotEmpty(username)) {
			LogLoginBO logLoginBO = SysLogUtils.getSysLoginLog();
			logLoginBO.setLoginUserName(username);
			logLoginBO.setLoginFlag(LoginFlagEnum.ERROR.getValue());
			logLoginBO.setRemark(exception.getLocalizedMessage());
			// 发送异步日志事件
			SpringContextHolderUtil.publishEvent(new SysLoginLogEvent(logLoginBO));
		}
		// 写出错误信息
		try {
			sendErrorResponse(request, response, exception);
		}
		catch (IOException e) {
			log.error("返回错误信息失败", e);
		}
	}

	/**
	 * 功能描述:
	 * 〈发送异常信息〉
	 * @param request request
	 * @param response response
	 * @param exception exception
	 * @author 蝉鸣
	 */
	private void sendErrorResponse(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException {
		OAuth2Error error = ((OAuth2AuthenticationException) exception).getError();
		ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
		httpResponse.setStatusCode(HttpStatus.OK);
		this.errorHttpResponseConverter.write(Result.error(HttpStatus.BAD_REQUEST.value(), error.getDescription()),
				MediaType.APPLICATION_JSON, httpResponse);
	}

}
