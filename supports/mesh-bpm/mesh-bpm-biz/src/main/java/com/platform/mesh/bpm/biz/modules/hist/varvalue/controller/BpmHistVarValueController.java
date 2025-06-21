package com.platform.mesh.bpm.biz.modules.hist.varvalue.controller;

import com.platform.mesh.bpm.biz.modules.hist.varvalue.service.IBpmHistVarValueService;
import com.platform.mesh.core.application.controller.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 约定当前controller 只引入当前service
 * @description 变量值信息
 * @author 蝉鸣
 */
@Tag(description = "BpmHistVarValueController", name = "变量值信息")
@RestController
public class BpmHistVarValueController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IBpmHistVarValueService bpmVarValueService;

  
}
