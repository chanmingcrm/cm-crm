package com.platform.mesh.bpm.biz.modules.temp.line.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.bpm.biz.modules.inst.line.service.IBpmInstLineService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 约定当前controller 只引入当前service
 * @description 流程线信息
 * @author 蝉鸣
 */
@Tag(description = "FlowTempLineController", name = "流程线信息")
@RestController
public class BpmTempLineController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IBpmInstLineService flowLineService;

  
}
