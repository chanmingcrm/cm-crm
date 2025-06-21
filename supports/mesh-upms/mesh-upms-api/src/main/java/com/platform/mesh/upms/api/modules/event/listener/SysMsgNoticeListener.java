package com.platform.mesh.upms.api.modules.event.listener;

import com.platform.mesh.core.application.domain.bo.MsgNoticeBO;
import com.platform.mesh.upms.api.modules.event.SysMsgNoticeEvent;
import com.platform.mesh.upms.api.modules.msg.feign.RemoteMsgService;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @description 异步监听系统消息提醒事件
 * @author 蝉鸣
 */
@Component
public class SysMsgNoticeListener {

	private final RemoteMsgService remoteMsgService;

	public SysMsgNoticeListener(RemoteMsgService remoteMsgService) {
		this.remoteMsgService = remoteMsgService;
	}

	/**
	 * 功能描述:
	 * 〈响应日志事件〉
	 * @param event event
	 * @author 蝉鸣
	 */
	@Async
	@Order
	@EventListener(SysMsgNoticeEvent.class)
	public void addMsgNotice(SysMsgNoticeEvent event) {
		MsgNoticeBO msgNoticeBO = (MsgNoticeBO)event.getSource();
		remoteMsgService.addMsgNotice(msgNoticeBO);
	}

}