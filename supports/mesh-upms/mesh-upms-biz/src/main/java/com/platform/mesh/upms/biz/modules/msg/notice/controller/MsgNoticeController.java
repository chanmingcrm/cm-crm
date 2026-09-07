package com.platform.mesh.upms.biz.modules.msg.notice.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.core.enums.custom.OperateTypeEnum;
import com.platform.mesh.log.annotation.Log;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.upms.biz.modules.msg.notice.domain.dto.MsgNoticeDTO;
import com.platform.mesh.upms.biz.modules.msg.notice.domain.dto.MsgNoticePageDTO;
import com.platform.mesh.upms.biz.modules.msg.notice.domain.vo.MsgNoticeVO;
import com.platform.mesh.upms.biz.modules.msg.notice.service.IMsgNoticeService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 约定当前controller 只引入当前service
 * @description 消息提醒
 * @author 蝉鸣
 */
@Tag(description = "MsgNoticeController", name = "消息提醒")
@RestController
@RequestMapping
public class MsgNoticeController extends BaseController{
    @Autowired
    private IMsgNoticeService msgNoticeService;

    /**
	 * 功能描述:
	 * 〈获取消息提醒列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<MsgNoticeVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取消息分页")
	@PostMapping("/msg/notice/page")
	public Result<PageVO<MsgNoticeVO>> selectPage(@RequestBody MsgNoticePageDTO pageDTO) {
        PageVO<MsgNoticeVO> voPage = msgNoticeService.selectPage(pageDTO);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈获取当前消息提醒信息〉
     * @param dataId dataId
     * @return 正常返回:{@link Result<MsgNoticeVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "获取当前消息信息")
    @GetMapping("/msg/notice/info/{dataId}")
    public Result<MsgNoticeVO> getBaseInfoById(@PathVariable("dataId")Long dataId) {
        MsgNoticeVO msgNoticeVO = msgNoticeService.getNoticeInfoById(dataId);
        return Result.success(msgNoticeVO);
    }

    /**
     * 功能描述:
     * 〈新增消息提醒〉
     * @param baseDTO baseDTO
     * @return 正常返回:{@link Result<MsgNoticeVO>}
     * @author 蝉鸣
     */
    @Operation(summary = "新增消息提醒")
    @Log(moduleName = "消息提醒管理", operateType = OperateTypeEnum.INSERT)
    @PostMapping("/msg/notice/add")
    public Result<Boolean> addBase(@Validated @RequestBody MsgNoticeDTO baseDTO) {
        return Result.success(msgNoticeService.addNotice(baseDTO));
    }
    
   /**
     * 功能描述:
     * 〈删除消息提醒〉
     * @param dataId dataId
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "删除消息提醒")
    @Log(moduleName = "消息提醒管理", operateType = OperateTypeEnum.DELETE)
    @PostMapping("/msg/notice/delete/{dataId}")
    public Result<Boolean> deleteBase(@PathVariable(value = "dataId",required = false)Long dataId) {
        return Result.success(msgNoticeService.deleteNotice(dataId));
    }

    /**
     * 功能描述:
     * 〈定时执行消息提醒〉
     * @author 蝉鸣
     */
    @Operation(summary = "执行消息提醒")
    @PostMapping(value = "/sys/msg/notice/handle")
    public void handleNotice(){
        msgNoticeService.handleNotice();
    }
}