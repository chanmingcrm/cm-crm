package com.platform.mesh.bpm.biz.modules.inst.action.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.bpm.biz.modules.inst.action.service.IBpmInstActionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 约定当前controller 只引入当前service
 * @description 动作信息
 * @author 蝉鸣
 */
@Tag(description = "BpmInstActionController", name = "动作信息")
@RestController
public class BpmInstActionController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IBpmInstActionService bpmActionService;

  
}
