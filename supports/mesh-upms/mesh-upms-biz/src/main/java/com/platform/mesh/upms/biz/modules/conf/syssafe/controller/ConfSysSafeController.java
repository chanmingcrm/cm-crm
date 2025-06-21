package com.platform.mesh.upms.biz.modules.conf.syssafe.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.upms.biz.modules.conf.syssafe.domain.dto.ConfSysSafeDTO;
import com.platform.mesh.upms.biz.modules.conf.syssafe.domain.vo.ConfSysSafeVO;
import com.platform.mesh.upms.biz.modules.conf.syssafe.service.IConfSysSafeService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 约定当前controller 只引入当前service
 * @description 配置安全性信息
 * @author 蝉鸣
 */
@Tag(description = "ConfSysSafeController", name = "配置安全性")
@RestController
public class ConfSysSafeController extends BaseController{

    @Autowired
    private IConfSysSafeService  confSysSafeService;


    /**
     * 功能描述:
     * 〈获取当前配置安全性信息〉
     * @param sysSafeId sysSafeId
     * @return 正常返回:{@link Result<ConfSysSafeVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前配置安全性信息")
    @GetMapping("/conf/sys/safe/info/{sysSafeId}")
    public Result<ConfSysSafeVO> getSysSafeInfoById(@PathVariable("sysSafeId")Long sysSafeId) {
        ConfSysSafeVO confSysSafeVO = confSysSafeService.getSysSafeInfoById(sysSafeId);
        return Result.success(confSysSafeVO);
    }

    /**
     * 功能描述:
     * 〈新增配置安全性〉
     * @param sysSafeDTO sysSafeDTO
     * @return 正常返回:{@link Result<ConfSysSafeVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增配置安全性")
    @Log(moduleName = "配置安全性管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/conf/sys/safe/add")
    public Result<ConfSysSafeVO> addSysSafe(@Validated @RequestBody ConfSysSafeDTO sysSafeDTO) {
        return Result.success(confSysSafeService.addSysSafe(sysSafeDTO));
    }

}