package com.platform.mesh.app.biz.modules.app.formcolumnmapping.controller;

import com.platform.mesh.app.biz.modules.app.formcolumnmapping.domain.dto.AppFormColumnMappingDTO;
import com.platform.mesh.app.biz.modules.app.formcolumnmapping.domain.po.AppFormColumnMapping;
import com.platform.mesh.app.biz.modules.app.formcolumnmapping.domain.vo.AppFormColumnMappingVO;
import com.platform.mesh.app.biz.modules.app.formcolumnmapping.service.IAppFormColumnMappingService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * 约定当前controller 只引入当前service
 * @description 单字段映射信息
 * @author 蝉鸣
 */
@Tag(description = "AppFormColumnMappingController", name = "单字段映射")
@RestController
@RequestMapping
public class AppFormColumnMappingController extends BaseController{
    @Autowired
    private IAppFormColumnMappingService appFormColumnMappingService;

    /**
	 * 功能描述:
	 * 〈获取单字段映射列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<AppFormColumnMappingVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取单字段映射分页")
	@PostMapping("/app/form/column/mapping/page")
	public Result<PageVO<AppFormColumnMappingVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<AppFormColumnMapping> formColumnMappingMPage = MPageUtil.pageEntityToMPage(pageDTO, AppFormColumnMapping.class);
        MPage<AppFormColumnMapping> page = appFormColumnMappingService.page(formColumnMappingMPage);
        PageVO<AppFormColumnMappingVO> voPage = MPageUtil.convertToVO(page, AppFormColumnMappingVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前单字段映射信息〉
     * @param formColumnMappingId formColumnMappingId
     * @return 正常返回:{@link Result<AppFormColumnMappingVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前单字段映射信息")
    @GetMapping("/app/form/column/mapping/info/{formColumnMappingId}")
    public Result<AppFormColumnMappingVO> getFormColumnMappingInfoById(@PathVariable("formColumnMappingId")Long formColumnMappingId) {
        AppFormColumnMappingVO appFormColumnMappingVO = appFormColumnMappingService.getFormColumnMappingInfoById(formColumnMappingId);
        return Result.success(appFormColumnMappingVO);
    }

    /**
     * 功能描述:
     * 〈新增单字段映射〉
     * @param formColumnMappingDTO formColumnMappingDTO
     * @return 正常返回:{@link Result<AppFormColumnMappingVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增单字段映射")
    @Log(moduleName = "单字段映射管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/app/form/column/mapping/add")
    public Result<AppFormColumnMappingVO> addFormColumnMapping(@Validated @RequestBody AppFormColumnMappingDTO formColumnMappingDTO) {
        return Result.success(appFormColumnMappingService.addFormColumnMapping(formColumnMappingDTO));
    }

    /**
     * 功能描述:
     * 〈修改单字段映射〉
     * @param formColumnMappingDTO formColumnMappingDTO
     * @return 正常返回:{@link Result<AppFormColumnMappingVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改单字段映射")
    @Log(moduleName = "单字段映射管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/app/form/column/mapping/edit")
    public Result<AppFormColumnMappingVO> editFormColumnMapping(@Validated @RequestBody AppFormColumnMappingDTO formColumnMappingDTO) {
        return Result.success(appFormColumnMappingService.editFormColumnMapping(formColumnMappingDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除单字段映射〉
     * @param formColumnMappingId formColumnMappingId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除单字段映射")
    @Log(moduleName = "单字段映射管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/app/form/column/mapping/delete/{formColumnMappingId}")
    public Result<Boolean> deleteFormColumnMapping(@PathVariable(value = "formColumnMappingId",required = false)Long formColumnMappingId) {
        return Result.success(appFormColumnMappingService.deleteFormColumnMapping(formColumnMappingId));
    }

}