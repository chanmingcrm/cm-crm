package com.platform.mesh.upms.biz.modules.msg.base.api;

import cn.hutool.core.bean.BeanUtil;
import com.platform.mesh.core.application.controller.BaseController;
import com.platform.mesh.upms.api.modules.msg.domain.bo.MsgBaseBO;
import com.platform.mesh.upms.biz.modules.msg.base.domain.dto.MsgBaseDTO;
import com.platform.mesh.upms.biz.modules.msg.base.service.IMsgBaseService;
import com.platform.mesh.utils.result.Result;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 约定当前controller 只引入当前service
 * @description 消息信息
 * @author 蝉鸣
 */
@Hidden
@Tag(description = "MsgBaseApi", name = "消息")
@RestController
public class MsgBaseApi extends BaseController{
    @Autowired
    private IMsgBaseService msgBaseService;

    /**
     * 功能描述:
     * 〈发送站内消息〉
     * @param msgBaseBO msgBaseBO
     * @return 正常返回:{@link Result<Boolean>}
     * @author 蝉鸣
     */
    @Operation(summary = "发送消息")
    @PostMapping(value = "/api/sys/send/msg")
    Result<Boolean> sendMsg(@RequestBody MsgBaseBO msgBaseBO){
        MsgBaseDTO baseDTO = BeanUtil.copyProperties(msgBaseBO, MsgBaseDTO.class);
        return Result.success(msgBaseService.addBase(baseDTO));
    }

}