package com.platform.mesh.log.event.listener;

import com.platform.mesh.log.event.SysLoginLogEvent;
import com.platform.mesh.log.event.SysModifyLogEvent;
import com.platform.mesh.upms.api.modules.sys.log.domain.bo.LogModifyEventBO;
import com.platform.mesh.upms.api.modules.sys.log.feign.RemoteLogService;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 * @description 异步监听系统登录日志事件
 * @author 蝉鸣
 */
public class SysModifyLogListener {

	private final RemoteLogService remoteLogService;

	public SysModifyLogListener(RemoteLogService remoteLogService) {
		this.remoteLogService = remoteLogService;
	}

	/**
	 * 功能描述:
	 * 〈响应日志事件〉
	 * @param event event
	 * @author 蝉鸣
	 */
	@Async
	@Order
	@EventListener(SysModifyLogEvent.class)
	public void saveSysLoginLog(SysLoginLogEvent event) {
		LogModifyEventBO modifyEventBO = (LogModifyEventBO)event.getSource();
		remoteLogService.saveModifyLog(modifyEventBO);
	}

}