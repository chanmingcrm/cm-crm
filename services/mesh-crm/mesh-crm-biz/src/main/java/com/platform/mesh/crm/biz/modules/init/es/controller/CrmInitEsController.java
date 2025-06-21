package com.platform.mesh.crm.biz.modules.init.es.controller;

import com.platform.mesh.app.api.modules.app.domain.dto.InitEsDTO;
import com.platform.mesh.app.api.modules.init.es.controller.AppInitEsController;
import com.platform.mesh.crm.biz.modules.init.es.service.ICrmInitEsService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * 约定当前controller 只引入当前service
 * @description 客户关系Es初始化
 * @author 蝉鸣
 */
@Tag(description = "CrmAllGroupController", name = "客户关系Es初始化")
@RestController
@RequestMapping
public class CrmInitEsController  extends AppInitEsController {

    @Autowired
    private ICrmInitEsService crmInitEsService;

    /**
	 * 功能描述:
	 * 〈客户关系缓存初始化〉
	 * @param initEsDTO initEsDTO
	 * @return 正常返回:{@link Result<Boolean>}
	 * @author 蝉鸣
	 */
    @Override
	@Operation(summary = "客户关系缓存初始化")
	@PostMapping("/crm/es/init")
	public Result<Boolean> initEs(@RequestBody InitEsDTO initEsDTO) {
        return Result.success(crmInitEsService.initEs(Boolean.FALSE,initEsDTO));
	}

    /**
     * 功能描述:
     * 〈客户关系缓存清除〉
     * @param initEsDTO initEsDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Override
    @Operation(summary = "客户关系缓存清除")
    @PostMapping("/crm/es/delete")
    public Result<Boolean> deleteES(@RequestBody InitEsDTO initEsDTO) {
        return Result.success(crmInitEsService.deleteES(initEsDTO));
    }

    /**
     * 功能描述:
     * 〈客户关系缓存刷新〉
     * @param initEsDTO initEsDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Override
    @Operation(summary = "客户关系缓存刷新")
    @PostMapping("/crm/es/refresh")
    public Result<Boolean> refreshES(@RequestBody InitEsDTO initEsDTO) {
        return Result.success(crmInitEsService.refreshES(initEsDTO));
    }

}