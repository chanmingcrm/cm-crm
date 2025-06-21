package com.platform.mesh.log.event;

import com.platform.mesh.upms.api.modules.sys.log.domain.bo.LogLoginBO;
import org.springframework.context.ApplicationEvent;

/**
 * @description 系统登录日志事件
 * @author 蝉鸣
 */
public class SysLoginLogEvent extends ApplicationEvent {

	public SysLoginLogEvent(LogLoginBO source) {
		super(source);
	}

}