package com.platform.mesh.bpm.biz.modules.temp.variable.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.bpm.biz.modules.temp.variable.service.IBpmTempVariableService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 约定当前controller 只引入当前service
 * @description 变量信息
 * @author 蝉鸣
 */
@Tag(description = "BpmTempVariableController", name = "变量信息")
@RestController
public class BpmTempVariableController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IBpmTempVariableService flowVariableService;

  
}
