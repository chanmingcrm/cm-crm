package com.platform.mesh.bpm.biz.modules.inst.process.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.bpm.api.modules.inst.domain.dto.BpmProcessStartDTO;
import com.platform.mesh.bpm.biz.modules.inst.nodesub.domain.dto.BpmInstNodeSubDTO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.dto.BpmInstProcessPageDTO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.po.BpmInstProcess;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessDesignVO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessOaVO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessRunVO;
import com.platform.mesh.bpm.biz.modules.inst.process.domain.vo.BpmInstProcessVO;
import com.platform.mesh.bpm.biz.modules.inst.process.service.IBpmInstProcessService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.security.utils.SecurityUtils;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 约定当前controller 只引入当前service
 * @description 流程过程信息
 * @author 蝉鸣
 */
@Tag(description = "BpmInstProcessController", name = "实例流程过程信息")
@RestController
public class BpmInstProcessController extends BaseController {

    /**
     * 服务对象
     */
    @Autowired
    private IBpmInstProcessService bpmInstProcessService;


    /**
     * 功能描述:
     * 〈启动流程实例〉
     * @param startDTO startDTO
     * @return 正常返回:{@link Result<BpmInstProcessVO>}
     */
    @Operation(summary = "启动流程实例")
    @PostMapping("/inst/process/start")
    public Result<BpmInstProcessVO> startInstProcessById(@RequestBody BpmProcessStartDTO startDTO) {
        BpmInstProcess bpmInstProcess = bpmInstProcessService.startProcessInst(startDTO);
        return Result.success(BeanUtil.copyProperties(bpmInstProcess, BpmInstProcessVO.class));
    }

    /**
     * 功能描述:
     * 〈获取流程实例〉
     * @param instProcessId tempProcessId
     * @return 正常返回:{@link Result<BpmInstProcessDesignVO>}
     */
    @Operation(summary = "获取流程实例")
    @GetMapping("/inst/process/get/design/{instProcessId}")
    public Result<BpmInstProcessDesignVO> getProcessInstDesignInfo(@PathVariable("instProcessId")Long instProcessId) {
        return Result.success(bpmInstProcessService.getProcessInst(instProcessId));
    }

    /**
     * 功能描述:
     * 〈获取流程实例〉
     * @param instProcessId tempProcessId
     * @return 正常返回:{@link Result<BpmInstProcessVO>}
     */
    @Operation(summary = "获取流程实例运行中信息")
    @GetMapping("/inst/process/get/run/{instProcessId}")
    public Result<BpmInstProcessRunVO> getProcessInstRunInfo(@PathVariable("instProcessId")Long instProcessId) {
        return Result.success(bpmInstProcessService.getProcessInstRunInfo(instProcessId));
    }

    /**
     * 功能描述:
     * 〈停止流程实例〉
     * @param instProcessId instProcessId
     * @return 正常返回:{@link Result<BpmInstProcessVO>}
     */
    @Operation(summary = "停止流程实例")
    @GetMapping("/inst/process/stop/{instProcessId}")
    public Result<BpmInstProcessVO> stopProcessInfoById(@PathVariable("instProcessId")Long instProcessId) {
        BpmInstProcess bpmInstProcess = bpmInstProcessService.stopProcessInst(instProcessId);
        return Result.success(BeanUtil.copyProperties(bpmInstProcess, BpmInstProcessVO.class));
    }

    /**
     * 功能描述:
     * 〈作废流程实例〉
     * @param instProcessId tempProcessId
     * @return 正常返回:{@link Result<BpmInstProcessVO>}
     */
    @Operation(summary = "作废流程实例")
    @GetMapping("/inst/process/cancel/{instProcessId}")
    public Result<BpmInstProcessVO> cancelProcessInfoById(@PathVariable("instProcessId")Long instProcessId) {
        BpmInstProcess bpmInstProcess = bpmInstProcessService.cancelProcessInst(instProcessId);
        return Result.success(BeanUtil.copyProperties(bpmInstProcess, BpmInstProcessVO.class));
    }

    /**
     * 功能描述:
     * 〈新建流程子项〉
     * @param instNodeSubDTO instNodeSubDTO
     * @return 正常返回:{@link Result<BpmInstProcess>}
     */
    @Operation(summary = "新建流程子项")
    @GetMapping("/inst/process/add/sub")
    public Result<BpmInstProcess> addSubProcessInst(@RequestBody BpmInstNodeSubDTO instNodeSubDTO) {
        BpmInstProcess bpmInstProcess = bpmInstProcessService.addSubProcessInst(instNodeSubDTO);
        return Result.success(bpmInstProcess);
    }

    /**
     * 功能描述:
     * 〈获取流程实例-待办〉
     * @return 正常返回:{@link Result<PageVO<BpmInstProcessVO>>}
     */
    @Operation(summary = "获取流程实例-待办")
    @PostMapping("/inst/process/get/todo")
    public Result<PageVO<BpmInstProcessOaVO>> getProcessInstTodo(@RequestBody BpmInstProcessPageDTO pageDTO) {
        Long accountId = SecurityUtils.getLoginUser().getAccountId();
        return Result.success(bpmInstProcessService.getProcessInstTodo(pageDTO,accountId));
    }

    /**
     * 功能描述:
     * 〈获取流程实例-已办〉
     * @return 正常返回:{@link Result<PageVO<BpmInstProcessVO>>}
     */
    @Operation(summary = "获取流程实例-已办")
    @PostMapping("/inst/process/get/done")
    public Result<PageVO<BpmInstProcessOaVO>> getProcessInstDone(@RequestBody BpmInstProcessPageDTO pageDTO) {
        Long accountId = SecurityUtils.getLoginUser().getAccountId();
        return Result.success(bpmInstProcessService.getProcessInstDone(pageDTO,accountId));
    }

    /**
     * 功能描述:
     * 〈获取流程实例-归档〉
     * @return 正常返回:{@link Result<PageVO<BpmInstProcessVO>>}
     */
    @Operation(summary = "获取流程实例-归档")
    @PostMapping("/inst/process/get/end")
    public Result<PageVO<BpmInstProcessOaVO>> getProcessInstEnd(@RequestBody BpmInstProcessPageDTO pageDTO) {
        Long accountId = SecurityUtils.getLoginUser().getAccountId();
        return Result.success(bpmInstProcessService.getProcessInstEnd(pageDTO,accountId));
    }

    /**
     * 功能描述:
     * 〈获取流程实例-跟进〉
     * @return 正常返回:{@link Result<PageVO<BpmInstProcessVO>>}
     */
    @Operation(summary = "获取流程实例-跟进")
    @PostMapping("/inst/process/get/follow")
    public Result<PageVO<BpmInstProcessOaVO>> getProcessInstFollow(@RequestBody BpmInstProcessPageDTO pageDTO) {
        Long accountId = SecurityUtils.getLoginUser().getAccountId();
        return Result.success(bpmInstProcessService.getProcessInstFollow(pageDTO,accountId));
    }
  
}
