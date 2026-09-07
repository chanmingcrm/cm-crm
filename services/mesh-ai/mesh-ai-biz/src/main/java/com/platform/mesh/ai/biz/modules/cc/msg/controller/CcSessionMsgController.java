package com.platform.mesh.ai.biz.modules.cc.msg.controller;

import com.platform.mesh.ai.biz.modules.cc.msg.domain.dto.CcSessionMsgDTO;
import com.platform.mesh.ai.biz.modules.cc.msg.domain.dto.CcSessionMsgPageDTO;
import com.platform.mesh.ai.biz.modules.cc.msg.domain.vo.CcSessionMsgVO;
import com.platform.mesh.ai.biz.modules.cc.msg.service.ICcSessionMsgService;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 约定当前controller 只引入当前service
 * @description 聊天消息
 * @author 蝉鸣
 */
@Tag(description = "CcSessionMsgController", name = "聊天消息")
@RestController
@RequestMapping
public class CcSessionMsgController {

    @Autowired
    private ICcSessionMsgService ccSessionMsgService;

    /**
     * 功能描述:
     * 〈获取客服会话消息列表〉
     * @param pageDTO pageDTO
     * @return 正常返回:{@link Result<PageVO<CcSessionMsgVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取客服会话消息分页")
    @PostMapping("/cc/session/msg/page")
    public Result<PageVO<CcSessionMsgVO>> selectPage(@RequestBody CcSessionMsgPageDTO pageDTO) {
        return Result.success(ccSessionMsgService.selectPage(pageDTO));
    }

    /**
     * 功能描述:
     * 〈获取当前客服会话消息信息〉
     * @param sessionMsgId sessionMsgId
     * @return 正常返回:{@link Result<CcSessionMsgVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前客服会话消息信息")
    @GetMapping("/cc/session/msg/info/{sessionMsgId}")
    public Result<CcSessionMsgVO> getCcSessionMsgInfoById(@PathVariable("sessionMsgId")Long sessionMsgId) {
        CcSessionMsgVO ccSessionMsgVO = ccSessionMsgService.getCcSessionMsgById(sessionMsgId);
        return Result.success(ccSessionMsgVO);
    }

    /**
     * 功能描述:
     * 〈新增客服会话消息〉
     * @param ccSessionMsgDTO ccSessionMsgDTO
     * @return 正常返回:{@link Result<CcSessionMsgVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增客服会话消息")
    @Log(moduleName = "客服会话消息管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/cc/session/msg/add")
    public Result<CcSessionMsgVO> addCcSessionMsg(@Validated @RequestBody CcSessionMsgDTO ccSessionMsgDTO) {
        return Result.success(ccSessionMsgService.addCcSessionMsg(ccSessionMsgDTO));
    }

    /**
     * 功能描述:
     * 〈修改客服会话消息〉
     * @param ccSessionMsgDTO ccSessionMsgDTO
     * @return 正常返回:{@link Result<CcSessionMsgVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "修改客服会话消息")
    @Log(moduleName = "客服会话消息管理", operateType = OperateTypeEnum.UPDATE)
    @PostMapping("/cc/session/msg/edit")
    public Result<CcSessionMsgVO> editCcSessionMsg(@Validated @RequestBody CcSessionMsgDTO ccSessionMsgDTO) {
        return Result.success(ccSessionMsgService.editCcSessionMsg(ccSessionMsgDTO));
    }

    /**
     * 功能描述:
     * 〈删除客服会话消息〉
     * @param sessionMsgId sessionMsgId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除客服会话消息")
    @Log(moduleName = "客服会话消息管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/cc/session/msg/delete/{sessionMsgId}")
    public Result<Boolean> deleteCcSessionMsg(@PathVariable(value = "sessionMsgId",required = false)Long sessionMsgId) {
        return Result.success(ccSessionMsgService.deleteCcSessionMsg(sessionMsgId));
    }
}
