package com.platform.mesh.uaa.biz.modules.authorization.listener;

import com.platform.mesh.security.event.UaaOauthEvent;
import com.platform.mesh.uaa.biz.modules.authorization.domain.po.Oauth2Authorization;
import com.platform.mesh.uaa.biz.modules.authorization.service.IOauth2AuthorizationService;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.stereotype.Component;

/**
 * @description 异步监听系统登录日志事件
 * @author 蝉鸣
 */
@Component
public class UaaOauthAuthorizationListener {

	private final IOauth2AuthorizationService oauth2AuthorizationService;

	public UaaOauthAuthorizationListener(IOauth2AuthorizationService oauth2AuthorizationService) {
		this.oauth2AuthorizationService = oauth2AuthorizationService;
	}

	/**
	 * 功能描述:
	 * 〈响应日志事件〉
	 * @param event event
	 * @author 蝉鸣
	 */
	@Async
	@Order
	@EventListener(UaaOauthEvent.class)
	public void addAuthorization(UaaOauthEvent event) {
		//获取源信息
		OAuth2Authorization oAuth2Authorization = (OAuth2Authorization)event.getSource();
		//转换PO对象
		Oauth2Authorization oauth2Authorization = oauth2AuthorizationService.getServiceManual().authorizationToPO(oAuth2Authorization);
		//保存授权信息
		oauth2AuthorizationService.save(oauth2Authorization);
	}

}