package com.platform.mesh.bpm.biz.modules.temp.node.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.bpm.biz.modules.temp.node.domain.po.BpmTempNode;
import com.platform.mesh.bpm.biz.modules.temp.node.service.IBpmTempNodeService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 约定当前controller 只引入当前service
 * @description 流程节点信息
 * @author 蝉鸣
 */
@Tag(description = "BpmTempNodeController", name = "模板流程节点信息")
@RestController
public class BpmTempNodeController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IBpmTempNodeService bpmTempNodeService;


    /**
     * 功能描述:
     * 〈获取模板下节点信息〉
     * @param tempProcessId tempProcessId
     * @return 正常返回:{@link Result<List<BpmTempNode>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取模板下节点信息")
    @GetMapping("/temp/node/get{tempProcessId}")
    public Result<List<BpmTempNode>> getNodeTemp(@PathVariable("tempProcessId")Long tempProcessId) {
        return Result.success(bpmTempNodeService.selectNodesByTemplateId(tempProcessId));
    }
  
}
