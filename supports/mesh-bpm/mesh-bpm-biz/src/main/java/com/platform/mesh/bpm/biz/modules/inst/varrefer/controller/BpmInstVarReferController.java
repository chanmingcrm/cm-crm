package com.platform.mesh.bpm.biz.modules.inst.varrefer.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.bpm.biz.modules.inst.varrefer.service.IBpmInstVarReferService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 约定当前controller 只引入当前service
 * @description 变量参照值信息
 * @author 蝉鸣
 */
@Tag(description = "BpmInstVarReferController", name = "变量参照值信息")
@RestController
public class BpmInstVarReferController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private IBpmInstVarReferService flowVarReferService;

  
}
