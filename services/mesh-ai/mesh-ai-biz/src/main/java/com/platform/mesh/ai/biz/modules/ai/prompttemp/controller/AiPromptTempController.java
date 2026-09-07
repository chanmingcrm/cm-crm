package com.platform.mesh.ai.biz.modules.ai.prompttemp.controller;

import com.platform.mesh.ai.biz.modules.ai.prompttemp.domain.dto.AiPromptTempDTO;
import com.platform.mesh.ai.biz.modules.ai.prompttemp.domain.po.AiPromptTemp;
import com.platform.mesh.ai.biz.modules.ai.prompttemp.domain.vo.AiPromptTempVO;
import com.platform.mesh.ai.biz.modules.ai.prompttemp.service.IAiPromptTempService;
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
 * @description AI提示词模板信息
 * @author 蝉鸣
 */
@Tag(description = "AiPromptTempController", name = "AI提示词模板")
@RestController
@RequestMapping
public class AiPromptTempController extends BaseController{
    
    @Autowired
    private IAiPromptTempService aiPromptTempService;

    /**
	 * 功能描述:
	 * 〈获取AI提示词模板列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<AiPromptTempVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取AI提示词模板分页")
	@PostMapping("/ai/promptTemp/page")
	public Result<PageVO<AiPromptTempVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<AiPromptTemp> mPage = MPageUtil.pageEntityToMPage(pageDTO, AiPromptTemp.class);
        MPage<AiPromptTemp> page = aiPromptTempService.page(mPage);
        PageVO<AiPromptTempVO> voPage = MPageUtil.convertToVO(page, AiPromptTempVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前AI提示词模板信息〉
     * @param promptTempId promptTempId
     * @return 正常返回:{@link Result<AiPromptTempVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前AI提示词模板信息")
    @GetMapping("/ai/promptTemp/info/{promptTempId}")
    public Result<AiPromptTempVO> getAiPromptTempInfoById(@PathVariable("promptTempId")Long promptTempId) {
        AiPromptTempVO aiPromptTempVO = aiPromptTempService.getAiPromptTempById(promptTempId);
        return Result.success(aiPromptTempVO);
    }

    /**
     * 功能描述:
     * 〈新增AI提示词模板〉
     * @param aiPromptTempDTO aiPromptTempDTO
     * @return 正常返回:{@link Result<AiPromptTempVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增AI提示词模板")
    @Log(moduleName = "AI提示词模板管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/ai/promptTemp/add")
    public Result<AiPromptTempVO> addAiPromptTemp(@Validated @RequestBody AiPromptTempDTO aiPromptTempDTO) {
        return Result.success(aiPromptTempService.addAiPromptTemp(aiPromptTempDTO));
    }

    /**
     * 功能描述:
     * 〈修改AI提示词模板〉
     * @param aiPromptTempDTO aiPromptTempDTO
     * @return 正常返回:{@link Result<AiPromptTempVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改AI提示词模板")
    @Log(moduleName = "AI提示词模板管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/ai/promptTemp/edit")
    public Result<AiPromptTempVO> editAiPromptTemp(@Validated @RequestBody AiPromptTempDTO aiPromptTempDTO) {
        return Result.success(aiPromptTempService.editAiPromptTemp(aiPromptTempDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除AI提示词模板〉
     * @param promptTempId promptTempId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除AI提示词模板")
    @Log(moduleName = "AI提示词模板管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/ai/promptTemp/delete/{promptTempId}")
    public Result<Boolean> deleteAiPromptTemp(@PathVariable(value = "promptTempId",required = false)Long promptTempId) {
        return Result.success(aiPromptTempService.deleteAiPromptTemp(promptTempId));
    }

}