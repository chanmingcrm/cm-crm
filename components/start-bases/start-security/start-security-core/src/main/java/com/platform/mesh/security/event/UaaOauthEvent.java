package com.platform.mesh.security.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;

/**
 * @description 系统登录日志事件
 * @author 蝉鸣
 */
public class UaaOauthEvent extends ApplicationEvent {

	public UaaOauthEvent(OAuth2Authorization source) {
		super(source);
	}

}