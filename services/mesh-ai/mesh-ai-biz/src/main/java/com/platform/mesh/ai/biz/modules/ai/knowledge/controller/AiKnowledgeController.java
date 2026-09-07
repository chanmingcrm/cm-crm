package com.platform.mesh.ai.biz.modules.ai.knowledge.controller;

import com.platform.mesh.ai.biz.modules.ai.knowledge.domain.dto.AiKnowledgeDTO;
import com.platform.mesh.ai.biz.modules.ai.knowledge.domain.vo.AiKnowledgeVO;
import com.platform.mesh.ai.biz.modules.ai.knowledge.service.IAiKnowledgeService;
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
 * @description AI知识库信息
 * @author 蝉鸣
 */
@Tag(description = "AiKnowledgeController", name = "AI知识库")
@RestController
@RequestMapping
public class AiKnowledgeController extends BaseController{
    
    @Autowired
    private IAiKnowledgeService aiKnowledgeService;

    /**
	 * 功能描述:
	 * 〈获取AI知识库列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<AiKnowledgeVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取AI知识库分页")
	@PostMapping("/ai/knowledge/page")
	public Result<PageVO<AiKnowledgeVO>> selectPage(@RequestBody PageDTO pageDTO) {
        MPage<AiKnowledgeVO> page = aiKnowledgeService.selectPage(pageDTO);
        PageVO<AiKnowledgeVO> voPage = MPageUtil.convertToVO(page, AiKnowledgeVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前AI知识库信息〉
     * @param knowledgeId knowledgeId
     * @return 正常返回:{@link Result<AiKnowledgeVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前AI知识库信息")
    @GetMapping("/ai/knowledge/info/{knowledgeId}")
    public Result<AiKnowledgeVO> getAiKnowledgeInfoById(@PathVariable("knowledgeId")Long knowledgeId) {
        AiKnowledgeVO aiKnowledgeVO = aiKnowledgeService.getAiKnowledgeById(knowledgeId);
        return Result.success(aiKnowledgeVO);
    }

    /**
     * 功能描述:
     * 〈新增AI知识库〉
     * @param aiKnowledgeDTO aiKnowledgeDTO
     * @return 正常返回:{@link Result<AiKnowledgeVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增AI知识库")
    @Log(moduleName = "AI知识库管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/ai/knowledge/add")
    public Result<AiKnowledgeVO> addAiKnowledge(@Validated @RequestBody AiKnowledgeDTO aiKnowledgeDTO) {
        return Result.success(aiKnowledgeService.addAiKnowledge(aiKnowledgeDTO));
    }

    /**
     * 功能描述:
     * 〈修改AI知识库〉
     * @param aiKnowledgeDTO aiKnowledgeDTO
     * @return 正常返回:{@link Result<AiKnowledgeVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改AI知识库")
    @Log(moduleName = "AI知识库管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/ai/knowledge/edit")
    public Result<AiKnowledgeVO> editAiKnowledge(@Validated @RequestBody AiKnowledgeDTO aiKnowledgeDTO) {
        return Result.success(aiKnowledgeService.editAiKnowledge(aiKnowledgeDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除AI知识库〉
     * @param knowledgeId knowledgeId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除AI知识库")
    @Log(moduleName = "AI知识库管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/ai/knowledge/delete/{knowledgeId}")
    public Result<Boolean> deleteAiKnowledge(@PathVariable(value = "knowledgeId",required = false)Long knowledgeId) {
        return Result.success(aiKnowledgeService.deleteAiKnowledge(knowledgeId));
    }

}