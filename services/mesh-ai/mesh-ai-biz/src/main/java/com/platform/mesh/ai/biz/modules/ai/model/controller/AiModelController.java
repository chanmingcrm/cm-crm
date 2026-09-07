package com.platform.mesh.ai.biz.modules.ai.model.controller;

import com.platform.mesh.ai.biz.modules.ai.model.domain.dto.AiModelDTO;
import com.platform.mesh.ai.biz.modules.ai.model.domain.po.AiModel;
import com.platform.mesh.ai.biz.modules.ai.model.domain.vo.AiModelVO;
import com.platform.mesh.ai.biz.modules.ai.model.service.IAiModelService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.core.application.domain.dto.PageDTO;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



/**
 * 约定当前controller 只引入当前service
 * @description AI模型信息
 * @author 蝉鸣
 */
@Tag(description = "AiModelController", name = "AI模型")
@RestController
@RequestMapping
public class AiModelController extends BaseController{
    
    @Autowired
    private IAiModelService aiModelService;

    /**
	 * 功能描述:
	 * 〈获取AI模型列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<AiModelVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取AI模型分页")
	@PostMapping("/ai/model/page")
	public Result<PageVO<AiModelVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<AiModel> mPage = MPageUtil.pageEntityToMPage(pageDTO, AiModel.class);
        MPage<AiModel> page = aiModelService.page(mPage);
        PageVO<AiModelVO> voPage = MPageUtil.convertToVO(page, AiModelVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前AI模型信息〉
     * @param modelId modelId
     * @return 正常返回:{@link Result<AiModelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前AI模型信息")
    @GetMapping("/ai/model/info/{modelId}")
    public Result<AiModelVO> getAiModelInfoById(@PathVariable("modelId")Long modelId) {
        AiModelVO aiModelVO = aiModelService.getAiModelById(modelId);
        return Result.success(aiModelVO);
    }

    /**
     * 功能描述:
     * 〈新增AI模型〉
     * @param aiModelDTO aiModelDTO
     * @return 正常返回:{@link Result<AiModelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增AI模型")
    @Log(moduleName = "AI模型管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/ai/model/add")
    public Result<AiModelVO> addAiModel(@Validated @RequestBody AiModelDTO aiModelDTO) {
        return Result.success(aiModelService.addAiModel(aiModelDTO));
    }

    /**
     * 功能描述:
     * 〈修改AI模型〉
     * @param modelDTO modelDTO
     * @return 正常返回:{@link Result<AiModelVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改AI模型")
    @Log(moduleName = "AI模型管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/ai/model/edit")
    public Result<AiModelVO> editAiModel(@Validated @RequestBody AiModelDTO modelDTO) {
        return Result.success(aiModelService.editAiModel(modelDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除AI模型〉
     * @param modelId modelId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除AI模型")
    @Log(moduleName = "AI模型管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/ai/model/delete/{modelId}")
    public Result<Boolean> deleteAiModel(@PathVariable(value = "modelId",required = false)Long modelId) {
        return Result.success(aiModelService.deleteAiModel(modelId));
    }

}