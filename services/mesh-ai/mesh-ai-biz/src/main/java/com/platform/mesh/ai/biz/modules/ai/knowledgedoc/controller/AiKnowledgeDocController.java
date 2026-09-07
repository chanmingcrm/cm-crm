package com.platform.mesh.ai.biz.modules.ai.knowledgedoc.controller;

import com.platform.mesh.ai.biz.modules.ai.knowledgedoc.domain.dto.AiKnowledgeDocDTO;
import com.platform.mesh.ai.biz.modules.ai.knowledgedoc.domain.vo.AiKnowledgeDocVO;
import com.platform.mesh.ai.biz.modules.ai.knowledgedoc.service.IAiKnowledgeDocService;
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
 * @description AI知识库附件信息
 * @author 蝉鸣
 */
@Tag(description = "AiKnowledgeDocController", name = "AI知识库附件")
@RestController
@RequestMapping
public class AiKnowledgeDocController extends BaseController{
    
    @Autowired
    private IAiKnowledgeDocService aiKnowledgeDocService;

    /**
	 * 功能描述:
	 * 〈获取AI知识库附件列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<AiKnowledgeDocVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取AI知识库附件分页")
	@PostMapping("/ai/knowledge/doc/page")
	public Result<PageVO<AiKnowledgeDocVO>> selectPage(@RequestBody PageDTO pageDTO) {
        MPage<AiKnowledgeDocVO> page = aiKnowledgeDocService.selectPage(pageDTO);
        PageVO<AiKnowledgeDocVO> voPage = MPageUtil.convertToVO(page, AiKnowledgeDocVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前AI知识库附件信息〉
     * @param knowledgeDocId knowledgeDocId
     * @return 正常返回:{@link Result<AiKnowledgeDocVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前AI知识库附件信息")
    @GetMapping("/ai/knowledge/doc/info/{knowledgeDocId}")
    public Result<AiKnowledgeDocVO> getAiKnowledgeDocInfoById(@PathVariable("knowledgeDocId")Long knowledgeDocId) {
        AiKnowledgeDocVO aiKnowledgeDocVO = aiKnowledgeDocService.getAiKnowledgeDocById(knowledgeDocId);
        return Result.success(aiKnowledgeDocVO);
    }

    /**
     * 功能描述:
     * 〈新增AI知识库附件〉
     * @param aiKnowledgeDocDTO aiKnowledgeDocDTO
     * @return 正常返回:{@link Result<AiKnowledgeDocVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增AI知识库附件")
    @Log(moduleName = "AI知识库附件管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/ai/knowledge/doc/add")
    public Result<AiKnowledgeDocVO> addAiKnowledgeDoc(@Validated @RequestBody AiKnowledgeDocDTO aiKnowledgeDocDTO) {
        return Result.success(aiKnowledgeDocService.addAiKnowledgeDoc(aiKnowledgeDocDTO));
    }

    /**
     * 功能描述:
     * 〈修改AI知识库附件〉
     * @param aiKnowledgeDocDTO aiKnowledgeDocDTO
     * @return 正常返回:{@link Result<AiKnowledgeDocVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改AI知识库附件")
    @Log(moduleName = "AI知识库附件管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/ai/knowledge/doc/edit")
    public Result<AiKnowledgeDocVO> editAiKnowledgeDoc(@Validated @RequestBody AiKnowledgeDocDTO aiKnowledgeDocDTO) {
        return Result.success(aiKnowledgeDocService.editAiKnowledgeDoc(aiKnowledgeDocDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除AI知识库附件〉
     * @param knowledgeDocId knowledgeDocId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除AI知识库附件")
    @Log(moduleName = "AI知识库附件管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/ai/knowledge/doc/delete/{knowledgeDocId}")
    public Result<Boolean> deleteAiKnowledgeDoc(@PathVariable(value = "knowledgeDocId",required = false)Long knowledgeDocId) {
        return Result.success(aiKnowledgeDocService.deleteAiKnowledgeDoc(knowledgeDocId));
    }

}