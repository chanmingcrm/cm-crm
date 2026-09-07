package com.platform.mesh.ai.biz.modules.ai.session.controller;

import com.platform.mesh.ai.biz.modules.ai.session.domain.dto.AiSessionDTO;
import com.platform.mesh.ai.biz.modules.ai.session.domain.dto.AiSessionPageDTO;
import com.platform.mesh.ai.biz.modules.ai.session.domain.vo.AiSessionVO;
import com.platform.mesh.ai.biz.modules.ai.session.service.IAiSessionService;
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


/**
 * 约定当前controller 只引入当前service
 * @description AI会话信息
 * @author 蝉鸣
 */
@Tag(description = "AiSessionController", name = "AI会话")
@RestController
@RequestMapping
public class AiSessionController extends BaseController{
    
    @Autowired
    private IAiSessionService aiSessionService;

    /**
	 * 功能描述:
	 * 〈获取AI会话列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<PageVO<AiSessionVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取AI会话分页")
	@PostMapping("/ai/session/page")
	public Result<PageVO<AiSessionVO>> selectPage(@RequestBody AiSessionPageDTO pageDTO) {
        MPage<AiSessionVO> page = aiSessionService.selectPage(pageDTO);
        PageVO<AiSessionVO> voPage = MPageUtil.convertToVO(page, AiSessionVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前AI会话信息〉
     * @param sessionId sessionId
     * @return 正常返回:{@link Result<AiSessionVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前AI会话信息")
    @GetMapping("/ai/session/info/{sessionId}")
    public Result<AiSessionVO> getAiSessionInfoById(@PathVariable("sessionId")Long sessionId) {
        AiSessionVO aiSessionVO = aiSessionService.getAiSessionById(sessionId);
        return Result.success(aiSessionVO);
    }

    /**
     * 功能描述:
     * 〈新增AI会话〉
     * @param aiSessionDTO aiSessionDTO
     * @return 正常返回:{@link Result<AiSessionVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增AI会话")
    @Log(moduleName = "AI会话管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/ai/session/add")
    public Result<AiSessionVO> addAiSession(@Validated @RequestBody AiSessionDTO aiSessionDTO) {
        return Result.success(aiSessionService.addAiSession(aiSessionDTO));
    }

    /**
     * 功能描述:
     * 〈修改AI会话〉
     * @param aiSessionDTO aiSessionDTO
     * @return 正常返回:{@link Result<AiSessionVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改AI会话")
    @Log(moduleName = "AI会话管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/ai/session/edit")
    public Result<AiSessionVO> editAiSession(@Validated @RequestBody AiSessionDTO aiSessionDTO) {
        return Result.success(aiSessionService.editAiSession(aiSessionDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除AI会话〉
     * @param sessionId sessionId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除AI会话")
    @Log(moduleName = "AI会话管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/ai/session/delete/{sessionId}")
    public Result<Boolean> deleteAiSession(@PathVariable(value = "sessionId",required = false)Long sessionId) {
        return Result.success(aiSessionService.deleteAiSession(sessionId));
    }

}