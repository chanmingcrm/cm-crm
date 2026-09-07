package com.platform.mesh.crm.biz.bi.plm.controller;

import com.platform.mesh.app.api.modules.bi.domain.dto.BiDTO;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.SimpVO;
import com.platform.mesh.crm.biz.bi.plm.service.IPlmProductBiService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 约定当前controller 只引入当前service
 * @description 产品看板BI统计信息
 * @author 蝉鸣
 */
@Tag(description = "PlmProductBiController", name = "产品看板BI统计")
@RestController
@RequestMapping
public class PlmProductBiController extends BaseController {

    @Autowired
    private IPlmProductBiService plmProductBiService;

    /**
     * 功能描述:
     * 【获取产品汇总统计】
     * @param biDTO biDTO
     * @return 正常返回:{@link Result<List<SimpVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取产品汇总统计")
    @PostMapping("/plm/bi/product/summary")
    public Result<List<SimpVO>> productSummary(@RequestBody BiDTO biDTO) {
        return Result.success(plmProductBiService.productSummary(biDTO));
    }

    /**
     * 功能描述:
     * 【获取生命周期结构统计】
     * @param biDTO biDTO
     * @return 正常返回:{@link Result<List<SimpVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取生命周期结构统计")
    @PostMapping("/plm/bi/product/lifecycle")
    public Result<List<SimpVO>> productLifecycle(@RequestBody BiDTO biDTO) {
        return Result.success(plmProductBiService.productLifecycle(biDTO));
    }

    /**
     * 功能描述:
     * 【获取产品健康度统计】
     * @param biDTO biDTO
     * @return 正常返回:{@link Result<List<SimpVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取产品健康度统计")
    @PostMapping("/plm/bi/product/health")
    public Result<List<SimpVO>> productHealth(@RequestBody BiDTO biDTO) {
        return Result.success(plmProductBiService.productHealth(biDTO));
    }

    /**
     * 功能描述:
     * 【获取产品运营指标统计】
     * @param biDTO biDTO
     * @return 正常返回:{@link Result<List<SimpVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取产品运营指标统计")
    @PostMapping("/plm/bi/product/operation")
    public Result<List<SimpVO>> productOperation(@RequestBody BiDTO biDTO) {
        return Result.success(plmProductBiService.productOperation(biDTO));
    }

    /**
     * 功能描述:
     * 【获取产品预警统计】
     * @param biDTO biDTO
     * @return 正常返回:{@link Result<List<SimpVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取产品预警统计")
    @PostMapping("/plm/bi/product/warning/items")
    public Result<List<SimpVO>> productWarningItems(@RequestBody BiDTO biDTO) {
        return Result.success(plmProductBiService.productWarningItems(biDTO));
    }

}
