package com.platform.mesh.upms.api.modules.event;

import com.platform.mesh.upms.api.modules.sys.log.domain.bo.LogModifyBO;
import org.springframework.context.ApplicationEvent;

/**
 * @description 系统登录日志事件
 * @author 蝉鸣
 */
public class SysModifyLogEvent extends ApplicationEvent {

	public SysModifyLogEvent(LogModifyBO source) {
		super(source);
	}

}