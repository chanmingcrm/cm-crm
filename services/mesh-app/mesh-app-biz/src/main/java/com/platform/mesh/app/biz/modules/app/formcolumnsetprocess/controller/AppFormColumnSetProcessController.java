package com.platform.mesh.app.biz.modules.app.formcolumnsetprocess.controller;

import com.platform.mesh.app.biz.modules.app.formcolumnsetprocess.service.IAppFormColumnSetProcessService;
import com.platform.mesh.core.application.controller.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * 约定当前controller 只引入当前service
 * @description 字段流程信息
 * @author 蝉鸣
 */
@Tag(description = "AppFormColumnSetProcessController", name = "字段流程")
@RestController
@RequestMapping
public class AppFormColumnSetProcessController extends BaseController{
    @Autowired
    private IAppFormColumnSetProcessService appFormColumnSetProcessService;



}