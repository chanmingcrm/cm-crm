package com.platform.mesh.upms.biz.modules.msg.base.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.biz.modules.msg.base.domain.dto.MsgBaseDTO;
import com.platform.mesh.upms.biz.modules.msg.base.domain.dto.MsgBasePageDTO;
import com.platform.mesh.upms.biz.modules.msg.base.domain.vo.MsgBaseVO;
import com.platform.mesh.upms.biz.modules.msg.base.service.IMsgBaseService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 约定当前controller 只引入当前service
 * @description 消息信息
 * @author 蝉鸣
 */
@Tag(description = "MsgBaseController", name = "消息")
@RestController
@RequestMapping
public class MsgBaseController extends BaseController{

    @Autowired
    private IMsgBaseService msgBaseService;

    /**
	 * 功能描述:
	 * 〈获取消息列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<MsgBaseVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取消息分页")
	@PostMapping("/msg/base/page")
	public Result<PageVO<MsgBaseVO>> selectPage(@RequestBody MsgBasePageDTO pageDTO) {
        PageVO<MsgBaseVO> voPage = msgBaseService.selectPage(pageDTO);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前消息信息〉
     * @param baseId baseId
     * @return 正常返回:{@link Result<MsgBaseVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前消息信息")
    @GetMapping("/msg/base/info/{baseId}")
    public Result<MsgBaseVO> getBaseInfoById(@PathVariable("baseId")Long baseId) {
        MsgBaseVO msgBaseVO = msgBaseService.getBaseInfoById(baseId);
        return Result.success(msgBaseVO);
    }

    /**
     * 功能描述:
     * 〈新增消息〉
     * @param baseDTO baseDTO
     * @return 正常返回:{@link Result<MsgBaseVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增消息")
    @Log(moduleName = "消息管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/msg/base/add")
    public Result<Boolean> addBase(@Validated @RequestBody MsgBaseDTO baseDTO) {
        return Result.success(msgBaseService.addBase(baseDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除消息〉
     * @param baseId baseId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除消息")
    @Log(moduleName = "消息管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/msg/base/delete/{baseId}")
    public Result<Boolean> deleteBase(@PathVariable(value = "baseId",required = false)Long baseId) {
        return Result.success(msgBaseService.deleteBase(baseId));
    }

}