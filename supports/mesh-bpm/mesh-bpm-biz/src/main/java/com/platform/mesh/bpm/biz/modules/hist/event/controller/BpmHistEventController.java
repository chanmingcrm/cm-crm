package com.platform.mesh.bpm.biz.modules.hist.event.controller;

import com.platform.mesh.bpm.biz.modules.hist.event.service.IBpmHistEventService;
import com.platform.mesh.core.application.controller.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 约定当前controller 只引入当前service
 * @description 动作信息
 * @author 蝉鸣
 */
@Tag(description = "BpmInstEventController", name = "历史事件信息")
@RestController
public class BpmHistEventController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IBpmHistEventService bpmEventService;

  
}
