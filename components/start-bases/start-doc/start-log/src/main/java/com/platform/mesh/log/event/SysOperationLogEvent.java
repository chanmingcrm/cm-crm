package com.platform.mesh.log.event;

import com.platform.mesh.upms.api.modules.sys.log.domain.bo.LogOperateBO;
import org.springframework.context.ApplicationEvent;

/**
 * @description 系统操作日志事件
 * @author 蝉鸣
 */
public class SysOperationLogEvent extends ApplicationEvent {

	public SysOperationLogEvent(LogOperateBO source) {
		super(source);
	}

}