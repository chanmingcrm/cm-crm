package com.platform.mesh.upms.biz.third.controller;

import com.platform.mesh.upms.biz.third.service.IThirdService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 约定当前controller 只引入当前service
 * @description 第三方服务对接
 * @author 蝉鸣
 */
@Tag(description = "ThirdController", name = "第三方服务对接")
@RestController
public class ThirdController {

	@Autowired
	private IThirdService thirdService;

	/**
	 * 功能描述:
	 * 〈第三方服务对接〉
	 * @return 正常返回:{@link Result<Boolean>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "第三方服务对接")
	@PostMapping("/ws/third/test")
	public Result<Boolean> thirdTest(@RequestParam Integer levelSource) {
        thirdService.thirdTest(levelSource);
		return Result.success(Boolean.TRUE);
	}
}
