package com.platform.mesh.tmp.biz.modules.init.db.listener;

import cn.hutool.core.collection.CollUtil;
import com.platform.mesh.app.api.modules.app.domain.bo.AppModuleBaseBO;
import com.platform.mesh.app.api.modules.app.domain.dto.InitEsDTO;
import com.platform.mesh.app.api.modules.app.feign.RemoteAppService;
import com.platform.mesh.app.api.modules.init.db.service.IDbService;
import com.platform.mesh.tmp.biz.modules.init.db.event.InitDbToEsEvent;
import com.platform.mesh.tmp.biz.modules.init.es.service.ITmpInitEsService;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description 异步监听初始化事件
 * @author 蝉鸣
 */
@Component
public class InitDbToEsListener {

	private final ITmpInitEsService tmpInitEsService;
	private final RemoteAppService remoteAppService;
	private final IDbService dbService;


	public InitDbToEsListener(RemoteAppService remoteAppService
							  , ITmpInitEsService tmpInitEsService
							  , IDbService dbService
	) {
		this.remoteAppService = remoteAppService;
		this.tmpInitEsService = tmpInitEsService;
		this.dbService = dbService;
	}

	/**
	 * 功能描述:
	 * 〈初始化事件〉
	 * @param event event
	 * @author 蝉鸣
	 */
	@Async
	@Order
	@EventListener(InitDbToEsEvent.class)
	public void initDbToEs(InitDbToEsEvent event) {
		InitEsDTO initEsDTO = new InitEsDTO();
		//获取需要初始化的表
		List<String> appTables = dbService.initEsDbTables();
		if(CollUtil.isEmpty(appTables)) {
			return;
		}
		//获取模块ID
		List<AppModuleBaseBO> moduleBaseBOS = remoteAppService.getModuleBaseInfoBySchema(appTables).getData();
		if(CollUtil.isEmpty(moduleBaseBOS)) {
			return;
		}
		List<Long> moduleIds = moduleBaseBOS.stream().map(AppModuleBaseBO::getId).distinct().toList();
		//设置当前模块顶层ID
		initEsDTO.setModuleIds(CollUtil.newArrayList(moduleIds));
		tmpInitEsService.initEs(Boolean.TRUE,initEsDTO);
	}

}