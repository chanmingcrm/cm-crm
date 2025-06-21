package com.platform.mesh.app.api.modules.init.es.controller;

import com.platform.mesh.app.api.modules.app.domain.dto.InitEsDTO;
import com.platform.mesh.app.api.modules.init.es.service.IAppInitEsService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.utils.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * 约定当前controller 只引入当前service
 * @description Es初始化
 * @author 蝉鸣
 */
public abstract class AppInitEsController extends BaseController{

    @Autowired
    private IAppInitEsService appInitEsService;

    /**
	 * 功能描述:
	 * 〈缓存初始化〉
	 * @param initEsDTO initEsDTO
	 * @return 正常返回:{@link Result<Boolean>}
	 * @author 蝉鸣
	 */
	public abstract Result<Boolean> initEs(@RequestBody InitEsDTO initEsDTO);

    /**
     * 功能描述:
     * 〈缓存清除〉
     * @param initEsDTO initEsDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    public abstract Result<Boolean> deleteES(@RequestBody InitEsDTO initEsDTO);

    /**
     * 功能描述:
     * 〈缓存刷新〉
     * @param initEsDTO initEsDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    public abstract Result<Boolean> refreshES(@RequestBody InitEsDTO initEsDTO);

}