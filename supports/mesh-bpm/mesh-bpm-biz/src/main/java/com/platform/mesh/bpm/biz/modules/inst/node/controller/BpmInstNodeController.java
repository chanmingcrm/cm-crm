package com.platform.mesh.bpm.biz.modules.inst.node.controller;

import com.platform.mesh.bpm.biz.modules.inst.node.domain.dto.BpmInstNodeHandleDTO;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.po.BpmInstNode;
import com.platform.mesh.bpm.biz.modules.inst.node.domain.vo.BpmInstNodeVarVO;
import com.platform.mesh.bpm.biz.modules.inst.node.service.IBpmInstNodeService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 约定当前controller 只引入当前service
 * @description 流程节点信息
 * @author 蝉鸣
 */
@Tag(description = "BpmInstNodeController", name = "实例流程节点信息")
@RestController
public class BpmInstNodeController extends BaseController {

    /**
     * 服务对象
     */
    @Autowired
    private IBpmInstNodeService bpmInstNodeService;



    /**
     * 功能描述:
     * 〈获取实例下节点信息〉
     * @param instProcessId instProcessId
     * @return 正常返回:{@link Result<List<BpmInstNode>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取实例下节点信息")
    @GetMapping("/inst/node/get/all/{instProcessId}")
    public Result<List<BpmInstNode>> getAllInstNode(@PathVariable("instProcessId")Long instProcessId) {
        return Result.success(bpmInstNodeService.selectNodesByInstProcessId(instProcessId));
    }

    /**
     * 功能描述:
     * 〈获取当前节点信息〉
     * @param instProcessId instProcessId
     * @return 正常返回:{@link Result<List<BpmInstNode>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前节点信息")
    @GetMapping("/inst/node/get/current/{instProcessId}")
    public Result<List<BpmInstNode>> getCurrentInstNode(@PathVariable("instProcessId")Long instProcessId) {
        return Result.success(bpmInstNodeService.selectNodesByInstProcessId(instProcessId));
    }

    /**
     * 功能描述:
     * 〈获取下一个节点信息〉
     * @param instNodeId instNodeId
     * @return 正常返回:{@link Result<List<BpmInstNode>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取下一个节点信息")
    @GetMapping("/inst/node/get/next/{instNodeId}")
    public Result<List<BpmInstNode>> getNextInstNode(@PathVariable("instNodeId")Long instNodeId) {
        return Result.success(bpmInstNodeService.selectNodesByInstProcessId(instNodeId));
    }

    /**
     * 功能描述:
     * 〈获取当前节点需要的变量信息〉
     * @return 正常返回:{@link Result<BpmInstNode>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前节点需要的变量信息")
    @PostMapping("/inst/node/var/{instNodeId}")
    public Result<BpmInstNodeVarVO> getInstNodeVar(@PathVariable("instNodeId")Long instNodeId) {
        BpmInstNodeVarVO bpmInstNodeVarVO = bpmInstNodeService.getInstNodeVar(instNodeId);
        return Result.success(bpmInstNodeVarVO);
    }

    /**
     * 功能描述:
     * 〈执行节点实例〉
     * @return 正常返回:{@link Result<BpmInstNode>}
     * @author 蝉鸣
     */
    @Operation(summary = "执行节点实例")
    @PostMapping("/inst/node/handle")
    public Result<BpmInstNode> handleInstNode(@RequestBody BpmInstNodeHandleDTO handleDTO) {
        BpmInstNode bpmInstNode = bpmInstNodeService.handleInstNode(handleDTO);
        return Result.success(bpmInstNode);
    }

    /**
     * 功能描述:
     * 〈跳至目标节点〉
     * @return 正常返回:{@link Result<BpmInstNode>}
     * @author 蝉鸣
     */
    @Operation(summary = "执行节点实例")
    @PostMapping("/inst/node/goto/target/{instNodeId}")
    public Result<Boolean> gotoTargetNode(@PathVariable("instNodeId")Long instNodeId) {
        Boolean gotoed = bpmInstNodeService.gotoTargetNode(instNodeId);
        return Result.success(gotoed);
    }
  
}
