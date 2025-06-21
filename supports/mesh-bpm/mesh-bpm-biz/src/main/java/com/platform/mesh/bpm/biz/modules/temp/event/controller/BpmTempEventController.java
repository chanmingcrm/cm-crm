package com.platform.mesh.bpm.biz.modules.temp.event.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.bpm.biz.modules.temp.event.service.IBpmTempEventService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 约定当前controller 只引入当前service
 * @description 事件信息
 * @author 蝉鸣
 */
@Tag(description = "FlowTempEventController", name = "事件信息")
@RestController
public class BpmTempEventController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IBpmTempEventService flowEventService;

  
}
