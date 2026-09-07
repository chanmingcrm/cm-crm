package com.platform.mesh.app.biz.modules.third.thirdformcolumn.controller;

import com.platform.mesh.app.biz.modules.third.thirdformcolumn.domain.dto.ThirdFormColumnDTO;
import com.platform.mesh.app.biz.modules.third.thirdformcolumn.domain.po.ThirdFormColumn;
import com.platform.mesh.app.biz.modules.third.thirdformcolumn.domain.vo.ThirdFormColumnVO;
import com.platform.mesh.app.biz.modules.third.thirdformcolumn.service.IThirdFormColumnService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 约定当前controller 只引入当前service
 * @description 第三方字段信息
 * @author 蝉鸣
 */
@Tag(description = "ThirdFormColumnController", name = "第三方字段")
@RestController
@RequestMapping
public class ThirdFormColumnController extends BaseController{

    @Autowired
    private IThirdFormColumnService thirdFormColumnService;

    /**
     * 功能描述:
     * 〈获取当前第三方字段信息〉
     * @param sourceFlag sourceFlag
     * @return 正常返回:{@link Result<List<ThirdFormColumn>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前第三方字段信息")
    @GetMapping("/third/form/column/list")
    public Result<List<ThirdFormColumnVO>> getThirdFormColumn(@RequestParam("sourceFlag")Integer sourceFlag) {
        List<ThirdFormColumnVO> thirdFormColumnVOS = thirdFormColumnService.getThirdFormColumn(sourceFlag);
        return Result.success(thirdFormColumnVOS);
    }

    /**
     * 功能描述:
     * 〈新增第三方字段〉
     * @param thirdFormColumnDTOS thirdFormColumnDTOS
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增第三方字段")
    @PostMapping("/third/form/column/add")
    public Result<Boolean> addThirdFormColumn(@Validated @RequestBody List<ThirdFormColumnDTO> thirdFormColumnDTOS) {
        return Result.success(thirdFormColumnService.addThirdFormColumn(thirdFormColumnDTOS));
    }

    /**
     * 功能描述:
     * 〈删除第三方字段〉
     * @param columnIds columnIds
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除第三方字段")
    @PostMapping("/third/form/column/delete")
    public Result<Boolean> delThirdFormColumn(@Validated @RequestBody List<Long> columnIds) {
        thirdFormColumnService.delThirdFormColumn(columnIds);
        return Result.success();
    }


}