package com.platform.mesh.bpm.biz.modules.inst.varvalue.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.bpm.biz.modules.inst.varvalue.service.IBpmInstVarValueService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 约定当前controller 只引入当前service
 * @description 变量值信息
 * @author 蝉鸣
 */
@Tag(description = "FlowInstVarValueController", name = "变量值信息")
@RestController
public class BpmInstVarValueController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IBpmInstVarValueService flowVarValueService;

  
}
