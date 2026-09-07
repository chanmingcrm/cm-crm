package com.platform.mesh.upms.biz.modules.log.modify.api;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.constants.HttpConst;
import com.platform.mesh.security.annotation.AuthIgnore;
import com.platform.mesh.upms.api.modules.sys.log.domain.bo.LogModifyBO;
import com.platform.mesh.upms.biz.modules.log.modify.service.ILogModifyService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 约定当前controller 只引入当前service
 * @description 修改日志信息
 * @author 蝉鸣
 */
@Hidden
@Tag(description = "LogModifyApi", name = "修改日志信息")
@RestController
public class LogModifyApi extends BaseController{

    /**
     * 服务对象
     */
    @Autowired
    private ILogModifyService logModifyService;

    /**
     * 功能描述:
     * 〈新增修改日志〉
     * @param modifyBO modifyBO
     * @author 蝉鸣
     */
    @AuthIgnore
    @Operation(summary = "新增修改日志")
    @PostMapping(value ="/api/log/modify/add")
    public void addModifyLog(@Validated @RequestBody LogModifyBO modifyBO) {
        logModifyService.addModifyLog(modifyBO);
    }


}
