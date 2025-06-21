package com.platform.mesh.upms.api.modules.event;

import com.platform.mesh.core.application.domain.bo.MsgNoticeBO;
import org.springframework.context.ApplicationEvent;

/**
 * @description 系统消息提醒事件
 * @author 蝉鸣
 */
public class SysMsgNoticeEvent extends ApplicationEvent {

	public SysMsgNoticeEvent(MsgNoticeBO source) {
		super(source);
	}

}