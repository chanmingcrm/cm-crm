package com.platform.mesh.upms.biz.captcha.event;

import org.springframework.context.ApplicationEvent;

/**
 * @description 系统登录日志事件
 * @author 蝉鸣
 */
public class CaptchaEvent extends ApplicationEvent {

	public CaptchaEvent(String fileFlag) {
		super(fileFlag);
	}

}