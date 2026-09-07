package com.platform.mesh.uaa.biz.auth.support.handler;

import com.platform.mesh.log.enums.LoginFlagEnum;
import com.platform.mesh.log.event.SysLoginLogEvent;
import com.platform.mesh.log.utils.SysLogUtils;
import com.platform.mesh.upms.api.modules.sys.log.domain.bo.LogLoginBO;
import com.platform.mesh.utils.spring.SpringContextHolderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * @description 处理退出登录成功事件
 * @author 蝉鸣
 */
@Component
public class LogoutSuccessEventHandler implements ApplicationListener<LogoutSuccessEvent> {

	private static final Logger log = LoggerFactory.getLogger(LogoutSuccessEventHandler.class);

	/**
	 * 功能描述:
	 * 〈登出事件〉
	 * @param event event
	 * @author 蝉鸣
	 */
	@Override
	public void onApplicationEvent(LogoutSuccessEvent event) {
		Authentication authentication = (Authentication)event.getSource();
		if (authentication instanceof PreAuthenticatedAuthenticationToken) {
			handle(authentication);
		}
	}

	/**
	 * 功能描述:
	 * 〈处理退出成功方法〉
	 * 获取到登录的authentication 对象
	 * @param authentication 登录对象
	 * @author 蝉鸣
	 */
	public void handle(Authentication authentication) {
		String username = authentication.getName();
		LogLoginBO logLoginBO = SysLogUtils.getSysLoginLog();
		logLoginBO.setLoginFlag(LoginFlagEnum.LOGOUT.getValue());
		logLoginBO.setRemark(LoginFlagEnum.LOGOUT.getDesc());
		logLoginBO.setLoginUserName(username);
		// 发送异步日志事件
		SpringContextHolderUtil.publishEvent(new SysLoginLogEvent(logLoginBO));
	}

}
