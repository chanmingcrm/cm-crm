package com.platform.mesh.bpm.biz.modules.inst.eventrel.controller;

import com.platform.mesh.bpm.biz.modules.inst.eventrel.service.IBpmInstEventRelService;
import com.platform.mesh.core.application.controller.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 约定当前controller 只引入当前service
 * @description 事件关联信息
 * @author 蝉鸣
 */
@Tag(description = "BpmInstEventCcController", name = "事件关联信息")
@RestController
public class BpmInstEventRelController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IBpmInstEventRelService bpmEventCcService;

  
}
