package com.platform.mesh.app.biz.modules.app.formcolumn.controller;

import cn.hutool.core.util.IdUtil;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.dto.AppFormColumnDTO;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.dto.AppFormColumnPageDTO;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.po.AppFormColumn;
import com.platform.mesh.app.biz.modules.app.formcolumn.domain.vo.AppFormColumnVO;
import com.platform.mesh.app.biz.modules.app.formcolumn.service.IAppFormColumnService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 约定当前controller 只引入当前service
 * @description 表单字段关联信息
 * @author 蝉鸣
 */
@Tag(description = "AppFormColumnController", name = "表单字段关联")
@RestController
public class AppFormColumnController extends BaseController{
    @Autowired
    private IAppFormColumnService appFormColumnService;

    /**
	 * 功能描述:
	 * 〈获取表单字段关联列表〉
	 * @param appFormColumnPageDTO appFormColumnPageDTO
	 * @return 正常返回:{@link Result<MPage<AppFormColumnVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取表单字段关联分页")
	@PostMapping("/app/form/column/page")
	public Result<PageVO<AppFormColumnVO>> selectPage(@RequestBody AppFormColumnPageDTO appFormColumnPageDTO) {
        MPage<AppFormColumn> page = appFormColumnService.page(appFormColumnPageDTO);
        PageVO<AppFormColumnVO> voPage = MPageUtil.convertToVO(page, AppFormColumnVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前表单字段关联信息〉
     * @param formId formId
     * @return 正常返回:{@link Result<List<AppFormColumnVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前表单字段关联信息")
    @GetMapping("/app/form/column/info/{moduleId}/{formId}")
    public Result<List<AppFormColumnVO>> getFormColumnInfo(@PathVariable("moduleId")Long moduleId
            ,@PathVariable("formId")Long formId) {
        List<AppFormColumnVO> appFormColumnVOs = appFormColumnService.getFormColumnTree(moduleId,formId);
        return Result.success(appFormColumnVOs);
    }

    /**
     * 功能描述:
     * 〈获取单字段关联信息〉
     * @param formId formId
     * @param columnHash columnHash
     * @return 正常返回:{@link Result<AppFormColumnVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取单字段关联信息")
    @GetMapping("/app/form/sing/column/info/{formId}/{columnHash}")
    public Result<AppFormColumnVO> getSingColumnInfo(@PathVariable("formId")Long formId
            ,@PathVariable("columnHash")String columnHash) {
        AppFormColumnVO appFormColumnVO = appFormColumnService.getSingColumnInfo(formId,columnHash);
        return Result.success(appFormColumnVO);
    }

    /**
     * 功能描述:
     * 〈新增表单字段关联〉
     * @param formColumnDTOs formColumnDTOs
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增表单字段关联")
//    @Log(moduleName = "表单字段关联管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/app/form/column/add")
    public Result<Boolean> addFormColumn(@Validated @RequestBody List<AppFormColumnDTO> formColumnDTOs) {
        Long batchId = IdUtil.getSnowflake().nextId();
        return Result.success(appFormColumnService.addFormColumn(batchId,formColumnDTOs));
    }
    
   /**
     * 功能描述:
     * 〈删除表单字段关联〉
     * @param formColumnIds formColumnIds
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除表单字段关联")
    @Log(moduleName = "表单字段关联管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/app/form/column/delete")
    public Result<Boolean> deleteFormColumn(@RequestBody List<Long> formColumnIds) {
        return Result.success(appFormColumnService.deleteFormColumn(formColumnIds));
    }

   /**
     * 功能描述:
     * 〈根据moduleId 表单类型快速获取默认字段信息〉
     * @param moduleId moduleId
     * @param formType formType
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "根据moduleId 业务字段类型快速获取默认字段信息")
    @PostMapping("/app/form/column/fast/form/type/{moduleId}/{formType}")
    public Result<List<AppFormColumnVO>> fastColumnVOByModuleAndFormType(@PathVariable("moduleId")Long moduleId, @PathVariable("formType")Integer formType) {
        return Result.success(appFormColumnService.fastColumnVOByModuleAndFormType(moduleId,formType));
    }

   /**
     * 功能描述:
     * 〈根据moduleId 表单类型快速获取默认字段信息〉
     * @param moduleId moduleId
     * @param formType formType
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "根据moduleId 业务字段类型快速获取默认字段信息")
    @PostMapping("/app/form/column/tree/{moduleId}/{formType}")
    public Result<List<AppFormColumnVO>> fastColumnTreeVOByModuleAndFormType(@PathVariable("moduleId")Long moduleId, @PathVariable("formType")Integer formType) {
        return Result.success(appFormColumnService.fastColumnTreeVOByModuleAndFormType(moduleId,formType));
    }

   /**
     * 功能描述:
     * 〈根据moduleId 业务字段类型快速获取默认字段信息〉
     * @param moduleId moduleId
     * @param columnType columnType
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "根据moduleId 业务字段类型快速获取默认字段信息")
    @PostMapping("/app/form/column/fast/{moduleId}/{columnType}")
    public Result<AppFormColumnVO> fastColumnByModuleAndType(@PathVariable("moduleId")Long moduleId, @PathVariable("columnType")Integer columnType) {
        return Result.success(appFormColumnService.fastColumnByModuleAndType(moduleId,columnType));
    }

}