package com.platform.mesh.log.event.listener;

import com.platform.mesh.log.event.SysLoginLogEvent;
import com.platform.mesh.upms.api.modules.sys.log.domain.bo.LogLoginBO;
import com.platform.mesh.upms.api.modules.sys.log.feign.RemoteLogService;
import com.platform.mesh.utils.format.DateTimeUtil;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import java.sql.Timestamp;

/**
 * @description 异步监听系统登录日志事件
 * @author 蝉鸣
 */
public class SysLoginLogListener {

	private final RemoteLogService remoteLogService;

	public SysLoginLogListener(RemoteLogService remoteLogService) {
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
	@EventListener(SysLoginLogEvent.class)
	public void saveSysLoginLog(SysLoginLogEvent event) {
		LogLoginBO logLoginBO = (LogLoginBO)event.getSource();
		Timestamp timestamp = DateTimeUtil.longToSqlTime(event.getTimestamp());
		logLoginBO.setCreateTime(timestamp.toLocalDateTime());
		remoteLogService.saveLoginLog(logLoginBO);
	}

}