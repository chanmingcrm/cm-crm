package com.platform.mesh.upms.api.modules.event.listener;

import com.platform.mesh.upms.api.modules.event.SysModifyLogEvent;
import com.platform.mesh.upms.api.modules.sys.log.domain.bo.LogModifyBO;
import com.platform.mesh.upms.api.modules.sys.log.feign.RemoteLogService;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @description 异步监听系统登录日志事件
 * @author 蝉鸣
 */
@Component
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
	public void saveSysModifyLog(SysModifyLogEvent event) {
		LogModifyBO modifyBO = (LogModifyBO)event.getSource();
		remoteLogService.saveModifyLog(modifyBO);
	}

}