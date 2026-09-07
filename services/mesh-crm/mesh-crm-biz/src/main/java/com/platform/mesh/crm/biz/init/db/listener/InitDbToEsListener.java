package com.platform.mesh.crm.biz.init.db.listener;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.crm.biz.init.db.event.InitDbToEsEvent;
import com.platform.mesh.crm.biz.init.db.service.ICrmDbService;
import com.platform.mesh.crm.biz.init.es.service.ICrmInitEsService;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description 异步监听系统登录日志事件
 * @author 蝉鸣
 */
@Component
public class InitDbToEsListener {

	private final ICrmInitEsService crmInitEsService;

	private final ICrmDbService dbService;


	public InitDbToEsListener(ICrmInitEsService crmInitEsService
							  , ICrmDbService dbService
	) {
		this.crmInitEsService = crmInitEsService;
		this.dbService = dbService;
	}

	/**
	 * 功能描述:
	 * 〈响应日志事件〉
	 * @param event event
	 * @author 蝉鸣
	 */
	@Async
	@Order
	@EventListener(InitDbToEsEvent.class)
	public void initDbToEs(InitDbToEsEvent event) {
		//获取需要初始化的表
		List<String> appTables = dbService.initEsDbTables();
		if(CollUtil.isEmpty(appTables)) {
			return;
		}
		crmInitEsService.initEs(Boolean.TRUE,appTables);
	}

}