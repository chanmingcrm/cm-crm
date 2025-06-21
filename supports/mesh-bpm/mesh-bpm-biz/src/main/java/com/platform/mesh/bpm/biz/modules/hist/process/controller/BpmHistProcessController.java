package com.platform.mesh.bpm.biz.modules.hist.process.controller;

import com.platform.mesh.bpm.biz.modules.hist.process.domain.vo.BpmHistProcessInfoVO;
import com.platform.mesh.bpm.biz.modules.hist.process.service.IBpmHistProcessService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 约定当前controller 只引入当前service
 * @description 流程过程信息
 * @author 蝉鸣
 */
@Tag(description = "BpmHistProcessController", name = "实例流程过程历史信息")
@RestController
public class BpmHistProcessController extends BaseController {

    /**
     * 服务对象
     */
    @Autowired
    private IBpmHistProcessService bpmHistProcessService;

    /**
     * 功能描述:
     * 〈获取流程实例历史信息〉
     * @param instProcessId instProcessId
     * @return 正常返回:{@link Result<BpmHistProcessInfoVO>}
     */
    @Operation(summary = "获取流程实例历史信息")
    @GetMapping("/hist/process/get/info/{instProcessId}")
    public Result<BpmHistProcessInfoVO> getProcessHistInfo(@PathVariable("instProcessId")Long instProcessId) {
        return Result.success(bpmHistProcessService.getProcessHistInfo(instProcessId));
    }
}
