package com.platform.mesh.upms.biz.modules.msg.userrel.controller;

import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.core.application.domain.vo.PageVO;
import com.platform.mesh.mybatis.plus.extention.MPage;
import com.platform.mesh.mybatis.plus.utils.MPageUtil;
import com.platform.mesh.upms.biz.modules.msg.userrel.domain.vo.UnReadVO;
import com.platform.mesh.upms.biz.modules.msg.userrel.domain.dto.MsgReadDTO;
import com.platform.mesh.upms.biz.modules.msg.userrel.domain.dto.MsgUserRelPageDTO;
import com.platform.mesh.upms.biz.modules.msg.userrel.domain.vo.MsgUserRelVO;
import com.platform.mesh.upms.biz.modules.msg.userrel.service.IMsgUserRelService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 约定当前controller 只引入当前service
 * @description 消息接收信息
 * @author 蝉鸣
 */
@Tag(description = "MsgUserRelController", name = "消息接收")
@RestController
@RequestMapping
public class MsgUserRelController extends BaseController{
    @Autowired
    private IMsgUserRelService msgUserRelService;

    /**
	 * 功能描述:
	 * 〈获取消息接收列表〉
	 * @param pageDTO pageDTO
	 * @return 正常返回:{@link Result<MPage<MsgUserRelVO>>}
	 * @author 蝉鸣
	 */
	@Operation(summary = "获取消息接收分页")
	@PostMapping("/msg/user/rel/page")
	public Result<PageVO<MsgUserRelVO>> selectPage(@RequestBody MsgUserRelPageDTO pageDTO) {
        MPage<MsgUserRelVO> page = msgUserRelService.selectPage(pageDTO);
        PageVO<MsgUserRelVO> voPage = MPageUtil.convertToVO(page, MsgUserRelVO.class);
        return Result.success(voPage);
	}

    /**
     * 功能描述:
     * 〈已读消息〉
     * @param msgReadDTO msgReadDTO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "已读消息")
    @PostMapping("/msg/user/rel/read")
    public Result<Boolean> readUserMsg(@Validated @RequestBody MsgReadDTO msgReadDTO) {
        return Result.success(msgUserRelService.readUserMsg(msgReadDTO));
    }

    /**
     * 功能描述:
     * 〈未读消息数量角标〉
     * @return 正常返回:{@link Result<List<UnReadVO>>}
     * @author 蝉鸣
     */
    @Operation(summary = "未读消息数量角标")
    @PostMapping("/msg/user/unread/count")
    public Result<List<UnReadVO>> countUnReadBase() {
        return Result.success(msgUserRelService.countUnReadBase());
    }

}