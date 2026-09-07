package com.platform.mesh.app.biz.modules.app.formcolumnsetprocess.api;

import com.platform.mesh.app.biz.modules.app.formcolumnsetprocess.service.IAppFormColumnSetProcessService;
import com.platform.mesh.core.application.controller.BaseController;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


/**
 * 约定当前controller 只引入当前service
 * @description 单信息
 * @author 蝉鸣
 */
@Hidden
@RestController
public class AppFormColumnSetProcessApi extends BaseController{

    @Autowired
    private IAppFormColumnSetProcessService appFormColumnSetProcessService;

}