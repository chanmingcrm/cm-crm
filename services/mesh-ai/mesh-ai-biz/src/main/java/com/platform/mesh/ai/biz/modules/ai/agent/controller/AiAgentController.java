package com.platform.mesh.ai.biz.modules.ai.agent.controller;

import com.platform.mesh.ai.biz.modules.ai.agent.domain.dto.AiAgentDTO;
import com.platform.mesh.ai.biz.modules.ai.agent.domain.po.AiAgent;
import com.platform.mesh.ai.biz.modules.ai.agent.domain.vo.AiAgentVO;
import com.platform.mesh.ai.biz.modules.ai.agent.service.IAiAgentService;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.dto.PageDTO;
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



/**
 * 约定当前controller 只引入当前service
 * @description AIAgent信息
 * @author 蝉鸣
 */
@Tag(description = "AiAgentController", name = "AIAgent信息")
@RestController
@RequestMapping
public class AiAgentController extends BaseController{
    
    @Autowired
    private IAiAgentService aiAgentService;

    /**
	 * 功能描述:
	 * 〈获取AIAgent列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<AiAgentVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取AIAgent分页")
	@PostMapping("/ai/agent/page")
	public Result<PageVO<AiAgentVO>> selectPage(@RequestBody PageDTO pageDTO) {
	    MPage<AiAgent> mPage = MPageUtil.pageEntityToMPage(pageDTO, AiAgent.class);
        MPage<AiAgent> page = aiAgentService.page(mPage);
        PageVO<AiAgentVO> voPage = MPageUtil.convertToVO(page, AiAgentVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前AIAgent信息〉
     * @param agentId agentId
     * @return 正常返回:{@link Result<AiAgentVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前AIAgent信息")
    @GetMapping("/ai/agent/info/{agentId}")
    public Result<AiAgentVO> getAiAgentInfoById(@PathVariable("agentId")Long agentId) {
        AiAgentVO aiAgentVO = aiAgentService.getAiAgentById(agentId);
        return Result.success(aiAgentVO);
    }

    /**
     * 功能描述:
     * 〈新增AIAgent〉
     * @param aiAgentDTO aiAgentDTO
     * @return 正常返回:{@link Result<AiAgentVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增AIAgent")
    @Log(moduleName = "AIAgent管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/ai/agent/add")
    public Result<AiAgentVO> addAiAgent(@Validated @RequestBody AiAgentDTO aiAgentDTO) {
        return Result.success(aiAgentService.addAiAgent(aiAgentDTO));
    }

    /**
     * 功能描述:
     * 〈修改AIAgent〉
     * @param aiAgentDTO aiAgentDTO
     * @return 正常返回:{@link Result<AiAgentVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改AIAgent")
    @Log(moduleName = "AIAgent管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/ai/agent/edit")
    public Result<AiAgentVO> editAiAgent(@Validated @RequestBody AiAgentDTO aiAgentDTO) {
        return Result.success(aiAgentService.editAiAgent(aiAgentDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除AIAgent〉
     * @param agentId agentId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除AIAgent")
    @Log(moduleName = "AIAgent管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/ai/agent/delete/{agentId}")
    public Result<Boolean> deleteAiAgent(@PathVariable(value = "agentId",required = false)Long agentId) {
        return Result.success(aiAgentService.deleteAiAgent(agentId));
    }

}