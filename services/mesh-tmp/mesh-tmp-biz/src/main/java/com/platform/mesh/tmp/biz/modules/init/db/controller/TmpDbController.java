package com.platform.mesh.tmp.biz.modules.init.db.controller;

import com.platform.mesh.app.api.modules.init.db.controller.AppDbController;
import com.platform.mesh.app.api.modules.init.db.domain.dto.DbTransDTO;
import com.platform.mesh.tmp.biz.modules.init.db.service.ITmpDbService;
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
 * @description 任务计划数据操作
 * @author 蝉鸣
 */
@Tag(description = "TmpDbController", name = "任务计划数据操作")
@RestController
@RequestMapping
public class TmpDbController extends AppDbController {

    @Autowired
    private ITmpDbService tmpDbService;

    /**
	 * 功能描述:
	 * 〈获取任务计划数据转化〉
	 * @param transDTO transDTO
	 * @return 正常返回:{@link Result<Boolean>}
	 * @author 蝉鸣
	 */
	@Override
	@Operation(summary = "获取任务计划数据转化")
	@PostMapping("/tmp/db/trans/data")
	public Result<Boolean> dbTransData(@RequestBody DbTransDTO transDTO) {
        return Result.success(tmpDbService.transDbData(transDTO));
	}

}