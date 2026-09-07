package com.platform.mesh.app.biz.modules.third.thirdformcolumnmapping.controller;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.app.biz.modules.third.thirdformcolumnmapping.domain.dto.ThirdFormColumnMappingDTO;
import com.platform.mesh.app.biz.modules.third.thirdformcolumnmapping.domain.po.ThirdFormColumnMapping;
import com.platform.mesh.app.biz.modules.third.thirdformcolumnmapping.domain.vo.ThirdFormColumnMappingVO;
import com.platform.mesh.app.biz.modules.third.thirdformcolumnmapping.service.IThirdFormColumnMappingService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.security.utils.UserCacheUtil;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 约定当前controller 只引入当前service
 * @description 第三方字段映射设置信息
 * @author 蝉鸣
 */
@Tag(description = "ThirdFormColumnMappingController", name = "第三方字段映射设置")
@RestController
@RequestMapping
public class ThirdFormColumnMappingController extends BaseController{

    
    @Autowired
    private IThirdFormColumnMappingService thirdFormColumnMappingService;


    /**
     * 功能描述:
     * 〈获取当前第三方字段映射信息〉
     * @param sourceFlag sourceFlag
     * @return 正常返回:{@link Result<List<ThirdFormColumnMappingVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前第三方字段信息")
    @GetMapping("/third/form/column/mapping/list")
    public Result<List<ThirdFormColumnMappingVO>> getThirdFormColumnMapping(@RequestParam("sourceFlag")Integer sourceFlag) {
        List<ThirdFormColumnMapping> thirdFormColumnMappings = thirdFormColumnMappingService.getThirdFormColumnMapping(sourceFlag);
        return Result.success(BeanUtil.copyToList(thirdFormColumnMappings, ThirdFormColumnMappingVO.class));
    }

    /**
     * 功能描述:
     * 〈新增第三方字段映射信息〉
     * @param thirdFormColumnMappingDTOS thirdFormColumnMappingDTOS
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增第三方字段")
    @PostMapping("/third/form/column/mapping/add")
    public Result<Boolean> addThirdFormColumnMapping(@Validated @RequestBody List<ThirdFormColumnMappingDTO> thirdFormColumnMappingDTOS) {
        return Result.success(thirdFormColumnMappingService.addThirdFormColumnMapping(thirdFormColumnMappingDTOS));
    }

}
