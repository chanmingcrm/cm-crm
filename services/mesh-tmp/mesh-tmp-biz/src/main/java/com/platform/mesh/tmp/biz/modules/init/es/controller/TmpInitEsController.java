package com.platform.mesh.tmp.biz.modules.init.es.controller;

import com.platform.mesh.app.api.modules.app.domain.dto.InitEsDTO;
import com.platform.mesh.app.api.modules.init.es.controller.AppInitEsController;
import com.platform.mesh.tmp.biz.modules.init.es.service.ITmpInitEsService;
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
 * @description 任务计划Es初始化
 * @author 蝉鸣
 */
@Tag(description = "TmpInitEsController", name = "任务计划Es初始化")
@RestController
@RequestMapping
public class TmpInitEsController extends AppInitEsController {

    @Autowired
    private ITmpInitEsService tmpInitEsService;

    /**
	 * 功能描述:
	 * 〈任务计划缓存初始化〉
	 * @param initEsDTO initEsDTO
	 * @return 正常返回:{@link Result<Boolean>}
	 * @author 蝉鸣
	 */
    @Override
	@Operation(summary = "任务计划缓存初始化")
	@PostMapping("/tmp/es/init")
	public Result<Boolean> initEs(@RequestBody InitEsDTO initEsDTO) {
        return Result.success(tmpInitEsService.initEs(Boolean.FALSE,initEsDTO));
	}

    /**
     * 功能描述:
     * 〈任务计划缓存清除〉
     * @param initEsDTO initEsDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Override
    @Operation(summary = "任务计划缓存清除")
    @PostMapping("/tmp/es/delete")
    public Result<Boolean> deleteES(@RequestBody InitEsDTO initEsDTO) {
        return Result.success(tmpInitEsService.deleteES(initEsDTO));
    }

    /**
     * 功能描述:
     * 〈任务计划缓存刷新〉
     * @param initEsDTO initEsDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Override
    @Operation(summary = "任务计划缓存刷新")
    @PostMapping("/tmp/es/refresh")
    public Result<Boolean> refreshES(@RequestBody InitEsDTO initEsDTO) {
        return Result.success(tmpInitEsService.refreshES(initEsDTO));
    }

}