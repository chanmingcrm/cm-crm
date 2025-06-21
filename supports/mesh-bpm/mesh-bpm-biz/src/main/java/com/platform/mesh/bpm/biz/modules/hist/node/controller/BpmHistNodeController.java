package com.platform.mesh.bpm.biz.modules.hist.node.controller;

import com.platform.mesh.bpm.biz.modules.hist.node.service.IBpmHistNodeService;
import com.platform.mesh.core.application.controller.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 约定当前controller 只引入当前service
 * @description 流程节点信息
 * @author 蝉鸣
 */
@Tag(description = "BpmHistNodeController", name = "流程节点信息")
@RestController
public class BpmHistNodeController extends BaseController {

    /**
     * 服务对象
     */
    @Autowired
    private IBpmHistNodeService bpmHistNodeService;

  
}
