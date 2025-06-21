package com.platform.mesh.crm.biz.modules.init.db.event;

import org.springframework.context.ApplicationEvent;

/**
 * @description 初始化数据到ES缓存事件
 * @author 蝉鸣
 */
public class InitDbToEsEvent extends ApplicationEvent {

	public InitDbToEsEvent(String initFlag) {
		super(initFlag);
	}

}