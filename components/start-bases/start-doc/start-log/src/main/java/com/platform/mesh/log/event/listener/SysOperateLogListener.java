package com.platform.mesh.log.event.listener;

import com.platform.mesh.log.event.SysOperationLogEvent;
import com.platform.mesh.upms.api.modules.sys.log.domain.bo.LogOperateBO;
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
public class SysOperateLogListener {

	private final RemoteLogService remoteLogService;

	public SysOperateLogListener(RemoteLogService remoteLogService) {
		this.remoteLogService = remoteLogService;
	}

	/**
	 * 功能描述:
	 * 〈响应操作日志事件〉
	 * @param event event
	 * @author 蝉鸣
	 */
	@Async
	@Order
	@EventListener(SysOperationLogEvent.class)
	public void saveSysOperateLog(SysOperationLogEvent event) {
		LogOperateBO logOperateBO = (LogOperateBO)event.getSource();
		Timestamp timestamp = DateTimeUtil.longToSqlTime(event.getTimestamp());
		logOperateBO.setCreateTime(timestamp.toLocalDateTime());
		remoteLogService.saveOperationLog(logOperateBO);
	}

}