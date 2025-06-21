package com.platform.mesh.log.event;

import com.platform.mesh.upms.api.modules.sys.log.domain.bo.LogModifyEventBO;
import org.springframework.context.ApplicationEvent;

/**
 * @description 系统登录日志事件
 * @author 蝉鸣
 */
public class SysModifyLogEvent extends ApplicationEvent {

	public SysModifyLogEvent(LogModifyEventBO source) {
		super(source);
	}

}